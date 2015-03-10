package org.booklibrary.app.manager;

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
     */
    T save(T obj);

    /**
     * Update entity in the database.
     *
     * @param obj
     * @return Updated Entity
     */
    T update(T obj);

    /**
     * Delete the entity from the database by primary key.
     *
     * @param key
     */
    void remove(PK key);

    /**
     * Delete the entity from the database by string uuid.
     *
     * @param uuid
     */
    void remove(String uuid);

    /**
     * Removes all entities from database.
     */
    void removeAll();

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
