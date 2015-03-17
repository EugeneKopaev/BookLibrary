package org.booklibrary.app.manager;

import org.booklibrary.app.manager.exceptions.EntityPersistenceException;
import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface GenericManagerLocal<T extends AbstractBaseEntity, PK> {

    /**
     * Persist object in the database.
     *
     * @param obj
     * @return Created Entity
     * @throws org.booklibrary.app.manager.exceptions.EntityPersistenceException
     */
    T save(T obj) throws EntityPersistenceException;

    /**
     * Update entity in the database.
     *
     * @param obj
     * @return Updated Entity
     *@throws org.booklibrary.app.manager.exceptions.EntityPersistenceException
     */
    T update(T obj) throws EntityPersistenceException;

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key
     * @throws org.booklibrary.app.manager.exceptions.EntityPersistenceException
     */
    void remove(PK key) throws EntityPersistenceException;

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     * @throws org.booklibrary.app.manager.exceptions.EntityPersistenceException
     */
    void remove(String uuid) throws EntityPersistenceException;

    /**
     * Removes all entities from database.
     *
     * @throws org.booklibrary.app.manager.exceptions.EntityPersistenceException
     */
    void removeAll() throws EntityPersistenceException;

    /**
     * Find object in the database by primary key.
     *
     * @param key
     * @return T entity
     */
    T findByPk(PK key);

    /**
     * Find object in the database by UUID.
     *
     * @param uuid
     * @return T entity
     */
    T findByUuid(String uuid);

    /**
     * Find all objects in the database.
     *
     * @param key
     * @return List<T> list of entities
     */
    List<T> findAll();
}
