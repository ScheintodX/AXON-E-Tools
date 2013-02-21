package de.axone.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class TestHttpServletResponse implements HttpServletResponse {
	
	@Override
	public void addCookie( Cookie cookie ) {
		cookies.add( cookie );
	}
	private List<Cookie> cookies = new LinkedList<Cookie>();
	public List<Cookie> getCookies(){
		return cookies;
	}

	private Map<String,Long> dateHeaders = new HashMap<String,Long>();
	public Map<String,Long> getDateHeaders(){
		return dateHeaders;
	}
	@Override
	public void addDateHeader( String key, long value ) {
		dateHeaders.put( key, value );
	}
	@Override
	public void setDateHeader( String key, long value ) {
		addDateHeader( key, value );
	}

	private Map<String,String> headers = new HashMap<String,String>();
	public Map<String,String> getHeaders(){
		return headers;
	}
	@Override
	public void addHeader( String key, String value ) {
		headers.put( key, value );
	}
	@Override
	public void setHeader( String key, String value ) {
		addHeader( key, value );

	}
	@Override
	public void addIntHeader( String key, int value ) {
		headers.put( key, ""+value );
	}
	@Override
	public void setIntHeader( String key, int value ) {
		addIntHeader( key, value );
	}

	@Override
	public boolean containsHeader( String key ) {
		return headers.containsKey( key );
	}

	@Override
	public String encodeRedirectURL( String url ) {
		return "[R" + url + "R]";
	}

	@Override
	@Deprecated
	public String encodeRedirectUrl( String url ) {
		return encodeRedirectURL( url );
	}

	@Override
	public String encodeURL( String url ) {
		return "[E" + url + "E]";
	}

	@Override
	@Deprecated
	public String encodeUrl( String url ) {
		return encodeURL( url );
	}

	@Override
	public void sendError( int code ) throws IOException {
		errorCode = code;
	}
	private int errorCode = -1;
	private String errorMessage;

	@Override
	public void sendError( int code, String message ) throws IOException {
		this.errorCode = code;
		this.errorMessage = message;
	}
	
	public boolean hasError(){
		return errorCode > 0;
	}
	public int getErrorCode(){
		return errorCode;
	}
	public String getErrorMessage(){
		return errorMessage;
	}

	@Override
	public void sendRedirect( String redirect ) throws IOException {
		this.redirect = redirect;
	}
	private String redirect;
	public String getRedirect(){
		return redirect;
	}
	

	@Override
	public void setStatus( int code ) {
		this.statusCode = code;
	}
	private int statusCode = -1;
	private String statusMessage;

	@Override
	public void setStatus( int code, String message ) {
		this.statusCode = code;
		this.statusMessage = message;
	}
	public boolean hasStatus(){
		return statusCode > 0;
	}
	public int getStatusCode(){
		return statusCode;
	}
	public String getStatusMessage(){
		return statusMessage;
	}

	@Override
	public void flushBuffer() throws IOException {
	}

	@Override
	public int getBufferSize() {
		return bufferSize;
	}
	private int bufferSize;
	@Override
	public void setBufferSize( int bufferSize ){
		this.bufferSize = bufferSize;
	}

	@Override
	public String getCharacterEncoding() {
		return characterEncoding;
	}
	private String characterEncoding;
	@Override
	public void setCharacterEncoding( String characterEncoding ) {
		this.characterEncoding = characterEncoding;
	}

	@Override
	public String getContentType() {
		return contentType;
	}
	private String contentType;
	@Override
	public void setContentType( String contentType ) {
		this.contentType = contentType;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}
	private Locale locale;
	@Override
	public void setLocale( Locale locale ) {
		this.locale = locale;
	}

	/* -------------------------------- */
	
	private StringBuilder content = new StringBuilder();
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return new TestServletOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return new PrintWriter( new TestWriter() );
	}

	@Override
	public boolean isCommitted() {
		return false;
	}

	@Override
	public void reset() {
	}

	@Override
	public void resetBuffer() {
		content = new StringBuilder();
	}

	@Override
	public void setContentLength( int contentLength ) {
		this.contentLength = contentLength;
	}
	private int contentLength;
	public int getContentLength(){
		return contentLength;
	}
	
	public String getContent(){
		return content.toString();
	}
	/* -------------------------------- */

	private class TestServletOutputStream extends ServletOutputStream {
		
		@Override
		public void write( int b ) throws IOException {
			content.append( (char) b );
		}
	}
	
	private class TestWriter extends Writer {

		@Override
		public void close() throws IOException {}
		
		@Override
		public void flush() throws IOException {}

		@Override
		public void write( char[] cbuf, int off, int len ) throws IOException {
			
			content.append( cbuf, off, len );
		}
	}
	
	/* Since 3.0 -------------------------------------------- */

	@Override
	public int getStatus() {
		return statusCode;
	}
	
	@Override
	public String getHeader( String name ) {
		return headers.get( name );
	}
	@Override
	public Collection<String> getHeaders( String name ) {
		//TODO: Das sollte an die Spec angeglichen werden. u.a. fehlen hier die date headers
		return Arrays.asList( headers.get( name ) );
	}
	@Override
	public Collection<String> getHeaderNames() {
		return headers.keySet();
	}
}
