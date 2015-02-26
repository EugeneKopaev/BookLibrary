package org.booklibrary.app.persistence.id;

import com.google.common.base.Objects;
import org.apache.commons.codec.binary.Hex;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Entity Identifier is a Universally Unique Identified (UUID, aka GUID), used as the primary key for all
 * entities. It can be represented as a 32 character hex string, e.g. "d91caf4de37640faa59602c42eecf237".
 * The class is Embeddable, and should be used with the EmbeddedId
 * annotation.
 */
@Embeddable
public class EntityIdentifier implements Serializable {

    /**
     * The unique entity id, set to a random UUID.
     */
    @Column(name = "ID",
            length = IdGenerator.NUM_UUID_BYTES,
            columnDefinition = "BINARY(16)")
    private byte[] id;

    public EntityIdentifier() {
        this.id = IdGenerator.generateUUID();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EntityIdentifier)) {
            return false;
        }

        final EntityIdentifier other = (EntityIdentifier) obj;

        return Arrays.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return Hex.encodeHexString(this.id);
    }

    public byte[] getId() {
        return this.id;
    }

}
