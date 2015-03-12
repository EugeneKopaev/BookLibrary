package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;

import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author home implementation
 */
@Stateless
@Named("authorHome")
public class AuthorHome extends AbstractGenericEntityPersistence<Author, EntityIdentifier>
        implements AuthorHomeLocal {

    @Inject
    private EntityManager entityManager;

    public AuthorHome() {
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
