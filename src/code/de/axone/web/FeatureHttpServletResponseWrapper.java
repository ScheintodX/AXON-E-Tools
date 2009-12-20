package de.axone.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class FeatureHttpServletResponseWrapper implements HttpServletResponse {
	
	private HttpServletResponse resp;
	private ServletOutputStream out;
	private GZIPOutputStream gOut;
	private PrintWriter writer;
	private boolean useGzip;

	public FeatureHttpServletResponseWrapper( HttpServletResponse resp, boolean useGzip ) throws IOException{
		
		this.resp = resp;
		this.useGzip = useGzip;
		if( useGzip ){
			gOut = new GZIPOutputStream( resp.getOutputStream() );
    		out = new ServletOutputStreamImpl( gOut );
    		writer = new PrintWriter( new OutputStreamWriter( out, Charset.forName( "UTF-8" ) ) );
			setHeader( "Content-Encoding", "gzip" );
		}
	}
	public FeatureHttpServletResponseWrapper( HttpServletResponse resp ) throws IOException{
		
		this( resp, false );
	}
	public void finish() throws IOException{
		
		if( gOut != null ) gOut.finish();
	}
	
	@Override
	public void addCookie( Cookie arg0 ) {
		resp.addCookie( arg0 );
	}

	@Override
	public void addDateHeader( String arg0, long arg1 ) {
		resp.addDateHeader( arg0, arg1 );
	}

	@Override
	public void addHeader( String arg0, String arg1 ) {
		resp.addHeader( arg0, arg1 );
	}

	@Override
	public void addIntHeader( String arg0, int arg1 ) {
		resp.addIntHeader( arg0, arg1 );
	}

	@Override
	public boolean containsHeader( String arg0 ) {
		return resp.containsHeader( arg0 );
	}

	@Override
	public String encodeRedirectURL( String arg0 ) {
		return resp.encodeRedirectURL( arg0 );
	}

	@SuppressWarnings("deprecation")
	@Override
	public String encodeRedirectUrl( String arg0 ) {
		return resp.encodeRedirectUrl( arg0 );
	}

	@Override
	public String encodeURL( String arg0 ) {
		return resp.encodeURL( arg0 );
	}

	@SuppressWarnings("deprecation")
	@Override
	public String encodeUrl( String arg0 ) {
		return resp.encodeUrl( arg0 );
	}

	@Override
	public void sendError( int arg0 ) throws IOException {
		resp.sendError( arg0 );
	}

	@Override
	public void sendError( int arg0, String arg1 ) throws IOException {
		resp.sendError( arg0, arg1 );
	}

	@Override
	public void sendRedirect( String arg0 ) throws IOException {
		resp.sendRedirect( arg0 );
	}

	@Override
	public void setDateHeader( String arg0, long arg1 ) {
		resp.setDateHeader( arg0, arg1 );
	}

	@Override
	public void setHeader( String arg0, String arg1 ) {
		resp.setHeader( arg0, arg1 );
	}

	@Override
	public void setIntHeader( String arg0, int arg1 ) {
		resp.setIntHeader( arg0, arg1 );
	}

	@Override
	public void setStatus( int arg0 ) {
		resp.setStatus( arg0 );
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setStatus( int arg0, String arg1 ) {
		resp.setStatus( arg0, arg1 );
	}

	@Override
	public void flushBuffer() throws IOException {
		resp.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		return resp.getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		return resp.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return resp.getContentType();
	}

	@Override
	public Locale getLocale() {
		return resp.getLocale();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if( useGzip ){
			return out;
		} else {
    		return resp.getOutputStream();
		}
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if( useGzip ){
			return writer;
		} else {
    		return resp.getWriter();
		}
	}

	@Override
	public boolean isCommitted() {
		return resp.isCommitted();
	}

	@Override
	public void reset() {
		resp.reset();
	}

	@Override
	public void resetBuffer() {
		resp.resetBuffer();
	}

	@Override
	public void setBufferSize( int arg0 ) {
		resp.setBufferSize( arg0 );
	}

	@Override
	public void setCharacterEncoding( String arg0 ) {
		resp.setCharacterEncoding( arg0 );
	}

	@Override
	public void setContentLength( int arg0 ) {
		resp.setContentLength( arg0 );
	}

	@Override
	public void setContentType( String arg0 ) {
		resp.setContentType( arg0 );
	}

	@Override
	public void setLocale( Locale arg0 ) {
		resp.setLocale( arg0 );
	}

	public class ServletOutputStreamImpl extends ServletOutputStream {
		
		public OutputStream wrapped;
		
		public ServletOutputStreamImpl( OutputStream wrapped ){
			this.wrapped = wrapped;
		}

		@Override
		public void write( int b ) throws IOException {
			
			wrapped.write( b );
		}
		
	}
}
