package org.booklibrary.app.web.backing;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class AuthorListProducer {

    @Inject
    private AuthorManagerLocal authorManager;

    private List<Author> authors;

    @Produces
    @Named
    public List<Author> getAuthors() {
        return authors;
    }
    public void onAuthorListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Author author) {
        retrieveAuthors();
    }

    @PostConstruct
    public void retrieveAuthors() {
        this.authors = authorManager.findAll();
    }
}
