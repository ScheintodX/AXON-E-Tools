package de.axone.web.rest;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.axone.tools.Str;
import de.axone.web.Method;

public interface RestFunctionRoute {

	public abstract Map<String, String> match( Method method, String uri );
	
	public static class Simple implements RestFunctionRoute {
		
		private static final Pattern PATTERN_PATTERN = Pattern.compile( ":(\\w+)" );
		
		private final EnumSet<Method> methods;
		
		private final String name;
		private final String patternString;
		private final Pattern pattern;
		private final List<String> parameterNames = new LinkedList<>();
		private final Map<String,Integer> parameterIndizes = new TreeMap<>();
		
		/* Constructor hell because of EnumSet */
		public Simple( String pattern, Method method ){
			this( null, pattern, EnumSet.of( method ) );
		}
		public Simple( String pattern, Method m1, Method m2 ){
			this( null, pattern, EnumSet.of( m1, m2 ) );
		}
		
		public Simple( String routeName, String pattern ){
			this( routeName, pattern, null );
		}
		
		public Simple( String pattern ){
			this( null, pattern, null );
		}
		public Simple( String routeName, String pattern, EnumSet<Method> methods ){
			
			this.patternString = pattern;
			this.methods = methods;
			this.name = routeName;
			
			Matcher m = PATTERN_PATTERN.matcher( pattern );
			int i=0;
			StringBuffer compiled = new StringBuffer();
			while( m.find() ){
				
				String parameterName = pattern.substring( m.start(), m.end() );
				
				parameterName = parameterName.substring( 1 ); // remove :
				
				if( parameterName.length() == 0 )
					throw new IllegalArgumentException( ": must be followed by word charackters" );
				
				parameterNames.add( parameterName );
				parameterIndizes.put( parameterName, Integer.valueOf( i++ ) );
				
				m.appendReplacement( compiled, "([^\\/]+)" );
			}
			m.appendTail( compiled );
			
			this.pattern = Pattern.compile( "^" + compiled.toString() + "$" );
		}
		
		@Override
		public Map<String,String> match( Method method, String uri ){
			
			if( methods != null ){
				if( ! methods.contains( method ) ) return null;
			}
			return match( uri );
		}
		
		Map<String,String> match( String uri ){
			
			Matcher m = pattern.matcher( uri );
			
			if( m.matches() ){
				
				Map<String,String> result = new TreeMap<>();
				
				result.put( "__pattern__", patternString );
				if( name != null ) result.put( "__name__", name );
				
				for( int i=0; i<m.groupCount(); i++ ){
					
					String name = parameterNames.get( i );
					String value = m.group( i+1 );
					
					result.put( name, value );
				}
				return result;
			} else {
				return null;
			}
		}
		
		@Override
		public String toString(){
			return patternString;
		}
		
	}
	
	public class Combined implements RestFunctionRoute {
		
		private final RestFunctionRoute [] routes;
		
		public Combined( RestFunctionRoute ... routes ){
			
			this.routes = routes;
		}
	
		@Override
		public Map<String, String> match( Method method, String uri ) {
			
			Map<String,String> result;
			
			for( RestFunctionRoute route : routes ){
				result = route.match( method, uri );
				if( result != null ) return result;
			}
			return null;
		}
		
		@Override
		public String toString(){
			return Str.join( ", ", routes );
		}
	
	}
		
		
}