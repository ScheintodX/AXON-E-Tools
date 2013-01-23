package de.axone.exception.codify;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import de.axone.tools.S;
import de.axone.tools.Str;
import de.axone.tools.Str.MapJoiner;
import de.axone.web.SuperURL;

public abstract class Codifier {
	
	private static final String ENCODING = "iso-8859-1";
	private static volatile String baseUrl = "http://www.axon-e.de/codify/codify.php/";
	
	public static void report( Throwable throwable ) throws IOException {
		
		if( throwable instanceof Codified ){
			report( ((Codified)throwable).getRealCause() );
			return;
		}
		
		Description desc = description( throwable );
		
		URL url = url( throwable );
		
		String parameters = Str.join( URL_JOINER, desc.map() );
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setDoOutput(true);
		con.setRequestProperty( "User-Agent", "Codify/1.0" );

		OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), ENCODING );

		writer.write(parameters);
		writer.flush();

		/*
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
		reader.close();         
		*/
		
		writer.close();
		
	}
	
	private static MapJoiner<String,String> URL_JOINER = new MapJoiner<String,String>() {

		@Override
		public String getRecordSeparator() { return "&"; }

		@Override
		public String getFieldSeparator() { return "="; }

		@Override
		public String keyToString( String nameField, int index ) {
			try {
				return URLEncoder.encode( nameField, ENCODING );
			} catch( UnsupportedEncodingException e ) {
				return "CANNOT_ENCODE_NAME";
			}
		}

		@Override
		public String valueToString( String valueField, int index ) {
			try {
				return URLEncoder.encode( valueField, ENCODING );
			} catch( UnsupportedEncodingException e ) {
				return "CANNOT_ENCODE_VALUE";
			}
		}
	};
	
	public static String codify( Throwable throwable ){
		
		return new Description( throwable ).code();
	}
	
	public static Description description( Throwable throwable ){
		return new Description( throwable );
	}
	
	public synchronized static void setBaseUrl( String baseUrl ){
		Codifier.baseUrl = baseUrl;
	}
	
	public static URL url( Throwable throwable ) {
		
		try {
			return new SuperURL( link( throwable ) ).toURL();
		} catch( Exception e ) {
			throw new RuntimeException( e );
		}
	}
	
	public static String baseUrl(){
		return baseUrl;
	}
	
	public static String link( Throwable throwable ){
		return baseUrl() + codify( throwable );
	}
	
	public static String message( Throwable throwable ){
		return throwable.getMessage() + " [" + link( throwable ) + "]";
	}
	
	public static String localizedMessage( Throwable throwable ){
		return throwable.getLocalizedMessage() + " [" + link( throwable ) + "]";
	}
	
	public static class Description {
		
		private final Throwable t;
		private final StackTraceElement e;
		
		public Description( Throwable t ){
			
			this.t = t;
			this.e = t.getStackTrace()[ 0 ];
		}
		
		public String file(){ return e.getFileName(); }
		public String method(){ return e.getMethodName(); }
		public int line(){ return e.getLineNumber(); }
		public String exception(){ return t.getClass().getSimpleName(); }
		public String message(){ return t.getMessage(); }
		public String stack(){
			StringBuilder result = new StringBuilder();
			appendStackTrace( result, t );
			return result.toString();
		}
		private void appendStackTrace( StringBuilder b, Throwable t ){
			if( t == null ) return;
			b.append( t.getClass().getCanonicalName() ).append(":\n");
			for( StackTraceElement e : t.getStackTrace() ){
				b.append( "\t" ).append( e.toString() ).append( S.nl );
			}
			appendStackTrace( b, t.getCause() );
		}
		
		public Map<String,String> map(){
			
			Map<String,String> result = new TreeMap<String,String>();
			
			result.put( "file", file() );
			result.put( "method", method() );
			result.put( "line", ""+line() );
			result.put( "exception", exception() );
			result.put( "message", message() );
			result.put( "stack", stack() );
			
			return result;
		}
		
		public String code(){
			
			int fileCode = file().hashCode();
			int methodCode = method().hashCode();
			int lineCode = line();
			int exceptionCode = exception().hashCode();
			int textCode = message().hashCode();
			
			String combinedCode = String.format( "%08x/%08x/%08d/%08x/%08x",
					fileCode, methodCode, lineCode, exceptionCode, textCode );
			
			return combinedCode;
		}
		
		@Override
		public String toString(){
			
			return map().toString();
		}
		
	}
	
}
