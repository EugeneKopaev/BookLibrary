package org.booklibrary.app.manager;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Generic interface for all managers interfaces.
 * Manager interface provide business logic operations
 *
 * @param <T> entity class
 * @param <K> primary key
 */
@Local
public interface GenericManagerLocal<T extends AbstractBaseEntity, K> {

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
    void removeAll();

    /**
     * Find object in the database by primary key.
     *
     * @param key primary key
     * @return T entity
     */
    T findByPk(K key);

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
