package org.booklibrary.app.persistence.repository.impl;

import org.booklibrary.app.persistence.entity.AbstractPersistentEntity;
import org.booklibrary.app.persistence.repository.CommonLocalFacade;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Common Facade implementation
 */
public abstract class AbstractCommonFacade<T extends AbstractPersistentEntity, PK>
        implements CommonLocalFacade<T, PK> {

    private Class<T> entityClass;

    public abstract EntityManager getEntityManager();

    public AbstractCommonFacade() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findByPk(PK key) {
        EntityManager entityManager = getEntityManager();
        if (key == null) {
            throw new NullPointerException("Primary key can't be null");
        }
        return entityManager.find(entityClass, key);
    }

    @Override
    public List<T> findAll() {
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery(
                "SELECT en from " + entityClass.getSimpleName() + " en", entityClass)
                .getResultList();
    }
}
