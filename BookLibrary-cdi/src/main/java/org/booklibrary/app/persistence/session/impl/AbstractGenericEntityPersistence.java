package org.booklibrary.app.persistence.session.impl;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.GenericPersistenceFacade;
import org.booklibrary.app.persistence.session.GenericPersistenceHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Base abstract class for all entity persistence related classes.
 */
public abstract class AbstractGenericEntityPersistence<T extends AbstractBaseEntity, PK>
        implements GenericPersistenceHome<T, PK>, GenericPersistenceFacade<T, PK> {

    private final transient Logger LOG = LoggerFactory.getLogger(getClass());

    private Class<T> entityClass;

    public abstract EntityManager getEntityManager();

    public AbstractGenericEntityPersistence() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findByPk(PK key) {
        long startTime = System.currentTimeMillis();

        if (key == null) {
            String errorMsg = "null argument passed to find";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }


        LOG.debug("findByPk invoked for entity of type [" + entityClass.getCanonicalName() +
                "] with pk [" + key + "]");

        T foundEntity = getEntityManager().find(entityClass, key);

        LOG.debug("Find by pk [" + key + "] complete; found [" + foundEntity + "]; took [" +
                (System.currentTimeMillis() - startTime) + "ms]");

        return foundEntity;

    }

    @Override
    public T findByUuid(String uuid) {
        if (uuid == null) {
            String errorMsg = "null argument passed to find";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        PK key = (PK) new EntityIdentifier(uuid);
        return findByPk(key);
    }

    @Override
    public List<T> findAll() {

        LOG.debug("findAll invoked for entities [" + entityClass.getCanonicalName() + "]");
        return getEntityManager().createQuery("FROM "
                + entityClass.getName()).getResultList();
    }

    @Override
    public T save(T obj) throws PersistenceException {

        long startTime = System.currentTimeMillis();

        if (obj == null) {
            String errorMsg = "null argument passed to persist";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        try {
            LOG.debug("persist invoked for entity [" + obj + "]");
            EntityManager entityManager = getEntityManager();
            entityManager.persist(obj);
            entityManager.flush();

        } catch (Exception e) {
            String errorMsg = "Failed to persist entity [" + obj + "]";
            LOG.error(errorMsg, e);
            throw new PersistenceException(errorMsg, e);
        }
        LOG.debug("Persist complete for [" + obj + "]; took [" +
                (System.currentTimeMillis() - startTime) + "ms]");

        return obj;
    }

    @Override
    public T update(T obj) throws PersistenceException {
        long startTime = System.currentTimeMillis();

        if (null == obj) {
            String errorMsg = "null argument passed to update";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        try {
            LOG.debug("update invoked for entity [" + obj + "]");
            EntityManager entityManager = getEntityManager();
            entityManager.merge(obj);
            entityManager.flush();

        } catch (Exception e) {
            String errorMsg = "Failed to update entity [" + obj + "]";
            LOG.error(errorMsg, e);
            throw new PersistenceException(errorMsg, e);
        }

        LOG.debug("Update complete for [" + obj + "]; took [" +
                (System.currentTimeMillis() - startTime) + "ms]");

        return obj;
    }

    @Override
    public void refresh(T obj) throws PersistenceException {

        long startTime = System.currentTimeMillis();

        if (null == obj) {
            String errorMsg = "null argument passed to refresh";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        try {
            LOG.debug("refresh invoked for entity [" + obj + "]");
            EntityManager entityManager = getEntityManager();
            if (entityManager.contains(obj)) {
                entityManager.refresh(obj);
            } else {
                LOG.warn("refreshing entity is not managed [" + obj + "]");
            }

        } catch (Exception e) {
            String errorMsg = "Failed to refresh entity [" + obj + "]";
            LOG.error(errorMsg, e);
            throw new PersistenceException(errorMsg, e);
        }

        LOG.debug("Refresh complete for [" + obj + "]; took [" +
                (System.currentTimeMillis() - startTime) + "ms]");
    }

    @Override
    public void clear() {
        LOG.debug("clear invoked");
        getEntityManager().clear();

    }

    @Override
    public void remove(PK key) throws PersistenceException {

        long startTime = System.currentTimeMillis();

        if (key == null) {
            String errorMsg = "null argument passed to remove";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        try {
            LOG.debug("remove invoked for entity of type: [" +
                    entityClass.getCanonicalName() + "] with pk: [" + key + "]");

            Object existEntity = findByPk(key);
            EntityManager entityManager = getEntityManager();
            entityManager.remove(existEntity);
            entityManager.flush();

        } catch (Exception e) {
            String errorMsg = "Failed to remove entity of type [" + entityClass.getCanonicalName() +
                    "] with pk [" + key + "]";
            LOG.error(errorMsg, e);
            throw new PersistenceException(errorMsg, e);
        }

        LOG.debug("Remove complete for [" + entityClass.getCanonicalName() + "] with pk: [" + key + "]; took ["
                + (System.currentTimeMillis() - startTime) + "ms]");

    }

    @Override
    public void remove(String uuid) throws PersistenceException {

        if (uuid == null) {
            String errorMsg = "null argument passed to remove";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        PK key = (PK) new EntityIdentifier(uuid);
        remove(key);

    }

    @Override
    public void removeAll() throws PersistenceException {
        try {
            LOG.debug("removeAll invoked for entities of type: [" +
                    entityClass.getCanonicalName() + "]");
            EntityManager entityManager = getEntityManager();
            String entityName = entityClass.getSimpleName();
            String deleteQuery = "DELETE FROM " + entityName;
            entityManager.createQuery(deleteQuery, entityClass);
        } catch (Exception e) {
            String errorMsg = "Failed to remove entities of type [" + entityClass.getCanonicalName() + "]";
            LOG.error(errorMsg, e);
            throw new PersistenceException(errorMsg, e);
        }
        LOG.debug("Remove all complete for [" + entityClass.getCanonicalName() + "]");
    }
}
