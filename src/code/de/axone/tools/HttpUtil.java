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
	
	public static class HttpUtilResponse {
		
		public int code = -1;
		public String eTag;
		public String lastModified;
		public long maxAge = -1;
		public byte[] content;
		public String encoding;
		
		@Override
		public String toString(){
			return 
				"code: " + code + "\n" +
				"etag: " + eTag + "\n" +
				"maxAge: " + maxAge + "\n" +
				"size: " + (content != null ? content.length : 0)
			;
		}
	}
	
	private static String printHttpInfo( HttpGet httpget, HttpResponse response ){
		
		StringBuilder result = new StringBuilder();
		
		result.append( "HTTP-INFO\n\n" );
		result.append( "Request: " );
		result.append( httpget.getURI().toString() + "\n" );
		for( Header header: httpget.getAllHeaders() ){
			result.append( "  " + header.getName() + ": " + header.getValue() + "\n" );
		}
		result.append( "Response: " );
		result.append( response.getStatusLine() + "\n" );
		for( Header header: response.getAllHeaders() ){
			result.append( "  " + header.getName() + ": " + header.getValue() + "\n" );
		}
		
		return result.toString();
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
		
		HttpResponse response = client.execute( httpget );
		//E.poster( printHttpInfo( httpget, response ) );
		
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
			
			Header cacheControl = response.getLastHeader( "Cache-Control" );
			if( cacheControl != null ){
				Pattern pattern = Pattern.compile( "max-age=([0-9]+)" );
				Matcher matcher = pattern.matcher( cacheControl.getValue() );
				if( matcher.find() ){
					String maxAgeGroup = matcher.group();
					String maxAgeString = maxAgeGroup.substring( maxAgeGroup.indexOf( '=' )+1 );
					uResponse.maxAge = Long.parseLong( maxAgeString );
				}
			}
			
			Header contentType = response.getLastHeader( "Content-Type" );
			if( contentType != null ){
				
				Pattern pattern = Pattern.compile( ".*; *charset=(.+)" );
				Matcher matcher = pattern.matcher( contentType.getValue() );
				if( matcher.find() ){
					//uResponse.encoding = found.substring( found.indexOf( "=" )+1 );
					uResponse.encoding = matcher.group(1).trim();
				}
			}
			
			HttpEntity entity = response.getEntity();
			
			InputStream in = entity.getContent();
			byte [] content = Slurper.slurp( in );
			uResponse.content = content;
			
		}
		return uResponse;
	}
}
