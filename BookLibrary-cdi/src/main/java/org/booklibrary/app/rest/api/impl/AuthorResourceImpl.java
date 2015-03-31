package org.booklibrary.app.rest.api.impl;


import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.rest.api.AuthorResource;
import org.booklibrary.app.rest.dto.AuthorDto;
import org.booklibrary.app.rest.dto.AuthorDtoCollection;
import org.booklibrary.app.rest.dto.BookDtoCollection;
import org.booklibrary.app.rest.dto.adapters.MapAdapter;
import org.booklibrary.app.rest.exceptions.ResourceNotFoundException;
import org.booklibrary.app.rest.util.LinkProducer;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

public class AuthorResourceImpl implements AuthorResource{

    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Can't find author with uuid: ";

    @Inject
    private AuthorManagerLocal authorManager;

    @Inject
    private Validator validator;

    @Override
    public Response getAuthor(String uuid) {
        Author author = authorManager.findByUuid(uuid);
        if (author == null) {
            throw new ResourceNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + uuid);
        }

        AuthorDto dto = new AuthorDto(author);
        return Response.ok(dto).build();
    }

    @Override
    public Response getAuthors(int start, int size, UriInfo uriInfo) {

        List<Author> authors = authorManager.findSegment(start, size);

        if (authors == null) {
            throw new ResourceNotFoundException("Can't find any author");
        }

        AuthorDtoCollection authorDtoCollection = new AuthorDtoCollection(authors);
        Link[] pagingLinks = LinkProducer.producePagingLinks(uriInfo, start, size, authors.size());

        return Response.ok(authorDtoCollection)
                .links(pagingLinks).build();
    }

    public Response getBooksByAuthor(String uuid,
                                     int start,
                                     int size,
                                     UriInfo uriInfo) {
        Author author = authorManager.findByUuid(uuid);
        if (author == null) {
            throw new ResourceNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + uuid);
        }
        List<Book> books = author.getBooks();
        if (books == null) {
            throw new ResourceNotFoundException("Can't find any books for author with uuid: " + uuid);
        }

        BookDtoCollection bookDtoCollection = new BookDtoCollection(books);
        Link[] pagingLinks = LinkProducer.producePagingLinks(uriInfo, start, size, books.size());
        return Response.ok(bookDtoCollection)
                .links(pagingLinks).build();
    }

    @Override
    public Response save(AuthorDto dto){
        Author author = dto.toEntity();

        Response.ResponseBuilder builder;
        try {
            validateAuthor(author);
            authorManager.save(author);
            builder = Response.status(Response.Status.CREATED).entity(new AuthorDto(author));
        } catch (ConstraintViolationException e) {
            // Handle bean validation issues
            builder = createViolationResponse(e.getConstraintViolations());
        } catch (Exception e) {
            // Handle generic exceptions
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return builder.build();
    }

    @Override
    public Response update(String id, AuthorDto dto) {
        Author author = authorManager.findByUuid(id);
        if (author == null) {
            throw new ResourceNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id);
        }
        Response.ResponseBuilder builder;
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        try {
            validateAuthor(author);
            Author updated = authorManager.update(author);
            builder = Response.ok(new AuthorDto(updated));
        } catch (ConstraintViolationException e) {
            // Handle bean validation issues
            builder = createViolationResponse(e.getConstraintViolations());
        } catch (Exception e) {
            builder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return builder.build();
    }

    @Override
    public Response delete(String uuid) {
        try {
            authorManager.removeByUuid(uuid);
        } catch (EJBException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    public Response deleteAll() {
        try {
            authorManager.removeAll();
        } catch (EJBException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok().build();
    }

    //TODO: // create util class for violation response
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations){

        Map<String, String> responseObj = new HashMap<>();
        MapAdapter adapter = new MapAdapter();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(adapter.marshal(responseObj));
    }

    private void validateAuthor(Author author) throws ConstraintViolationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Author>> violations = validator.validate(author);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
    }
}
