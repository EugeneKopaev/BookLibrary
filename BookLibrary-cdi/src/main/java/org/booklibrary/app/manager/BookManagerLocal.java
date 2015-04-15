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

    Book saveUnique(Book book) throws DuplicateResourceException;

    Double countAvgBookRating(String id);

    List<Book> findAllByAuthor(String id);

    List<Book> findAllByReviewRating(int rate);
}
