package org.booklibrary.app.web.controller;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.manager.exceptions.EntityManagerException;
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
public class AuthorController implements Serializable{

    @Inject
    private Logger LOG;

    @Inject
    private AuthorManagerLocal authorManager;

    private Author managedAuthor = null;
    private List<Author> authors = new ArrayList<>();
    private int page = 1;

    public void save() {
        if (managedAuthor != null) {
            try {
                authorManager.save(managedAuthor);
            } catch (EntityManagerException e) {
                e.printStackTrace();
            }
        }
    }

    public void remove() {
        if (managedAuthor != null) {
            try {
                authorManager.removeByPk(managedAuthor.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (managedAuthor != null) {
            try {
                authorManager.update(managedAuthor);
            } catch (EntityManagerException e) {
                e.printStackTrace();
            }
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
        return this.authors = authorManager.findAll();
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
