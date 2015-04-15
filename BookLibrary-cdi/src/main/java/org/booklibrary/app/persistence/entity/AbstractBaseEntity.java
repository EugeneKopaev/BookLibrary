package org.booklibrary.app.persistence.entity;

import org.booklibrary.app.persistence.id.IdGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Parent for all entities encapsulated primary key, created and changed date
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {

    @Id
    @Column(name = "ID",
            columnDefinition = "CHAR(32)")
    private String id = IdGenerator.generateUUID();

    @Column(name = "CREATED",
            insertable = false,
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column(name = "CHANGED",
            nullable = false,
            columnDefinition = "TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changed;

    public AbstractBaseEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getChanged() {
        return changed;
    }

    @Override
    public String toString() {
        return "AbstractBaseEntity{" +
                "id=" + id +
                ", created=" + created +
                ", changed=" + changed +
                '}';
    }
}
