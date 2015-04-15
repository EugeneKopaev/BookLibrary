package org.booklibrary.app.persistence.session;

import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.persistence.session.common.GenericHomeLocal;

import javax.ejb.Local;

/**
 * Review Home Local interface
 *
 * @see org.booklibrary.app.persistence.session.common.GenericHomeLocal
 */
@Local
public interface ReviewHomeLocal extends GenericHomeLocal<Review, String>{
}
