package de.axone.gfx;

public abstract class ImageScalerGMOptions implements ImageScalerOption {
	
	public abstract String commandLine();
	
	@Override
	public String toString(){
		return commandLine();
	}
	
	public static final ImageScalerGMOptions COMPOSE_OVER = Compose( "over" ),
                                            RESIZE = O( "-resize" ),
                                            STRIP = O( "-strip" ),
                                            GRAVITY_SOUTHEAST = Gravity( "SouthEast" ),
                                            HIGH_QUALITY = Quality( 90 ),
                                            LOW_QUALITY = Quality( 60 )
                                            ;
                                            
    private static class Configured extends ImageScalerGMOptions {
    	
    	private final String commandLine;
    	
		public Configured( String commandLine ) {
			this.commandLine = commandLine;
		}

		@Override
		public String commandLine() {
			return commandLine;
		}
	};
	
	public static ImageScalerGMOptions O( String commandLine ) {
		return new Configured( commandLine );
	}
	
	public static ImageScalerGMOptions Option( String commandName, String commandLine ) {
		return new Configured( "-" + commandName + " " + commandLine );
	}
	
	public static ImageScalerGMOptions Resize( int w, int h ) {
		return Option( "resize", + w + "x" + h );
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
