package org.booklibrary.app.persistence.id;

import com.google.common.base.Objects;
import org.apache.commons.codec.DecoderException;
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
     * The length of the eid array.
     */
    public static final int ID_LENGTH = 16;

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

    public EntityIdentifier(String uuid) {
        if (uuid.length() != (ID_LENGTH * 2)) {
            String errMsg =
                    String.format("The length %d of input String %s is incorrect", uuid.length(), uuid);
            throw new IllegalArgumentException(errMsg);
        }
        try {
            this.id = Hex.decodeHex(uuid.toCharArray());

        } catch (DecoderException e) {
            String errMsg = String.format("The input String %s is invalid", uuid);
            throw new IllegalArgumentException(errMsg, e);
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != getClass()) {
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

    public static void main(String[] args) {
        System.out.println(IdGenerator.generateUUID().toString());
    }

}
