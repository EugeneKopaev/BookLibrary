package org.booklibrary.app.persistence.listeners;

import org.booklibrary.app.persistence.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;

/**
 * Listener for recalculating avg book rating at every review creation
 */
public class BookRatingListener {

    private static final Logger LOG = LoggerFactory.getLogger(BookRatingListener.class);

    @PersistenceContext
    private EntityManager entityManager;

    @PrePersist
    public void recalculateRating(Review review) {
        String referenceId = review.getBook().getId();
        Double rating = entityManager.createNamedQuery("Book.countRating", Double.class)
                .setParameter("bookID", referenceId)
                .getSingleResult();
        LOG.debug("Count rating: " + rating);
        int update = entityManager.createNamedQuery("Book.updateRating")
                .setParameter("rating", rating)
                .setParameter("bookID", referenceId)
                .executeUpdate();

        LOG.debug("Update entity: " + update);
    }
}
