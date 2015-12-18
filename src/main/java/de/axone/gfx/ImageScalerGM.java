package de.axone.gfx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.shell.ShellExec;

public class ImageScalerGM implements ImageScaler {
	
	private static final Logger log = LoggerFactory.getLogger( ImageScaler.class );
	
	private static final String CONVERT = "convert",
	                            COMPOSITE = "composite",
	                            _COMPOSE_OVER = "-compose Over",
	                            _RESIZE = "-resize",
	                            _STRIP = "-strip",
	                            _GRAVITY_SOUTHEAST = "-gravity SouthEast",
	                            _HIGH_QUALITY = "-quality 90",
	                            _LOW_QUALITY = "-quality 60"
	                            ;
	
	private final Path gmCommand;
	
	public ImageScalerGM( Path gmCommand ) {
		
		this.gmCommand = gmCommand;
	}

	@Override
	public synchronized void scale( Path outPath, Path imagePath, Optional<Path> watermarkPath,
			int size, boolean hq ) throws IOException {
		
		String quality = hq ? _HIGH_QUALITY : _LOW_QUALITY;
				
		if( watermarkPath.isPresent() ) {
			
			Path tmp = null;
			try {
				
				tmp = Files.createTempFile( "em-comp-", ".jpg" );
				
				String watermarkPathS = watermarkPath.get().toFile().getAbsolutePath(),
				       imagePathS = imagePath.toFile().getAbsolutePath(),
				       tmpS = tmp.toFile().getAbsolutePath(),
				       outPathS = outPath.toFile().getAbsolutePath()
				       ;
			
				log.trace( "Composite/Convert '{}' + '{}' -> '{}' -> '{}'", imagePathS, watermarkPathS, tmpS, outPathS );
				
				ShellExec.quickexec( gmCommand,
						COMPOSITE,
						_HIGH_QUALITY,
						_COMPOSE_OVER,
						_GRAVITY_SOUTHEAST,
						watermarkPathS,
						imagePathS,
						tmpS
				);
				
				ShellExec.quickexec( gmCommand,
						CONVERT,
						quality,
						_RESIZE, size + "x" + size,
						_STRIP,
						tmpS,
						outPathS
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
				String imagePathS = imagePath.toFile().getAbsolutePath(),
				       outPathS = outPath.toFile().getAbsolutePath()
				       ;
				
				log.trace( "Convert '{}' -> '{}'", imagePathS, outPathS );
				
				ShellExec.quickexec( gmCommand,
						CONVERT,
						quality,
						_RESIZE, size + "x" + size,
						_STRIP,
						imagePath.toFile().getAbsolutePath(),
						outPath.toFile().getAbsolutePath()
				);
			} catch( InterruptedException e ) {
				
				throw new Error( "Error scaling " + imagePath );
			}
		}
		
	}

}
