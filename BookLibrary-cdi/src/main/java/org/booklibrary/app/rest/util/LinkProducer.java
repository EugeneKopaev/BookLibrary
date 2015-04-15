package org.booklibrary.app.rest.util;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class for generate links
 */
public class LinkProducer {

    public  static Link[] producePagingLinks(UriInfo uriInfo, int start, int pageSize, int resultSize) {

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
