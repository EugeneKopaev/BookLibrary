package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;
import org.booklibrary.app.persistence.listeners.BookRatingListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "REVIEWS")
@EntityListeners({BookRatingListener.class})
public class Review extends AbstractBaseEntity implements Serializable {

    private static final int MIN_NAME_SIZE = 3;
    private static final int MAX_NAME_SIZE = 20;
    private static final int MIN_RATING_SIZE = 1;
    private static final int MAX_RATING_SIZE = 5;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "COMMENTER_NAME", nullable = false)
    private String commenterName;

    @NotNull
    @Lob
    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Min(MIN_RATING_SIZE)
    @Max(MAX_RATING_SIZE)
    @Column(name = "RATING")
    private int rating;

    @NotNull
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
                && Objects.equal(this.comment, other.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(commenterName, comment, rating);
    }

}
