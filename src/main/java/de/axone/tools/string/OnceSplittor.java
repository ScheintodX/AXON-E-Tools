package de.axone.tools.string;


public abstract class OnceSplittor {
	
	public CharOnceToArraySplittorStarting CHAR_ONCE_TO_ARRAY_SPLITTOR_STARTING = new CharOnceToArraySplittorStarting();
	
	public static class CharOnceToArraySplittorStarting {
		
		public static String [] split( String s, char split, int startAt ){
			
			// TODO: indexOf kann beschleunigt werden
			int pos = s.indexOf( split, startAt );
			
			if( pos >= 0 ){
				return new String[]{ s.substring( 0, pos ), s.substring( pos+1 ) };
			} else {
				return new String[]{ s };
			}
		}
	}
	
	public StringOnceToArraySplittorStarting STRING_ONCE_TO_ARRAY_SPLITTOR_STARTING = new StringOnceToArraySplittorStarting();
	
	public static class StringOnceToArraySplittorStarting {
		
		public static String [] split( String s, String split, int startAt ){
			
			// TODO: indexOf kann beschleunigt werden
			int pos = s.indexOf( split, startAt );
			
			if( pos >= 0 ){
				return new String[]{ s.substring( 0, pos ), s.substring( pos+split.length() ) };
			} else {
				return new String[]{ s };
			}
		}
	}
	
}
