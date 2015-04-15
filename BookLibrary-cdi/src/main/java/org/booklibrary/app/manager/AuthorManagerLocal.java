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

    Author saveUnique(Author author) throws DuplicateResourceException;

    List<Author> findAllByBookAvgRating(Double rate);
}
