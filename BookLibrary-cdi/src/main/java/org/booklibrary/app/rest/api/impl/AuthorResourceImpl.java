package org.booklibrary.app.rest.api.impl;


import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.rest.api.AuthorResource;
import org.booklibrary.app.rest.dto.AuthorDto;
import org.booklibrary.app.rest.dto.AuthorDtoCollection;
import org.booklibrary.app.rest.dto.BookDtoCollection;
import org.booklibrary.app.exceptions.ResourceNotFoundException;
import org.booklibrary.app.rest.util.ErrorResponseBuilder;
import org.booklibrary.app.rest.util.LinkProducer;

import javax.inject.Inject;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

public class AuthorResourceImpl implements AuthorResource {

    @Inject
    private AuthorManagerLocal authorManager;

    @Override
    public Response getAuthor(String uuid) {
        Author author = authorManager.findByUuid(uuid);
        if (author == null) {
            throw new ResourceNotFoundException();
        }

        AuthorDto dto = new AuthorDto(author);
        return Response.ok(dto).build();
    }

    @Override
    public Response getAuthors(int start, int size, UriInfo uriInfo) {

        List<Author> authors = authorManager.findRange(start, size);

        if (authors == null) {
            throw new ResourceNotFoundException();
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
           throw new ResourceNotFoundException();
        }
        List<Book> books = author.getBooks();
        if (books == null) {
            throw new ResourceNotFoundException();
        }

        BookDtoCollection bookDtoCollection = new BookDtoCollection(books);
        Link[] pagingLinks = LinkProducer.producePagingLinks(uriInfo, start, size, books.size());
        return Response.ok(bookDtoCollection)
                .links(pagingLinks).build();
    }

    @Override
    public Response save(AuthorDto dto) {
        Author author = dto.toEntity();

        Response.ResponseBuilder builder;
        try {
            authorManager.saveUnique(author);
            builder = Response.status(Response.Status.CREATED).entity(new AuthorDto(author));

        } catch (Exception e) {
            builder = ErrorResponseBuilder.buildErrorResponse(e);
        }
        return builder.build();
    }

    @Override
    public Response update(String id, AuthorDto dto) {
        Author author = authorManager.findByUuid(id);
        if (author == null) {
            throw new ResourceNotFoundException();
        }
        Response.ResponseBuilder builder;
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        try {
            Author updated = authorManager.update(author);
            builder = Response.ok(new AuthorDto(updated));
        } catch (Exception e) {
            builder = ErrorResponseBuilder.buildErrorResponse(e);
        }
        return builder.build();
    }

    @Override
    public Response delete(String uuid) {
        try {
            authorManager.removeByUuid(uuid);
        } catch (Exception e) {
            return ErrorResponseBuilder.buildErrorResponse(e).build();
        }
        return Response.ok().build();
    }

    public Response deleteAll() {
        try {
            authorManager.removeAll();
        } catch (Exception e) {
            return ErrorResponseBuilder.buildErrorResponse(e).build();
        }
        return Response.ok().build();
    }
}
