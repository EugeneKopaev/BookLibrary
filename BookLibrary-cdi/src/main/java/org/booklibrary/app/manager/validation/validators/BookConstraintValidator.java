package org.booklibrary.app.manager.validation.validators;

import org.booklibrary.app.manager.validation.annotation.BookConstraint;
import org.booklibrary.app.persistence.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class BookConstraintValidator implements ConstraintValidator<BookConstraint, Book> {

    @PersistenceUnit(name = "bookstore")
    private EntityManagerFactory managerFactory;
    //        @Inject
//    private EntityManager entityManager;
    private BookConstraint bookConstraint;

    @Override
    public void initialize(BookConstraint constraintAnnotation) {
        this.bookConstraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(Book value, ConstraintValidatorContext context) {
        long isbn = value.getIsbn();

        EntityManager entityManager = managerFactory.createEntityManager();

        try {
            Book book = entityManager
                    .createNamedQuery("Book.findByIsbn", Book.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } catch (NoResultException e) {
            return true;
        } finally {
            entityManager.close();
        }
        context.buildConstraintViolationWithTemplate(bookConstraint.message())
                .addPropertyNode("isbn")
                .addConstraintViolation().disableDefaultConstraintViolation();
        return false;
    }
}
