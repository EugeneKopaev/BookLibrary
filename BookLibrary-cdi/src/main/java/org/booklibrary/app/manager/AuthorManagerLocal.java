package org.booklibrary.app.manager;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.ejb.Local;

@Local
public interface AuthorManagerLocal extends GenericManagerLocal<Author, EntityIdentifier>{
}
