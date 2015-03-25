package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.common.GenericHomeLocal;

import javax.ejb.Local;

/**
 * Book Home Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericHomeLocal
 */
@Local
public interface BookHomeLocal extends GenericHomeLocal<Book, EntityIdentifier> {
}
