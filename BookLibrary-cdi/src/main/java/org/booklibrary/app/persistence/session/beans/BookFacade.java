package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.BookFacadeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Book Facade bean
 *
 * @see org.booklibrary.app.persistence.session.BookFacadeLocal
 */
@Stateless
public class BookFacade extends AbstractGenericEntityPersistence<Book, EntityIdentifier>
        implements BookFacadeLocal{

    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    public BookFacade() {
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
