package de.axone.tools.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LimitedSplittor {

	public static class LimitedCharToArraySplittor {
		
		public String [] split( String s, char split, int n ){
			
			final String [] result = new String[ n ];
			
			final int len = s.length();
			
			int num=0;
			int i, last=0;
			for( i=0; i<len; i++ ){
				
				if( split == s.charAt( i ) ){
					
					result[ num ] = s.substring( last, i );
					
					last = i+1;
					num++;
					if( num == n-1 ) break;
				};
			}
			
			if( len>=last ){
				result[ num ] = s.substring( last );
			}
			
			return Arrays.copyOf( result, num+1 );
			
		}
	}
	
	public static class LimitedCharToListSplittor {
		
		public List<String> split( String s, char split, int n ){
			
			final List<String> result = new ArrayList<>( n );
			
			final int len = s.length();
			
			int num=0;
			int i, last=0;
			for( i=0; i<len; i++ ){
				
				if( split == s.charAt( i ) ){
					
					result.add( s.substring( last, i ) );
					
					last = i+1;
					num++;
					if( num == n-1 ) break;
				};
			}
			
			if( len>=last ){
				result.add( s.substring( last ) );
			}
			
			return result;
			
		}
	}
}