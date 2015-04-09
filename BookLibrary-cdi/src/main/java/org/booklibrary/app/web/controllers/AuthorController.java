package org.booklibrary.app.web.controllers;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.web.util.FacesMessageUtils;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named
@ViewScoped
public class AuthorController implements Serializable {

    @Inject
    private Logger logger;

    @EJB
    private AuthorManagerLocal authorManager;

    @Inject
    private FacesContext facesContext;

    private String currentAuthorId;
    private Author managedAuthor = null;
    private Author createdAuthor = new Author();
    private List<Author> authors = new ArrayList<>();
    private Map<Object, Object> checkedItems = new HashMap();
    private int page = 1;

    public void save() {
        if (createdAuthor == null) {
            return;
        }
        try {
            authorManager.saveUnique(createdAuthor);
            createdAuthor = new Author();
        } catch (DuplicateResourceException e) {
            FacesMessageUtils.addErrorMessage(e, e.getMessage());
        } catch (EJBException e) {
            FacesMessageUtils.addErrorMessage(e, "Save unsuccessful");
        }
    }

    public void update() {
        if (managedAuthor == null) {
            return;
        }
        try {
            authorManager.update(managedAuthor);
        } catch (EJBException e) {
            FacesMessageUtils.addErrorMessage(e, "Update unsuccessful");
        }
    }

    public void remove() {
        try {
            authorManager.removeByPk(managedAuthor.getId());
            FacesMessageUtils.addInfoMessage("Removed author successful");
        } catch (EJBException e) {
            FacesMessageUtils.addErrorMessage(e, "Remove unsuccessful");
        }
    }

    public void removeSelected() {
        if (!checkedItems.isEmpty()) {
            List<String> listToDelete = new ArrayList<>();
            for (Object key : checkedItems.keySet()) {
                if ((Boolean) checkedItems.get(key)) {
                    listToDelete.add((String) key);
                    checkedItems.put(key, false);
                }
            }
            for (String entry: listToDelete) {
                try {
                    authorManager.removeByPk(entry);
                    checkedItems.clear();
                    FacesMessageUtils.addInfoMessage("Removed author with id: " + entry);
                } catch (EJBException e) {
                    FacesMessageUtils.addErrorMessage(e, "Remove unsuccessful for author with id: " + entry);
                }
            }
        } else {
            FacesMessageUtils.addInfoMessage("There is nothing to delete");
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

    @Produces
    @Named
    public Author getCreatedAuthor() {
        return createdAuthor;
    }

    public void loadData() {
        managedAuthor = authorManager.findByUuid(getCurrentAuthorId());
    }

    public String getCurrentAuthorId() {
        return currentAuthorId;
    }

    public void setCurrentAuthorId(String currentAuthorId) {
        this.currentAuthorId = currentAuthorId;
    }

    public void setCreatedAuthor(Author createdAuthor) {
        this.createdAuthor = createdAuthor;
    }

    public void setManagedAuthor(Author managedAuthor) {
        this.managedAuthor = managedAuthor;
    }

    public Map<Object, Object> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(Map<Object, Object> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
