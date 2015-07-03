package de.axone.gfx;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class ImageScalerGM implements ImageScaler {
	
	/*
	private static final Path GM = Paths.get( "/usr/bin/gm" );
	private static final String RESIZE = "-resize";
	*/

	@Override
	public void scale( Path outPath, Path imagePath, Optional<Path> watermarkPath,
			int size, boolean hq ) throws IOException {
	}

}
