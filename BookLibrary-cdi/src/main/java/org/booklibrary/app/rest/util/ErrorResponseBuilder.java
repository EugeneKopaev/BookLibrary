package org.booklibrary.app.rest.util;

import com.google.common.base.Throwables;
import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.rest.dto.adapters.MapAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Util class for building error responses
 */
public class ErrorResponseBuilder {

    public static Response.ResponseBuilder buildErrorResponse(Exception e) {
        Throwable t = Throwables.getRootCause(e);
        if (t instanceof DuplicateResourceException) {
            return createErrorMessageResponse(
                    (t.getMessage()), Response.Status.CONFLICT);
        }
        if (t instanceof ConstraintViolationException) {
            ConstraintViolationException violationException = (ConstraintViolationException) t;
            return createViolationResponse(violationException.getConstraintViolations());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR);
    }

    public static Response.ResponseBuilder createErrorMessageResponse(String errorMessage, Response.Status status) {
        JAXBElement<String> responseElement = new JAXBElement<>(
                QName.valueOf("error"), String.class,
                JAXBElement.GlobalScope.class, errorMessage);
        return Response.status(status).entity(responseElement);
    }

    private static Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {

        Map<String, String> responseObj = new HashMap<>();
        MapAdapter adapter = new MapAdapter();
        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(adapter.marshal(responseObj));
    }
}
