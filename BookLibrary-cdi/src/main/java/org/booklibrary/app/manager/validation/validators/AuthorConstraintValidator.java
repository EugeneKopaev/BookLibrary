package org.booklibrary.app.manager.validation.validators;

import org.booklibrary.app.manager.validation.annotation.AuthorConstraint;
import org.booklibrary.app.persistence.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuthorConstraintValidator implements ConstraintValidator<AuthorConstraint, Author> {

    @PersistenceUnit(name = "bookstore")
    private EntityManagerFactory managerFactory;
//        @Inject
//    private EntityManager entityManager;
    private AuthorConstraint authorConstraint;

    @Override
    public void initialize(AuthorConstraint constraintAnnotation) {

        this.authorConstraint = constraintAnnotation;
    }

    @Override
    public boolean isValid(Author value, ConstraintValidatorContext context) {
        String firstName = value.getFirstName();
        String lastName = value.getLastName();
        EntityManager entityManager = managerFactory.createEntityManager();
        try {
            Author author = entityManager
                    .createNamedQuery("Author.findByFirstAndLastName", Author.class)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getSingleResult();
        } catch (NoResultException e) {
            return true;
        } finally {
            entityManager.close();
        }
        context.buildConstraintViolationWithTemplate(authorConstraint.message())
                .addPropertyNode("firstName, ")
                .addPropertyNode("lastName")
                .addConstraintViolation().disableDefaultConstraintViolation();
        return false;

    }
}
