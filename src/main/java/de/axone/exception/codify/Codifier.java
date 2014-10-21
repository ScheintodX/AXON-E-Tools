package de.axone.exception.codify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.data.Charsets;
import de.axone.tools.Mapper;
import de.axone.tools.S;
import de.axone.tools.Str;
import de.axone.tools.Str.MapJoiner;
import de.axone.web.SuperURLBuilders;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

// TODO: Registrieren von Packages. Oder macht man das besser online? Oder beides?
// Vermutlich beides, da man lokal so auch eine Unterscheidung der eigenen Packages
// hinbekommt, online dagegen Package-Listen gepflegt werden können

public abstract class Codifier {
	
	private static final Logger log = LoggerFactory.getLogger( Codifier.class );
	
	private static volatile String baseUrl = "http://codify.axon-e.de/codify.php/";
	private static volatile boolean includeLink = false;
	private static volatile String project = "Codifier";
	private static volatile String version = "1.0";
	
	public static void report( Throwable throwable ) throws IOException {
		report( throwable, null );
	}
	public static void report( Throwable throwable, Object context ) throws IOException {
		report( throwable, Mapper.treeMap( "context", context.toString() ) );
	}
	
	@SuppressFBWarnings( value="RV_DONT_JUST_NULL_CHECK_READLINE",
			justification="See comment below." )
	public static void report( Throwable throwable, Map<String,String> parametersAdd ) throws IOException {
		
		if( throwable instanceof Codified ){
			report( ((Codified)throwable).getWrapped(), parametersAdd );
			return;
		}
		
		Description desc = description( throwable );
		
		URL url = url( throwable );
		
		Map<String,String> parametersUse = new TreeMap<String,String>();
		if( parametersAdd != null ) parametersUse.putAll( parametersAdd );
		parametersUse.putAll( desc.map() );
		parametersUse.put( "action", "report" );
		
		if( log.isDebugEnabled() ){
			if( ! log.isTraceEnabled() ){
				Map<String,String> parametersLog = new TreeMap<>( parametersUse );
				parametersLog.remove( "stack" );
				log.debug( "Report: {}", parametersLog );
			} else {
				log.trace( "Report: {}", parametersUse );
			}
		}
		
		String parameters = Str.join( URL_JOINER, parametersUse );
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setDoOutput(true);
		con.setRequestProperty( "User-Agent", "Codify/1.0" );
		con.setInstanceFollowRedirects( false );

		try( OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream(), Charsets.UTF8 ) ){
		
			writer.write(parameters);
			writer.flush();
	
			try( BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream())) ){
	
				// Debugging:
				
				if( log.isDebugEnabled() ){
					
					StringBuilder resultText = new StringBuilder();
					String line;
					while( (line=reader.readLine()) != null){
			
						resultText.append( line );
					}
					log.debug( resultText.toString() );
				} else {
					
					// This is needed. Without reading the answer the request isn't send properly.
					// Quantum effects?
					while( reader.readLine() != null );
				}
			}
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
				return URLEncoder.encode( nameField, Charsets.utf8 );
			} catch( UnsupportedEncodingException e ) {
				return "CANNOT_ENCODE_NAME";
			}
		}

		@Override
		public String valueToString( String valueField, int index ) {
			if( valueField == null ) return S.NULL;
			try {
				return URLEncoder.encode( valueField, Charsets.utf8 );
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
	
	public synchronized static void setIncludeLink( boolean includeLink ){
		Codifier.includeLink = includeLink;
	}
	
	public synchronized static void setProject( String project ){
		Codifier.project = project;
	}
	public synchronized static void setVersion( String version ){
		Codifier.version = version;
	}
	
	public static URL url( Throwable throwable ) {
		
		return SuperURLBuilders.fromString().build( link( throwable ) ).toURL();
	}
	
	public static String baseUrl(){
		return baseUrl;
	}
	
	public static String link( Throwable throwable ){
		return baseUrl() + codify( throwable );
	}
	
	public static String message( Throwable throwable ){
		return throwable.getMessage() + " [" + (includeLink ? link( throwable ) : codify( throwable )) + "]";
	}
	
	public static String localizedMessage( Throwable throwable ){
		return throwable.getLocalizedMessage() + " [" + (includeLink ? link( throwable ) : codify( throwable )) + "]";
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
		/*
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
		*/
		public String stack(){
			StringWriter s = new StringWriter();
			PrintWriter pw = new PrintWriter( s );
			t.printStackTrace( pw );
			return s.getBuffer().toString();
		}
		
		public Map<String,String> map(){
			
			Map<String,String> result = new TreeMap<String,String>();
			
			result.put( "project", project );
			result.put( "file", file() );
			result.put( "method", method() );
			result.put( "line", ""+line() );
			result.put( "exception", exception() );
			result.put( "message", message() );
			result.put( "stack", stack() );
			result.put( "version", version );
			
			return result;
		}
		
		public String code(){
			
			int fileCode = Hash.hash( file() );
			int methodCode = Hash.hash( method() );
			int lineCode = Hash.hash( line() );
			int exceptionCode = Hash.hash( exception() );
			int textCode = message() != null ? Hash.hash( message() ) : 0;
			
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
