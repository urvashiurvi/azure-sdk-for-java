/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.kubernetesconfiguration.v2019_11_01_preview.implementation;

import retrofit2.Retrofit;
import com.google.common.reflect.TypeToken;
import com.microsoft.azure.AzureServiceFuture;
import com.microsoft.azure.CloudException;
import com.microsoft.azure.ListOperationCallback;
import com.microsoft.azure.Page;
import com.microsoft.azure.PagedList;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import java.io.IOException;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;
import retrofit2.Response;
import rx.functions.Func1;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in Operations.
 */
public class OperationsInner {
    /** The Retrofit service to perform REST calls. */
    private OperationsService service;
    /** The service client containing this operation class. */
    private SourceControlConfigurationClientImpl client;

    /**
     * Initializes an instance of OperationsInner.
     *
     * @param retrofit the Retrofit instance built from a Retrofit Builder.
     * @param client the instance of the service client containing this operation class.
     */
    public OperationsInner(Retrofit retrofit, SourceControlConfigurationClientImpl client) {
        this.service = retrofit.create(OperationsService.class);
        this.client = client;
    }

    /**
     * The interface defining all the services for Operations to be
     * used by Retrofit to perform actually REST calls.
     */
    interface OperationsService {
        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.kubernetesconfiguration.v2019_11_01_preview.Operations list" })
        @GET("providers/Microsoft.KubernetesConfiguration/operations")
        Observable<Response<ResponseBody>> list(@Query("api-version") String apiVersion, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

        @Headers({ "Content-Type: application/json; charset=utf-8", "x-ms-logging-context: com.microsoft.azure.management.kubernetesconfiguration.v2019_11_01_preview.Operations listNext" })
        @GET
        Observable<Response<ResponseBody>> listNext(@Url String nextUrl, @Header("accept-language") String acceptLanguage, @Header("User-Agent") String userAgent);

    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param apiVersion The API version to be used with the HTTP request.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;ResourceProviderOperationInner&gt; object if successful.
     */
    public PagedList<ResourceProviderOperationInner> list(final String apiVersion) {
        ServiceResponse<Page<ResourceProviderOperationInner>> response = listSinglePageAsync(apiVersion).toBlocking().single();
        return new PagedList<ResourceProviderOperationInner>(response.body()) {
            @Override
            public Page<ResourceProviderOperationInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param apiVersion The API version to be used with the HTTP request.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<ResourceProviderOperationInner>> listAsync(final String apiVersion, final ListOperationCallback<ResourceProviderOperationInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listSinglePageAsync(apiVersion),
            new Func1<String, Observable<ServiceResponse<Page<ResourceProviderOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param apiVersion The API version to be used with the HTTP request.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ResourceProviderOperationInner&gt; object
     */
    public Observable<Page<ResourceProviderOperationInner>> listAsync(final String apiVersion) {
        return listWithServiceResponseAsync(apiVersion)
            .map(new Func1<ServiceResponse<Page<ResourceProviderOperationInner>>, Page<ResourceProviderOperationInner>>() {
                @Override
                public Page<ResourceProviderOperationInner> call(ServiceResponse<Page<ResourceProviderOperationInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param apiVersion The API version to be used with the HTTP request.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ResourceProviderOperationInner&gt; object
     */
    public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> listWithServiceResponseAsync(final String apiVersion) {
        return listSinglePageAsync(apiVersion)
            .concatMap(new Func1<ServiceResponse<Page<ResourceProviderOperationInner>>, Observable<ServiceResponse<Page<ResourceProviderOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> call(ServiceResponse<Page<ResourceProviderOperationInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
    ServiceResponse<PageImpl<ResourceProviderOperationInner>> * @param apiVersion The API version to be used with the HTTP request.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;ResourceProviderOperationInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> listSinglePageAsync(final String apiVersion) {
        if (apiVersion == null) {
            throw new IllegalArgumentException("Parameter apiVersion is required and cannot be null.");
        }
        return service.list(apiVersion, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ResourceProviderOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ResourceProviderOperationInner>> result = listDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ResourceProviderOperationInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ResourceProviderOperationInner>> listDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<ResourceProviderOperationInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<ResourceProviderOperationInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the PagedList&lt;ResourceProviderOperationInner&gt; object if successful.
     */
    public PagedList<ResourceProviderOperationInner> listNext(final String nextPageLink) {
        ServiceResponse<Page<ResourceProviderOperationInner>> response = listNextSinglePageAsync(nextPageLink).toBlocking().single();
        return new PagedList<ResourceProviderOperationInner>(response.body()) {
            @Override
            public Page<ResourceProviderOperationInner> nextPage(String nextPageLink) {
                return listNextSinglePageAsync(nextPageLink).toBlocking().single().body();
            }
        };
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @param serviceFuture the ServiceFuture object tracking the Retrofit calls
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<List<ResourceProviderOperationInner>> listNextAsync(final String nextPageLink, final ServiceFuture<List<ResourceProviderOperationInner>> serviceFuture, final ListOperationCallback<ResourceProviderOperationInner> serviceCallback) {
        return AzureServiceFuture.fromPageResponse(
            listNextSinglePageAsync(nextPageLink),
            new Func1<String, Observable<ServiceResponse<Page<ResourceProviderOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> call(String nextPageLink) {
                    return listNextSinglePageAsync(nextPageLink);
                }
            },
            serviceCallback);
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ResourceProviderOperationInner&gt; object
     */
    public Observable<Page<ResourceProviderOperationInner>> listNextAsync(final String nextPageLink) {
        return listNextWithServiceResponseAsync(nextPageLink)
            .map(new Func1<ServiceResponse<Page<ResourceProviderOperationInner>>, Page<ResourceProviderOperationInner>>() {
                @Override
                public Page<ResourceProviderOperationInner> call(ServiceResponse<Page<ResourceProviderOperationInner>> response) {
                    return response.body();
                }
            });
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
     * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the PagedList&lt;ResourceProviderOperationInner&gt; object
     */
    public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> listNextWithServiceResponseAsync(final String nextPageLink) {
        return listNextSinglePageAsync(nextPageLink)
            .concatMap(new Func1<ServiceResponse<Page<ResourceProviderOperationInner>>, Observable<ServiceResponse<Page<ResourceProviderOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> call(ServiceResponse<Page<ResourceProviderOperationInner>> page) {
                    String nextPageLink = page.body().nextPageLink();
                    if (nextPageLink == null) {
                        return Observable.just(page);
                    }
                    return Observable.just(page).concatWith(listNextWithServiceResponseAsync(nextPageLink));
                }
            });
    }

    /**
     * List all the available operations the KubernetesConfiguration resource provider supports.
     *
    ServiceResponse<PageImpl<ResourceProviderOperationInner>> * @param nextPageLink The NextLink from the previous successful call to List operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the PagedList&lt;ResourceProviderOperationInner&gt; object wrapped in {@link ServiceResponse} if successful.
     */
    public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> listNextSinglePageAsync(final String nextPageLink) {
        if (nextPageLink == null) {
            throw new IllegalArgumentException("Parameter nextPageLink is required and cannot be null.");
        }
        String nextUrl = String.format("%s", nextPageLink);
        return service.listNext(nextUrl, this.client.acceptLanguage(), this.client.userAgent())
            .flatMap(new Func1<Response<ResponseBody>, Observable<ServiceResponse<Page<ResourceProviderOperationInner>>>>() {
                @Override
                public Observable<ServiceResponse<Page<ResourceProviderOperationInner>>> call(Response<ResponseBody> response) {
                    try {
                        ServiceResponse<PageImpl<ResourceProviderOperationInner>> result = listNextDelegate(response);
                        return Observable.just(new ServiceResponse<Page<ResourceProviderOperationInner>>(result.body(), result.response()));
                    } catch (Throwable t) {
                        return Observable.error(t);
                    }
                }
            });
    }

    private ServiceResponse<PageImpl<ResourceProviderOperationInner>> listNextDelegate(Response<ResponseBody> response) throws CloudException, IOException, IllegalArgumentException {
        return this.client.restClient().responseBuilderFactory().<PageImpl<ResourceProviderOperationInner>, CloudException>newInstance(this.client.serializerAdapter())
                .register(200, new TypeToken<PageImpl<ResourceProviderOperationInner>>() { }.getType())
                .registerError(CloudException.class)
                .build(response);
    }

}
