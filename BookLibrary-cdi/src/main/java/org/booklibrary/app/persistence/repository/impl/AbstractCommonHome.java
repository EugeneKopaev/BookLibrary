package org.booklibrary.app.persistence.repository.impl;

import org.booklibrary.app.persistence.entity.AbstractPersistentEntity;
import org.booklibrary.app.persistence.repository.CommonLocalHome;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;

/**
 * Common Home implementation
 */
public abstract class AbstractCommonHome<T extends AbstractPersistentEntity, PK>
        implements CommonLocalHome<T, PK>{

    private Class<T> entityClass;

    public abstract EntityManager getEntityManager();

    public AbstractCommonHome() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    @Override
    public void remove(PK key) {
        EntityManager entityManager = getEntityManager();
        T entity = entityManager.find(entityClass, key);
        if (entity == null) {
            throw new NullPointerException("Entity not found");
        }
        entityManager.remove(entity);
        entityManager.flush();
    }
}
