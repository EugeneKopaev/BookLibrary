package org.booklibrary.app.persistence.id;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Id Generator for generating random UUIDs and converting them to a byte array.
 * 
 */
public final class IdGenerator {

	/**
	 * Number of bytes required to represent a UUID as a byte array.
	 */
	public static final int NUM_UUID_BYTES = 16;


	/**
	 * Generate a new random UUID and return it as the 16-byte entity id, performing the necessary conversion.
	 * 
	 * @return byte[] array containing the 16 byte UUID
	 */
	public static byte[] generateUUID() {
		UUID newUUID = UUID.randomUUID();
		return toByteArray(newUUID);
	}


	/**
	 * Convert a UUID to a byte array.
	 * 
	 * @param  uuid UUID to convert
	 * @return byte[] array containing the 16 byte UUID
	 */
	public static byte[] toByteArray(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits()); // order is important here!
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}


	/**
	 * Convert a byte array to a UUID.
	 *
	 * @param  byte[] array to convert
	 * @return UUID
	 */
	public static UUID toUUID(byte[] byteArray) {
		long msb = 0;                // Most Significant Bits
		long lsb = 0;                // Least Significant Bits
		for (int i = 0; i < 8; i++) {
			msb = (msb << 8) | (byteArray[i] & 0xff);
		}
		for (int i = 8; i < 16; i++) {
			lsb = (lsb << 8) | (byteArray[i] & 0xff);
		}
		UUID result = new UUID(msb, lsb);
		return result;
	}

	public static void main(String[] args) {
		UUID uuid = UUID.randomUUID();
		String arr = Hex.encodeHexString(toByteArray(uuid));
		String a = Base64.encodeBase64String(toByteArray(uuid));
		System.out.println(a);
	}

}
