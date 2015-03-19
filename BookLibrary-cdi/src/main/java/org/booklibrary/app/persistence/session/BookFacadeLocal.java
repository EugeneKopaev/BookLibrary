package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;

import javax.ejb.Local;

@Local
public interface BookFacadeLocal extends GenericFacadeLocal<Book, EntityIdentifier> {
}
