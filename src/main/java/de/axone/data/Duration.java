package de.axone.data;

public abstract class Duration {
	
	public static long milliseconds( long milliseconds ){
		return milliseconds;
	}

	public static long seconds( long seconds ){
		return seconds * 1000;
	}
	
	public static long minutes( long minutes ){
		return minutes * 60 * 1000;
	}
	
	public static long hours( long hours ){
		return hours * 60 * 60 * 1000;
	}
	
	public static long days( long days ){
		return days * 24 * 60 * 60 * 1000;
	}
	
	public static double inSeconds( long milliseconds ){
		return milliseconds / 1000;
	}
	
	public static double inMinutes( long milliseconds ){
		return milliseconds / 1000 / 60;
	}
	
	public static double inHours( long milliseconds ){
		return milliseconds / 1000 / 60 / 60;
	}
	
	public static double inDays( long milliseconds ){
		return milliseconds / 1000 / 60 / 60 / 24;
	}
	
}
