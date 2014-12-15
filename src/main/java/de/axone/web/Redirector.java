package de.axone.web;

import javax.servlet.http.HttpServletResponse;

/**
 * Replacement class for Urlifier because Urlification is done
 * by SuperURLPrinter now
 * 
 * @author flo
 */
public class Redirector {
	
	/**
	 * Only the two status codes we use.
	 * 
	 * There is no need to have more which would make things only difficult.
	 * 
	 * @author flo
	 */
	public enum Status {
		
		PERMANENT( 301 ),	// SE friendly redirect
		//@Deprecated
		//FOUND( 302 ),				// temp. redirect.
		TEMPORARY( 303 )//,			// POST -> GET
		//TEMPORARY_REDIRECT( 307 ),	// replacement for 302
		//PREMANENT_REDIRECT( 308 );	// experimental
		;
		
		private final int code;
		Status( int code ){
			this.code = code;
		}
		
		public int code(){
			return code;
		}
	}

	public static void redirectTo( HttpServletResponse response, SuperURL url, Status status ){
		
		response.setStatus( status.code() );
		response.setHeader( "Location", SuperURLPrinter.ForRedirect.toString( url ) );
		
	}
}
