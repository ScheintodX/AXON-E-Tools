package de.axone.gfx;

import de.axone.shell.ShellExec;
import de.axone.tools.Str;

public abstract class ImageScalerGMOptions implements ImageScalerOption, ShellExec.Arg {

	@Override
	public String toString(){
		return Str.join( " ", commandLine() );
	}

	public static final ImageScalerGMOptions CONVERT = Command( "convert" ),
	                                         COMPOSITE = Command( "composite" ),
	                                         COMPOSE_OVER = Compose( "over" ),
                                             RESIZE = O( "-resize" ),
                                             STRIP = O( "-strip" ),
                                             GRAVITY_SOUTHEAST = Gravity( "SouthEast" ),
                                             HIGH_QUALITY = Quality( 90 ),
                                             LOW_QUALITY = Quality( 60 )
                                             ;

    private static class Configured extends ImageScalerGMOptions {

    	private final String [] commandLine;

		public Configured( String [] commandLine ) {
			this.commandLine = commandLine;
		}

		@Override
		public String [] commandLine() {
			return commandLine;
		}
	};

	public static ImageScalerGMOptions Command( String commandLine ) {
		return new Configured( new String [] { commandLine } );
	}
	public static ImageScalerGMOptions O( String commandLine ) {
		return new Configured( new String [] { commandLine } );
	}

	public static ImageScalerGMOptions Option( String commandName, String commandLine ) {
		return new Configured( new String[] { "-" + commandName, commandLine } );
	}

	public static ImageScalerGMOptions Resize( int w, int h ) {
		return Option( "resize", "" + w + "x" + h );
	}
	public static ImageScalerGMOptions Quality( int q ) {
		return Option( "quality", ""+q );
	}
	public static ImageScalerGMOptions Gravity( String where ) {
		return Option( "gravity", where );
	}
	public static ImageScalerGMOptions Compose( String where ) {
		return Option( "compose", where );
	}
	/**
	 * @return option for -100..100
	 * @param value
	 */
	public static ImageScalerGMOptions Brightness( int value ) {
		return Modulate( value, 100, 100 );
	}
	public static ImageScalerGMOptions Saturation( int value ) {
		return Modulate( 100, value, 100 );
	}
	public static ImageScalerGMOptions Hue( int value ) {
		return Modulate( 100, 100, value );
	}
	public static ImageScalerGMOptions Modulate( int brightness, int saturation, int hue ) {
		return Option( "modulate", brightness + "," + saturation + "," + hue );
	}

}
