package org.booklibrary.app.persistence.entity;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "BOOKS")
public class Book extends AbstractBaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ISBN", nullable = false)
    private long isbn;

    @Column(name = "PUBLISH_YEAR", nullable = false)
    private int publishYear;

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

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + Objects.hashCode(name, isbn, publishYear);
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
                && Objects.equal(this.publishYear, other.publishYear);
    }
}
