package org.booklibrary.app.web.controllers;

import org.booklibrary.app.manager.ReviewManagerLocal;
import org.booklibrary.app.persistence.entity.Review;
import org.booklibrary.app.web.util.FacesMessageUtils;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class ReviewController implements Serializable {

    @EJB
    private ReviewManagerLocal reviewManager;

    private String currentReviewId;
    private Review managedReview = null;
    private Review createdReview = new Review();
    private List<Review> reviews = new ArrayList<>();
    private Map<Object, Object> checkedItems = new HashMap();
    private int page = 1;

    /**
     * Save new review
     */
    public void save() {
        if (createdReview == null) {
            return;
        }
        try {
            reviewManager.save(createdReview);
            createdReview = new Review();
        } catch (EJBException e) {
            FacesMessageUtils.addErrorMessage(e, "Save unsuccessful");
        }
    }

    @Produces
    @Named
    public Review getManagedReview() {
        return managedReview;
    }

    public void setManagedReview(Review managedReview) {
        this.managedReview = managedReview;
    }

    @Produces
    @Named
    public List<Review> getReviews() {
        this.reviews = reviewManager.findAll();
        return this.reviews;
    }

    @Produces
    @Named
    public Review getCreatedReview() {
        return createdReview;
    }

    public void setCreatedReview(Review createdReview) {
        this.createdReview = createdReview;
    }

    public void loadData() {
        managedReview = reviewManager.findByUuid(getCurrentReviewId());
    }

    public String getCurrentReviewId() {
        return currentReviewId;
    }

    public void setCurrentReviewId(String currentReviewId) {
        this.currentReviewId = currentReviewId;
    }

    public Map<Object, Object> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(Map<Object, Object> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
