package de.axone.web;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CssColorRotator {
	
	private static final String C3 = "#[0-9a-fA-F]{3}";
	private static final String C6 = "#[0-9a-fA-F]{6}";
	//private static final String RGB = "rgb\\(\\s*[0-9]{1,3}\\s*,\\s*[0-9]{1,3}\\s*,\\s*[0-9]{1,3}\\s*\\)";
	
	static final Pattern COLOR = Pattern.compile( "(" + C6 + "\\b)|(" + C3 + "\\b)" );
	
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
	 * @see #rotate( String, double, double, double )
	 * 
	 * @param hueDelta  0° .. 360°
	 * @param saturationGamma  0 .. oo in 1/1000th
	 * @param brightnessGamma  0 .. oo in 1/1000th
	 */
	public CssColorRotator( int hueDelta, int saturationGamma, int brightnessGamma, boolean inverse ){
		
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
	 * @param hueDelta  0 .. 1 => 0° .. 360°
	 * @param saturationGamma  0 .. oo
	 * @param brightnessGamma  0 .. oo
	 */
	public CssColorRotator( double hueDelta, double saturationGamma, double brightnessGamma, boolean inverse ){
		
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
	 * @param css
	 */
	public String rotate( String css ){
		
		if( css == null ) throw new IllegalArgumentException( "css is null" );
		
		// Skip rendering if nothing to do
		if( checkUnchanged( hueDelta, saturationGamma, brightnessGamma, inverse ) )
			return css;
		
		Matcher matcher = COLOR.matcher( css );
		
		StringBuffer result = new StringBuffer();
		
		while( matcher.find() ){
			
			String colorCode = matcher.group( 0 ).substring( 1 );
			
			Color color;
			if( colorCode.length() == 3 ){
				color = c3( colorCode );
			} else {
				color = c6( colorCode );
			}
			
			Color newColor = rotate( color );
			
			matcher.appendReplacement( result, c2s( newColor ) );
		}
		matcher.appendTail( result );
		
		return result.toString();
	}
	
	public static boolean checkUnchanged( double hueDelta, double saturationGamma, double brightnessGamma, boolean inverse ){
		return hueDelta %1 == 0 && saturationGamma == 1 && brightnessGamma == 1 && inverse == false;
	}
	
	static Color c3( String code ){
		
		assert code != null;
		assert code.length() == 3;
		
		int r = Integer.parseInt( code.substring( 0,1 ), 16 );
		int g = Integer.parseInt( code.substring( 1,2 ), 16 );
		int b = Integer.parseInt( code.substring( 2,3 ), 16 );
		return new Color( r|r<<4, g|g<<4, b|b<<4 );
	}
	
	static Color c6( String code ){
		
		assert code != null;
		assert code.length() == 6;
		
		int r = Integer.parseInt( code.substring( 0,2 ), 16 );
		int g = Integer.parseInt( code.substring( 2,4 ), 16 );
		int b = Integer.parseInt( code.substring( 4,6 ), 16 );
		return new Color( r, g, b );
	}
	static String c2s( Color color ){
		
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		String result;
		
		if( (r&0xf) == r>>4 && (g&0xf) == g>>4 && (b&0xf) == b>>4 ){
			result = String.format( "#%1x%1x%1x", r&0xf, g&0xf, b&0xf );
		} else {
			result = String.format( "#%02x%02x%02x", r, g, b );
		}
			
		return result;
	}
	
	public Color rotate( Color color ){
		
		return rotate( color, hueDelta, saturationGamma, brightnessGamma, inverse );
	}
	
	public static Color rotate( Color color, double hueDelta, double saturationGamma, double brightnessGamma, boolean inverse ){
		
		float [] hsb = Color.RGBtoHSB( color.getRed(), color.getGreen(), color.getBlue(), null );
		
		hsb = rotateHSB( hsb, hueDelta, saturationGamma, brightnessGamma, inverse );
		
		return Color.getHSBColor( hsb[0], hsb[1], hsb[2] );
	}

	public static int rotate( int color, double hueDelta, double saturationGamma, double brightnessGamma, boolean inverse ){
		
		float [] hsb = Color.RGBtoHSB( (color>>16)&0xff, (color>>8)&0xff, color&0xff, null );
		
		hsb = rotateHSB( hsb, hueDelta, saturationGamma, brightnessGamma, inverse );
		
		int rgb = Color.HSBtoRGB( hsb[0], hsb[1], hsb[2] );
		
		return (color & 0xff000000) | (rgb & 0xffffff);
	}
	
	public static float [] rotateHSB( float [] hsb, double hueDelta, double saturationGamma, double brightnessGamma, boolean inverse ){
		
		float h = (float)((hsb[0]+hueDelta)%1);
		float s = (float)Math.pow( hsb[1], saturationGamma );
		float b = (float)Math.pow( hsb[2], brightnessGamma );
		
		if( inverse ){ b = 1-b; }
		
		return new float[]{ h, s, b };
		
	}
}
