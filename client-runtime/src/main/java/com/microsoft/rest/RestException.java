/**
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 *
 */

package com.microsoft.rest;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Exception thrown for an invalid response with custom error information.
 */
public class RestException extends RuntimeException {
    /**
     * Information about the associated HTTP response.
     */
    private Response<ResponseBody> response;

    /**
     * The HTTP response body.
     */
    private Object body;

    /**
     * Initializes a new instance of the RestException class.
     */
    public RestException(String message, Response<ResponseBody> response) {
        super(message);
        this.response = response;
    }

    /**
     * Initializes a new instance of the RestException class.
     *
     * @param message The exception message.
     */
    public RestException(String message, Response<ResponseBody> response, Object body) {
        super(message);
        this.response = response;
        this.body = body;
    }

    /**
     * @return information about the associated HTTP response
     */
    public Response<ResponseBody> response() {
        return response;
    }

    /**
     * @return the HTTP response body
     */
    public Object body() {
        return body;
    }
}
