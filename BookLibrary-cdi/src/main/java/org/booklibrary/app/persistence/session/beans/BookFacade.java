package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.session.BookFacadeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Book Facade bean
 *
 * @see org.booklibrary.app.persistence.session.BookFacadeLocal
 */
@Named
@Stateless
public class BookFacade extends AbstractGenericEntityPersistence<Book, String>
        implements BookFacadeLocal {

    @Inject
    private EntityManager entityManager;

    public BookFacade() {
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Book findByIsbn(long isbn) {
        List<Book> resultList = entityManager.createNamedQuery("Book.findByIsbn", Book.class)
                .setParameter("isbn", isbn).getResultList();
        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public List<Book> findByReviewRating(int rate) {
        return entityManager.createNamedQuery("Book.findReviewRating", Book.class)
                .setParameter("rating", rate)
                .getResultList();
    }

    @Override
    public List<Book> findAllByAuthor(String id) {
        return entityManager.createNamedQuery("Book.findAllByAuthor", Book.class)
                .setParameter("authorID", id).getResultList();
    }

    @Override
    public Double countBookRating(String id) {
        return entityManager.createNamedQuery("Book.countRating", Double.class)
                .setParameter("bookID", id).getSingleResult();
    }
}
