/*
 * Copyright Microsoft Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.microsoft.azure.storage.blob;

import io.reactivex.*;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import javafx.util.Pair;

import java.io.IOException;
import java.lang.Error;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

import static java.lang.StrictMath.toIntExact;

/**
 * This class contains a collection of methods (and structures associated with those methods) which perform higher-level
 * operations. Whereas operations on the URL types guarantee a single REST request and make no assumptions on desired
 * behavior, these methods will often compose several requests to provide a convenient way of performing more complex
 * operations. Further, we will make our own assumptions and optimizations for common cases that may not be ideal for
 * rarer cases.
 */
public class TransferManager {

    /**
     * Configures the parallel upload behavior for methods on the {@code TransferManager}.
     */
    public static class UploadToBlockBlobOptions {

        /**
         * An object which represents the default parallel upload options.
         */
        public static final UploadToBlockBlobOptions DEFAULT = new UploadToBlockBlobOptions(null,
                null, null, null, null);

        private IProgressReceiver progressReceiver;

        private BlobHTTPHeaders httpHeaders;

        private Metadata metadata;

        private BlobAccessConditions accessConditions;

        private int parallelism;

        /**
         * Creates a new object that configures the parallel upload behavior. Null may be passed to accept the default
         * behavior.
         *
         * @param progressReceiver
         *         An object that implements the {@link IProgressReceiver} interface which will be invoked periodically
         *         as bytes are sent in a PutBlock call to the BlockBlobURL. May be null if no progress reports are
         *         desired.
         * @param httpHeaders
         *         {@link BlobHTTPHeaders}
         * @param metadata
         *         {@link Metadata}
         * @param accessConditions
         *         {@link BlobAccessConditions}
         * @param parallelism
         *         A {@code int} that indicates the maximum number of blocks to upload in parallel. Must be greater than
         *         0. May be null to accept default behavior.
         */
        public UploadToBlockBlobOptions(IProgressReceiver progressReceiver, BlobHTTPHeaders httpHeaders,
                Metadata metadata, BlobAccessConditions accessConditions, Integer parallelism) {
            if (parallelism == null) {
                this.parallelism = 5;
            } else if (parallelism <= 0) {
                throw new IllegalArgumentException("Parallelism must be > 0");
            } else {
                this.parallelism = parallelism;
            }

            this.progressReceiver = progressReceiver;
            this.httpHeaders = httpHeaders;
            this.metadata = metadata;
            this.accessConditions = accessConditions == null ? BlobAccessConditions.NONE : accessConditions;
        }
    }

    /**
     * The default size of a download chunk for download large blobs.
     */
    public static final int BLOB_DEFAULT_DOWNLOAD_BLOCK_SIZE = 4 * Constants.MB;

    /**
     * Configures the parallel download behavior for methods on the {@code TransferManager}.
     */
    public static final class DownloadFromBlobOptions {

        /**
         * The default download options.
         */
        public static final DownloadFromBlobOptions DEFAULT = new DownloadFromBlobOptions(null,
                null, null, null, null);

        private int blockSize;

        private IProgressReceiver progressReceiver;

        private BlobAccessConditions accessConditions;

        private int parallelism;

        private RetryReaderOptions retryReaderOptionsPerBlock;

        /**
         * Returns an object that configures the parallel download behavior for methods on the {@code TransferManager}.
         *
         * @param blockSize
         *         The size of the chunk into which large download operations will be broken into. These methods operate
         *         on {@code ByteBuffer}s and {@code ByteBuffer}s only support {@code int} for their size, so only chunk
         *         sizes of up to 2^31 can be processed.
         * @param progressReceiver
         *         {@link IProgressReceiver}
         * @param accessConditions
         *         {@link BlobAccessConditions}
         * @param parallelism
         *         A {@code int} that indicates the maximum number of blocks to upload in parallel. Must be greater than
         *         0. May be null to accept default behavior.
         * @param retryReaderOptions
         *         {@link RetryReaderOptions}
         */
        public DownloadFromBlobOptions(Integer blockSize, IProgressReceiver progressReceiver,
                BlobAccessConditions accessConditions, Integer parallelism, RetryReaderOptions retryReaderOptions) {
            if (blockSize != null) {
                Utility.assertInBounds("blockSize", blockSize, 1, Long.MAX_VALUE);
                this.blockSize = blockSize;
            } else {
                this.blockSize = TransferManager.BLOB_DEFAULT_DOWNLOAD_BLOCK_SIZE;
            }

            if (parallelism != null) {
                Utility.assertInBounds("parallelism", parallelism, 1, Integer.MAX_VALUE);
                this.parallelism = parallelism;
            } else {
                this.parallelism = 5;
            }

            this.accessConditions = accessConditions == null ? BlobAccessConditions.NONE : accessConditions;
            this.progressReceiver = progressReceiver;
            this.retryReaderOptionsPerBlock = retryReaderOptions == null ?
                    new RetryReaderOptions() : retryReaderOptions;
        }
    }

    /**
     * Uploads the contents of a file to a block blob in parallel, breaking it into block-size chunks if necessary.
     *
     * @apiNote
     * ## Sample Code \n
     * [!code-java[Sample_Code](../azure-storage-java/src/test/java/com/microsoft/azure/storage/Samples.java?name=tm_file "Sample code for TransferManager.uploadFileToBlockBlob")] \n
     * For more samples, please see the [Samples file](https://github.com/Azure/azure-storage-java/blob/New-Storage-SDK-V10-Preview/src/test/java/com/microsoft/azure/storage/Samples.java)
     *
     * @param file
     *         The file to upload.
     * @param blockBlobURL
     *         Points to the blob to which the data should be uploaded.
     * @param blockLength
     *         If the data must be broken up into blocks, this value determines what size those blocks will be. This
     *         will affect the total number of service requests made. This value will be ignored if the data can be
     *         uploaded in a single put-blob operation. Must be between 1 and
     *         {@link BlockBlobURL#MAX_STAGE_BLOCK_BYTES}. Note as well that {@code fileLength/blockLength} must be less
     *         than or equal to {@link BlockBlobURL#MAX_BLOCKS}.
     * @param options
     *         {@link UploadToBlockBlobOptions}
     * @return Emits the successful response.
     */
    public static Single<CommonRestResponse> uploadFileToBlockBlob(
            final FileChannel file, final BlockBlobURL blockBlobURL, final int blockLength,
            final UploadToBlockBlobOptions options) {
        Utility.assertNotNull("file", file);
        Utility.assertNotNull("blockBlobURL", blockBlobURL);
        Utility.assertInBounds("blockLength", blockLength, 1, BlockBlobURL.MAX_STAGE_BLOCK_BYTES);
        UploadToBlockBlobOptions optionsReal = options == null ? UploadToBlockBlobOptions.DEFAULT : options;

        try {
            // If the size of the file can fit in a single upload, do it this way.
            if (file.size() < BlockBlobURL.MAX_PUT_BLOB_BYTES) {
                return doSingleShotUpload(
                        Flowable.just(file.map(FileChannel.MapMode.READ_ONLY, 0, file.size())), file.size(),
                        blockBlobURL, optionsReal);
            }

            int numBlocks = calculateNumBlocks(file.size(), blockLength);
            return Observable.range(0, numBlocks)
                    .map((Function<Integer, ByteBuffer>) i -> {
                        /*
                        The docs say that the result of mapping a region which is not entirely contained by the file
                        is undefined, so we must be precise with the last block size.
                         */
                        int count = Math.min(blockLength, (int) (file.size() - i * blockLength));
                        // Memory map the file to get a ByteBuffer to an in memory portion of the file.
                        MappedByteBuffer buf = file.map(FileChannel.MapMode.READ_ONLY, i * blockLength, count);
                        buf.load();
                        return buf;
                    })
                    // Gather all of the buffers, in order, into this list, which will become the block list.
                    .collectInto(new ArrayList<>(numBlocks),
                            (BiConsumer<ArrayList<ByteBuffer>, ByteBuffer>) ArrayList::add)
                    // Turn the list into a call to uploadByteBuffersToBlockBlob and return that result.
                    .flatMap(blocks ->
                            uploadByteBuffersToBlockBlob(blocks, blockBlobURL, optionsReal));
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static int calculateNumBlocks(long dataSize, int blockLength) {
        // Can successfully cast to an int because MaxBlockSize is an int, which this expression must be less than.
        int numBlocks = toIntExact(dataSize / blockLength);
        // Include an extra block for trailing data.
        if (dataSize % blockLength != 0) {
            numBlocks++;
        }
        return numBlocks;
    }

    /**
     * Uploads a large ByteBuffer to a block blob in parallel, breaking it up into block-size chunks if necessary.
     *
     * @apiNote
     * ## Sample Code \n
     * [!code-java[Sample_Code](../azure-storage-java/src/test/java/com/microsoft/azure/storage/Samples.java?name=tm_buffer "Sample code for TransferManager.uploadByteBufferToBlockBlob")] \n
     * For more samples, please see the [Samples file](https://github.com/Azure/azure-storage-java/blob/New-Storage-SDK-V10-Preview/src/test/java/com/microsoft/azure/storage/Samples.java)
     *
     * @param data
     *         The buffer to upload.
     * @param blockBlobURL
     *         A {@link BlockBlobURL} that points to the blob to which the data should be uploaded.
     * @param blockLength
     *         If the data must be broken up into blocks, this value determines what size those blocks will be. This
     *         will affect the total number of service requests made. This value will be ignored if the data can be
     *         uploaded in a single put-blob operation.
     * @param options
     *         {@link UploadToBlockBlobOptions}
     * @return Emits the successful response.
     */
    public static Single<CommonRestResponse> uploadByteBufferToBlockBlob(
            final ByteBuffer data, final BlockBlobURL blockBlobURL, final int blockLength,
            final UploadToBlockBlobOptions options) {
        Utility.assertNotNull("data", data);
        Utility.assertNotNull("blockBlobURL", blockBlobURL);
        Utility.assertInBounds("blockLength", blockLength, 1, BlockBlobURL.MAX_STAGE_BLOCK_BYTES);
        UploadToBlockBlobOptions optionsReal = options == null ? UploadToBlockBlobOptions.DEFAULT : options;

        // If the size of the buffer can fit in a single upload, do it this way.
        if (data.remaining() < BlockBlobURL.MAX_PUT_BLOB_BYTES) {
            return doSingleShotUpload(Flowable.just(data), data.remaining(), blockBlobURL, optionsReal);
        }

        int numBlocks = calculateNumBlocks(data.remaining(), blockLength);

        return Observable.range(0, numBlocks)
                .map(i -> {
                    int count = Math.min(blockLength, data.remaining() - i * blockLength);
                    ByteBuffer block = data.duplicate();
                    block.position(i * blockLength);
                    block.limit(i * blockLength + count);
                    return block;
                })
                .collectInto(new ArrayList<>(numBlocks),
                        (BiConsumer<ArrayList<ByteBuffer>, ByteBuffer>) ArrayList::add)
                .flatMap(blocks -> uploadByteBuffersToBlockBlob(blocks, blockBlobURL, optionsReal));
    }

    /**
     * Uploads an iterable of {@code ByteBuffers} to a block blob. The data will first data will first be examined to
     * check the size and validate the number of blocks. If the total amount of data in all the buffers is small enough
     * (i.e. less than or equal to {@link BlockBlobURL#MAX_PUT_BLOB_BYTES}, this method will perform a single upload
     * operation. Otherwise, each {@code ByteBuffer} in the iterable is assumed to be its own discreet block of data for
     * the block blob and will be uploaded as such. Note that in this case, each ByteBuffer must be less than or equal
     * to {@link BlockBlobURL#MAX_STAGE_BLOCK_BYTES}. Note as well that there can only be up to
     * {@link BlockBlobURL#MAX_BLOCKS} ByteBuffers in the list.
     *
     * @apiNote
     * ## Sample Code \n
     * [!code-java[Sample_Code](../azure-storage-java/src/test/java/com/microsoft/azure/storage/Samples.java?name=tm_buffers "Sample code for TransferManager.uploadByteBuffersToBlockBlob")] \n
     * For more samples, please see the [Samples file](https://github.com/Azure/azure-storage-java/blob/New-Storage-SDK-V10-Preview/src/test/java/com/microsoft/azure/storage/Samples.java)
     *
     * @apiNote
     * ## Sample Code \n
     * [!code-java[Sample_Code](../azure-storage-java/src/test/java/com/microsoft/azure/storage/Samples.java?name=tm_buffers "Sample code for TransferManager.uploadByteBuffersToBlockBlob")]\n
     * For more samples, please see the [Samples file] (https://github.com/Azure/azure-storage-java/blob/New-Storage-SDK-V10-Preview/src/test/java/com/microsoft/azure/storage/Samples.java)
     *
     * @param data
     *         The data to upload.
     * @param blockBlobURL
     *         A {@link BlockBlobURL} that points to the blob to which the data should be uploaded.
     * @param options
     *         {@link UploadToBlockBlobOptions}
     * @return Emits the successful response.
     */
    public static Single<CommonRestResponse> uploadByteBuffersToBlockBlob(
            final Iterable<ByteBuffer> data, final BlockBlobURL blockBlobURL,
            final UploadToBlockBlobOptions options) {
        Utility.assertNotNull("data", data);
        Utility.assertNotNull("blockBlobURL", blockBlobURL);
        UploadToBlockBlobOptions optionsReal = options == null ? UploadToBlockBlobOptions.DEFAULT : options;

        // Determine the size of the blob and the number of blocks
        long size = 0;
        int numBlocks = 0;
        for (ByteBuffer b : data) {
            size += b.remaining();
            numBlocks++;
        }

        // If the size can fit in 1 upload call, do it this way.
        if (size <= BlockBlobURL.MAX_PUT_BLOB_BYTES) {
            return doSingleShotUpload(Flowable.fromIterable(data), size, blockBlobURL, optionsReal);
        }

        if (numBlocks > BlockBlobURL.MAX_BLOCKS) {
            throw new IllegalArgumentException(SR.BLOB_OVER_MAX_BLOCK_LIMIT);
        }

        // Generate an observable that emits items which are the ByteBuffers in the provided Iterable.
        return Observable.fromIterable(data)
                /*
                 For each ByteBuffer, make a call to stageBlock as follows. concatMap ensures that the items
                 emitted by this Observable are in the same sequence as they are begun, which will be important for
                 composing the list of Ids later.
                 */
                .concatMapEager(blockData -> {
                    if (blockData.remaining() > BlockBlobURL.MAX_STAGE_BLOCK_BYTES) {
                        throw new IllegalArgumentException(SR.INVALID_BLOCK_SIZE);
                    }

                    // TODO: progress

                    final String blockId = Base64.getEncoder().encodeToString(
                            UUID.randomUUID().toString().getBytes());

                    /*
                     Make a call to stageBlock. Instead of emitting the response, which we don't care about other than
                     that it was successful, emit the blockId for this request. These will be collected below. Turn that
                     into an Observable which emits one item to comply with the signature of concatMapEager.
                     */
                    return blockBlobURL.stageBlock(blockId, Flowable.just(blockData),
                            blockData.remaining(), optionsReal.accessConditions.getLeaseAccessConditions())
                            .map(x -> blockId).toObservable();

                /*
                 Specify the number of concurrent subscribers to this map. This determines how many concurrent rest
                 calls are made. This is so because maxConcurrency is the number of internal subscribers available to
                 subscribe to the Observables emitted by the source. A subscriber is not released for a new subscription
                 until its Observable calls onComplete, which here means that the call to stageBlock is finished.
                 Prefetch is a hint that each of the Observables emitted by the source will emit only one value,
                 which is true here because we have converted from a Single.
                 */

                }, optionsReal.parallelism, 1)
                /*
                collectInto will gather each of the emitted blockIds into a list. Because we used concatMap, the Ids
                will be emitted according to their block number, which means the list generated here will be properly
                ordered. This also converts into a Single.
                */
                .collectInto(new ArrayList<>(numBlocks), (BiConsumer<ArrayList<String>, String>) ArrayList::add)
                /*
                collectInto will not emit the list until its source calls onComplete. This means that by the time we
                call stageBlock list, all of the stageBlock calls will have finished. By flatMapping the list, we can
                "map" it into a call to commitBlockList.
                 */
                .flatMap(ids ->
                        blockBlobURL.commitBlockList(ids, optionsReal.httpHeaders, optionsReal.metadata,
                                optionsReal.accessConditions))
                /*
                Finally, we must turn the specific response type into a CommonRestResponse by mapping.
                 */
                .map(CommonRestResponse::createFromPutBlockListResponse);

    }

    private static Single<CommonRestResponse> doSingleShotUpload(
            Flowable<ByteBuffer> data, long size, BlockBlobURL blockBlobURL, UploadToBlockBlobOptions options) {
        if (options.progressReceiver != null) {
            // TODO: Wrap in a progress stream once progress is written.
        }

        // Transform the specific RestResponse into a CommonRestResponse.
        return blockBlobURL.upload(data, size, options.httpHeaders,
                options.metadata, options.accessConditions)
                .map(CommonRestResponse::createFromPutBlobResponse);
    }

    /**
     * Downloads a file directly into a file, splitting the download into chunks and parallelizing as necessary.
     *
     * @apiNote
     * ## Sample Code \n
     * [!code-java[Sample_Code](../azure-storage-java/src/test/java/com/microsoft/azure/storage/Samples.java?name=tm_file "Sample code for TransferManager.downloadBlobToFile")] \n
     * For more samples, please see the [Samples file] (https://github.com/Azure/azure-storage-java/blob/New-Storage-SDK-V10-Preview/src/test/java/com/microsoft/azure/storage/Samples.java)
     *
     * @param file
     *      The destination file to which the blob will be written.
     * @param blobURL
     *         The URL to the blob to download.
     * @param range
     *         {@link BlobRange}
     * @param options
     *         {@link DownloadFromBlobOptions}
     * @return A {@code Completable} that will signal when the download is complete.
     */
    public static Completable downloadBlobToFile(FileChannel file, BlobURL blobURL, BlobRange range,
            DownloadFromBlobOptions options) {
        BlobRange r = range == null ? BlobRange.DEFAULT : range;
        DownloadFromBlobOptions o = options == null ? DownloadFromBlobOptions.DEFAULT : options;
        Utility.assertNotNull("blobURL", blobURL);
        Utility.assertNotNull("file", file);

        Single<Pair<Long, BlobAccessConditions>> dataSizeSingle = getSetupSingle(blobURL, r, o);

        return dataSizeSingle.flatMapCompletable(setupPair -> {
            Long dataSize = setupPair.getKey();
            o.accessConditions = setupPair.getValue();
            /*
            A single MappedByteBuffer can only be of size up to maxInt. It is therefore possible that we will need to
            use several buffers to get all the data into the file. We want to make as few of these as possible, so
             */
            int numBuffers = calculateNumBlocks(dataSize, Integer.MAX_VALUE);

            return Observable.range(0, numBuffers)
                    .flatMapCompletable(i -> {
                        long bufferSizeActual = Math.min(Integer.MAX_VALUE, dataSize - (long)i * Integer.MAX_VALUE);
                        MappedByteBuffer block = file.map(FileChannel.MapMode.READ_WRITE, i * Integer.MAX_VALUE,
                                bufferSizeActual);
                        block.load();

                        return downloadBlobToBuffer(block, blobURL, new BlobRange(
                                i * Integer.MAX_VALUE + r.getOffset(),
                                bufferSizeActual), o)
                                .doFinally(block::force);
                    });
        });
    }

    /**
     * Downloads a blob directly into a buffer, splitting the download into chunks and parallelizing as necessary.
     *
     * Note that only blobs of 2GB or less can be downloaded using this method due to the size constrains on {@code
     * ByteBuffer}. For downloading larger blobs, please see {@link TransferManager#downloadBlobToFile}.
     *
     * @apiNote
     * ## Sample Code \n
     * [!code-java[Sample_Code](../azure-storage-java/src/test/java/com/microsoft/azure/storage/Samples.java?name=tm_buffer_download "Sample code for TransferManager.downloadBlobToBuffer")] \n
     * For more samples, please see the [Samples file] (https://github.com/Azure/azure-storage-java/blob/New-Storage-SDK-V10-Preview/src/test/java/com/microsoft/azure/storage/Samples.java)
     *
     * @param buffer
     *         The destination buffer to which the blob data will be written.
     * @param blobURL
     *         The URL to the blob to download.
     * @param range
     *         {@link BlobRange}
     * @param options
     *         {@link DownloadFromBlobOptions}
     * @return A {@code Completable} that will signal when the download is complete.
     */
    public static Completable downloadBlobToBuffer(ByteBuffer buffer, BlobURL blobURL, BlobRange range,
            DownloadFromBlobOptions options) {
        BlobRange r = range == null ? BlobRange.DEFAULT : range;
        DownloadFromBlobOptions o = options == null ? DownloadFromBlobOptions.DEFAULT : options;
        Utility.assertNotNull("blobURL", blobURL);
        Utility.assertNotNull("buffer", buffer);

        Single<Pair<Long, BlobAccessConditions>> dataSizeSingle = getSetupSingle(blobURL, r, o);

        return dataSizeSingle.flatMapCompletable(setupPair -> {
            Long dataSize = setupPair.getKey();
            BlobAccessConditions realConditions = setupPair.getValue();
            if (buffer.remaining() < dataSize) {
                throw new IllegalArgumentException("The buffer's remaining size should be greater " +
                        "than or equal to the request dataSize of bytes: " + dataSize);
            }

            int numBlocks = calculateNumBlocks(dataSize, o.blockSize);

            // For each block, we will download a corresponding part of the blob and write it to the buffer.
            return Observable.range(0, numBlocks)
                    .flatMap(i -> {
                        /*
                        Setup a window of the buffer which is scoped to this particular block download. We can safely
                        call dataSize.intValue because if dataSize were a long, it would have exceeded the
                        size of the buffer and thrown above.
                         */
                        int blockSizeActual = Math.min(o.blockSize, dataSize.intValue() - i * o.blockSize);
                        ByteBuffer block = buffer.duplicate();
                        block.position(i * o.blockSize);
                        block.limit(i * o.blockSize + blockSizeActual);

                        // Make the download call.
                        BlobRange blockRange = new BlobRange(r.getOffset() + (i * o.blockSize),
                                (long) blockSizeActual);
                        return blobURL.download(blockRange, realConditions, false)
                                // Extract the body.
                                .flatMapObservable(response ->
                                        response.body(o.retryReaderOptionsPerBlock)
                                                /*
                                                For each buffer emitted, write it into the user-provided buffer. The
                                                buffers are emitted from the Flowable in order, and a call to put
                                                advances the position of both buffers, so we can simply call put without
                                                any bookkeeping.
                                                 */
                                                .map(block::put)
                                                // We must satisfy the return type.
                                                .toObservable());
                    }, o.parallelism)
                    // We don't care for any return values, so we transform to a Completable.
                    .ignoreElements();
        });
    }

    private static Single<Pair<Long, BlobAccessConditions>> getSetupSingle(BlobURL blobURL, BlobRange r,
            DownloadFromBlobOptions o) {
        /*
        Construct a Single which will emit the total count of bytes to be downloaded and retrieve an etag to lock on to
        if one was not specified. We use a single for this because we may have to make a REST call to get the length to
        calculate the count and we need to maintain asynchronicity.
         */
        if (r.getCount() == null || o.accessConditions.getHttpAccessConditions().getIfMatch() == ETag.NONE) {
            return blobURL.getProperties(o.accessConditions)
                    .map(response -> {
                        BlobAccessConditions newConditions;
                        if (o.accessConditions.getHttpAccessConditions().getIfMatch() == ETag.NONE) {
                            newConditions = new BlobAccessConditions(
                                    new HTTPAccessConditions(
                                            o.accessConditions.getHttpAccessConditions().getIfModifiedSince(),
                                            o.accessConditions.getHttpAccessConditions().getIfModifiedSince(),
                                            new ETag(response.headers().eTag()),
                                            o.accessConditions.getHttpAccessConditions().getIfNoneMatch()),
                                    o.accessConditions.getLeaseAccessConditions(),
                                    o.accessConditions.getAppendBlobAccessConditions(),
                                    o.accessConditions.getPageBlobAccessConditions());
                        }
                        else {
                            newConditions = o.accessConditions;
                        }
                        long newCount;
                        if (r.getCount() == null) {
                            newCount = response.headers().contentLength() - r.getOffset();
                        }
                        else {
                            newCount = r.getCount();
                        }
                        return new Pair<>(newCount, newConditions);
                    });
        } else {
            return Single.just(new Pair<>(r.getCount(), o.accessConditions));
        }
    }
}
