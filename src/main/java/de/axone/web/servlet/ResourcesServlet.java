package de.axone.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;

import de.axone.cache.Cache;
import de.axone.cache.CacheLRUMap;
import de.axone.tools.EasyParser;
import de.axone.tools.FileWatcher;
import de.axone.tools.UrlParser;
import de.axone.web.CssColorRotator;
import de.axone.web.Header;
import de.axone.web.ImgColorRotator;

public abstract class ResourcesServlet extends HttpServlet {
	
	private static final Logger log = LoggerFactory.getLogger( ResourcesServlet.class );
	
	private static final long serialVersionUID = 1L;

	private static final String FS = ";";
	private static final String PREFIX = "/static";
	private static final String P_DO_YUI = "yui";
	private static final float MIN_COMPRESSION = 0.90f;
	
	private static final Pattern COLORIZE = Pattern.compile( "(/([cpgji]{1,5})\\.([0-9]+)\\.([0-9]+)\\.([0-9]+)).*" );
	private static final Pattern DEPLENK = Pattern.compile( "\\s*:\\s*" );

	protected abstract File basedir();
	protected abstract long cachetime();
	
	protected Logger log(){ return log; }
	
	protected String uriPrefix(){
		return "";
	}
	
	protected String filter( String uri ){
		
		if( uri.endsWith( "favicon.ico" ) ){
			return favicon( uri );
		}
		
		String prefix = uriPrefix();
		if( uri.startsWith( prefix ) ){
			uri = uri.substring( prefix.length() );
		}
		
		uri = DEPLENK.matcher( uri ).replaceAll( ":" );
			
		return uri; // Do nothing per default
	}
	
	protected String favicon( String uri ){
		return uri; // Do nothing per default
	}
	
	private Cache<String,Object> cache = new CacheLRUMap<>( "resource cache", 1000 );
	protected Cache<String,Object> buffer(){
		return cache;
	}
	
	protected void protect( HttpServletRequest request, String uri ) throws Exception {};

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) {
		
		try {
			// Get settings
    		//File basedir = EMogul.rt().settingBackend().getFile( "static.basedir" );
			File basedir = basedir();
    		//long cachetime = EMogul.rt().settingBackend().getAsLong( "static.cachetime", 86400 );
			long cachetime = cachetime();

    		// Get requested resources
			String uri = filter( request.getRequestURI() );
			
			protect( request, uri );

			//Cache<String,Object> buffer = EMogul.rt().getResourceBuffer();
			Cache<String,Object> buffer = buffer();
			
			String pYui = request.getParameter( P_DO_YUI );
			boolean doYui = !EasyParser.isNo( pYui );

			HttpDataHolder httpData = null;
			
			synchronized( buffer ) {
				
				String urlKey = uri + (doYui?"!":"");
				
				// Is there sth. in the buffer?
				if( buffer.containsKey( urlKey ) ) {

					httpData = (HttpDataHolder) buffer.get( urlKey );
				}
				
				if( httpData == null || httpData.hasChanged() ){
					
					String [] uriSplitted = uri.split( FS );
					
					LinkedList<FileWatcher> watcherList = new LinkedList<FileWatcher>();
					LinkedList<byte[]> datas = new LinkedList<byte[]>();
					
					String basePath=null;
					CssColorRotator rotCss = null;
					ImgColorRotator rotImg = null;

					for( String uriPart : uriSplitted ){
						
						// Remove /static
						if( uriPart.startsWith( PREFIX ) ){
			    			uriPart = uriPart.substring( PREFIX.length() );
						}
						
						@SuppressWarnings( "unused" )
						boolean isHtml=false, isCss=false, isJs=false, isPng=false, isJpg=false, isGif=false, isIcon=false;
						if( uriPart.endsWith( ".html" ) || uriPart.endsWith( ".xhtml" ) ){
							isHtml = true;
						} else if( uriPart.endsWith( ".css" ) ){
							isCss = true;
						} else if( uriPart.endsWith( ".js" ) ){
							isJs = true;
						} else if( uriPart.endsWith( ".png" ) ){
							isPng = true;
						} else if( uriPart.endsWith( ".gif" ) ){
							isGif = true;
						} else if( uriPart.endsWith( ".jpg" )||uriPart.endsWith( ".jepg" ) ){
							isJpg = true;
						} else if( uriPart.endsWith( ".ico" ) ){
							isIcon = true;
						}
						
						// Make colorizer
						Matcher cm = COLORIZE.matcher( uriPart );
						if( cm.matches() ){
							
							String found = cm.group( 1 );
							String mode = cm.group( 2 );
							
							boolean doCss = isCss && mode.contains( "c" );
							boolean doPng = isPng && mode.contains( "p" );
							boolean doJpg = isJpg && mode.contains( "j" );
							boolean doGif = isGif && mode.contains( "g" );
							boolean inverse = mode.contains( "i" );
							
							if( doCss || doPng || doJpg || doGif ){
								Integer h = Integer.parseInt( cm.group( 3 ) );
								Integer s = Integer.parseInt( cm.group( 4 ) );
								Integer b = Integer.parseInt( cm.group( 5 ) );
								
								if( doCss ){
									rotCss = new CssColorRotator( h, s, b, inverse );
								} else if( doPng || doJpg || doGif ){
									rotImg = new ImgColorRotator( h, s, b, inverse );
								}
							}
							uriPart = uriPart.substring( found.length() );
						}
						
						// For first save Path. All others will be relative
						File file;
						if( basePath == null ){
							
							File uriFile = new File( uriPart );
							basePath = uriFile.getParent();
						
							file = new File( basedir, uriPart );
							basedir = new File( basedir, basePath );
							
						} else {
							file = new File( basedir, uriPart );
						}
						
						FileWatcher watcher = new FileWatcher( file );
						watcher.hasChanged(); //reset watcher
	
						if( !file.isFile() ) {
	
							log().error( "Not found: " + uriPart );
	
							response.sendError( HttpServletResponse.SC_NOT_FOUND, uriPart );
	
							return;
						}
	
						// File size
						long filesizeL = file.length();
						if( filesizeL > Integer.MAX_VALUE ) {
	
							log().error( "Too large: " + uriPart );
	
							response.sendError(
									HttpServletResponse.SC_NOT_IMPLEMENTED,
									"File is too large: " + filesizeL + "B" );
							
							return;
						}
						int filesize = (int) filesizeL;
	
						// Read via nio
						FileInputStream fin = null;
						FileChannel in = null;
						try {
	    					fin = new FileInputStream( file );
	    					in = fin.getChannel();
	
	    					response.setContentLength( filesize );
	
	    					ByteBuffer bb = in.map( FileChannel.MapMode.READ_ONLY, 0,
	    							filesize );
	
	    					byte[] plainData = new byte[ filesize ];
	
	    					bb.get( plainData );
	    					
	    					if( ( isCss || isJs ) && doYui ){
	    						
	    						String dataAsString = new String( plainData, "utf-8" );
	    						
	    						if( isCss && rotCss != null ){
	    							
    								dataAsString = rotCss.rotate( dataAsString );
	    						}
	    						
	    						StringReader sIn = new StringReader( dataAsString );
	    						StringWriter sOut = new StringWriter();
	    						
	    						if( isCss ){
		    						CssCompressor compressor = new CssCompressor( sIn );
		    						compressor.compress( sOut, 0 );
	    						} else {
	    							JavaScriptCompressor compressor = new JavaScriptCompressor( sIn, null );
	    							compressor.compress( sOut, 0, true, false, false, false );
	    						}
	    						
	    						String compressedString = sOut.toString();
	    						plainData = compressedString.getBytes();
	    					}
	    					
	    					if( (isPng|isJpg|isGif) && rotImg != null ){
	    						
	    						if( isPng ){
		    						plainData = rotImg.rotate( plainData, "png" );
		    						
	    						} else if( isJpg ){
		    						plainData = rotImg.rotate( plainData, "jpeg" );
		    						
	    						} else if( isGif ){
		    						plainData = rotImg.rotate( plainData, "gif" );
	    						}
	    					}
	
	    					datas.add( plainData );
	    					watcherList.add( watcher );
	
						} finally {
	    					if( in != null ) in.close();
	    					if( fin != null ) fin.close();
						}
						
					}
					
					int datasize=0;
					for( byte[] d : datas ){
						datasize += d.length;
					}
					ByteBuffer allData = ByteBuffer.allocate( datasize );
					for( byte[] d : datas ){
						allData.put( d );
					}
					allData.flip();
					
					byte [] dataAsArray = allData.array();
					
					httpData = new HttpDataHolder( dataAsArray, watcherList );
					buffer.put( urlKey, httpData );
				}
			}

			String requestETag = request.getHeader( "If-None-Match" );
			
			String myETag = httpData.getETag();

			if( requestETag != null && requestETag.equals( myETag ) ){

				// Don't send anything if request matches response
				response.sendError( 304 );
				return;
			}

			// Content-type vie uri / extension
			String contentType = UrlParser.contentTypeFor( uri, true );
			if( contentType == null ) {

				log().error( "Unknown filetype: " + uri );

				response.sendError( HttpServletResponse.SC_NOT_IMPLEMENTED,
						"Unknown filetype" );
				return;
			}
			response.setContentType( contentType );

			// Encoding
			String encoding = UrlParser.encodingFor( uri );
			if( encoding != null ) {
				response.setCharacterEncoding( encoding );
			}

			// Cache settings
			if( cachetime >= 0 ){
    			response.setHeader( "Cache-Control", "max-age="+cachetime );
			}

			// Proxies: Hold de-/compressed versions
			response.setHeader( "Vary", "Accept-Encoding" );

			// ETag
			response.setHeader( "ETag", myETag );

			String supportedCompression = request.getHeader( "Accept-Encoding" );

			if(
    			( contentType.startsWith( "text/" ) || contentType.equals( "image/svg+xml" ) )
    			&& supportedCompression != null && supportedCompression.matches( ".*gzip.*" )
    			&& httpData.isGzipAvailable()
			){

				response.setHeader( "Content-Encoding", "gzip" );
				byte [] data = httpData.getGziped();
    			response.setContentLength( data.length );

    			ServletOutputStream outs = response.getOutputStream();
    			outs.write( data );
    			outs.flush();

			} else {

    			response.setContentLength( httpData.getData().length );

    			ServletOutputStream outs = response.getOutputStream();
    			outs.write( httpData.getData() );
    			outs.flush();

			}
			
		} catch( Throwable e ) {
			
			log().error( "Error processing {}", request.getRequestURI(), e );
			
			try {
				onError( request, response, e );
			} catch( Throwable t ){
				log().error( "Error handling error", t );
				t.printStackTrace();
			}
		}
	}
	
	protected void onError( HttpServletRequest req, HttpServletResponse resp, Throwable e ) 
	throws Exception {
		
		try {
			try {
				PrintWriter o2 = resp.getWriter();
				e.printStackTrace( o2 );
			} catch( IllegalStateException ise ){
				ServletOutputStream sout = resp.getOutputStream();
				e.printStackTrace( new PrintStream( sout ) );
			}
			
			resp.sendError( HttpServletResponse.SC_NOT_FOUND,
					e.getMessage() );
			
		} catch( IOException e1 ) {
			e1.printStackTrace();
		}
	}
	

	class HttpDataHolder {

		private byte[] data;
		private byte[] gziped;
		private Boolean isGzipAvailable;
		private String eTag;
		private List<FileWatcher> watcher;

		HttpDataHolder( byte[] data, List<FileWatcher> watcher ){

			this.data = data;
			this.watcher = watcher;
		}

		public byte[] getData() {
			return data;
		}

		public byte[] getGziped(){

			if( gziped == null && isGzipAvailable == null ){

				try {
    				ByteArrayOutputStream bOut = new ByteArrayOutputStream( data.length );
    				GZIPOutputStream gOut = new GZIPOutputStream( bOut );
    				gOut.write( data );
    				gOut.finish();
    				gziped = bOut.toByteArray();
				} catch( IOException e ) {
					log().error( "Cannot write gziped content", e );
				}
			}

			if( gziped != null && gziped.length > data.length * MIN_COMPRESSION ){
				gziped = null;
				isGzipAvailable = false;
			} else {
				isGzipAvailable = true;
			}

			return gziped;
		}

		public boolean isGzipAvailable(){

			if( isGzipAvailable == null ){
				getGziped();
			}
			return isGzipAvailable;
		}

		public String getETag() {

			if( eTag == null ){

				eTag = Header.makeETag( data );
			}
			return eTag;
		}

		public List<FileWatcher> getWatchers() {
			return watcher;
		}
		
		public boolean hasChanged() {
			boolean hasChanged = false;
			for( FileWatcher watcher : getWatchers() ){
				if( watcher.hasChanged() ){
					hasChanged = true;
					break;
				}
			}
			return hasChanged;
		}
		
	}

}