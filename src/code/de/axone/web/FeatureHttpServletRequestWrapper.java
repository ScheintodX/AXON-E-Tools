package de.axone.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class FeatureHttpServletRequestWrapper implements HttpServletRequest {
	
	private HttpServletRequest req;
	
	private HashMap<String,String> parametersOverride =
		new HashMap<String,String>();
	
	public FeatureHttpServletRequestWrapper( HttpServletRequest wrapped ){
		
		this.req = wrapped;
	}

	@Override
	public String getAuthType() {
		return req.getAuthType();
	}

	@Override
	public String getContextPath() {
		return req.getContextPath();
	}

	@Override
	public Cookie[] getCookies() {
		return req.getCookies();
	}

	@Override
	public long getDateHeader( String arg0 ) {
		return req.getDateHeader( arg0 );
	}

	@Override
	public String getHeader( String arg0 ) {
		return req.getHeader( arg0 );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getHeaderNames() {
		return req.getHeaderNames();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getHeaders( String arg0 ) {
		return req.getHeaders( arg0 );
	}

	@Override
	public int getIntHeader( String arg0 ) {
		return req.getIntHeader( arg0 );
	}

	@Override
	public String getMethod() {
		return req.getMethod();
	}

	@Override
	public String getPathInfo() {
		return req.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return req.getPathTranslated();
	}

	@Override
	public String getQueryString() {
		return req.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return req.getRemoteUser();
	}

	@Override
	public String getRequestURI() {
		return req.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return req.getRequestURL();
	}

	@Override
	public String getRequestedSessionId() {
		return req.getRequestedSessionId();
	}

	@Override
	public String getServletPath() {
		return req.getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return req.getSession();
	}

	@Override
	public HttpSession getSession( boolean arg0 ) {
		return req.getSession( arg0 );
	}

	@Override
	public Principal getUserPrincipal() {
		return req.getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return req.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return req.isRequestedSessionIdFromURL();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return req.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return req.isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole( String arg0 ) {
		return req.isUserInRole( arg0 );
	}

	@Override
	public Object getAttribute( String arg0 ) {
		return req.getAttribute( arg0 );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getAttributeNames() {
		return req.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return req.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return req.getContentLength();
	}

	@Override
	public String getContentType() {
		return req.getContentType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return req.getInputStream();
	}

	@Override
	public String getLocalAddr() {
		return req.getLocalAddr();
	}

	@Override
	public String getLocalName() {
		return req.getLocalName();
	}

	@Override
	public int getLocalPort() {
		return req.getLocalPort();
	}

	@Override
	public Locale getLocale() {
		return req.getLocale();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getLocales() {
		return req.getLocales();
	}
	
	public void overrideParameter( String key, String value ){
		
		parametersOverride.put( key, value );
	}

	@Override
	public String getParameter( String arg0 ) {
		
		String value = parametersOverride.get( arg0 );
		
		if( value != null ) return value;
		
		return req.getParameter( arg0 );
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getParameterMap() {
		return req.getParameterMap();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getParameterNames() {
		return req.getParameterNames();
	}

	@Override
	public String[] getParameterValues( String arg0 ) {
		return req.getParameterValues( arg0 );
	}

	@Override
	public String getProtocol() {
		return req.getProtocol();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return req.getReader();
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getRealPath( String arg0 ) {
		return req.getRealPath( arg0 );
	}

	@Override
	public String getRemoteAddr() {
		return req.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return req.getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		return req.getRemotePort();
	}

	@Override
	public RequestDispatcher getRequestDispatcher( String arg0 ) {
		return req.getRequestDispatcher( arg0 );
	}

	@Override
	public String getScheme() {
		return req.getScheme();
	}

	@Override
	public String getServerName() {
		return req.getServerName();
	}

	@Override
	public int getServerPort() {
		return req.getServerPort();
	}

	@Override
	public boolean isSecure() {
		return req.isSecure();
	}

	@Override
	public void removeAttribute( String arg0 ) {
		req.removeAttribute( arg0 );
	}

	@Override
	public void setAttribute( String arg0, Object arg1 ) {
		req.setAttribute( arg0, arg1 );
	}

	@Override
	public void setCharacterEncoding( String arg0 )
			throws UnsupportedEncodingException {
		
		req.setCharacterEncoding( arg0 );
	}

}
