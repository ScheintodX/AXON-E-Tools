package de.axone.gfx;

import static de.axone.gfx.ImageScalerGMOptions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.shell.ShellExec;
import de.axone.shell.ShellExec.QuickResult;
import de.axone.shell.ShellExec.ShellException;

public class ImageScalerGM implements ImageScaler {

	private static final Logger log = LoggerFactory.getLogger( ImageScaler.class );

	private static final String CONVERT = "convert",
	                            COMPOSITE = "composite"
	                            ;

	private final Path gmCommand;

	// private ImageScalerOption [] options;

	public ImageScalerGM( Path gmCommand ) {

		this.gmCommand = gmCommand;
	}
	public ImageScalerGM( Path gmCommand, ImageScalerOption ... options ) {

		this.gmCommand = gmCommand;
		//this.options = options;
	}

	@Override
	public synchronized void scale( Path outPath, Path imagePath, Optional<Path> watermarkPath,
			int size, boolean hq ) throws IOException {

		ImageScalerGMOptions quality = hq ? HIGH_QUALITY : LOW_QUALITY;

		if( watermarkPath.isPresent() ) {

			if( ! Files.exists( watermarkPath.get() ) )
					throw new IllegalArgumentException( "404 Cannot find watermark" );

			Path tmp = null;
			try {

				tmp = Files.createTempFile( "em-comp-", ".jpg" );

				String watermarkPathS = watermarkPath.get().toFile().getAbsolutePath(),
				       imagePathS = imagePath.toFile().getAbsolutePath(),
				       tmpS = tmp.toFile().getAbsolutePath(),
				       outPathS = outPath.toFile().getAbsolutePath()
				       ;

				log.trace( "Composite/Convert '{}' + '{}' -> '{}' -> '{}'", imagePathS, watermarkPathS, tmpS, outPathS );

				QuickResult res;

				res = ShellExec.quickexec( gmCommand,
						COMPOSITE,
						HIGH_QUALITY.commandLine(),
						COMPOSE_OVER.commandLine(),
						GRAVITY_SOUTHEAST.commandLine(),
						watermarkPathS,
						imagePathS,
						tmpS
				);
				if( res.getExitValue() != 0 )
						throw new ShellException( res );

				res = ShellExec.quickexec( gmCommand,
						CONVERT,
						quality.commandLine(),
						Resize( size, size ).commandLine(),
						//Brightness( -95 ).commandLine(),
						STRIP.commandLine(),
						tmpS,
						outPathS
				);
				if( res.getExitValue() != 0 )
						throw new ShellException( res );

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

				QuickResult res;

				res = ShellExec.quickexec( gmCommand,
						CONVERT,
						quality.commandLine(),
						Resize( size, size ).commandLine(),
						STRIP.commandLine(),
						imagePath.toFile().getAbsolutePath(),
						outPath.toFile().getAbsolutePath()
				);
				if( res.getExitValue() != 0 )
						throw new ShellException( res );

			} catch( InterruptedException e ) {

				throw new Error( "Error scaling " + imagePath );
			}
		}

	}

}
