package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.session.BookHomeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Book Home Bean
 *
 * @see org.booklibrary.app.persistence.session.BookHomeLocal
 */
@Named
@Stateless
public class BookHome extends AbstractGenericEntityPersistence<Book, String>
        implements BookHomeLocal {

    @Inject
    private EntityManager entityManager;

    public BookHome() {
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
