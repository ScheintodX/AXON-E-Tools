package de.axone.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.tools.E;
import de.axone.tools.Slurper;
import de.axone.web.Method;
import de.axone.web.SuperURL;

public abstract class AbstractRestFunction<DATA, REQUEST extends RestRequest>
	implements RestFunction<DATA,REQUEST> {

	private final String name;
	
	public AbstractRestFunction( String name ){
	
		this.name = name;
	}
	
	@Override
	public String name(){
		return name;
	}
	
	@Override
	public abstract RestFunctionDescription description();
	

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
		
		if( data == null ){
			
			try {
				data = Slurper.slurpString( req.getInputStream() );
			} catch( IOException e ) {
				throw new RestFunctionException( e );
			}
		}
		E.rr( type.getSimpleName() );
		E.rr( data );
		
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
