package org.booklibrary.app.persistence.session.beans;

import org.booklibrary.app.persistence.entity.AbstractBaseEntity;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.GenericFacadeLocal;
import org.booklibrary.app.persistence.session.GenericHomeLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Base abstract class for all entity persistence related classes.
 */
public abstract class AbstractGenericEntityPersistence<T extends AbstractBaseEntity, PK>
        implements GenericHomeLocal<T, PK>, GenericFacadeLocal<T, PK> {

    private final transient Logger LOG = LoggerFactory.getLogger(getClass());

    private Class<T> entityClass;

    public abstract EntityManager getEntityManager();

    public AbstractGenericEntityPersistence() {
        this.entityClass = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findByPk(PK key) {

        if (key == null) {
            String errorMsg = "null argument passed to find";
            LOG.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }

        LOG.debug("findByPk invoked for entity of type [" + entityClass.getCanonicalName() +
                "] with pk [" + key + "]");

        return getEntityManager().find(entityClass, key);
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
    public T save(T obj) throws Exception {

        LOG.debug("persist invoked for object: {}", obj);
        getEntityManager().persist(obj);
        return obj;
    }

    @Override
    public T update(T obj) throws Exception {

        LOG.debug("update invoked for object {}", obj);
        getEntityManager().merge(obj);
        return obj;
    }

    @Override
    public void clear() {
        LOG.debug("clear invoked");
        getEntityManager().clear();
    }

    @Override
    public void remove(PK key) throws Exception {

        LOG.debug("remove invoked for entity of type: [" +
                entityClass.getCanonicalName() + "] with pk: [" + key + "]");

        Object existEntity = getEntityManager().getReference(entityClass, key);
        getEntityManager().remove(existEntity);
    }

    @Override
    public void remove(String uuid) throws Exception {
        LOG.debug("remove invoked for entity of type: [" +
                entityClass.getCanonicalName() + "] with uuid: [" + uuid + "]");
        PK key = (PK) new EntityIdentifier(uuid);
        remove(key);

    }

    @Override
    public void removeAll() throws Exception {
        LOG.debug("removeAll invoked for entities of type: {}",
                entityClass.getCanonicalName());
        String entityName = entityClass.getSimpleName();
        String deleteQuery = "DELETE FROM " + entityName;
        getEntityManager().createQuery(deleteQuery)
                .executeUpdate();
        getEntityManager().flush();
    }
}
