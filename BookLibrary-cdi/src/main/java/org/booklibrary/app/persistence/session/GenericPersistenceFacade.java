package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;

import javax.ejb.Local;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Generic interface for all entity facades interfaces.
 * k
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
     */
    T findByPk(PK key) throws PersistenceException;

    /**
     * Find object in the database by UUID.
     *
     * @param uuid
     */
    T findByUuid(String uuid) throws PersistenceException;

    /**
     * Find all objects in the database.
     *
     * @param key
     */
    List<T> findAll();

}
