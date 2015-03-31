package org.booklibrary.app.manager.validation.annotation;

import org.booklibrary.app.manager.validation.validators.AuthorConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {AuthorConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorConstraint {

    String message() default "Author all ready exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
