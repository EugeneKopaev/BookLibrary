package org.booklibrary.app.persistence.repository;

import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.ejb.Local;

@Local
public interface AuthorFacade extends CommonLocalFacade<Author, EntityIdentifier>{
}
