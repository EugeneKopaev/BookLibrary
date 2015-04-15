package org.booklibrary.app.manager.beans;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.manager.AuthorManagerLocal;
import org.booklibrary.app.persistence.entity.Author;
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

    public Author save(Author obj) {
        throw new UnsupportedOperationException("use saveUnique method instead");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Author saveUnique(Author obj) throws DuplicateResourceException {
        logger.debug("save invoked for object: {}", obj);
        validateAuthor(obj);
        return authorHome.save(obj);
    }

    @Override
    public List<Author> findAllByBookAvgRating(Double rate) {
        logger.debug("find by book avg rating invoked");
        return authorFacade.findByBookAvgRating(rate);
    }

    public Author update(Author obj) {
        logger.debug("update invoked for obj: {}", obj);
        return authorHome.update(obj);
    }

    public void removeByPk(String key) {
        logger.debug("Remove called for entity with key: {}", key);
        authorHome.removeByPk(key);
    }

    public void removeByUuid(String uuid) {
        removeByPk(uuid);
    }

    public void removeList(List<String> keys) {
        logger.debug("remove list invoked");
        authorHome.removeList(keys);
    }

    public void removeAll() {
        logger.debug("Remove all invoked");
        authorHome.removeAll();
    }

    public Author findByPk(String key) {
        logger.debug("Find invoked for object with key: {}", key);
        return authorFacade.findByPk(key);
    }

    public Author findByUuid(String uuid) {
        logger.debug("Find invoked for object with key: {}", uuid);
        return authorFacade.findByUuid(uuid);
    }

    public List<Author> findAll() {
        logger.debug("Find all called");
        return authorFacade.findAll();
    }

    public List<Author> findRange(int start, int size) {
        logger.debug("Find segment called");
        return authorFacade.findRange(start, size);
    }

    public int countEntity() {
        logger.debug("Count all authors");
        int count = authorFacade.countEntity();
        logger.debug("result: " + count);
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
