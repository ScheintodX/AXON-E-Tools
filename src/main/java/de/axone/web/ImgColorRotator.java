package de.axone.web;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.IndexColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImgColorRotator {
	
	private final double hueDelta;
	private final double saturationGamma;
	private final double brightnessGamma;
	private final boolean inverse;
		
	/**
	 * Rotate all colors in the csv for some degrees and change it's
	 * brightness and saturation.
	 * 
	 * Convinience method with user-friendly input
	 * 
	 * @see ImgColorRotator#rotate(int)
	 * 
	 * @param hueDelta  0° .. 360°
	 * @param saturationGamma  0 .. oo in 1/1000th
	 * @param brightnessGamma  0 .. oo in 1/1000th
	 * @param inverse the output
	 */
	public ImgColorRotator( int hueDelta, int saturationGamma, int brightnessGamma, boolean inverse ){
		
		this( hueDelta/360.0, saturationGamma/1000.0, brightnessGamma/1000.0, inverse );
	}
	
	/**
	 * Rotate all colors in the csv for some degrees and change it's
	 * brightness and saturation.
	 * 
	 * Colors are found by patterns matching #abc and #abcdef.
	 * 
	 * <tt>degree</tt> is in the range from 0 to 1.0 which corresponds to 0° to 360°
	 * <tt>saturationGamma</tt> and <tt>brightnessGamma</tt> are in the range from
	 * 0 to endless.
	 * 
	 * hue is calculated by adding the value and doing a %1 operation.
	 * saturation and brightness are calculated by setting the original value to the pow of the
	 * given gamma value;
	 * 
	 * @param hueDelta  0 .. 1 =&gt; 0° .. 360°
	 * @param saturationGamma  0 .. oo
	 * @param brightnessGamma  0 .. oo
	 * @param inverse the output
	 */
	public ImgColorRotator( double hueDelta, double saturationGamma, double brightnessGamma, boolean inverse ){
		
		if( hueDelta < 0 ) throw new IllegalArgumentException( "hueDelta < 0" );
		if( saturationGamma <= 0 ) throw new IllegalArgumentException( "saturationGamma <= 0" );
		if( brightnessGamma <= 0 ) throw new IllegalArgumentException( "brightnessGamma <= 0" );
		
		this.hueDelta = hueDelta;
		this.saturationGamma = 1/saturationGamma;
		this.brightnessGamma = 1/brightnessGamma;
		this.inverse = inverse;
	}
	

	/**
	 * Rotate all colors in the csv for some degrees and change it's
	 * brightness and saturation.
	 * 
	 * <tt>degree</tt> is in the range from 0 to 1.0 which corresponds to 0° to 360°
	 * <tt>saturationGamma</tt> and <tt>brightnessGamma</tt> are in the range from
	 * 0 to endless.
	 * 
	 * hue is calculated by adding the value and doing a %1 operation.
	 * saturation and brightness are calculated by setting the original value to the pow of the
	 * given gamma value;
	 * 
	 * @param imageData as raw byte array
	 * @param imageType 
	 * @return the converted image data
	 * @throws IOException 
	 */
	public byte[] rotate( byte [] imageData, String imageType ) throws IOException{
		
		assert imageData != null;
		
		// Skip rendering if nothing to do
		if( CssColorRotator.checkUnchanged( hueDelta, saturationGamma, brightnessGamma, inverse ) )
			return imageData;
		
		ByteArrayInputStream bIn = new ByteArrayInputStream( imageData );
		
		BufferedImage image = ImageIO.read( bIn );
		
		ColorModel cm = image.getColorModel();
		
		// Change gifs color table
		if( cm instanceof IndexColorModel ){
			
			IndexColorModel indexCM = (IndexColorModel) image.getColorModel();
			int [] cMap = new int[ indexCM.getMapSize() ];
			indexCM.getRGBs( cMap );
			
			for( int i=0; i<cMap.length; i++ ){
				
				cMap[i] = rotate( cMap[i] );
			}
			
			IndexColorModel newCm = new IndexColorModel( 
					8, cMap.length, cMap, 
					0, indexCM.getTransparency() != Transparency.OPAQUE,
					indexCM.getTransparentPixel(), DataBuffer.TYPE_BYTE );
			
			image = new BufferedImage( newCm, image.getRaster(), false, null );
			
		// Change every single pixel
		} else {
			int w = image.getWidth();
			int h = image.getHeight();
			
			//long start = System.currentTimeMillis();
			int [] imgRGB = image.getRGB( 0, 0, w, h, null, 0, w );
			
			for( int i=0; i<imgRGB.length; i++ ){
				
				imgRGB[ i ] = rotate( imgRGB[ i ] );
			}
			
			image.setRGB( 0, 0, w, h, imgRGB, 0, w );
			//E.rr( "Duration: " + (System.currentTimeMillis() - start) );
		}
		
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		ImageIO.write( image, imageType, bOut );
		
		return bOut.toByteArray();
	}
	
	public int rotate( int color ){
		
		return CssColorRotator.rotate( color, hueDelta, saturationGamma, brightnessGamma, inverse );
	}
}
