package de.axone.tools;

public interface Speller {

	public String inWords( int number );

	public static class De implements Speller {

		private static String[] einer = { "Null", "Ein", "Zwei", "Drei",
				"Vier", "Fünf", "Sechs", "Sieben", "Acht", "Neun", "Zehn",
				"Elf", "Zwölf" };

		@Override
		public String inWords( int i ) {
			return InWords( i );
		}
		
		public static String InWords( int i ) {

			if( i == 0 ) {
				return einer[ 0 ];
			} else if( i == 1 ) {
				return "Eins";
			} else if( i <= 12 ) {
				return einer[ i ];
			} else if( i < 100 ) {

				int e = i % 10;
				int h = i / 10;

				if( i <= 19 ) {
					if( i == 17 ) {
						return "Siebzehn";
					} else {
						return einer[ e ] + "zehn";
					}
				} else if( i == 20 ) {
					return "Zwanzig";
				} else if( i <= 29 ) {
					return einer[ e ] + "undzwanzig";
				} else {
					String z = einer[ h ];
					if( h == 3 ) {
						z += "sig";
					} else {
						z += "zig";
					}
					if( e == 0 ) {
						return z;
					} else {
						return einer[ e ] + "und" + z.toLowerCase();
					}
				}

			} else if( i == 100 ) {
				return "Hundert";
			} else {
				return Integer.toString( i );
			}
		}
	}

	public static class En implements Speller {

		private static String[] oner = { "Zero", "One", "Two", "Three", "Four",
				"Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven",
				"Twelf", "Thirteen", null, "Fifteen" };
		

		@Override
		public String inWords( int i ) {
			return InWords( i );
		}
		
		public static String InWords( int i ) {
			if( i <= 13 || i == 15 ) {
				return oner[ i ];
			} else {

				int o = i % 10;
				int t = i / 10;

				if( i <= 19 ) {
					return oner[ o ] + "teen";
				} else if( i < 100 ) {
					String r;
					if( t == 2 ) {
						r = "Twenty";
					} else if( t == 3 ) {
						r = "Thirty";
					} else {
						if( oner[ t ].charAt( oner[ t ].length() - 1 ) == 'e' ) {

							r = oner[ t ].substring( 0, oner[ t ].length() - 1 );
						} else {
							r = oner[ t ];
						}
						r += "ty";
					}
					if( o > 0 ) {
						r += oner[ o ].toLowerCase();
					}
					return r;
				} else if( i == 100 ) {
					return "Hundred";
				}
			}

			return "(E:" + Integer.toString( i ) + ")";
		}

	}
}
