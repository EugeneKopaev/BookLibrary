package org.booklibrary.app.persistence.repository.impl;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.repository.AuthorHome;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author home implementation
 */
@Stateless
public class AuthorHomeBean extends AbstractCommonHome<Author, EntityIdentifier>
        implements AuthorHome {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public AuthorHomeBean() {
    }

}
