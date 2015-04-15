package org.booklibrary.app.rest.util;

import org.booklibrary.app.exceptions.ResourceNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * This class build responce for all ResourceNotFoundExceptions
 */
@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    public Response toResponse(ResourceNotFoundException exception) {
        return ErrorResponseBuilder.createErrorMessageResponse(
                exception.getMessage(), Response.Status.NOT_FOUND)
                .build();
    }
}
