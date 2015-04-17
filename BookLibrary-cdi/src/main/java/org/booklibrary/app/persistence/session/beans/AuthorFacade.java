package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Author Facade bean
 *
 * @see org.booklibrary.app.persistence.session.AuthorFacadeLocal
 */
@Named
@Stateless
@TransactionAttribute(value = TransactionAttributeType.SUPPORTS)
public class AuthorFacade extends AbstractGenericEntityPersistence<Author, String>
        implements AuthorFacadeLocal {

    @Inject
    private EntityManager entityManager;

    public AuthorFacade() {
    }

    @Override
    public Author findByFirstAndLastName(String firstName, String lastName) {
        Author author = null;
        List<Author> authors = entityManager.createNamedQuery("Author.findByFirstAndLastName", Author.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();
        if (!authors.isEmpty()) {
            author = authors.get(0);
        }
        return author;
    }

    @Override
    public List<Author> findByBookAvgRating(Double rate) {
        return entityManager.createNamedQuery("Author.findByBookAvgRating", Author.class)
                .setParameter("avgRating", rate)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
