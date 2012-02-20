package de.axone.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class BufferedHttpServletResponse implements HttpServletResponse {
	
	ByteArrayOutputStream outS;
	
	private StringWriter out;
	private PrintWriter printer;
	
	@Override
	public void addCookie( Cookie arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addDateHeader( String arg0, long arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addHeader( String arg0, String arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addIntHeader( String arg0, int arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsHeader( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeRedirectURL( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeRedirectUrl( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeURL( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeUrl( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendError( int arg0 ) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendError( int arg0, String arg1 ) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sendRedirect( String arg0 ) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDateHeader( String arg0, long arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setHeader( String arg0, String arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setIntHeader( String arg0, int arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatus( int arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setStatus( int arg0, String arg1 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void flushBuffer() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getBufferSize() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCharacterEncoding() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContentType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	@Override
	public synchronized ServletOutputStream getOutputStream() throws IOException {
		
		if( out != null ) throw new IllegalStateException( "Already using Writer" );
		
		if( outS == null ) outS = new ByteArrayOutputStream();
	
		return new ServletOutputStream() {
			@Override
			public void write( int b ) throws IOException {
				outS.write( b );
			}
		};
	}

	@Override
	public synchronized PrintWriter getWriter() throws IOException {
		
		if( outS != null ) throw new IllegalStateException( "Already using OutputStream" );
		
		if( printer == null ){
			out = new StringWriter();
			printer = new PrintWriter( out );
		}
	
		return printer;
	}

	@Override
	public boolean isCommitted() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void resetBuffer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBufferSize( int arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCharacterEncoding( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContentLength( int arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContentType( String arg0 ) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLocale( Locale arg0 ) {
		throw new UnsupportedOperationException();
	}
	
	public StringBuffer getBuffer(){
		return out.getBuffer();
	}
	public ByteArrayOutputStream getByteArray(){
		return outS;
	}
	
}
