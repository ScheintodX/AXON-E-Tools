package de.axone.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class TestHttpSession implements HttpSession {

	@Override
	public Object getAttribute( String arg0 ) {
		return attributes.get( arg0 );
	}
	HashMap<String,Object>attributes = new HashMap<String,Object>();

	@Override
	public Enumeration<String> getAttributeNames() {
		return (new Vector( attributes.keySet() )).elements();
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}
	long creationTime = 0;
	public void setCreationTime( long creationTime ){
		this.creationTime = creationTime;
	}

	@Override
	public String getId() {
		return id;
	}
	String id;
	public void setId( String id ) {
		this.id = id;
	}


	@Override
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}
	long lastAccessedTime = 0;

	@Override
	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
	int maxInactiveInterval;

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}
	ServletContext servletContext;

	@Override
	public HttpSessionContext getSessionContext() {
		return sessionContext;
	}
	HttpSessionContext sessionContext;

	@Override
	public Object getValue( String arg0 ) {
		return values.get( arg0 );
	}
	HashMap<String,Object> values = new HashMap<String,Object>();

	@Override
	public String[] getValueNames() {
		return values.keySet().toArray( new String[0] );
	}

	@Override
	public void invalidate() {
	}

	@Override
	public boolean isNew() {
		return isNew;
	}
	boolean isNew = false;

	@Override
	public void putValue( String arg0, Object arg1 ) {
		values.put( arg0, arg1 );

	}

	public void setAttributes( HashMap<String, Object> attributes ) {
		this.attributes = attributes;
	}

	public void setLastAccessedTime( long lastAccessedTime ) {
		this.lastAccessedTime = lastAccessedTime;
	}

	public void setServletContext( ServletContext servletContext ) {
		this.servletContext = servletContext;
	}

	public void setSessionContext( HttpSessionContext sessionContext ) {
		this.sessionContext = sessionContext;
	}

	public void setValues( HashMap<String, Object> values ) {
		this.values = values;
	}

	public void setNew( boolean isNew ) {
		this.isNew = isNew;
	}

	@Override
	public void removeAttribute( String arg0 ) {
		attributes.remove( arg0 );
	}

	@Override
	public void removeValue( String arg0 ) {
		values.remove( arg0 );

	}

	@Override
	public void setAttribute( String arg0, Object arg1 ) {
		attributes.put( arg0, arg1 );

	}

	@Override
	public void setMaxInactiveInterval( int arg0 ) {
	}

}
