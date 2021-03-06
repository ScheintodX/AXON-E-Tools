package de.axone.web.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.data.Charsets;
import de.axone.data.Mime.MimeTypes;
import de.axone.web.SuperURL;
import de.axone.web.SuperURLBuilders;

public abstract class AbstractRestServlet<DATA, REQUEST extends RestRequest> extends HttpServlet {
	
	private static final long serialVersionUID = 2270602676285881313L;
	private static final Logger log = LoggerFactory.getLogger( AbstractRestServlet.class );

	private final RestFunctionRegistry<DATA, REQUEST> registry;
	
	protected AbstractRestServlet( RestFunctionRegistry<DATA, REQUEST> registry ){
		
		this.registry = registry;
	}
	
	protected abstract REQUEST makeRequest( HttpServletRequest req, HttpServletResponse resp );
	
	protected void _process( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		
		if( log.isTraceEnabled() ){
			SuperURL u = SuperURLBuilders.fromRequest().build( req );
			log.trace( "Request for: {}", u.toDebug() );
		}
		
		resp.setCharacterEncoding( Charsets.utf8 );
		resp.setContentType( MimeTypes.JSON.text() );
		resp.setHeader( "Access-Control-Allow-Origin", "*" );
		
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
