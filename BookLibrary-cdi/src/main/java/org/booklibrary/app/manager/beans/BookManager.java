package org.booklibrary.app.manager.beans;

import org.booklibrary.app.exceptions.DuplicateResourceException;
import org.booklibrary.app.manager.BookManagerLocal;
import org.booklibrary.app.persistence.entity.Book;
import org.booklibrary.app.persistence.session.BookFacadeLocal;
import org.booklibrary.app.persistence.session.BookHomeLocal;
import org.slf4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

/**
* Book Manager implementation.
* This bean encapsulate business logic.
*
* @see org.booklibrary.app.manager.BookManagerLocal
*/
@Stateless
public class BookManager implements BookManagerLocal {

    @Inject
    private Logger logger;

    @EJB
    private BookFacadeLocal bookFacade;

    @EJB
    private BookHomeLocal bookHome;

    public Book save(Book obj) {
        throw new UnsupportedOperationException("use saveUnique method instead");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Book saveUnique(Book obj) throws DuplicateResourceException {
        logger.debug("save invoked for object: {}", obj);
        validateBook(obj);
        return bookHome.save(obj);
    }


    @Override
    public Book update(Book obj) {
        logger.debug("update invoked for obj: {}", obj);
        return bookHome.update(obj);
    }

    @Override
    public void removeByPk(String key) {
        logger.debug("Remove called for entity with key: {}", key);
        bookHome.removeByPk(key);
    }

    @Override
    public void removeByUuid(String uuid) {
        removeByPk(uuid);
    }

    public void removeList(List<String> keys) {
        logger.debug("Remove list invoked");
        bookHome.removeList(keys);
    }

    @Override
    public void removeAll() {
        logger.debug("Remove all invoked");
        bookHome.removeAll();
    }

    @Override
    public Book findByPk(String key) {
        logger.debug("Find invoked for object with key: {}", key);
        return bookFacade.findByPk(key);
    }

    @Override
    public Book findByUuid(String uuid) {
        logger.debug("Find invoked for object with key: {}", uuid);
        return bookFacade.findByUuid(uuid);
    }

    @Override
    public List<Book> findAll() {
        logger.debug("Find all called");
        return bookFacade.findAll();
    }

    @Override
    public List<Book> findRange(int start, int size) {
        logger.debug("Find segment called");
        return bookFacade.findRange(start, size);
    }

    @Override
    public int countEntity() {
        logger.debug("Count all books");
        int count = bookFacade.countEntity();
        logger.debug("result: " + count);
        return count;
    }

    @Override
    public Double countAvgBookRating(String id) {
        logger.debug("Count boor rating");
        double rating = bookFacade.countBookRating(id);
        logger.debug("result: " + rating);
        return rating;
    }

    @Override
    public List<Book> findAllByAuthor(String id) {
        logger.debug("Find by author called");
        return bookFacade.findAllByAuthor(id);
    }

    @Override
    public List<Book> findAllByReviewRating(int rate) {
        logger.debug("Find by rating called");
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
