package de.axone.web.pics;

import java.time.Instant;

public abstract class ImageDescriptorAbstract implements ImageDescriptor {
	
	protected static final char FS = ':';
	
	protected final String cachedir;
	protected final int dimension;
	protected final String watermark;
	protected final Instant mTime;
	protected final String name;
	
	protected ImageDescriptorAbstract( String cachedir, int dimension,
			String watermark, Instant mTime, String name ) {
		this.cachedir = cachedir;
		this.dimension = dimension;
		this.watermark = watermark;
		this.mTime = mTime;
		this.name = name;
	}
	protected ImageDescriptorAbstract( int offset, String ... parts ) {
		
		if( parts.length < offset+3 )
				throw new IllegalArgumentException( "Cannot decode: " + parts );
		
		this.cachedir = parts[ offset+0 ];
		this.dimension = Integer.parseInt( parts[ offset+1 ] );
		this.watermark = parts[ offset+2 ];
		if( parts.length > offset+2 );
				this.mTime = Instant.ofEpochSecond( Integer.parseInt( parts[offset+3] ) );
		if( parts.length > offset+2 );
				this.name = parts[ offset+4 ];
	}

	@Override public String name(){ return name; }
	
	public String cachedir(){ return cachedir; }
	public int dimension(){ return dimension; }
	public String watermark(){ return watermark; }
	public Instant mTime(){ return mTime; }

}
