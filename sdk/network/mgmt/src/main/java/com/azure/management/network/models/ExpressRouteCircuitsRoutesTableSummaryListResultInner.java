// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.network.models;

import com.azure.core.annotation.Fluent;
import com.azure.management.network.ExpressRouteCircuitRoutesTableSummary;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The ExpressRouteCircuitsRoutesTableSummaryListResult model. */
@Fluent
public final class ExpressRouteCircuitsRoutesTableSummaryListResultInner {
    /*
     * A list of the routes table.
     */
    @JsonProperty(value = "value")
    private List<ExpressRouteCircuitRoutesTableSummary> value;

    /*
     * The URL to get the next set of results.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value property: A list of the routes table.
     *
     * @return the value value.
     */
    public List<ExpressRouteCircuitRoutesTableSummary> value() {
        return this.value;
    }

    /**
     * Set the value property: A list of the routes table.
     *
     * @param value the value value to set.
     * @return the ExpressRouteCircuitsRoutesTableSummaryListResultInner object itself.
     */
    public ExpressRouteCircuitsRoutesTableSummaryListResultInner withValue(
        List<ExpressRouteCircuitRoutesTableSummary> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: The URL to get the next set of results.
     *
     * @return the nextLink value.
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: The URL to get the next set of results.
     *
     * @param nextLink the nextLink value to set.
     * @return the ExpressRouteCircuitsRoutesTableSummaryListResultInner object itself.
     */
    public ExpressRouteCircuitsRoutesTableSummaryListResultInner withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
    }
}
