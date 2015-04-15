package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.ReviewFacadeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Review Facade bean
 *
 * @see org.booklibrary.app.persistence.session.ReviewFacadeLocal
 */
@Named
@Stateless
public class ReviewFacade extends AbstractGenericEntityPersistence<Review, String>
        implements ReviewFacadeLocal {

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
