package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.ejb.Local;

@Local
public interface AuthorHomeLocal extends GenericHomeLocal<Author, EntityIdentifier> {
}
