package org.booklibrary.app.manager;

import org.booklibrary.app.persistence.entity.Review;

import javax.ejb.Local;

/**
 *
 * Review Manager Local interface
 *
 * @see org.booklibrary.app.manager.GenericManagerLocal
 */
@Local
public interface ReviewManagerLocal extends GenericManagerLocal<Review, String>{
}
