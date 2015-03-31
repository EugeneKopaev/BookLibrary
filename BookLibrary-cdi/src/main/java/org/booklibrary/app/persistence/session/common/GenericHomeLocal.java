package org.booklibrary.app.persistence.session.common;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

/**
 * Generic interface for all entity home interfaces.
 * Home interface provide write operations for entity.
 */
@Local
public interface GenericHomeLocal<T extends AbstractBaseEntity, K> {

    /**
     * Persist object in the database.
     *
     * @param obj
     * @return Created Entity
     * @throws PersistenceException
     */
    T save(T obj);

    /**
     * Update entity in the database.
     *
     * @param obj
     * @return Updated Entity
     * @throws PersistenceException
     */
    T update(T obj);

    /**
     * Clear the persistence context
     */
    void clear();

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key
     */
    void removeByPk(K key);

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     */
    void removeByUuid(String uuid);

    /**
     * Removes all entities from database.
     *
     */
    int removeAll();

}
