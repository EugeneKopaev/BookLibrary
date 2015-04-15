package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Author Home bean
 *
 * @see org.booklibrary.app.persistence.session.AuthorHomeLocal
 */

@Named
@Stateless
public class AuthorHome extends AbstractGenericEntityPersistence<Author, String>
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
