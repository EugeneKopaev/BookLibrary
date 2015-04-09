package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.ReviewHomeLocal;
import org.booklibrary.app.persistence.session.common.AbstractGenericEntityPersistence;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Review Home Bean
 *
 * @see org.booklibrary.app.persistence.session.ReviewHomeLocal
 */
@Stateless
public class ReviewHome extends AbstractGenericEntityPersistence<Review, String>
        implements ReviewHomeLocal {

        @Inject
        private EntityManager entityManager;

        @Inject
        private Logger logger;

        @Override
        public EntityManager getEntityManager() {
                return this.entityManager;
        }

        @Override
        public Logger getLogger() {
                return this.logger;
        }
}
