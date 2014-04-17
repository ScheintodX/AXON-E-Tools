package de.axone.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.web.Method;
import de.axone.web.SuperURL;

public abstract class AbstractRestFunction<DATA, REQUEST extends RestRequest>
	implements RestFunction<DATA,REQUEST> {

	private final String name;
	private final RestFunctionDescription description;
	
	public AbstractRestFunction( String name, RestFunctionDescription description ){
	
		this.name = name;
		this.description = description;
	}
	
	@Override
	public String name(){
		return name;
	}
	
	public RestFunctionDescription description(){
		return description;
	}
	
	@Override
	public void setRoute( RestFunctionRoute route ){
		description.setRoute( route );
	}

	@Override
	public void renderHelp( PrintWriter out, String message, boolean detailed )
			throws Exception {

		if( message != null ){
			out.write( "<h1>" + message + "</h1>" );
		}
		
		if( detailed ){
			String template = description().getTemplate();
			if( template != null ){
				
				out.write( template );
			}
		}
		out.write( description().toHtml() );
		
	}

	protected String readParamAsString( REQUEST req, String name, boolean required ) throws RestFunctionException {

			String text = req.getParameter( name );

			if( required && text == null )
				throw new RestFunctionException(
						name + " request parameter is missing in request" );

			return text;
	}
	
	protected <T> T readData( REQUEST req, Class<T> type )
			throws RestFunctionException {
		
		T jsonData;
		
		String data = req.getParameter( "data" );
		
		// Only for debugging
		/*
		if( data == null ){
			
			try {
				data = Slurper.slurpString( req.getInputStream() );
			} catch( IOException e ) {
				throw new RestFunctionException( e );
			}
		}
		E.rr( type.getSimpleName() );
		E.rr( data );
		*/
		
		try {
			if( data != null ){
				jsonData = req.mapper().readValue( data, type );
			} else {
				jsonData = req.mapper().readValue( req.getInputStream(), type );
			}
			
			
		} catch( IOException e ) {
			e.printStackTrace();
			throw new RestFunctionException( "Cannot read JSON String", e );
		}
		
		return jsonData;
	}

	public abstract void doRun( DATA data, Method method, Map<String, String> parameters, SuperURL url,
			PrintWriter out, REQUEST req, HttpServletResponse resp ) throws Exception;
	
	
	@Override
	public void run( DATA data, Method method, Map<String,String> parameters,
			SuperURL url, PrintWriter out, REQUEST req, HttpServletResponse resp ) throws Exception {
		
		resp.setContentType( "application/json" );
		resp.setCharacterEncoding( "utf-8" );

		doRun( data, method, parameters, url, out, req, resp );
	}
	
}
