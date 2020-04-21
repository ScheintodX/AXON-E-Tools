package de.axone.tools;

public abstract class Di {

	static final String SEP[] = { "[", "|", "]" };

	/**
	 * Compare two strings and output a string showing differences
	 *
	 * @param str1
	 * @param str2
	 * @return String showing differences
	 */
	public static String ff( String str1, String str2 ) {

		if( str1 == str2 ) return str1;
		if( str1 == null ) return SEP[0] + "null" + SEP[1] + str2 + SEP[2];
		if( str2 == null ) return SEP[0] + str1 + SEP[1] + "null" + SEP[2];

		if( str1.equals( str2 ) ) return str1;

		int len1 = str1.length(),
		    len2 = str2.length();

		int iL, cR;

		// iterate from left to right
		for( iL=0; iL<Math.min( len1, len2 ); iL++ ) {

			char l = str1.charAt( iL )
			   , r = str2.charAt( iL )
			   ;
			if( l != r ) break;
		}

		// iterate from right to left
		for( cR=0; cR<Math.min( str1.length(), str2.length() )-iL; cR++ ) {

			char l = str1.charAt( len1-cR-1 )
			   , r = str2.charAt( len2-cR-1 )
			   ;
			if( l != r ) break;
		}

		int iR1 = len1-cR,
		    iR2 = len2-cR;

		StringBuilder all = new StringBuilder();

		all.append( str1.substring( 0, iL ) )
		   .append( SEP[0] )
		   .append( str1.substring( iL, iR1 ) )
		   .append( SEP[1] )
		   .append( str2.substring( iL, iR2 ) )
		   .append( SEP[2] )
		   .append( str2.substring( iR2 ) )
		   ;

		return all.toString();
	}

	/**
	 * Compare two objects and output a string showing differences
	 *
	 * The objects will be formated to a string before generating diffs.
	 *
	 * @see F#ormat(Object)
	 *
	 * @param str1
	 * @param str2
	 * @return String showing differences
	 */
	public static String ff( Object obj1, Object obj2 ) {

		String str1 = F.ormat( obj1 ),
		       str2 = F.ormat( obj2 );

		return ff( str1, str2 );
	}

}
