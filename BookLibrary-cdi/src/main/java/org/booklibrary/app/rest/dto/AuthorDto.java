package org.booklibrary.app.rest.dto;

import org.booklibrary.app.persistence.entity.Author;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.jboss.resteasy.links.RESTServiceDiscovery;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


@Mapped(namespaceMap = @XmlNsMap(jsonName = "atom", namespace = "http://www.w3.org/2005/Atom"))
@XmlRootElement(name = "author")
@XmlAccessorType(XmlAccessType.NONE)
public class AuthorDto implements Serializable{

    @XmlID
    @XmlAttribute(name = "id")
    private String authorId;

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElementRef
    private RESTServiceDiscovery atomLinks;

    public AuthorDto() {
    }

    public AuthorDto(Author author) {
        this.authorId = author.getId().toString();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
    }

    public Author toEntity() {
        Author author = new Author();
        author.setFirstName(this.firstName);
        author.setLastName(this.lastName);
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
