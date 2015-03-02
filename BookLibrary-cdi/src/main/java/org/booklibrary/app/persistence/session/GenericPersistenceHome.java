package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.persistence.PersistenceException;

/**
 * Generic interface for all entity home interfaces.
 * Home interface provide write operations for entity.
 */
public interface GenericPersistenceHome<T extends AbstractBaseEntity, PK> {

    /**
     * Persist object in the database.
     *
     * @param obj
     * @return Created Entity
     * @throws PersistenceException
     */
    T save(T obj) throws PersistenceException;

    /**
     * Update entity in the database.
     *
     * @param obj
     * @return Updated Entity
     * @throws PersistenceException
     */
    T update(T obj) throws PersistenceException;

    /**
     * Refresh the state of the instance from the database
     *
     * @param obj
     * @throws PersistenceException
     */
    void refresh(T obj) throws PersistenceException;

    /**
     * Clear the persistence context
     */
    void clear();

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key
     */
    void remove(PK key) throws PersistenceException;

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     */
    void remove(String uuid) throws PersistenceException;

    /**
     * Removes all entities from database.
     *
     * @return removed records count
     */
    void removeAll();

}
