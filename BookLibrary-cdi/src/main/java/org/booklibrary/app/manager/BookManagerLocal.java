package org.booklibrary.app.manager;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.persistence.entity.Book;

import javax.ejb.Local;
import java.util.List;

/**
 * Book Manager Local interface
 *
 * @see org.booklibrary.app.manager.GenericManagerLocal
 */
@Local
public interface BookManagerLocal extends GenericManagerLocal<Book, String>{

    /**
     * Save unique book in database
     *
     * @param book Object to safe
     * @return Saved book
     * @throws DuplicateResourceException
     */
    Book saveUnique(Book book) throws DuplicateResourceException;

    /**
     * Count average book rating
     *
     * @param id Book id
     * @return   Avg rating
     */
    Double countAvgBookRating(String id);

    /**
     * Find books by author id
     *
     * @param id Author id
     * @return   List of books
     */
    List<Book> findAllByAuthor(String id);

    /**
     * Find books by review rating
     *
     * @param rate Review rating
     * @return     List of books
     */
    List<Book> findAllByReviewRating(int rate);
}
