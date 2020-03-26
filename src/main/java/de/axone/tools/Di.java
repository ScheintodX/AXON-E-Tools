package de.axone.tools;

public abstract class Di {

	/**
	 * Compare two strings and output a string showing differences
	 *
	 * @param str1
	 * @param str2
	 * @return String showing differences
	 */
	public static String ff( String str1, String str2 ) {

		if( str1 == str2 ) return str1;
		if( str1 == null ) return "=[null|=|" + str2 + "]=";
		if( str2 == null ) return "=[" + str1 + "|=|null]=";

		if( str1.equals( str2 ) ) return str1;

		int len1 = str1.length(),
		    len2 = str2.length();

		int iL, iR;

		// iterate from left to right
		for( iL=0; iL<Math.min( len1, len2 ); iL++ ) {

			char l = str1.charAt( iL )
			   , r = str2.charAt( iL )
			   ;
			if( l != r ) break;
		}

		// iterate from right to left
		for( iR=0; iR<Math.min( str1.length(), str2.length() ); iR++ ) {

			char l = str1.charAt( len1-iR-1 )
			   , r = str2.charAt( len2-iR-1 )
			   ;
			if( l != r ) break;
		}

		StringBuilder all = new StringBuilder();

		all.append( str1.substring( 0, iL ) )
		   .append( "=[" )
		   .append( str1.substring( iL, len1-iR ) )
		   .append( "|=|" )
		   .append( str2.substring( iL, len2-iR ) )
		   .append( "]=" )
		   .append( str2.substring( len2-iR ) )
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
