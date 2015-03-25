package org.booklibrary.app.rest.api;

import org.booklibrary.app.rest.dto.AuthorDto;
import org.booklibrary.app.rest.dto.AuthorDtoCollection;
import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;
import org.jboss.resteasy.links.LinkResources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


@Path("/authors")
@Consumes({"application/xml", "application/json"})
@Produces({"application/xml", "application/json"})
public interface AuthorResource {

    @AddLinks
    @LinkResource(AuthorDto.class)
    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") String uuid);

    @AddLinks
    @LinkResource(value = AuthorDto.class, rel = "list")
    @GET
    @Produces({"application/xml", "application/json"})
    public Response getAuthors(@QueryParam("start") @DefaultValue("0") int start,
                               @QueryParam("size") @DefaultValue("3") int size,
                               @Context UriInfo uriInfo);

    @AddLinks
    @LinkResource(value = AuthorDto.class, rel = "books")
    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") String uuid,
                                              @QueryParam("start") @DefaultValue("0") int start,
                                              @QueryParam("size") @DefaultValue("3") int size,
                                              @Context UriInfo uriInfo);

    @AddLinks
    @LinkResources({
            @LinkResource(AuthorDto.class),
            @LinkResource(AuthorDtoCollection.class)
    })
    @POST
    public Response save(AuthorDto dto);

    @AddLinks
    @LinkResource(AuthorDto.class)
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, AuthorDto dto);

    @LinkResource(AuthorDto.class)
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String uuid);

    @LinkResource(AuthorDtoCollection.class)
    @DELETE
    public Response deleteAll();
}
