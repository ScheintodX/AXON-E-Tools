package de.axone.gfx;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public interface ImageScaler {

	public void scale( Path outPath, Path imagePath, Optional<Path> watermarkPath, int size, boolean hq ) throws IOException;
		
	default public void scale( File outPath, File imagePath, File watermarkPath, int size, boolean hq ) throws IOException {
		scale( outPath.toPath(), imagePath.toPath(), watermarkPath != null ? Optional.of( watermarkPath.toPath() ) : Optional.empty(), size, hq );
	}
	
	public static ImageScaler instance() {
		//return ImageScalerAWT.instance();
		return new ImageScalerGM( Paths.get( "/usr/bin/gm" ) );
	}
	public static ImageScaler instance( ImageScalerOption ... options ) {
		//return ImageScalerAWT.instance();
		return new ImageScalerGM( Paths.get( "/usr/bin/gm" ), options );
	}
}
