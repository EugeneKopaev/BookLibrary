package org.booklibrary.app.persistence.repository;

import org.booklibrary.app.persistence.entity.AbstractPersistentEntity;

import javax.ejb.Local;

/**
 * Home interface provide write operations for entity
 *
 * @param <T>  - entity
 * @param <PK> - primary key
 */
@Local
public interface CommonLocalHome<T extends AbstractPersistentEntity, PK> {

    void create(T entity);

    T update(T entity);

    void remove(PK key);

}
