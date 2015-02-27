package org.booklibrary.app.persistence.repository.impl;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.repository.AuthorFacade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author facade implementation
 */
@Stateless
@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
public class AuthorFacadeBean extends AbstractCommonFacade<Author, EntityIdentifier>
        implements AuthorFacade {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public AuthorFacadeBean() {
    }
}
