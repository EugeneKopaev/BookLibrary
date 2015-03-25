package org.booklibrary.app.manager.beans;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.manager.exceptions.EntityManagerException;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;
import org.slf4j.Logger;

import javax.ejb.*;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

/**
 * Author Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see org.booklibrary.app.manager.AuthorManagerLocal
 */
@Stateless
@Local
public class AuthorManager implements AuthorManagerLocal{

    @Inject
    private Logger logger;

    @EJB
    private AuthorFacadeLocal authorFacade;

    @EJB
    private AuthorHomeLocal authorHome;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author save(Author obj) throws EntityManagerException {
        logger.debug("save invoked for object: {}", obj);
        Author entity;
        try {
            entity = authorHome.save(obj);
        } catch (Exception e) {
            String errorMsg = "Failed to persist object: {}";
            logger.error(errorMsg, obj);
            throw new EntityManagerException(errorMsg, e);
        }
        logger.debug("Persist entity: {}", entity);
        return entity;
    }

    public Author update(Author obj) throws EntityManagerException {
        logger.debug("update invoked for obj: {}", obj);
        Author updated;
        try {
            updated = authorHome.update(obj);
        } catch (Exception e) {
            String errorMsg = "Failed to update object: {}";
            logger.error(errorMsg, obj);
            throw new EntityManagerException(errorMsg, e);
        }
        logger.debug("Update entity: {}", updated);
        return updated;
    }

    public void removeByPk(EntityIdentifier key) throws EntityManagerException {
        logger.debug("Remove called for entity with key: {}", key);
        try {
            authorHome.removeByPk(key);
        } catch (Exception e) {
            String errorMsg = "Failed to remove object with key: {}";
            logger.error(errorMsg, key);
            throw new EntityManagerException(errorMsg, e);
        }
    }

    public void removeByUuid(String uuid) throws EntityManagerException {
        removeByPk(new EntityIdentifier(uuid));
    }

    public void removeAll() throws EntityManagerException {
        logger.debug("Remove all invoked");
        try {
            authorHome.removeAll();
        } catch (Exception e) {
            String errorMsg = "Failed to remove all objects ";
            logger.error(errorMsg);
            throw new EntityManagerException(errorMsg, e);
        }
    }

    public Author findByPk(EntityIdentifier key) {
        logger.debug("Find invoked for object with key: {}", key);
        return authorFacade.findByPk(key);
    }

    public Author findByUuid(String uuid) {
        logger.debug("Find invoked for object with key: {}", uuid);
        return authorFacade.findByUuid(uuid);
    }

    public List<Author> findAll() {
        logger.debug("Find all called");
        List<Author> authors =  authorFacade.findAll();
        return authors;
    }

    public List<Author> findSegment(int start, int size) {
        logger.debug("Find segment called");
        List<Author> authors = authorFacade.findSegment(start, size);
        return authors;
    }
}
