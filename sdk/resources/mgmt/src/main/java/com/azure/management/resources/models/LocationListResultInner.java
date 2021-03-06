// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.resources.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The LocationListResult model. */
@Fluent
public final class LocationListResultInner {
    /*
     * An array of locations.
     */
    @JsonProperty(value = "value")
    private List<LocationInner> value;

    /**
     * Get the value property: An array of locations.
     *
     * @return the value value.
     */
    public List<LocationInner> value() {
        return this.value;
    }

    /**
     * Set the value property: An array of locations.
     *
     * @param value the value value to set.
     * @return the LocationListResultInner object itself.
     */
    public LocationListResultInner withValue(List<LocationInner> value) {
        this.value = value;
        return this;
    }
}
