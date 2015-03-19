package org.booklibrary.app.manager.exceptions;

import javax.ejb.ApplicationException;

/**
 * Wrap all persistence exceptions in checked exception.
 * Throwing this exception will perform transaction rollback
 */
@ApplicationException(rollback = true)
public class EntityManagerException extends Exception{

    public EntityManagerException() {

    }

    public EntityManagerException(String message) {
        super(message);
    }

    public EntityManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityManagerException(Throwable cause) {
        super(cause);
    }

    public EntityManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
