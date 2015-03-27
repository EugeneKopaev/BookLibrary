package org.booklibrary.app.web.controller;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.slf4j.Logger;

import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class AuthorController implements Serializable {

    @Inject
    private transient Logger logger;

    @Inject
    private AuthorManagerLocal authorManager;

    private Author managedAuthor = null;
    private int page = 1;

    public void save() {
        if (managedAuthor != null) {
            authorManager.save(managedAuthor);
        }
    }

    public void remove() {
        if (managedAuthor != null) {
            authorManager.removeByPk(managedAuthor.getId());
        }
    }

    public void update() {
        if (managedAuthor != null) {
            authorManager.update(managedAuthor);
        }
    }

    @Produces
    @Named
    public Author getManagedAuthor() {
        return managedAuthor;
    }

    @Produces
    @Named
    public List<Author> getAuthors() {
        return authorManager.findAll();
    }

    public void setManagedAuthor(Author managedAuthor) {
        this.managedAuthor = managedAuthor;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
