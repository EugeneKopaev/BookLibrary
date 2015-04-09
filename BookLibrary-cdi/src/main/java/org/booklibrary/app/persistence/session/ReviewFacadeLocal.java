package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.common.GenericFacadeLocal;

import javax.ejb.Local;

/**
 * Review Facade Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericFacadeLocal
 */
@Local
public interface ReviewFacadeLocal extends GenericFacadeLocal<Review, String>{
}
