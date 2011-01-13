package de.axone.web;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class TestServletConfig implements ServletConfig {
	
	@Override
	public String getInitParameter( String name ) {
		return initParameters.get( name );
	}
	Map<String,String> initParameters = new HashMap<String,String>();
	public void setInitParameters( Map<String,String> initParameters ){
		this.initParameters = initParameters;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return Collections.enumeration( initParameters.keySet() );
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}
	private ServletContext servletContext;
	public void setServletContext( ServletContext servletContext ){
		this.servletContext = servletContext;
	}

	@Override
	public String getServletName() {
		return servletName;
	}
	private String servletName;
	public void setServletName( String servletName ){
		this.servletName = servletName;
	}

}
