package org.booklibrary.app.persistence.id;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Id Generator for generating random UUIDs and converting them to a String
 * 
 */
public final class IdGenerator {

	/**
	 *
	 * @return String containing the UUID
	 */
	public static String generateUUID() {
		UUID newUUID = UUID.randomUUID();
		byte[] b = toByteArray(newUUID);
		return Hex.encodeHexString(b);
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

//	public static void main(String[] args) {
//		UUID uuid = UUID.randomUUID();
//		String arr = Hex.encodeHexString(toByteArray(uuid));
//		String a = Base64.encodeBase64String(toByteArray(uuid));
//		System.out.println(a);
//		EntityIdentifier identifier = new EntityIdentifier();
//		String key = identifier.toString();
//		System.out.println(key);
//	}
}
