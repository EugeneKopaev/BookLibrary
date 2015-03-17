package org.booklibrary.app.manager.beans;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.manager.exceptions.EntityPersistenceException;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

@Stateless
public class AuthorManager implements AuthorManagerLocal{

    private final transient Logger LOG = LoggerFactory.getLogger(getClass());

    @EJB
    private AuthorFacadeLocal authorFacade;

    @EJB
    private AuthorHomeLocal authorHome;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author save(Author obj) throws EntityPersistenceException{

        Author entity;
        try {
            entity = authorHome.save(obj);
        } catch (Exception e) {
            String errorMsg = "Failed to persist object: {}";
            LOG.error(errorMsg, obj);
            throw new EntityPersistenceException(errorMsg, e);
        }
        LOG.debug("Persist entity: {}", entity);
        return entity;
    }

    @Override
    public Author update(Author obj) throws EntityPersistenceException {
        Author updated;
        try {
            updated = authorHome.update(obj);
        } catch (Exception e) {
            String errorMsg = "Failed to update object: {}";
            LOG.error(errorMsg, obj);
            throw new EntityPersistenceException(errorMsg, e);
        }
        LOG.debug("Update entity: {}", updated);
        return updated;
    }

    @Override
    public void remove(EntityIdentifier key) throws EntityPersistenceException {
        try {
            authorHome.remove(key);
        } catch (Exception e) {
            String errorMsg = "Failed to remove object with pk: {}";
            LOG.error(errorMsg, key);
            throw new EntityPersistenceException(errorMsg, e);
        }
        LOG.debug("Remove entity with pk: {}", key);
    }

    @Override
    public void remove(String uuid) throws EntityPersistenceException{

    }

    @Override
    public void removeAll() throws EntityPersistenceException{

    }

    @Override
    public Author findByPk(EntityIdentifier key) {
        return null;
    }

    @Override
    public Author findByUuid(String uuid) {

        return authorFacade.findByUuid(uuid);
    }

    @Override
    public List<Author> findAll() {

        return authorFacade.findAll();
    }
}