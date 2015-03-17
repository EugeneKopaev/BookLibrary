package org.booklibrary.app.web.controller;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.manager.exceptions.EntityPersistenceException;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Model
public class AuthorController {

    @Inject
    private EntityManager entityManager;

    @Inject
    private AuthorManagerLocal authorManager;

    @Inject
    private Event<Author> authorEvent;

    private Author managedAuthor = new Author();

    private EntityIdentifier pk = new EntityIdentifier();
    private int page = 1;

    public void save() {
        if (managedAuthor != null) {
            try {
                authorManager.save(managedAuthor);
                authorEvent.fire(managedAuthor);
            } catch (EntityPersistenceException e) {
                e.printStackTrace();
            }
        }
    }

//    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void remove() {
        if (managedAuthor != null) {
            EntityIdentifier id = managedAuthor.getId();
            System.out.println("ID: " + id);
            System.out.println(managedAuthor.toString());
            System.out.println("UUID: " + pk);
            try {
//                entityManager.remove(managedAuthor);
                authorManager.remove(pk);
                authorEvent.fire(managedAuthor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (managedAuthor != null) {
            try {
                System.out.println(managedAuthor.toString());
                authorManager.save(managedAuthor);
                authorEvent.fire(managedAuthor);
            } catch (EntityPersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    @Produces
    @Named
    public Author getManagedAuthor() {
        return managedAuthor;
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

    public EntityIdentifier getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = new EntityIdentifier(pk);
    }
}
