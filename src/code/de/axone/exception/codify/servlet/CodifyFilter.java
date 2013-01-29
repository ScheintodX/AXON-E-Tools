package de.axone.exception.codify.servlet;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import de.axone.exception.codify.CodifiedError;
import de.axone.exception.codify.CodifiedRuntimeException;
import de.axone.exception.codify.Codifier;
import de.axone.tools.E;


public class CodifyFilter implements javax.servlet.Filter {

	@Override
	public void init( FilterConfig config ) throws ServletException {
		System.out.println( "Starting codify filter" );
		E.rr( config.getInitParameter( "paralell" ) );
	}
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter( ServletRequest req, ServletResponse resp,
			FilterChain chain ) throws IOException, ServletException {
		
		try {
			chain.doFilter( req, resp );
			
		} catch( IOException e ){
			Codifier.report( e );
			throw new CodifiedIOException( e );
		} catch( ServletException e ){
			Codifier.report( e );
			throw new CodifiedServletException( e );
		} catch( RuntimeException e ){
			Codifier.report( e );
			throw new CodifiedRuntimeException( e );
		} catch( Error e ){
			Codifier.report( e );
			throw new CodifiedError( e );
		}
		
	}

}