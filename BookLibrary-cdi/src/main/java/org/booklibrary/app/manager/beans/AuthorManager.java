package org.booklibrary.app.manager.beans;

import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.id.EntityIdentifier;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
 * Author Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see org.booklibrary.app.manager.AuthorManagerLocal
 */
@Stateless
public class AuthorManager implements AuthorManagerLocal {

    @Inject
    private Logger logger;

    @EJB
    private AuthorFacadeLocal authorFacade;

    @EJB
    private AuthorHomeLocal authorHome;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author save(Author obj) {
        logger.debug("save invoked for object: {}", obj);
        Author entity = authorHome.save(obj);
        return entity;
    }

    public Author update(Author obj) {
        logger.debug("update invoked for obj: {}", obj);
        Author updated = authorHome.update(obj);
        return updated;
    }

    public void removeByPk(EntityIdentifier key) {
        logger.debug("Remove called for entity with key: {}", key);
        authorHome.removeByPk(key);
    }

    public void removeByUuid(String uuid) {
        removeByPk(new EntityIdentifier(uuid));
    }

    public void removeAll() {
        logger.debug("Remove all invoked");
        authorHome.removeAll();
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
        List<Author> authors = authorFacade.findAll();
        return authors;
    }

    public List<Author> findSegment(int start, int size) {
        logger.debug("Find segment called");
        List<Author> authors = authorFacade.findSegment(start, size);
        return authors;
    }
}
