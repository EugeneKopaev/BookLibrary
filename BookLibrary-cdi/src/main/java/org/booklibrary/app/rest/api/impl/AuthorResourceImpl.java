package org.booklibrary.app.rest.api.impl;


import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.manager.exceptions.EntityManagerException;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.rest.api.AuthorResource;
import org.booklibrary.app.rest.dto.AuthorDto;
import org.booklibrary.app.rest.dto.AuthorDtoCollection;
import org.booklibrary.app.rest.dto.BookDtoCollection;
import org.booklibrary.app.rest.exceptions.ResourceNotFoundException;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AuthorResourceImpl implements AuthorResource{

    @Inject
    private AuthorManagerLocal authorManager;

    @Override
    public Response getAuthor(String uuid) {
        Author author = authorManager.findByUuid(uuid);
        if (author == null) {
            throw new ResourceNotFoundException("Can't find author with uuid: " + uuid);
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
        Link[] pagingLinks = producePagingLinks(uriInfo, start, size, authors.size());

        return Response.ok(authorDtoCollection)
                .links(pagingLinks).build();
    }

    public Response getBooksByAuthor(String uuid,
                                     int start,
                                     int size,
                                     UriInfo uriInfo) {
        Author author = authorManager.findByUuid(uuid);
        if (author == null) {
            throw new ResourceNotFoundException("Can't find author with uuid: " + uuid);
        }
        List<Book> books = author.getBooks();
        if (books == null) {
            throw new ResourceNotFoundException("Can't find any books for author with uuid: " + uuid);
        }

        BookDtoCollection bookDtoCollection = new BookDtoCollection(books);
        Link[] pagingLinks = producePagingLinks(uriInfo, start, size, books.size());
        return Response.ok(bookDtoCollection)
                .links(pagingLinks).build();
    }

    @Override
    public Response save(AuthorDto dto) {

        Author author = dto.toEntity();
        try {
            authorManager.save(author);
        } catch (EntityManagerException e) {
            throw new WebApplicationException("failed to save obj: " + dto);
        }
        return Response.ok(new AuthorDto(author))
                .status(Response.Status.CREATED)
                .build();
    }

    @Override
    public Response update(String id, AuthorDto dto) {
        Author author = authorManager.findByUuid(id);
        if (author == null) {
            throw new ResourceNotFoundException("Can't find author with uuid: " + id);
        }
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        try {
            authorManager.update(author);
        } catch (EntityManagerException e) {
            throw new WebApplicationException("failed to update obj with uuid: " + author.getId());
        }
        return Response.ok(new AuthorDto(author)).build();
    }

    @Override
    public Response delete(String uuid) {
        try {
            authorManager.removeByUuid(uuid);
        } catch (EntityManagerException e) {
            throw new WebApplicationException("failed to delete obj with uuid: " + uuid);
        }
        return Response.ok().build();
    }

    public Response deleteAll() {
        try {
            authorManager.removeAll();
        } catch (EntityManagerException e) {
            throw new WebApplicationException("failed to remove all");
        }
        return Response.ok().build();
    }

    private Link[] producePagingLinks(UriInfo uriInfo, int start, int pageSize, int resultSize) {

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.queryParam("start", "{start}");
        builder.queryParam("size", "{size}");

        List<Link> links = new ArrayList<>();
        // next link
        // If the size returned is equal then assume there is a next
        if (resultSize == pageSize) {
            int next = start + pageSize;
            URI nextUri = builder.clone().build(next, pageSize);
            Link nextLink = Link.fromUri(nextUri)
                    .rel("next")
                    .type("application/xml, application/json")
                    .build();
            links.add(nextLink);
        }
        // previous link
        if (start > 0) {
            int previous = start - pageSize;
            if (previous < 0) {
                previous = 0;
            }
            URI previousUri = builder.clone().build(previous, pageSize);
            Link previousLink = Link.fromUri(previousUri)
                    .rel("previous")
                    .type("application/xml, application/json")
                    .build();
            links.add(previousLink);
        }

        Link[] linkArr = new Link[links.size()];

        links.toArray(linkArr);
        return linkArr;
    }
}
