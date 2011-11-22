package de.axone.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class TestHttpServletRequest implements HttpServletRequest {

	@Override
	public String getAuthType() {
		return authType;
	}
	private String authType;
	public void setAuthType( String authType ){
		this.authType = authType;
	}

	@Override
	public String getContextPath() {
		return contextPath;
	}
	private String contextPath;
	public void setContextPath( String contextPath ){
		this.contextPath = contextPath;
	}

	@Override
	public Cookie[] getCookies() {
		return cookies;
	}
	private Cookie[] cookies;
	public void setCookies( Cookie[] cookies ){
		this.cookies = cookies;
	}

	@Override
	public long getDateHeader( String key ) {
		return dateHeaders.get( key );
	}
	private Map<String, Long> dateHeaders = new HashMap<String, Long>();
	public void setDateHeaders( Map<String, Long> dateHeaders ){
		this.dateHeaders = dateHeaders;
	}

	@Override
	public String getHeader( String key ) {
		return headers.get( key );
	}
	private Map<String,String> headers = new HashMap<String,String>();
	public void setHeaders( Map<String, String> headers ){
		this.headers = headers;
	}
	
	public void setHeader( String name, String value ){
		headers.put( name, value );
	}

	@Override
	public Enumeration<?> getHeaderNames() {
		return Collections.enumeration( headers.keySet() );
	}

	@Override
	public Enumeration<?> getHeaders( String key ) {
		return Collections.enumeration( headers.entrySet() );
	}

	@Override
	public int getIntHeader( String key ) {
		return Integer.parseInt( getHeader( key ) );
	}

	@Override
	public String getMethod() {
		return method;
	}
	private String method;
	public void setMethod( String method ){
		this.method = method;
	}

	@Override
	public String getPathInfo() {
		return pathInfo;
	}
	private String pathInfo;
	public void setPathInfo( String pathInfo ){
		this.pathInfo = pathInfo;
	}

	@Override
	public String getPathTranslated() {
		return pathTranslated;
	}
	private String pathTranslated;
	public void setPathTranslated( String pathTranslated ){
		this.pathTranslated = pathTranslated;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}
	private String queryString;
	public void setQueryString( String queryString ){
		this.queryString = queryString;
	}

	@Override
	public String getRemoteUser() {
		return remoteUser;
	}
	private String remoteUser;
	public void setRemoteUser( String remoteUser ){
		this.remoteUser = remoteUser;
	}

	@Override
	public String getRequestURI() {
		return requestURI;
	}
	private String requestURI;
	public void setRequestURI( String requestURI ){
		this.requestURI = requestURI;
	}

	@Override
	public StringBuffer getRequestURL() {
		return requestURL;
	}
	private StringBuffer requestURL;
	public void setRequestURL( StringBuffer requestURL ){
		this.requestURL = requestURL;
	}

	@Override
	public String getRequestedSessionId() {
		return requestSessionId;
	}
	private String requestSessionId;
	public void setRequestSessionId( String requestSessionId ){
		this.requestSessionId = requestSessionId;
	}

	@Override
	public String getServletPath() {
		return servletPath;
	}
	private String servletPath;
	public void setServletPath( String servletPath ){
		this.servletPath = servletPath;
	}

	@Override
	public HttpSession getSession() {
		return httpSession;
	}
	private HttpSession httpSession = new TestHttpSession();
	public void setSession( HttpSession httpSession ){
		this.httpSession = httpSession;
	}

	@Override
	public HttpSession getSession( boolean create ) {
		
		if( httpSession == null && create == true ){
			httpSession = new TestHttpSession();
		}
		return httpSession;
	}

	@Override
	public Principal getUserPrincipal() {
		return principal;
	}
	private Principal principal;
	public void setUserprincipal( Principal principal ){
		this.principal = principal;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return isRequestedSessionIdFromCookie;
	}
	private boolean isRequestedSessionIdFromCookie;
	public void setRequestSessionIdFromCookie( boolean isRequestSessionIdFromCookie ){
		this.isRequestedSessionIdFromCookie = isRequestSessionIdFromCookie;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return isRequestedSessionIdFromURL;
	}
	private boolean isRequestedSessionIdFromURL;
	public void setRequestedSessionIdFromURL( boolean isRequestedSessionIdFromURL ){
		this.isRequestedSessionIdFromURL = isRequestedSessionIdFromURL;
	}

	@Override
	@Deprecated
	public boolean isRequestedSessionIdFromUrl() {
		return isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return isRequestedSessionIdValid;
	}
	private boolean isRequestedSessionIdValid;
	public void setRequestedSessionIdValid( boolean isRequestedSessionIdValid ){
		this.isRequestedSessionIdValid = isRequestedSessionIdValid;
	}

	@Override
	public boolean isUserInRole( String role ) {
		return roles.contains( role );
	}
	private Set<String> roles = new HashSet<String>();
	public void setRoles( Set<String> roles ){
		this.roles = roles;
	}

	@Override
	public Object getAttribute( String key ) {
		return attributes.get( key );
	}
	private Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public Enumeration<?> getAttributeNames() {
		return Collections.enumeration( attributes.keySet() );
	}

	@Override
	public void removeAttribute( String key ) {
		attributes.remove( key );
	}

	@Override
	public void setAttribute( String key, Object value ) {
		attributes.put( key, value );
	}

	@Override
	public String getCharacterEncoding() {
		return characterEncoding;
	}
	private String characterEncoding;
	@Override
	public void setCharacterEncoding( String characterEncoding ) throws UnsupportedEncodingException {
		this.characterEncoding = characterEncoding;
	}

	/* -------------------------------------------------------- */
	
	private String content;
	public void setContent( String content ){
		this.content = content;
	}
	
	@Override
	public int getContentLength() {
		return content.length();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return new TestServletInputStream(); //Connected via inner class
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader( new StringReader( content ) );
	}

	@Override
	public String getContentType() {
		return contentType;
	}
	private String contentType;
	public void setContentType( String contentType ){
		this.contentType = contentType;
	}

	/* -------------------------------------------------------- */

	@Override
	public String getLocalAddr() {
		return localAddr;
	}
	private String localAddr;
	public void setLocalAddr( String localAddr ){
		this.localAddr = localAddr;
	}

	@Override
	public String getLocalName() {
		return localName;
	}
	private String localName;
	public void setLocalName( String localName ){
		this.localName = localName;
	}

	@Override
	public int getLocalPort() {
		return localPort;
	}
	private int localPort;
	public void setLocalPort( int localPort ){
		this.localPort = localPort;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}
	private Locale locale;
	public void setLocale( Locale locale ){
		this.locale = locale;
	}

	@Override
	public Enumeration<?> getLocales() {
		return Collections.enumeration( locales );
	}
	private List<Locale> locales = new LinkedList<Locale>();
	public void setLocales( List<Locale> locales ){
		this.locales = locales;
	}

	@Override
	public String getParameter( String key ) {
		return parameters.get( key );
	}
	private Map<String, String> parameters = new HashMap<String, String>();
	public void setParameters( Map<String, String> parameters ){
		this.parameters = parameters;
	}
    public void setParameter( String key, String value ){
    	parameters.put( key, value );
    }
	
	@Override
	public Map<?,?> getParameterMap() {
		return parameters;
	}

	@Override
	public Enumeration<?> getParameterNames() {
		return Collections.enumeration( parameters.keySet() );
	}

	@Override
	public String[] getParameterValues( String arg0 ) {
		/*
		String[] result = new String[ parameters.size() ];
		for( int i=0; i< parameters.size(); i++ ) result[i] = parameters.get( i );
		return result;
		*/
		String value = parameters.get( arg0 );
		if( value == null ){
			return new String[]{};
		} else {
			return new String[]{ value };
		}
	}

	@Override
	public String getProtocol() {
		return protocol;
	}
	private String protocol;
	public void setProtocol( String protocol ){
		this.protocol = protocol;
	}

	@Override
	@Deprecated
	public String getRealPath( String arg0 ) {
		return realPath;
	}
	private String realPath;
	public void setRealPath( String realPath ){
		this.realPath = realPath;
	}

	@Override
	public String getRemoteAddr() {
		return remoteAddr;
	}
	private String remoteAddr;
	public void setRemoteAddr( String remoteAddr ){
		this.remoteAddr = remoteAddr;
	}

	@Override
	public String getRemoteHost() {
		return remoteHost;
	}
	private String remoteHost;
	public void setRemoteHost( String remoteHost ){
		this.remoteHost = remoteHost;
	}

	@Override
	public int getRemotePort() {
		return remotePort;
	}
	private int remotePort;
	public void setRemotePort( int remotePort ){
		this.remotePort = remotePort;
	}

	@Override
	public RequestDispatcher getRequestDispatcher( String arg0 ) {
		return requestDispatcher;
	}
	private RequestDispatcher requestDispatcher;
	public void setRequestDispatcher( RequestDispatcher requestDispatcher ){
		this.requestDispatcher = requestDispatcher;
	}

	@Override
	public String getScheme() {
		return scheme;
	}
	private String scheme;
	public void setScheme( String scheme ){
		this.scheme = scheme;
	}

	@Override
	public String getServerName() {
		return serverName;
	}
	private String serverName;
	public void setServerName( String serverName ){
		this.serverName = serverName;
	}

	@Override
	public int getServerPort() {
		return serverPort;
	}
	private int serverPort;
	public void setServerPort( int serverPort ){
		this.serverPort = serverPort;
	}

	@Override
	public boolean isSecure() {
		return isSecure;
	}
	private boolean isSecure;
	public void setSecure( boolean isSecure ){
		this.isSecure = isSecure;
	}

	private class TestServletInputStream extends ServletInputStream {
		
		int count = 0;
		
		@Override
		public int read() throws IOException {
			
			if( count < content.length() ){
				return content.charAt( count++ );
			} else {
				return -1;
			}
		}
		
	}
}
