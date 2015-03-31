package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;
import org.booklibrary.app.manager.validation.annotation.BookConstraint;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@BookConstraint
@Entity
@Table(name = "BOOKS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"ISBN"})
})
@NamedQueries({
        @NamedQuery(name = "Book.findByIsbn",
                query = "SELECT b FROM Book b WHERE b.isbn = :isbn"),
})
public class Book extends AbstractBaseEntity implements Serializable{

    private static final int MIN_NAME_SIZE = 3;
    private static final int MAX_NAME_SIZE = 20;
    private static final int INTEGER_ISBN_DIGITS = 13;
    private static final int INTEGER_PUBLISH_YEAR_DIGITS = 4;
    private static final int FRACTION_DIGITS = 0;


    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "NAME", nullable = false)
    private String name;

    @Digits(integer = INTEGER_ISBN_DIGITS, fraction = FRACTION_DIGITS)
    @Column(name = "ISBN", nullable = false)
    private long isbn;

    @Digits(integer = INTEGER_PUBLISH_YEAR_DIGITS, fraction = FRACTION_DIGITS)
    @Column(name = "PUBLISH_YEAR", nullable = false)
    private int publishYear;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

    @NotNull
    @ManyToMany(cascade = CascadeType.PERSIST)
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

    public List<Author> getAuthors() {
        return Collections.unmodifiableList(this.authors);
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
