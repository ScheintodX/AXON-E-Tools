package de.axone.data;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;


public enum Encoding {
	
	// NOTE: Must be written in upper case

	utf8( Charsets.utf8, "UTF8" ),
	latin1( Charsets.latin1, "LATIN1", "LATIN-1", "ISO8859-1" ),
	latin15( Charsets.latin15, "LATIN15", "LATIN-15", "ISO8859-15" ),
	ascii( Charsets.ascii, "ASCII" )
	;

	private String isoName;
	private Charset charset;
	private HashSet<String> names = new HashSet<String>();

	Encoding( String isoName, String ... aliases ){

		this.isoName = isoName;
		this.charset = Charset.forName( isoName );
		this.names.add( name().toUpperCase() );
		this.names.add( isoName );
		this.names.addAll( Arrays.asList( aliases ) );
	}

	public String isoName(){
		return isoName;
	}
	
	@Override
	public String toString(){
		return isoName;
	}
	
	public Charset charset(){
		return charset;
	}

	public static Encoding forAnyName( String name ){
		
		name = name.toUpperCase();

		for( Encoding encoding : Encoding.values() ){

			if( encoding.names.contains( name ) )
				return encoding;
		}

		return null;
	}
}
