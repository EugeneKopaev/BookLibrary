package org.booklibrary.app.rest.util;

import org.booklibrary.app.rest.exceptions.ResourceNotFoundException;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    public Response toResponse(ResourceNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type("text/plain").build();
    }
}
