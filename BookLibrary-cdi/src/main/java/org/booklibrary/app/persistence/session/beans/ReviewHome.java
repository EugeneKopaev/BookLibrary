package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.ReviewHomeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

/**
 * Review Home Bean
 *
 * @see org.booklibrary.app.persistence.session.ReviewHomeLocal
 */
@Named
@Stateless
public class ReviewHome extends AbstractGenericEntityPersistence<Review, String>
        implements ReviewHomeLocal {

    @Inject
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

}
