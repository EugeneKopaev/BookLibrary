package org.booklibrary.app.persistence.entity;


import com.google.common.base.Objects;
import org.booklibrary.app.persistence.id.EntityIdentifier;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Parent for all entities encapsulated primary key, created and changed date
 *
 */
@MappedSuperclass
public abstract class AbstractBaseEntity implements Serializable {

    @EmbeddedId
    private EntityIdentifier id = new EntityIdentifier();

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

    public EntityIdentifier getId() {
        return id;
    }

    public void setId(EntityIdentifier id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getChanged() {
        return changed;
    }

}
