package org.booklibrary.app.persistence.session.common;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Generic interface for all entity facades interfaces.
 * Facade interface provide read operations for entity.
 *
 * @param <T>  - entity
 * @param <K> - primary key
 */
@Local
public interface GenericFacadeLocal<T extends AbstractBaseEntity, K> {

    /**
     * Find object in the database by primary key.
     *
     * @param key
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
     * @return List<T> list of entities
     */
    List<T> findAll();

    /**
     * Find limited segment of objects.
     *
     * @param start first result
     * @param size max size
     * @return List<T> list of entities
     */
    List<T> findSegment(int start, int size);

}
