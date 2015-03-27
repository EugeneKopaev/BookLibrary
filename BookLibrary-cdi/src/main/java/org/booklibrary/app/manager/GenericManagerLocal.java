package org.booklibrary.app.manager;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Generic interface for all managers interfaces.
 * Manager interface provide business logic operations
 *
 * @param <T>  entity class
 * @param <PK> primary key
 */
@Local
public interface GenericManagerLocal<T extends AbstractBaseEntity, PK> {

    /**
     * Persist object in the database.
     *
     * @param obj object to save
     * @return T created entity
     */
    T save(T obj);

    /**
     * Update entity in the database.
     *
     * @param obj object to update
     * @return T updated entity
     */
    T update(T obj);

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key primary key
     */
    void removeByPk(PK key);

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    void removeByUuid(String uuid);

    /**
     * Removes all entities from database.
     *
     * @throws org.booklibrary.app.manager.exceptions.EntityManagerException
     */
    void removeAll();

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
     * @param start index
     * @param size  result list size
     * @return List
     */
    List<T> findSegment(int start, int size);
}
