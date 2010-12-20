package de.axone.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class FeatureHttpServletResponseWrapper extends HttpServletResponseWrapper {
	
	private ServletOutputStream out;
	private GZIPOutputStream gOut;
	private PrintWriter writer;
	private boolean useGzip;

	public FeatureHttpServletResponseWrapper( HttpServletResponse resp, boolean useGzip ) throws IOException{
		
		super( resp );
		
		this.useGzip = useGzip;
		
		if( useGzip ){
			gOut = new GZIPOutputStream( resp.getOutputStream() );
    		out = new ServletOutputStreamImpl( gOut );
    		writer = new PrintWriter( new OutputStreamWriter( gOut, Charset.forName( "UTF-8" ) ) );
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
	public ServletOutputStream getOutputStream() throws IOException {
		if( useGzip ){
			return out;
		} else {
    		return super.getOutputStream();
		}
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if( useGzip ){
			return writer;
		} else {
    		return super.getWriter();
		}
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
