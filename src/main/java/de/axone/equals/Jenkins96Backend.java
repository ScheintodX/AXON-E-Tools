package de.axone.equals;

/**
 * Hash algorithm by Bob Jenkins, 1996.
 * 
 * You may use this code any way you wish, private, educational, or commercial.  It's free.
 * See: http://burtleburtle.net/bob/hash/doobs.html
 *
 * Use for hash table lookup, or anything where one collision in 2^^32
 * is acceptable.  Do NOT use for cryptographic purposes.
 *
 * Java port by Gray Watson http://256.com/gray/
 * 
 * Flo: inserted 'state' to make it threadsafe
 */
public class Jenkins96Backend {
	
	// max value to limit it to 4 bytes
	private static final long MAX_VALUE = 0xFFFFFFFFL; 
	
	// internal variables used in the various calculations
	private static final class state {
		long a, b, c;
	}
	
	/**
	 * Convert a byte into a long value without making it negative.
	 */
	private long byteToLong(byte b) {
		long val = b & 0x7F;
		if ((b & 0x80) != 0) {
			val += 128;
		}
		return val;
	}
	
	/**
	 * Do addition and turn into 4 bytes. 
	 */
	private long add(long val, long add) {
		return (val + add) & MAX_VALUE;
	}
	
	/**
	 * Do subtraction and turn into 4 bytes. 
	 */
	private long subtract(long val, long subtract) {
		return (val - subtract) & MAX_VALUE;
	}
	
	/**
	 * Left shift val by shift bits and turn in 4 bytes. 
	 */
	private long xor(long val, long xor) {
		return (val ^ xor) & MAX_VALUE;
	}
	
	/**
	 * Left shift val by shift bits.  Cut down to 4 bytes. 
	 */
	private long leftShift(long val, int shift) {
		return (val << shift) & MAX_VALUE;
	}
	
	/**
	 * Convert 4 bytes from the buffer at offset into a long value.
	 */
	private long fourByteToLong(byte[] bytes, int offset) {
		return (byteToLong(bytes[offset + 0])
				+ (byteToLong(bytes[offset + 1]) << 8)
				+ (byteToLong(bytes[offset + 2]) << 16)
				+ (byteToLong(bytes[offset + 3]) << 24));
	}
	
	/**
	 * Mix up the values in the hash function.
	 */
	private void hashMix( state s ) {
		long a=s.a, b=s.b, c=s.c;
	   a = subtract(a, b); a = subtract(a, c); a = xor(a, c >> 13);
	   b = subtract(b, c); b = subtract(b, a); b = xor(b, leftShift(a, 8));
	   c = subtract(c, a); c = subtract(c, b); c = xor(c, (b >> 13));
	   a = subtract(a, b); a = subtract(a, c); a = xor(a, (c >> 12));
	   b = subtract(b, c); b = subtract(b, a); b = xor(b, leftShift(a, 16));
	   c = subtract(c, a); c = subtract(c, b); c = xor(c, (b >> 5));
	   a = subtract(a, b); a = subtract(a, c); a = xor(a, (c >> 3));
	   b = subtract(b, c); b = subtract(b, a); b = xor(b, leftShift(a, 10));
	   c = subtract(c, a); c = subtract(c, b); c = xor(c, (b >> 15));
	   s.a=a; s.b=b; s.c=c;
	 }

	/**
	 * Hash a variable-length key into a 32-bit value.  Every bit of the
	 * key affects every bit of the return value.  Every 1-bit and 2-bit
	 * delta achieves avalanche.  The best hash table sizes are powers of 2.
	 *
	 * @param buffer Byte array that we are hashing on.
	 * @param initialValue Initial value of the hash if we are continuing from
	 * a previous run.  0 if none.
	 * @return Hash value for the buffer.
	 */
	public long hash(byte[] buffer, long initialValue) {
		int len, pos;
		
		state s = new state();
		
		// set up the internal state
		// the golden ratio; an arbitrary value
		s.a = 0x09e3779b9L;
		// the golden ratio; an arbitrary value
		s.b = 0x09e3779b9L;
        // the previous hash value
		s.c = initialValue;
		
		// handle most of the key
		pos = 0;
		for (len = buffer.length; len >=12; len -= 12) {
			s.a = add(s.a, fourByteToLong(buffer, pos));
			s.b = add(s.b, fourByteToLong(buffer, pos + 4));
			s.c = add(s.c, fourByteToLong(buffer, pos + 8));
			hashMix( s );
			pos += 12;
		}
		
		s.c += buffer.length;
		
		// all the case statements fall through to the next on purpose
		switch(len) {
		case 11:
			s.c = add(s.c, leftShift(byteToLong(buffer[pos + 10]), 24));
		case 10:
			s.c = add(s.c, leftShift(byteToLong(buffer[pos + 9]), 16));
		case 9:
			s.c = add(s.c, leftShift(byteToLong(buffer[pos + 8]), 8));
			// the first byte of c is reserved for the length
		case 8:
			s.b = add(s.b, leftShift(byteToLong(buffer[pos + 7]), 24));
		case 7:
			s.b = add(s.b, leftShift(byteToLong(buffer[pos + 6]), 16));
		case 6:
			s.b = add(s.b, leftShift(byteToLong(buffer[pos + 5]), 8));
		case 5:
			s.b = add(s.b, byteToLong(buffer[pos + 4]));
		case 4:
			s.a = add(s.a, leftShift(byteToLong(buffer[pos + 3]), 24));
		case 3:
			s.a = add(s.a, leftShift(byteToLong(buffer[pos + 2]), 16));
		case 2:
			s.a = add(s.a, leftShift(byteToLong(buffer[pos + 1]), 8));
		case 1:
			s.a = add(s.a, byteToLong(buffer[pos + 0]));
		default:
			// case 0: nothing left to add
		}
		hashMix( s );
		
		return s.c;
	}
	
	/**
	 * See hash(byte[] buffer, long initialValue)
	 * @param buffer Byte array that we are hashing on.
	 * @return Hash value for the buffer.
	 */
	public long hash(byte[] buffer) {
		return hash(buffer, 0);
	}
	public long hash(String string ){
		return hash( string.getBytes(), 0 );
	}
}
