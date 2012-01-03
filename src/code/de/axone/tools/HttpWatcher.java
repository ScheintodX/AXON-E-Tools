package de.axone.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.tools.HttpUtil.HttpUtilResponse;

/**
 * Tells if a web resource needs reloading.
 * 
 * This is done by means of e-tag/expiry watching and some kind of minimal recheck
 * timeout to minimize worst case network load.
 * 
 * Due to the nature of http this class returns the access to the changed
 * file if it has changed. (Request if-changed)
 * 
 * This class is similar in use to FileWatcher
 * 
 * @see de.axone.tools.FileWatcher
 *
 * @author flo
 */
public class HttpWatcher {

	public static final Logger log =
			LoggerFactory.getLogger( HttpWatcher.class );

	private static final long SECONDS = 1000;
	private static final long DAYS = SECONDS * 60 * 60 * 24;
	private static final long TIMEOUT = 2 * SECONDS;
	private static final long MAX_TIMEOUT = 1 * DAYS; // At least recheck once a day
	//private static final long MAX_TIMEOUT = 1 * SECONDS; // For manual testing

	private final URL url;
	private final long minTimeout;

	private String eTag;
	private String lastModified;
	private long expires = -1;
	private long lastCheckTime = -1;

	private long timeout = -1;
	
	/**
	 * Create a HttpWatcher for one url with the given
	 * timeout.
	 *
	 * @param url to watch
	 * @param minTimeout which has to pass until a new check is done
	 */
	public HttpWatcher( URL url, long minTimeout ){

		this.url = url;
		this.minTimeout = minTimeout;
	}

	/**
	 * Create a HttpWatcher with a default timeout of 2s
	 *
	 * @param url to watch
	 */
	public HttpWatcher( URL url ){

		this( url, TIMEOUT );
	}

	public HttpUtilResponse hasChanged(){

		long time = System.currentTimeMillis();
		
		long div = time - lastCheckTime;
		
		HttpUtilResponse result = null;
		
		// See if timeout has passed to do a recheck
		if( div >= timeout || timeout < 0 ){
			
			lastCheckTime = time;
			
			try {
				
				if( log.isTraceEnabled() ) log.trace( "Check for: " + url + "(" + eTag + ")" );
				
				HttpUtilResponse response = HttpUtil.request( url, eTag, lastModified );
				
				log.trace( response.toString() );
		
				if( response.code == 200 ){
					eTag = response.eTag;
					lastModified = response.lastModified;
					
					long maxAgeMs = response.maxAge * 1000;
					maxAgeMs = ( maxAgeMs > minTimeout ? maxAgeMs : minTimeout );
					maxAgeMs = ( maxAgeMs < MAX_TIMEOUT ? maxAgeMs : MAX_TIMEOUT );
					
					timeout = maxAgeMs;
					
					result = response;
				}
			} catch( ClientProtocolException e ) {
				log.error( url.toString(), e );
			} catch( URISyntaxException e ) {
				log.error( url.toString(), e );
			} catch( IOException e ) {
				log.error( url.toString(), e );
			}
		}
		
		return result;
	}

	public URL getURL(){
		return url;
	}

	@Override
	public String toString(){

		return
			"FileWatcher for: " + url + 
			" timeout: " + timeout +
			" E-Tag: " + eTag +
			" Expires: " + expires +
			" Last check: + " + lastCheckTime;
	}

	/* STATIC METHODS for persistent Watchers */
	private static HashMap<String, HttpWatcher> staticWatchers = new HashMap<String, HttpWatcher>();
	public synchronized static HttpWatcher staticWatcher( URL url, long timeout ){

		String key = url.toString() + "/" + timeout;

		if( ! staticWatchers.containsKey( key ) ){
			staticWatchers.put( key, new HttpWatcher( url, timeout ) );
		}
		return staticWatchers.get( key );
	}
	public static HttpWatcher staticWatcher( URL url ){
		return staticWatcher( url, TIMEOUT );
	}

}
