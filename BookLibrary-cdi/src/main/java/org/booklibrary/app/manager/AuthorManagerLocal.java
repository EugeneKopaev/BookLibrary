package org.booklibrary.app.manager;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.ejb.Local;

/**
 *
 * Author Manager Local interface
 *
 * @see org.booklibrary.app.manager.GenericManagerLocal
 */
@Local
public interface AuthorManagerLocal extends GenericManagerLocal<Author, EntityIdentifier>{
}
