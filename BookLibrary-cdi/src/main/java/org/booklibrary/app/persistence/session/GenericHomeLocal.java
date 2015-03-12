package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

/**
 * Generic interface for all entity home interfaces.
 * Home interface provide write operations for entity.
 */
@Local
public interface GenericHomeLocal<T extends AbstractBaseEntity, PK> {

    /**
     * Persist object in the database.
     *
     * @param obj
     * @return Created Entity
     * @throws PersistenceException
     */
    T save(T obj) throws Exception;

    /**
     * Update entity in the database.
     *
     * @param obj
     * @return Updated Entity
     * @throws PersistenceException
     */
    T update(T obj) throws Exception;

    /**
     * Clear the persistence context
     */
    void clear();

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key
     */
    void remove(PK key) throws Exception;

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     */
    void remove(String uuid) throws Exception;

    /**
     * Removes all entities from database.
     *
     */
    void removeAll() throws Exception;

}
