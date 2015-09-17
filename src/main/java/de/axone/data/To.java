package de.axone.data;

public abstract class To {
	
	private static final long
			F_MS = 1,
			F_SEC = 1000,
			F_MIN = 60*1000,
			F_HOUR = 60*60*1000,
			F_DAY = 24*60*60*1000
			;
	
	public static final Converter
			Ms = new Converter( F_MS ),
			Second = new Converter( F_SEC ),
			Minute = new Converter( F_MIN ),
			Hour = new Converter( F_HOUR ),
			Day = new Converter( F_DAY )
			;
	
	public static class Converter {
		
		private final long factor;
		
		public Converter( long factor ) {
			this.factor = factor;
		}
		
		public long ms( long value ) {
			return value/factor;
		}
		public double msf( double value ) {
			return value/factor;
		}
		
		public long seconds( long value ) {
			return value*F_MS/factor;
		}
		public double secondsf( double value ) {
			return value*F_MS/factor;
		}
		
		public long minutes( long value ) {
			return value*F_MIN/factor;
		}
		public double minutesf( double value ) {
			return value*F_MIN/factor;
		}
		
		public long hours( long value ) {
			return value*F_HOUR/factor;
		}
		public double hoursf( double value ) {
			return value*F_HOUR/factor;
		}
		
		public long days( long value ) {
			return value*F_DAY/factor;
		}
		public double daysf( double value ) {
			return value*F_DAY/factor;
		}
	}
}
