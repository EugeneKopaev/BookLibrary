package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.session.common.GenericHomeLocal;

import javax.ejb.Local;

/**
 * Author Home Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericHomeLocal
 */
@Local
public interface AuthorHomeLocal extends GenericHomeLocal<Author, String> {
}
