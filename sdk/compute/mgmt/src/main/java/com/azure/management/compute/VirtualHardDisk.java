// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.compute;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The VirtualHardDisk model. */
@Fluent
public final class VirtualHardDisk {
    /*
     * Specifies the virtual hard disk's uri.
     */
    @JsonProperty(value = "uri")
    private String uri;

    /**
     * Get the uri property: Specifies the virtual hard disk's uri.
     *
     * @return the uri value.
     */
    public String uri() {
        return this.uri;
    }

    /**
     * Set the uri property: Specifies the virtual hard disk's uri.
     *
     * @param uri the uri value to set.
     * @return the VirtualHardDisk object itself.
     */
    public VirtualHardDisk withUri(String uri) {
        this.uri = uri;
        return this;
    }
}
