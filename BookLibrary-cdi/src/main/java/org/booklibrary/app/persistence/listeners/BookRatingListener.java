package org.booklibrary.app.persistence.listeners;

import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.BookFacadeLocal;
import org.booklibrary.app.persistence.session.BookHomeLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.persistence.*;

/**
* Listener for calculating avg book rating from review
*/
public class BookRatingListener {

    private static final Logger LOG = LoggerFactory.getLogger(BookRatingListener.class);

    @EJB
    private BookFacadeLocal bookFacade;

    @EJB
    private BookHomeLocal bookHome;

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
