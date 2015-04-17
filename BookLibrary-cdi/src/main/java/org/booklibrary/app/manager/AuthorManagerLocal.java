package org.booklibrary.app.manager;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.persistence.entity.Author;

import javax.ejb.Local;
import java.util.List;

/**
 * Author Manager Local interface
 *
 * @see org.booklibrary.app.manager.GenericManagerLocal
 */
@Local
public interface AuthorManagerLocal extends GenericManagerLocal<Author, String> {

    /**
     * Save unique author in database
     *
     * @param author Object to safe
     * @return Saved author
     * @throws DuplicateResourceException
     */
    Author saveUnique(Author author) throws DuplicateResourceException;

    /**
     * Find authors by book rating
     *
     * @param rate Book avg rating
     * @return List of authors
     */
    List<Author> findAllByBookAvgRating(Double rate);
}
