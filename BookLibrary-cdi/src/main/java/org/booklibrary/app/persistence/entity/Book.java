package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "BOOKS")
public class Book extends AbstractBaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ISBN", nullable = false)
    private long isbn;

    @Column(name = "PUBLISH_YEAR", nullable = false)
    private int publishYear;

    @Column(name = "PUBLISHER", nullable = false)
    private String publisher;

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
        return 31 * super.hashCode() + Objects.hashCode(name, isbn, publishYear, publisher);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final Book other = (Book) obj;
        return Objects.equal(this.name, other.name)
                && Objects.equal(this.isbn, other.isbn)
                && Objects.equal(this.publishYear, other.publishYear)
                && Objects.equal(this.publisher, other.publisher);
    }
}
