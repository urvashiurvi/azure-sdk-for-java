/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * jkl
 */

package com.microsoft.azure.management.storagecache.v2020_03_01.implementation;

import com.microsoft.azure.arm.model.implementation.WrapperImpl;
import com.microsoft.azure.management.storagecache.v2020_03_01.UsageModels;
import rx.Observable;
import rx.functions.Func1;
import com.microsoft.azure.PagedList;
import com.microsoft.azure.Page;
import com.microsoft.azure.arm.utils.PagedListConverter;
import com.microsoft.azure.management.storagecache.v2020_03_01.UsageModel;

class UsageModelsImpl extends WrapperImpl<UsageModelsInner> implements UsageModels {
    private PagedListConverter<UsageModelInner, UsageModel> converter;
    private final StorageCacheManager manager;

    UsageModelsImpl(StorageCacheManager manager) {
        super(manager.inner().usageModels());
        this.manager = manager;
        this.converter = new PagedListConverter<UsageModelInner, UsageModel>() {
            @Override
            public Observable<UsageModel> typeConvertAsync(UsageModelInner inner) {
                return Observable.just((UsageModel) wrapModel(inner));
            }
        };
    }

    public StorageCacheManager manager() {
        return this.manager;
    }

    private UsageModelImpl wrapModel(UsageModelInner inner) {
        return  new UsageModelImpl(inner, manager());
    }

    @Override
    public PagedList<UsageModel> list() {
        UsageModelsInner client = this.inner();
        return converter.convert(client.list());
    }

    @Override
    public Observable<UsageModel> listAsync() {
        UsageModelsInner client = this.inner();
        return client.listAsync()
        .flatMapIterable(new Func1<Page<UsageModelInner>, Iterable<UsageModelInner>>() {
            @Override
            public Iterable<UsageModelInner> call(Page<UsageModelInner> page) {
                return page.items();
            }
        })
        .map(new Func1<UsageModelInner, UsageModel>() {
            @Override
            public UsageModel call(UsageModelInner inner) {
                return wrapModel(inner);
            }
        });
    }

}
