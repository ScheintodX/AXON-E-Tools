package de.axone.gfx;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Optional;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ImageScalerAWT implements ImageScaler {

	private static final String imageioLock = "ImageIO read lock";
	
	@Override
	public void scale( Path outPath, Path imagePath, Optional<Path> watermarkPath, int size, boolean hq ) throws IOException {
				// Read in
		BufferedImage inImage;
		try{
			synchronized( imageioLock ){ // Prevent problems with liblcms2-2
				inImage = ImageIO.read( imagePath.toFile() );
			}
		} catch( Throwable e ){
			throw new IOException( "Cannot read: " + imagePath, e );
		}

		// Make watermark
		if( watermarkPath.isPresent() ) {

			BufferedImage watermarkImage;
			try {
				synchronized( imageioLock ){ // Prevent problems with liblcms2-2
					watermarkImage = ImageIO.read( watermarkPath.get().toFile() );
				}
			} catch( IOException e ){
				throw new IOException( "Cannot read watermark: " + watermarkPath.get(), e );
			}


			int ww = watermarkImage.getWidth();
			int wh = watermarkImage.getHeight();

			int iw = inImage.getWidth();
			int ih = inImage.getHeight();

			Graphics2D drawGraphics = inImage.createGraphics();
			drawGraphics.drawImage( watermarkImage, iw - ww, ih - wh,
					ww, wh, null );
		}

		// Scale
		Dim d = mkOuterbox( size, inImage.getWidth(), inImage
				.getHeight() );

		BufferedImage resultImage = new BufferedImage( d.w, d.h,
				BufferedImage.TYPE_INT_RGB );

		Image scaled = inImage.getScaledInstance( d.w, d.h,
				Image.SCALE_SMOOTH );

		Graphics2D g2 = resultImage.createGraphics();

		g2.drawImage( scaled, 0, 0, d.w, d.h, null );

		// Quality can be higher for smaller images
		float quality;
		if( hq ) {
			quality = 1f;
		} else {
			if( size <= 200 ) {
				quality = .9f;
			} else if( size <= 400 ) {
				quality = .7f;
			} else if( size <= 700 ) {
				quality = .5f;
			} else {
				quality = .4f;
			}
		}

		// ImageIO.write( resultImage, "jpeg", cache );
		// Code from:
		// http://www.universalwebservices.net/web-programming-resources/java/adjust-jpeg-image-compression-quality-when-saving-images-in-java
		// Let you choose compression level
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName( "jpeg" );
		ImageWriter writer = iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
		iwp.setProgressiveMode( ImageWriteParam.MODE_DEFAULT );
		iwp.setCompressionQuality( quality );
		IIOImage image = new IIOImage( resultImage, null, null );
		try( FileImageOutputStream output = new FileImageOutputStream( outPath.toFile() ) ){
			writer.setOutput( output );
			synchronized( imageioLock ){ // Just in case writing is as buggy as reading
				writer.write( null, image, iwp );
			}
		}

	}
	
	private static class Dim {
		int w, h;
	}
	
	private static Dim mkOuterbox( int size, int originalWidth, int originalHeight ) {

		Dim res = new Dim();

		double max = originalWidth > originalHeight ? originalWidth
				: originalHeight;

		res.w = (int) ( ( originalWidth / max ) * size );
		res.h = (int) ( ( originalHeight / max ) * size );

		return res;
	}

	private static ImageScaler instance;
	public synchronized static ImageScaler instance() {
		if( instance == null ) {
			instance = new ImageScalerAWT();
		}
		return instance;
	}

}
