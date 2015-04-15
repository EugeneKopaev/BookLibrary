package org.booklibrary.app.manager.beans;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
import org.booklibrary.app.persistence.session.AuthorFacadeLocal;
import org.booklibrary.app.persistence.session.AuthorHomeLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Author Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see org.booklibrary.app.manager.AuthorManagerLocal
 */
@Named
@Stateless
public class AuthorManager implements AuthorManagerLocal {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorManager.class);

    @EJB
    private AuthorFacadeLocal authorFacade;

    @EJB
    private AuthorHomeLocal authorHome;

    public Author save(Author obj) {
        throw new UnsupportedOperationException("Use saveUnique method instead");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author saveUnique(Author obj) throws DuplicateResourceException {
        LOG.debug("save invoked for object: {}", obj);
        validateAuthor(obj);
        return authorHome.save(obj);
    }

    @Override
    public List<Author> findAllByBookAvgRating(Double rate) {
        LOG.debug("find by book avg rating invoked");
        return authorFacade.findByBookAvgRating(rate);
    }

    public Author update(Author obj) {
        LOG.debug("update invoked for obj: {}", obj);
        return authorHome.update(obj);
    }

    public void removeByPk(String key) {
        LOG.debug("Remove called for entity with key: {}", key);
        authorHome.removeByPk(key);
    }

    public void removeByUuid(String uuid) {
        removeByPk(uuid);
    }

    public void removeList(List<String> keys) {
        LOG.debug("remove list invoked");
        authorHome.removeList(keys);
    }

    public void removeAll() {
        LOG.debug("Remove all invoked");
        authorHome.removeAll();
    }

    public Author findByPk(String key) {
        LOG.debug("Find invoked for object with key: {}", key);
        return authorFacade.findByPk(key);
    }

    public Author findByUuid(String uuid) {
        LOG.debug("Find invoked for object with key: {}", uuid);
        return authorFacade.findByUuid(uuid);
    }

    public List<Author> findAll() {
        LOG.debug("Find all called");
        return authorFacade.findAll();
    }

    public List<Author> findRange(int start, int size) {
        LOG.debug("Find segment called");
        return authorFacade.findRange(start, size);
    }

    public int countEntity() {
        LOG.debug("Count all authors");
        int count = authorFacade.countEntity();
        LOG.debug("result: " + count);
        return count;
    }

    private void validateAuthor(Author author) throws DuplicateResourceException {
        if (checkAuthorAlreadyExists(author.getFirstName(), author.getLastName())) {
            throw new DuplicateResourceException("Author already exist");
        }
    }

    private boolean checkAuthorAlreadyExists(String firstName, String lastName) {
        Author author = authorFacade.findByFirstAndLastName(firstName, lastName);
        return author != null;
    }
}
