package org.booklibrary.app.manager;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.ejb.Local;

/**
 * Book Manager Local interface
 *
 * @see org.booklibrary.app.manager.GenericManagerLocal
 */
@Local
public interface BookManagerLocal extends GenericManagerLocal<Book, EntityIdentifier>{

    Book saveUnique(Book book) throws DuplicateResourceException;
}
