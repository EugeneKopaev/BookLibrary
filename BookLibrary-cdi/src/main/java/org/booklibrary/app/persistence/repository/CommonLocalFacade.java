package org.booklibrary.app.persistence.repository;

import org.booklibrary.app.persistence.entity.AbstractPersistentEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Facade interface provide read operations for entity
 *
 * @param <T>  - entity
 * @param <PK> - primary key
 */
@Local
public interface CommonLocalFacade<T extends AbstractPersistentEntity, PK> {

    T findByPk(PK key);

    List<T> findAll();

}
