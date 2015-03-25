package org.booklibrary.app.manager;

import org.booklibrary.app.manager.exceptions.EntityManagerException;
import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Generic interface for all managers interfaces.
 * Manager interface provide business logic operations
 *
 * @param <T> entity class
 * @param <PK> primary key
 */
@Local
public interface GenericManagerLocal<T extends AbstractBaseEntity, PK> {

    /**
     * Persist object in the database.
     *
     * @param obj object to save
     * @return T created entity
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    T save(T obj) throws EntityManagerException;

    /**
     * Update entity in the database.
     *
     * @param obj object to update
     * @return T updated entity
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    T update(T obj) throws EntityManagerException;

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key primary key
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    void removeByPk(PK key) throws EntityManagerException;

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    void removeByUuid(String uuid) throws EntityManagerException;

    /**
     * Removes all entities from database.
     *
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    void removeAll() throws EntityManagerException;

    /**
     * Find object in the database by primary key.
     *
     * @param key primary key
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
     * @return List
     */
    List<T> findAll();

    /**
     * Find segment of objects in the database.
     *
     * @param start first result
     * @param size max size
     * @return List<T> list of entities
     */
    List<T> findSegment(int start, int size);
}
