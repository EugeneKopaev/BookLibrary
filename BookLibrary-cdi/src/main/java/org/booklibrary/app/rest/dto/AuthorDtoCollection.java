package org.booklibrary.app.rest.dto;

import org.booklibrary.app.persistence.entity.Author;
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
@XmlRootElement(name = "authors")
@XmlAccessorType(XmlAccessType.NONE)
public class AuthorDtoCollection implements Serializable {

    @XmlElementRef(name = "author")
    @Formatted
    private Collection<AuthorDto> authorDtoList = new ArrayList<>();

    @XmlElementRef
    private final RESTServiceDiscovery atomLinks = new RESTServiceDiscovery();

    public AuthorDtoCollection() {
    }

    public AuthorDtoCollection(Collection<Author> authors) {
        for (Author entry : authors) {
            this.authorDtoList.add(new AuthorDto(entry));
        }
    }
}
