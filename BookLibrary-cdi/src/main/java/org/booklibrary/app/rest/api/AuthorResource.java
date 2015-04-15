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

    /**
     * Find author by uuid in param request
     *
     * @param uuid Author id
     * @return Author dto
     */
    @AddLinks
    @LinkResource(AuthorDto.class)
    @GET
    @Path("/{id}")
    public Response getAuthor(@PathParam("id") String uuid);

    /**
     * Find authors with paging query params
     *
     * @param start   start position
     * @param size    result list size
     * @param uriInfo uri info for adding links
     * @return List of authors dto with paging links
     */
    @AddLinks
    @LinkResource(value = AuthorDto.class, rel = "list")
    @GET
    @Produces({"application/xml", "application/json"})
    public Response getAuthors(@QueryParam("start") @DefaultValue("0") int start,
                               @QueryParam("size") @DefaultValue("3") int size,
                               @Context UriInfo uriInfo);

    /**
     * Find books by author with paging query param
     *
     * @param uuid    author id
     * @param start   start position
     * @param size    result list size
     * @param uriInfo uri info for adding links
     * @return List of books dto with paging links
     */
    @AddLinks
    @LinkResource(value = AuthorDto.class, rel = "books")
    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthor(@PathParam("id") String uuid,
                                     @QueryParam("start") @DefaultValue("0") int start,
                                     @QueryParam("size") @DefaultValue("3") int size,
                                     @Context UriInfo uriInfo);

    /**
     * Save new Author in database
     *
     * @param dto Author dto
     * @return created resource
     */
    @AddLinks
    @LinkResources({
            @LinkResource(AuthorDto.class),
            @LinkResource(AuthorDtoCollection.class)
    })
    @POST
    public Response save(AuthorDto dto);

    /**
     * Update existing Author in database
     *
     * @param id  Author id
     * @param dto Author dto
     * @return updated resource
     */
    @AddLinks
    @LinkResource(AuthorDto.class)
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, AuthorDto dto);

    /**
     * Remove Author from database
     *
     * @param uuid Author id
     * @return status response
     */
    @LinkResource(AuthorDto.class)
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String uuid);

    /**
     * Remove all Author from database
     *
     * @return status response
     */
    @LinkResource(AuthorDtoCollection.class)
    @DELETE
    public Response deleteAll();
}
