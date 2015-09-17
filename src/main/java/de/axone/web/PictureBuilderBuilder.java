package de.axone.web;

import java.nio.file.Path;

public interface PictureBuilderBuilder {

	public PictureBuilderNG builder( String identifier, int index );
	
	public PictureBuilderBuilder watermark( String watermark );
	
	
	public static PictureBuilderBuilder instance( Path homedir, Path maindir, Path cachedir, int hashLength ) {
		
		return new PictureBuilderBuilderImpl( homedir, maindir, cachedir, hashLength );
	}
	public static PictureBuilderBuilder instance( Path homedir ) {
		
		return new PictureBuilderBuilderImpl( homedir, null, null, 1 );
	}

}
