package org.booklibrary.app.persistence.session.common;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;
import org.booklibrary.app.persistence.session.common.GenericHomeLocal;
import org.slf4j.Logger;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * Base abstract class for all entity persistence related classes.
 *
 * @see org.booklibrary.app.persistence.session.common.GenericHomeLocal
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
public abstract class AbstractGenericEntityPersistence<T extends AbstractBaseEntity, PK>
        implements GenericHomeLocal<T, PK>, GenericFacadeLocal<T, PK> {

    private Class<T> entityClass;

    // will be injected in implementations
    public abstract EntityManager getEntityManager();
    public abstract Logger getLogger();

    public AbstractGenericEntityPersistence() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findByPk(PK key) {

        if (key == null) {
            String errorMsg = "null argument passed to find";
            getLogger().error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        getLogger().debug("find invoked for entity of type [" + entityClass.getCanonicalName() +
                "] with pk [" + key + "]");

        T entity = getEntityManager().find(entityClass, key);
        getLogger().debug("result entity: {}", entity);
        return entity;
    }

    @Override
    public T findByUuid(String uuid) {
        if (uuid == null) {
            String errorMsg = "null argument passed to find";
            getLogger().error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        PK key = (PK) new EntityIdentifier(uuid);
        return findByPk(key);
    }

    @Override
    public List<T> findAll() {
        getLogger().debug("findAll invoked for entities of type: {}", entityClass.getCanonicalName());
        List<T> resultList = getEntityManager().createQuery("FROM "
                + entityClass.getName()).getResultList();
        getLogger().debug("result list size: {}", resultList.size());
        return resultList;
    }

    public List<T> findSegment(int start, int size) {
        getLogger().debug("findSegment invoked for entities of type: {}", entityClass.getCanonicalName());
        List<T> resultList = getEntityManager()
                .createQuery("SELECT a FROM " +  entityClass.getName() + " a")
                .setFirstResult(start)
                .setMaxResults(size)
                .getResultList();
        return resultList;
    }

    @Override
    public T save(T obj) throws Exception {
        getLogger().debug("persist invoked for object: {}", obj);
        getEntityManager().persist(obj);
        return obj;
    }

    @Override
    public T update(T obj) throws Exception {
        getLogger().debug("update invoked for object {}", obj);
        return getEntityManager().merge(obj);
    }

    @Override
    public void clear() {
        getLogger().debug("clear invoked");
        getEntityManager().clear();
    }

    @Override
    public void removeByPk(PK key) throws Exception {
        if (key == null) {
            String errorMsg = "null argument passed to remove";
            getLogger().error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        getLogger().debug("remove invoked for entity of type: [" +
                entityClass.getCanonicalName() + "] with key: [" + key + "]");
        Object existEntity = getEntityManager().getReference(entityClass, key);
        getEntityManager().remove(existEntity);

    }

    @Override
    public void removeByUuid(String uuid) throws Exception {
        if (uuid == null) {
            String errorMsg = "null argument passed to remove";
            getLogger().error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        PK key = (PK) new EntityIdentifier(uuid);
        removeByPk(key);

    }

    @Override
    public int removeAll() throws Exception {
        getLogger().debug("removeAll invoked for entities of type: {}",
                entityClass.getCanonicalName());
        String entityName = entityClass.getSimpleName();
        String deleteQuery = "DELETE FROM " + entityName;
        int resultSize = getEntityManager().createQuery(deleteQuery)
                .executeUpdate();
        getEntityManager().flush();
        getLogger().debug("removed row size: {}", resultSize);
        return resultSize;
    }
}
