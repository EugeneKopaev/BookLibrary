package org.booklibrary.app.manager.validation.annotation;

import org.booklibrary.app.manager.validation.validators.BookConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {BookConstraintValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookConstraint {

    String message() default "Book all ready exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
