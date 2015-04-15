package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "BOOKS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ISBN"})
})
@NamedQueries({
        @NamedQuery(name = "Book.findByIsbn",
                query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
        @NamedQuery(name = "Book.countRating",
                query = "SELECT avg(r.rating) as book_rating FROM Review r where r.book.id = :bookID"),
        @NamedQuery(name = "Book.updateRating",
                query = "UPDATE Book b SET b.avgRating = :rating WHERE b.id = :bookID"),
        @NamedQuery(name = "Book.removeList",
                query = "DELETE FROM Book b WHERE b.id IN (:bookIdList)"),
        @NamedQuery(name = "Book.findAllByAuthor",
                query = "SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorID"),
        @NamedQuery(name = "Book.findReviewRating",
                query = "SELECT b FROM Book b JOIN b.reviews r WHERE r.rating = :rating")
})
public class Book extends AbstractBaseEntity implements Serializable {

    private static final int MIN_NAME_SIZE = 3;
    private static final int MAX_NAME_SIZE = 20;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ISBN", nullable = false)
    private long isbn;

    @NotNull
    @Column(name = "PUBLISH_YEAR", nullable = false)
    private int publishYear;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @Column(name = "AVG_RATING")
    private Double avgRating;

    @NotNull
    @ManyToMany
    @JoinTable(name = "BOOKS_TO_AUTHORS",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID"))
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public List<Author> getAuthors() {
        return this.authors;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public List<Review> getReviews() {
        return Collections.unmodifiableList(this.reviews);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, isbn, publishYear, publisher);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.isbn, other.isbn)
                && Objects.equal(this.publishYear, other.publishYear)
                && Objects.equal(this.publisher, other.publisher);
    }
}
