package de.axone.gfx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import de.axone.shell.ShellExec;

public class ImageScalerGM implements ImageScaler {
	
	private static final String CONVERT = "convert",
	                            COMPOSITE = "composite",
	                            _COMPOSE_OVER = "-compose Over",
	                            _RESIZE = "-resize",
	                            _STRIP = "-strip",
	                            _GRAVITY_SOUTHEAST = "-gravity SouthEast"
	                            ;
	
	private final Path gmCommand;
	
	public ImageScalerGM( Path gmCommand ) {
		
		this.gmCommand = gmCommand;
	}

	@Override
	public synchronized void scale( Path outPath, Path imagePath, Optional<Path> watermarkPath,
			int size, boolean hq ) throws IOException {
		
		if( watermarkPath.isPresent() ) {
			
			Path tmp = null;
			try {
				
				tmp = Files.createTempFile( "em-comp-", ".jpg" );
			
				ShellExec.quickexec( gmCommand,
						COMPOSITE,
						_COMPOSE_OVER,
						_GRAVITY_SOUTHEAST,
						watermarkPath.get().toFile().getAbsolutePath(),
						imagePath.toFile().getAbsolutePath(),
						tmp.toFile().getAbsolutePath()
				);
				
				ShellExec.quickexec( gmCommand,
						CONVERT,
						_RESIZE, size + "x" + size,
						_STRIP,
						tmp.toFile().getAbsolutePath(),
						outPath.toFile().getAbsolutePath()
				);
				
			} catch( InterruptedException e ) {
				
				throw new Error( "Error scaling " + imagePath );
				
			} finally {
				
				if( tmp != null && Files.isRegularFile( tmp ) ){
					
					Files.delete( tmp );
				}
				
			}
			
		} else {
			try {
				ShellExec.quickexec( gmCommand,
						CONVERT,
						_RESIZE, size + "x" + size,
						imagePath.toFile().getAbsolutePath(),
						outPath.toFile().getAbsolutePath()
				);
			} catch( InterruptedException e ) {
				
				throw new Error( "Error scaling " + imagePath );
			}
		}
		
	}

}
