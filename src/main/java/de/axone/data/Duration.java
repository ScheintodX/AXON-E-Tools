package de.axone.data;

public abstract class Duration {
	
	public static long milliseconds( int milliseconds ){
		return milliseconds;
	}

	public static long seconds( int seconds ){
		return seconds * 1000;
	}
	
	public static long minutes( int minutes ){
		return minutes * 60 * 1000;
	}
	
	public static long hours( int hours ){
		return hours * 60 * 60 * 1000;
	}
	
	public static long days( int days ){
		return days * 24 * 60 * 60 * 1000;
	}
	
}
