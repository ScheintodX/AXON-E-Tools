package de.axone.web;

import java.io.File;

import javax.servlet.ServletContext;

public class PathUtilServlet implements PathUtil {
	
	private final ServletContext servletContext;
	
	public PathUtilServlet( ServletContext servletContext ) {
		
		this.servletContext = servletContext;
		
	}

	@Override
	public File realFile( String file ) {
		
		return new File( servletContext.getRealPath( file ) );
	}

	@Override
	public File settingsFile() {
		
		String settingsPath = servletContext.getInitParameter( "settings.path" );
		
		if( settingsPath== null ) throw new IllegalArgumentException (
				"Please configure a context-paramter 'settings.path' with path to settings file" );

		return new File( settingsPath );
	}
}
