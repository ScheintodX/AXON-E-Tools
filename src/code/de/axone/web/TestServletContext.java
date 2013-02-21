package de.axone.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServletContext implements ServletContext {
	
	public static final Logger log =
			LoggerFactory.getLogger( TestServletContext.class );

	/* --- Attributes --- */
	
	@Override
	public Object getAttribute( String name ) {
		return attributes.get( name );
	}
	private Map<String, Object> attributes = new HashMap<String, Object>();
	public void setAttributes( Map<String, Object> attributes ){
		this.attributes = attributes;
	}
	@Override
	public void removeAttribute( String name ) {
		attributes.remove( name );
	}
	@Override
	public void setAttribute( String name, Object object ) {
		attributes.put( name, object );
	}
	@Override
	public Enumeration<String> getAttributeNames() {
		return Collections.enumeration( attributes.keySet() );
	}

	/* --- Contexts --- */
	@Override
	public ServletContext getContext( String uripath ) {
		return servletContext;
	}
	private ServletContext servletContext;
	public void setServletContext( ServletContext servletContext ){
		this.servletContext = servletContext;
	}
	
	@Override
	public String getContextPath() {
		return contextPath;
	}
	private String contextPath;
	public void setContextPath( String contextPath ){
		this.contextPath = contextPath;
	}

	/* --- Parameters --- */
	@Override
	public String getInitParameter( String name ) {
		return initParameters.get( name );
	}
	Map<String,String> initParameters = new HashMap<String,String>();
	public void setInitParameters( Map<String,String> initParameters ){
		this.initParameters = initParameters;
	}
	@Override
	public Enumeration<String> getInitParameterNames() {
		return Collections.enumeration( initParameters.keySet() );
	}

	/* --- Dispatcher (whatever...) --- */
	@Override
	public RequestDispatcher getNamedDispatcher( String name ) {
		return namedDispatchers.get( name );
	}
	private Map<String,RequestDispatcher> namedDispatchers = new HashMap<String,RequestDispatcher>();
	public void setNamedDispatchers( Map<String,RequestDispatcher> namedDispatchers ){
		this.namedDispatchers = namedDispatchers;
	}
	
	@Override
	public RequestDispatcher getRequestDispatcher( String path ) {
		return requestDispatchers.get( path );
	}
	private Map<String,RequestDispatcher> requestDispatchers = new HashMap<String,RequestDispatcher>();
	public void setRequestDispatchers( Map<String,RequestDispatcher> requestDispatchers ){
		this.requestDispatchers = requestDispatchers;
	}
	
	/* --- Resources --- */
	@Override
	public String getRealPath( String path ) {
		return path;
	}
	@Override
	public URL getResource( String path ) throws MalformedURLException {
		return new URL( path );
	}
	@Override
	public InputStream getResourceAsStream( String path ) {
		try {
			return new FileInputStream( new File( path ) );
		} catch( FileNotFoundException e ) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Set<String> getResourcePaths( String path ) {
		
		File dir = new File( path );
		
		if( dir.isDirectory() ){
			
    		HashSet<String> paths = new HashSet<String>();
			
    		for( File file : dir.listFiles() ){
    			
    			paths.add( file.getPath() );
    		}
    		return paths;
		} else {
			return null;
		}
	}
	
	/* --- Servlets --- */
	@Override
	public Servlet getServlet( String name ) throws ServletException {
		return servlets.get( name );
	}
	private Map<String,Servlet> servlets = new HashMap<String,Servlet>();
	public void setServlets( Map<String,Servlet> servlets ){
		this.servlets = servlets;
	}
	@Override
	public Enumeration<String> getServletNames() {
		return Collections.enumeration( servlets.keySet() );
	}
	@Override
	public Enumeration<Servlet> getServlets() {
		return Collections.enumeration( servlets.values() );
	}

	/* --- Log --- */
	@Override
	public void log( String msg ) {
		log.info( msg );
	}
	@Override
	public void log( Exception exception, String msg ) {
		log.info( msg, exception );
	}
	@Override
	public void log( String message, Throwable throwable ) {
		log.info( message, throwable );
	}

	/* --- Stuff --- */
	@Override
	public String getServerInfo() {
		return "AXON-E Test. But no server!";
	}
	@Override
	public String getServletContextName() {
		return "AXON-E Test Servlet Context";
	}
	
	@Override
	public int getMajorVersion() {
		return majorVersion;
	}
	private int majorVersion=-1;
	public void setMajorVersion( int majorVersion ){
		this.majorVersion = majorVersion;
	}

	@Override
	public int getMinorVersion() {
		return minorVersion;
	}
	private int minorVersion = -1;
	public void setMiniorVersion( int minorVersion ){
		this.minorVersion = minorVersion;
	}
	
	@Override
	public String getMimeType( String file ) {
		return mimeType;
	}
	private String mimeType;
	public void setMimeType( String mimeType ){
		this.mimeType = mimeType;
	}
	
	/* Since 3.0 -------------------------------------------- */
	
	@Override
	public int getEffectiveMajorVersion() {
		return effectiveMajorVersion;
	}
	private int effectiveMajorVersion;
	public void setEffectiveMajorVersion( int effectiveMajorVersion ){
		this.effectiveMajorVersion = effectiveMajorVersion;
	}
	
	@Override
	public int getEffectiveMinorVersion() {
		return effectiveMinorVersion;
	}
	private int effectiveMinorVersion;
	public void setEffectiveMinorVersion( int effectiveMinorVersion ){
		this.effectiveMinorVersion = effectiveMinorVersion;
	}
	
	@Override
	public boolean setInitParameter( String name, String value ) {
		initParameters.put( name, value );
		return true;
	}
	
	@Override
	public Dynamic addServlet( String servletName, String className ) {
		
		try {
			servlets.put( servletName, (Servlet)Class.forName( className ).newInstance() );
		} catch( InstantiationException | IllegalAccessException | ClassNotFoundException e ) {
			throw new RuntimeException( e );
		}
		
		// TODO: Rückgabe-Wert sollte was anders sein...
		return null;
	}
	@Override
	public Dynamic addServlet( String servletName, Servlet servlet ) {
		
		servlets.put( servletName, servlet );
		
		// TODO: Rückgabe-Wert sollte was anders sein...
		return null;
	}
	@Override
	public Dynamic addServlet( String servletName,
			Class<? extends Servlet> servletClass ) {
		
		try {
			servlets.put( servletName, servletClass.newInstance() );
		} catch( InstantiationException | IllegalAccessException e ) {
			throw new RuntimeException( e );
		}
		
		// TODO: Rückgabe-Wert sollte was anders sein...
		return null;
	}
	@Override
	public <T extends Servlet> T createServlet( Class<T> c )
			throws ServletException {
		
		throw new UnsupportedOperationException();
	}
	@Override
	public ServletRegistration getServletRegistration( String servletName ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		throw new UnsupportedOperationException();
	}
	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(
			String filterName, String className ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(
			String filterName, Filter filter ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(
			String filterName, Class<? extends Filter> filterClass ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public <T extends Filter> T createFilter( Class<T> c )
			throws ServletException {
		throw new UnsupportedOperationException();
	}
	@Override
	public FilterRegistration getFilterRegistration( String filterName ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		throw new UnsupportedOperationException();
	}
	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void setSessionTrackingModes(
			Set<SessionTrackingMode> sessionTrackingModes )
			throws IllegalStateException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}
	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		throw new UnsupportedOperationException();
	}
	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		throw new UnsupportedOperationException();
	}
	@Override
	public void addListener( Class<? extends EventListener> listenerClass ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public void addListener( String className ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public <T extends EventListener> void addListener( T t ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public <T extends EventListener> T createListener( Class<T> c )
			throws ServletException {
		throw new UnsupportedOperationException();
	}
	@Override
	public void declareRoles( String ... roleNames ) {
		throw new UnsupportedOperationException();
	}
	@Override
	public ClassLoader getClassLoader() {
		throw new UnsupportedOperationException();
	}
	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		throw new UnsupportedOperationException();
	}

}
