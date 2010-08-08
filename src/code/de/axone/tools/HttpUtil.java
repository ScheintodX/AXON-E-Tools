package de.axone.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class HttpUtil {
	
	public static void main( String [] args ) throws Exception {
		
		E.cho( request( 
				new URL( "http://www.plamundo.de/pics/CshswtlOVR_DrhkwL4nD4S_85eO0CBHCN1idiOJ4F1vwHPSm5YpguA0vgGc7pEc7/06705_200.jpg" ), 
				"ec3de308", null ) );
	}
	
	public static class HttpUtilResponse {
		
		public int code = -1;
		public String eTag;
		public String lastModified;
		public long maxAge = -1;
		public byte[] content;
		public String encoding;
		
		public String toString(){
			return 
				"code: " + code + "\n" +
				"etag: " + eTag + "\n" +
				"maxAge: " + maxAge + "\n" +
				"size: " + content.length
			;
		}
	}

	public static HttpUtilResponse request( URL url, String eTag, String lastModified ) throws URISyntaxException, ClientProtocolException, IOException{
		
		HttpClient client = new DefaultHttpClient();
		
		HttpGet httpget = new HttpGet( url.toURI() );
		if( eTag != null ){
			httpget.setHeader( "If-None-Match", eTag );
		}
		if( lastModified != null ){
			httpget.setHeader( "If-Modified-Since", lastModified );
		}
		E.rr( (Object[])httpget.getAllHeaders() );
		
		HttpResponse response = client.execute( httpget );
		
		E.rr( response.getStatusLine() );
		E.rr( (Object[]) response.getAllHeaders() );
		
		HttpUtilResponse uResponse = new HttpUtilResponse();
		uResponse.code = response.getStatusLine().getStatusCode();
		
		// break here if not modified
		if( uResponse.code == 304 ) return uResponse;
		
		if( uResponse.code == 200 ){
		
			Header [] eTags = response.getHeaders( "ETag" );
			if( eTags != null && eTags.length > 0 ){
				uResponse.eTag = eTags[ eTags.length-1 ].getValue();
			}
			Header [] lastModifieds = response.getHeaders( "Last-Modified" );
			if( lastModifieds != null && lastModifieds.length > 0 ){
				uResponse.lastModified = lastModifieds[ lastModifieds.length-1 ].getValue();
			}
			
			Header [] cacheControls = response.getHeaders( "Cache-Control" );
			String cacheControl = null;
			if( cacheControls != null && cacheControls.length > 0 ){
				cacheControl = cacheControls[ cacheControls.length - 1 ].getValue();
			}
			if( cacheControl != null ){
				Pattern pattern = Pattern.compile( "max-age=([0-9]+)" );
				Matcher matcher = pattern.matcher( cacheControl );
				if( matcher.find() ){
					String maxAgeGroup = matcher.group();
					String maxAgeString = maxAgeGroup.substring( maxAgeGroup.indexOf( '=' )+1 );
					uResponse.maxAge = Long.parseLong( maxAgeString );
				}
			}
			
			
			HttpEntity entity = response.getEntity();
			
			Header encodingHeader = entity.getContentEncoding();
			if( encodingHeader != null ){
				uResponse.encoding = encodingHeader.getValue();
			}
			
			InputStream in = entity.getContent();
			byte [] content = Slurper.slurp( in );
			uResponse.content = content;
			
		}
		return uResponse;
	}
}
