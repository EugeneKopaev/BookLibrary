package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;

import javax.ejb.Local;
import java.util.List;

/**
 * Book Facade Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
@Local
public interface BookFacadeLocal extends GenericFacadeLocal<Book, String> {

    /**
     * Find book by isbn number
     *
     * @param isbn
     * @return book
     */
    Book findByIsbn(long isbn);

    /**
     * Find book by rating
     *
     * @param isbn
     * @return book
     */
    List<Book> findByReviewRating(int rate);

    /**
     * Find all objects in the database.
     *
     * @return List<T> list of entities
     */
    List<Book> findAllByAuthor(String id);

    /**
     * Count book rating based on avg reviews rating
     *
     * @param id
     * @return rating for book with given id
     */
    Double countBookRating(String id);
}
