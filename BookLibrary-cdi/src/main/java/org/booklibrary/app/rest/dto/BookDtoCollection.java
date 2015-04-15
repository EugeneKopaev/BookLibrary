package org.booklibrary.app.rest.dto;

import org.booklibrary.app.persistence.entity.Book;
import org.jboss.resteasy.annotations.providers.jaxb.Formatted;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.links.RESTServiceDiscovery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Mapped(namespaceMap = @XmlNsMap(jsonName = "atom", namespace = "http://www.w3.org/2005/Atom"))
@XmlRootElement(name = "books")
@XmlAccessorType(XmlAccessType.NONE)
public class BookDtoCollection implements Serializable{

    @XmlElementRef(name = "book")
    @Formatted
    private Collection<BookDto> books = new ArrayList<>();

    @XmlElementRef
    private RESTServiceDiscovery atomLinks;

    public BookDtoCollection() {
    }

    public BookDtoCollection(Collection<Book> books) {
        for (Book book: books) {
            this.books.add(new BookDto(book));
        }
    }

}
