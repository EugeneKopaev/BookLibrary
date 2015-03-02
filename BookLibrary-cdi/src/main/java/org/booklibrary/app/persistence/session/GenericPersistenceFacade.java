package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Generic interface for all entity facades interfaces.
 * Facade interface provide read operations for entity.
 *
 * @param <T>  - entity
 * @param <PK> - primary key
 */
@Local
public interface GenericPersistenceFacade<T extends AbstractBaseEntity, PK> {

    /**
     * Find object in the database by primary key.
     *
     * @param key
     * @return T entity
     */
    T findByPk(PK key) throws PersistenceException;

    /**
     * Find object in the database by UUID.
     *
     * @param uuid
     * @return T entity
     */
    T findByUuid(String uuid) throws PersistenceException;

    /**
     * Find all objects in the database.
     *
     * @param key
     * @return List<T> list of entities
     */
    List<T> findAll();

}
