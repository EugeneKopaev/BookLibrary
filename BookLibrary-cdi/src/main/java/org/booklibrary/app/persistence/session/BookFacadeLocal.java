package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;

import javax.ejb.Local;

/**
 * Book Facade Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
@Local
public interface BookFacadeLocal extends GenericFacadeLocal<Book, EntityIdentifier> {
}
