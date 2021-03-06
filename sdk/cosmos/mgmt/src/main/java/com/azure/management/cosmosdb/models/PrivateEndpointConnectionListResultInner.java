// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.cosmosdb.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The PrivateEndpointConnectionListResult model. */
@Fluent
public final class PrivateEndpointConnectionListResultInner {
    /*
     * Array of private endpoint connections
     */
    @JsonProperty(value = "value")
    private List<PrivateEndpointConnectionInner> value;

    /**
     * Get the value property: Array of private endpoint connections.
     *
     * @return the value value.
     */
    public List<PrivateEndpointConnectionInner> value() {
        return this.value;
    }

    /**
     * Set the value property: Array of private endpoint connections.
     *
     * @param value the value value to set.
     * @return the PrivateEndpointConnectionListResultInner object itself.
     */
    public PrivateEndpointConnectionListResultInner withValue(List<PrivateEndpointConnectionInner> value) {
        this.value = value;
        return this;
    }
}
