package de.axone.exception.codify;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import de.axone.tools.Str;
import de.axone.tools.Str.MapJoiner;
import de.axone.web.SuperURL;

public abstract class Codifier {
	
	private static volatile String baseUrl = "http://tracker.axon-e.de/codify.php/";
	
	public static void report( Throwable throwable ) throws IOException {
		
		if( throwable instanceof Codified ){
			report( throwable.getCause() );
			return;
		}
		
		Description desc = description( throwable );
		
		URL url = url( throwable );
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches( false );
		
		String parameters = Str.join( URL_JOINER, desc.map() );
		
		con.setRequestProperty( "User-Agent", "WebTools" );
		con.setDoOutput( true );
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		con.setRequestProperty("charset", "utf-8");
		con.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
		con.setUseCaches (false);
		
		DataOutputStream wr = new DataOutputStream( con.getOutputStream() );
		try {
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();
		} finally {
			con.disconnect();
		}
		
		BufferedReader in = new BufferedReader( new InputStreamReader( con.getInputStream() ) );
		try {
			String line;
			while( ( line = in.readLine() ) != null ){
				
				System.err.println( line );
			}
		} finally {
			in.close();
		}
	}
	
	private static MapJoiner<String,String> URL_JOINER = new MapJoiner<String,String>() {

		@Override
		public String getRecordSeparator() { return "&"; }

		@Override
		public String getFieldSeparator() { return "="; }

		@Override
		public String keyToString( String nameField, int index ) {
			try {
				return URLEncoder.encode( nameField, "UTF-8" );
			} catch( UnsupportedEncodingException e ) {
				return "CANNOT_ENCODE_NAME";
			}
		}

		@Override
		public String valueToString( String valueField, int index ) {
			try {
				return URLEncoder.encode( valueField, "UTF-8" );
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
	
	public static class Description {
		
		private final String file;
		private final String method;
		private final int line;
		private final String text;
		private final String exception;
		
		public Description( Throwable t ){
			
			StackTraceElement e = t.getStackTrace()[ 0 ];
			
			file = e.getFileName();
			method = e.getMethodName();
			line = e.getLineNumber();
			text = t.getMessage();
			exception = t.getClass().getSimpleName();
		}
		
		public String file(){ return file; }
		public String method(){ return method; }
		public int line(){ return line; }
		public String exception(){ return exception; }
		public String text(){ return text; }
		
		public Map<String,String> map(){
			
			Map<String,String> result = new TreeMap<String,String>();
			
			result.put( "f", file() );
			result.put( "m", method() );
			result.put( "l", ""+line() );
			result.put( "e", exception() );
			result.put( "t", text() );
			
			return result;
		}
		
		public String code(){
			
			int fileCode = file().hashCode();
			int methodCode = method().hashCode();
			int lineCode = line();
			int exceptionCode = exception().hashCode();
			int textCode = text().hashCode();
			
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
