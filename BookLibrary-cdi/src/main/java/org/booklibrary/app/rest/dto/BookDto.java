package org.booklibrary.app.rest.dto;

import org.booklibrary.app.persistence.entity.Book;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.links.RESTServiceDiscovery;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Mapped(namespaceMap = @XmlNsMap(jsonName = "atom", namespace = "http://www.w3.org/2005/Atom"))
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.NONE)
public class BookDto implements Serializable{

    @XmlID
    @XmlAttribute(name = "id")
    private String bookId;

    @XmlElement
    private String name;

    @XmlElement
    private long isbn;

    @XmlElement
    private int publishYear;

    @XmlElement
    private String publisher;

    @XmlElementRef
    private RESTServiceDiscovery atomLinks;

    public BookDto() {
    }

    public BookDto(Book book) {
        this.name = book.getName();
        this.bookId = book.getId().toString();
        this.publisher = book.getPublisher();
        this.publishYear = book.getPublishYear();
        this.isbn = book.getIsbn();

    }
}
