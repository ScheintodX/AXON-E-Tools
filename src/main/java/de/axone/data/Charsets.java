package de.axone.data;

import java.nio.charset.Charset;

public interface Charsets {
	
	public static final String utf8 = "UTF-8",
	                           latin1 = "ISO-8859-1",
	                           latin15 = "ISO-8859-15",
	                           ascii = "US-ASCII",
	                           win1252 = "Windows-1252"
	                           ;

	public static final Charset UTF8 = Charset.forName( utf8 ),
	                            LATIN1 = Charset.forName( latin1 ),
	                            LATIN15 = Charset.forName( latin15 ),
	                            ASCII = Charset.forName( ascii )
	                            ;
	
}
