/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage.v2019_04_01;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for Services.
 */
public final class Services extends ExpandableStringEnum<Services> {
    /** Static value b for Services. */
    public static final Services B = fromString("b");

    /** Static value q for Services. */
    public static final Services Q = fromString("q");

    /** Static value t for Services. */
    public static final Services T = fromString("t");

    /** Static value f for Services. */
    public static final Services F = fromString("f");

    /**
     * Creates or finds a Services from its string representation.
     * @param name a name to look for
     * @return the corresponding Services
     */
    @JsonCreator
    public static Services fromString(String name) {
        return fromString(name, Services.class);
    }

    /**
     * @return known Services values
     */
    public static Collection<Services> values() {
        return values(Services.class);
    }
}
