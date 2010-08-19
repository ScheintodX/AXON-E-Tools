package de.axone.tools;

import java.net.URL;
import java.util.HashMap;

import de.axone.logging.Log;
import de.axone.logging.Logging;
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

	private static Log log = Logging.getLog( HttpWatcher.class );

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
	
	public static void main( String [] args ) throws Exception {
		
		HttpWatcher w = new HttpWatcher( new URL( "http://img0.gmodules.com/ig/images/igoogle_logo_sm.gif" ), 0 );
		
		for( int i = 0; i < 200; i++ ){
			E.rr( w.hasChanged() != null );
			try {
				Thread.sleep( 100 );
			} catch( InterruptedException e ){}
		}
	}
	

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
			
		E.rr( div + " >=" + timeout );
		
		// See if timeout has passed to do a recheck
		if( div >= timeout || timeout < 0 ){
			
			lastCheckTime = time;
			
			try {
				
				log.trace( "Check for: " + url + "(" + eTag + ")" );
				
				HttpUtilResponse response = HttpUtil.request( url, eTag, lastModified );
		
				E.rr( "a" );
			
				if( response.code == 200 ){
					eTag = response.eTag;
					lastModified = response.lastModified;
					E.rr( eTag );
					long maxAgeMs = response.maxAge * 1000;
					maxAgeMs = ( maxAgeMs > minTimeout ? maxAgeMs : minTimeout );
					maxAgeMs = ( maxAgeMs < MAX_TIMEOUT ? maxAgeMs : MAX_TIMEOUT );
					E.rr( maxAgeMs/1000 + "s" );
					timeout = maxAgeMs;
					E.rr( timeout/1000 );
					result = response;
				}
			} catch( Exception e ){
				// In case of error: nothing has changed
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
