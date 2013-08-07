package de.axone.web.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.web.SuperURL;

public abstract class AbstractRestServlet<DATA, REQUEST extends RestRequest> extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger( AbstractRestServlet.class );

	public enum Method { GET, POST, PUT, DELETE; }
	
	private final RestFunctionRegistry<DATA, REQUEST> registry;
	
	protected AbstractRestServlet( RestFunctionRegistry<DATA, REQUEST> registry ){
		
		this.registry = registry;
	}
	
	protected abstract REQUEST makeRequest( HttpServletRequest req, HttpServletResponse resp );
	
	protected void _process( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		if( log.isTraceEnabled() ){
			SuperURL u = new SuperURL( req );
			log.trace( "Request for: {}", u.toString() );
		}
		
		resp.setCharacterEncoding( "utf-8" );
		resp.setContentType( "application/json" );
		
		REQUEST request = makeRequest( req, resp );
		
		try {
			
			registry.run( null, request, resp );
		} catch( Throwable t ){
			
			throw new ServletException( t );
			
		} finally {
		
			resp.getWriter().close();
		}
		
	}

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		_process( req, resp );
	}

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		_process( req, resp );
	}

	@Override
	protected void doPut( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		_process( req, resp );
	}

	@Override
	protected void doDelete( HttpServletRequest req, HttpServletResponse resp )
			throws ServletException, IOException {
		
		_process( req, resp );
	}
}
