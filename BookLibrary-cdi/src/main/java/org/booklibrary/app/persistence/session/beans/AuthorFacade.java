package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author facade implementation
 */
@Stateless
@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
public class AuthorFacade extends AbstractGenericEntityPersistence<Author, EntityIdentifier>
        implements AuthorFacadeLocal {

    @Inject
    private EntityManager entityManager;

    public AuthorFacade() {
    }

    @Override
    public Author findByFirstName(String firstName) {
        return entityManager.createNamedQuery("Author.findByFirstName", Author.class)
                .setParameter("firstName", firstName)
                .getSingleResult();
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }
}
