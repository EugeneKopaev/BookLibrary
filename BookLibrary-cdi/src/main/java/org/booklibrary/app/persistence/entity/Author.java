package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "AUTHORS")
public class Author extends AbstractPersistentEntity {

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

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
        return Collections.unmodifiableList(this.books);
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
        if (!super.equals(obj)) {
            return false;
        }
        final Author other = (Author) obj;
        return Objects.equal(this.firstName, other.firstName)
                && Objects.equal(this.lastName, other.lastName);
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hashCode(firstName, lastName);
    }
}
