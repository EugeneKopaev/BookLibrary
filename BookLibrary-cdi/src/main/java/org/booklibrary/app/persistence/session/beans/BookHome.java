package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.BookHomeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Book Home Bean
 *
 * @see org.booklibrary.app.persistence.session.AuthorHomeLocal
 */

@Stateless
public class BookHome extends AbstractGenericEntityPersistence<Book, EntityIdentifier>
        implements BookHomeLocal{

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public BookHome() {
    }

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
