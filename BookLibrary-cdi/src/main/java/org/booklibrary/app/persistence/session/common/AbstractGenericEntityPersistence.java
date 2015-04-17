package org.booklibrary.app.persistence.session.common;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Base abstract class for all entity persistence related classes.
 *
 * @see org.booklibrary.app.persistence.session.common.GenericHomeLocal
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
public abstract class AbstractGenericEntityPersistence<T extends AbstractBaseEntity, K>
        implements GenericHomeLocal<T, K>, GenericFacadeLocal<T, K> {

    private static final Logger LOG = LoggerFactory.getLogger(
            AbstractGenericEntityPersistence.class);

    private final Class<T> entityClass;

    public AbstractGenericEntityPersistence() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    // will be injected in implementations
    public abstract EntityManager getEntityManager();

    @Override
    public T findByPk(K key) {

        if (key == null) {
            String errorMsg = "null argument passed to find";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        LOG.debug("find invoked for entity of type [" + entityClass.getCanonicalName() +
                "] with pk [" + key + "]");

        T entity = getEntityManager().find(entityClass, key);
        LOG.debug("result entity: {}", entity);
        return entity;
    }

    @Override
    public T findByUuid(String uuid) {
        if (uuid == null) {
            String errorMsg = "null argument passed to find";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        K key = (K) uuid;
        return findByPk(key);
    }

    @Override
    public List<T> findAll() {
        LOG.debug("findAll invoked for entities of type: {}", entityClass.getCanonicalName());
        List<T> resultList = getEntityManager().createQuery("FROM "
                + entityClass.getName()).getResultList();
        LOG.debug("result list size: {}", resultList.size());
        return resultList;
    }

    public List<T> findRange(int start, int size) {
        LOG.debug("findRange invoked for entities of type: {}", entityClass.getCanonicalName());
        List<T> resultList = getEntityManager()
                .createQuery("SELECT a FROM " + entityClass.getName() + " a")
                .setFirstResult(start)
                .setMaxResults(size)
                .getResultList();
        LOG.debug("result list size: {}", resultList.size());
        return resultList;
    }

    public int countEntity() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public T save(T obj) {
        LOG.debug("persist invoked for object: {}", obj);
        getEntityManager().persist(obj);
        return obj;
    }

    @Override
    public T update(T obj) {
        LOG.debug("update invoked for object {}", obj);
        return getEntityManager().merge(obj);
    }

    @Override
    public void clear() {
        LOG.debug("clear invoked");
        getEntityManager().clear();
    }

    @Override
    public void removeByPk(K key) {
        if (key == null) {
            String errorMsg = "null argument passed to removeSelected";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        LOG.debug("removeSelected invoked for entity of type: [" +
                entityClass.getCanonicalName() + "] with key: [" + key + "]");
        Object existEntity = getEntityManager().getReference(entityClass, key);
        getEntityManager().remove(existEntity);

    }

    @Override
    public void removeByUuid(String uuid) {
        if (uuid == null) {
            String errorMsg = "null argument passed to removeSelected";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        K key = (K) uuid;
        removeByPk(key);

    }

    public int removeList(List<K> keys) {
        if (keys == null) {
            String errorMsg = "null argument passed to removeList";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        String entityName = entityClass.getSimpleName();
        return getEntityManager()
                .createQuery("DELETE FROM " + entityName + " e WHERE e.id IN (:keys)")
                .setParameter("keys", keys)
                .executeUpdate();
    }

    @Override
    public int removeAll() {
        LOG.debug("removeAll invoked for entities of type: {}",
                entityClass.getCanonicalName());
        String entityName = entityClass.getSimpleName();
        String deleteQuery = "DELETE FROM " + entityName;
        int resultSize = getEntityManager().createQuery(deleteQuery)
                .executeUpdate();
        getEntityManager().flush();
        LOG.debug("removed row size: {}", resultSize);
        return resultSize;
    }
}
