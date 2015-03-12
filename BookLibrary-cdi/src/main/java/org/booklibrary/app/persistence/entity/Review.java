package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;

import javax.persistence.*;

@Entity
@Table(name = "REVIEWS")
public class Review extends AbstractBaseEntity {

    @Column(name = "COMMENTER_NAME", nullable = false)
    private String commenterName;

    @Lob
    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Column(name = "RATING")
    private int rating;

    @ManyToOne(optional = false)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    public Review() {
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Review other = (Review) obj;
        return Objects.equal(this.commenterName, other.commenterName)
                && Objects.equal(this.comment, other.comment)
                && Objects.equal(this.rating, other.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commenterName, comment, rating);
    }

}
