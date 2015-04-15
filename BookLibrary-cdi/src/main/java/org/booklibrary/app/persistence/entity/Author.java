package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AUTHORS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"FIRST_NAME", "LAST_NAME"})
})
@NamedQueries({
        @NamedQuery(name = "Author.findByFirstName",
                query = "SELECT a FROM Author a WHERE a.firstName = :firstName"),
        @NamedQuery(name = "Author.findByFirstAndLastName",
                query = "SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName"),
        @NamedQuery(name = "Author.findByBookAvgRating",
                query = "SELECT a FROM Author a JOIN a.books b WHERE b.avgRating = :avgRating")
})
public class Author extends AbstractBaseEntity
        implements Serializable {

    private static final int MIN_NAME_SIZE = 3;
    private static final int MAX_NAME_SIZE = 20;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return this.books;
    }

    public void addBooks(Book book) {
        this.books.add(book);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        return Objects.equal(this.firstName, other.firstName)
                && Objects.equal(this.lastName, other.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                "} " + super.toString();
    }
}
