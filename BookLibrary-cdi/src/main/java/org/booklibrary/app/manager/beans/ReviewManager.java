package org.booklibrary.app.manager.beans;

import org.booklibrary.app.manager.ReviewManagerLocal;
import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.ReviewFacadeLocal;
import org.booklibrary.app.persistence.session.ReviewHomeLocal;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Review Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see org.booklibrary.app.manager.BookManagerLocal
 */
@Stateless
public class ReviewManager implements ReviewManagerLocal {

    @Inject
    private Logger logger;

    @EJB
    private ReviewFacadeLocal reviewFacade;

    @EJB
    private ReviewHomeLocal reviewHome;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Review save(Review obj) {
        logger.debug("save invoked for object: {}", obj);
        Review entity = reviewHome.save(obj);
        return entity;
    }

    @Override
    public Review update(Review obj) {
        logger.debug("update invoked for obj: {}", obj);
        Review updated = reviewHome.update(obj);
        return updated;
    }

    @Override
    public void removeByPk(EntityIdentifier key) {
        logger.debug("Remove called for entity with key: {}", key);
        reviewHome.removeByPk(key);
    }

    @Override
    public void removeByUuid(String uuid) {
        removeByPk(new EntityIdentifier(uuid));
    }

    @Override
    public void removeAll() {
        logger.debug("Remove all invoked");
        reviewHome.removeAll();
    }

    @Override
    public Review findByPk(EntityIdentifier key) {
        logger.debug("Find invoked for object with key: {}", key);
        return reviewFacade.findByPk(key);
    }

    @Override
    public Review findByUuid(String uuid) {
        logger.debug("Find invoked for object with key: {}", uuid);
        return reviewFacade.findByUuid(uuid);
    }

    @Override
    public List<Review> findAll() {
        logger.debug("Find all called");
        List<Review> reviews = reviewFacade.findAll();
        return reviews;
    }

    @Override
    public List<Review> findSegment(int start, int size) {
        logger.debug("Find segment called");
        List<Review> reviews = reviewFacade.findSegment(start, size);
        return reviews;
    }
}
