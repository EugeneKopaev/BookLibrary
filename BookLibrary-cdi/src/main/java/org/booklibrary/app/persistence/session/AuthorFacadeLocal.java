package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;

import javax.ejb.Local;
import java.util.List;

/**
 * Author Facade Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
@Local
public interface AuthorFacadeLocal extends GenericFacadeLocal<Author, String> {

    /**
     * Find Author in the database by first and last name.
     *
     * @param firstName Author first name
     * @param lastName  Author last name
     * @return Author   Author entity
     */
    Author findByFirstAndLastName(String firstName, String lastName);

    /**
     * Find objects in the database by book rating.
     *
     * @param rate rating
     * @return List of Authors
     */
    List<Author> findByBookAvgRating(Double rate);

}
