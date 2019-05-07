package de.axone.web;

import java.nio.file.Path;

public interface PictureBuilderBuilder {

	public static final int DEFAULT_PRESCALE_SIZE = 2000;

	public PictureBuilderNG builder( String identifier, int index );

	public PictureBuilderBuilder watermark( String watermark );

	public PictureBuilderBuilder prescaleSize( int prescaleSize );


	/*
	public static PictureBuilderBuilder instance( Path homedir, Path maindir, Path cachedir, int hashLength, int prescaleSize ) {

		return new PictureBuilderBuilderImpl( homedir, maindir, cachedir, hashLength, prescaleSize );
	}
	*/

	public static PictureBuilderBuilder instance( Path homedir, Path maindir, Path cachedir, int hashLength ) {

		return new PictureBuilderBuilderImpl( homedir, maindir, cachedir, hashLength, DEFAULT_PRESCALE_SIZE );
	}

	public static PictureBuilderBuilder instance( Path homedir ) {

		return new PictureBuilderBuilderImpl( homedir, null, null, 1, DEFAULT_PRESCALE_SIZE );
	}

}
