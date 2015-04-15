package org.booklibrary.app.manager.beans;

import org.booklibrary.app.manager.ReviewManagerLocal;
import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.ReviewFacadeLocal;
import org.booklibrary.app.persistence.session.ReviewHomeLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Review Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see org.booklibrary.app.manager.BookManagerLocal
 */
@Named
@Stateless
public class ReviewManager implements ReviewManagerLocal {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewManager.class);

    @EJB
    private ReviewFacadeLocal reviewFacade;

    @EJB
    private ReviewHomeLocal reviewHome;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Review save(Review obj) {
        LOG.debug("save invoked for object: {}", obj);
        return reviewHome.save(obj);
    }

    public Review update(Review obj) {
        LOG.debug("update invoked for obj: {}", obj);
        return reviewHome.update(obj);
    }

    public void removeByPk(String key) {
        LOG.debug("Remove called for entity with key: {}", key);
        reviewHome.removeByPk(key);
    }

    public void removeByUuid(String uuid) {
        removeByPk(uuid);
    }

    public void removeList(List<String> keys) {
        LOG.debug("Remove list invoked");
        reviewHome.removeList(keys);
    }

    @Override
    public void removeAll() {
        LOG.debug("Remove all invoked");
        reviewHome.removeAll();
    }

    @Override
    public Review findByPk(String key) {
        LOG.debug("Find invoked for object with key: {}", key);
        return reviewFacade.findByPk(key);
    }

    @Override
    public Review findByUuid(String uuid) {
        LOG.debug("Find invoked for object with key: {}", uuid);
        return reviewFacade.findByUuid(uuid);
    }

    @Override
    public List<Review> findAll() {
        LOG.debug("Find all called");
        return reviewFacade.findAll();
    }

    @Override
    public List<Review> findRange(int start, int size) {
        LOG.debug("Find segment called");
        return reviewFacade.findRange(start, size);
    }

    @Override
    public int countEntity() {
        LOG.debug("Count all reviews");
        int count = reviewFacade.countEntity();
        LOG.debug("result: " + count);
        return count;
    }
}
