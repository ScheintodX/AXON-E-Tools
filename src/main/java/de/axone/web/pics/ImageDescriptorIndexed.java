package de.axone.web.pics;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import de.axone.tools.Str;
import de.axone.web.SuperURL;


public class ImageDescriptorIndexed extends ImageDescriptorAbstract {
	
	private final String identifier;
	private final int index;

	ImageDescriptorIndexed( String identifier, int index, int dimension,
			String watermark, String cachedir, Instant mTime, String name ) {
		
		super( cachedir, dimension, watermark, mTime, name );
		
		this.identifier = identifier;
		this.index = index;
	}
	
	public static ImageDescriptorIndexed create( String plainString ) {
		
		String [] parts = Str.splitFast( plainString, FS );
		
		if( parts.length < 5 || parts.length > 6 )
				throw new IllegalArgumentException( "Illeal number of arguments in: " + parts );
		
		return new ImageDescriptorIndexed(
				parts[ 0 ],
				Integer.parseInt( parts[ 1 ] ),
				Integer.parseInt( parts[ 2 ] ),
				parts[ 3 ],
				parts[ 4 ],
				parts.length > 5 ? Instant.ofEpochSecond( Integer.parseInt( parts[5] ) ) : null,
				null
		);
	}
	
	// https://domain.de/pics/hn1/
	public static ImageDescriptorIndexed create( SuperURL url ) {
		
		SuperURL.Path path = url.getPath();
		
		if( path.length() < 5 || path.length() > 7 )
				throw new IllegalArgumentException( "Illeal number of arguments in: " + path );
		
		return new ImageDescriptorIndexed(
				path.get( 1 ),
				Integer.parseInt( path.get( 2 ) ),
				Integer.parseInt( path.get( 3 ) ),
				path.get( 4 ),
				path.get( 0 ),
				path.length() > 5 ? Instant.ofEpochSecond( Integer.parseInt( path.get( 5 ) ) ) : null,
				path.length() > 6 ? path.get( 6 ).replaceAll( ".jpg$", "" ) : null
		);
	}
	
	@Override
	public SuperURL toUrl() {
		
		SuperURL result = new SuperURL();
		
		result.getPath()
				.addAll( cachedir, identifier, ""+index, ""+dimension, watermark )
				;
		if( mTime != null )
				result.getPath().addLast( ""+mTime.toEpochMilli() );
		if( name != null )
				result.getPath().addLast( name + ".jpg" ); // What about svg?
			
		return result;
	}

	@Override
	public String toEncodableString() {
		
		StringBuilder result = new StringBuilder();
		
		result.append( identifier )
				.append( FS ).append( index )
				.append( FS ).append( dimension )
				.append( FS ).append( watermark )
				.append( FS ).append( cachedir )
				;
		
		if( mTime != null )
				result.append( FS ).append( ""+mTime.toEpochMilli() );
		
		return result.toString();
	}
	
	@Override
	public Path toPath( Path basepath ) throws IOException {
		if( index == 0 ){
			return Paths.get( identifier + ".jpg" );
		} else {
			
			try( DirectoryStream<Path> paths = Files.newDirectoryStream( basepath.resolve( identifier ) ) ) {
				
				int i = 0;
				for( Path path : paths ) {
					
					if( i == index ) return path;
				
					i++;
				}
			}
			
			return null;
			
		}
	}
	
	public String identifier(){ return identifier; }
	public int index(){ return index; }
}
