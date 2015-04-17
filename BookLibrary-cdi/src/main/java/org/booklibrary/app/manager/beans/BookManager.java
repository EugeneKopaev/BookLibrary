package org.booklibrary.app.manager.beans;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.manager.BookManagerLocal;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.session.BookFacadeLocal;
import org.booklibrary.app.persistence.session.BookHomeLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import java.util.List;

/**
 * Book Manager implementation.
 * This bean encapsulate business logic.
 *
 * @see org.booklibrary.app.manager.BookManagerLocal
 */
@Named
@Stateless
public class BookManager implements BookManagerLocal {

    private static final Logger LOG = LoggerFactory.getLogger(BookManager.class);

    @EJB
    private BookFacadeLocal bookFacade;

    @EJB
    private BookHomeLocal bookHome;

    public Book save(Book obj) {
        throw new UnsupportedOperationException("use saveUnique method instead");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Book saveUnique(Book obj) throws DuplicateResourceException {
        LOG.debug("save invoked for object: {}", obj);
        validateBook(obj);
        return bookHome.save(obj);
    }


    @Override
    public Book update(Book obj) {
        LOG.debug("update invoked for obj: {}", obj);
        return bookHome.update(obj);
    }

    @Override
    public void removeByPk(String key) {
        LOG.debug("Remove called for entity with key: {}", key);
        bookHome.removeByPk(key);
    }

    @Override
    public void removeByUuid(String uuid) {
        removeByPk(uuid);
    }

    public void removeList(List<String> keys) {
        LOG.debug("Remove list invoked");
        bookHome.removeList(keys);
    }

    @Override
    public void removeAll() {
        LOG.debug("Remove all invoked");
        bookHome.removeAll();
    }

    @Override
    public Book findByPk(String key) {
        LOG.debug("Find invoked for object with key: {}", key);
        return bookFacade.findByPk(key);
    }

    @Override
    public Book findByUuid(String uuid) {
        LOG.debug("Find invoked for object with key: {}", uuid);
        return bookFacade.findByUuid(uuid);
    }

    @Override
    public List<Book> findAll() {
        LOG.debug("Find all called");
        return bookFacade.findAll();
    }

    @Override
    public List<Book> findRange(int start, int size) {
        LOG.debug("Find segment called");
        return bookFacade.findRange(start, size);
    }

    @Override
    public int countEntity() {
        LOG.debug("Count all books");
        int count = bookFacade.countEntity();
        LOG.debug("result: " + count);
        return count;
    }

    @Override
    public Double countAvgBookRating(String id) {
        LOG.debug("Count boor rating");
        double rating = bookFacade.countBookRating(id);
        LOG.debug("result: " + rating);
        return rating;
    }

    @Override
    public List<Book> findAllByAuthor(String id) {
        LOG.debug("Find by author called");
        return bookFacade.findAllByAuthor(id);
    }

    @Override
    public List<Book> findAllByReviewRating(int rate) {
        LOG.debug("Find by rating called");
        return bookFacade.findByReviewRating(rate);
    }

    private void validateBook(Book book) throws DuplicateResourceException {
        if (checkBookAlreadyExists(book.getIsbn())) {
            throw new DuplicateResourceException("Book already exist");
        }
    }

    private boolean checkBookAlreadyExists(long isbn) {
        Book book = bookFacade.findByIsbn(isbn);
        return book != null;
    }
}
