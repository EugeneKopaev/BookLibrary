package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;

import javax.ejb.Local;

/**
 * Author Facade Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
@Local
public interface AuthorFacadeLocal extends GenericFacadeLocal<Author, String> {

    Author findByFirstAndLastName(String firstName, String lastName);
}
