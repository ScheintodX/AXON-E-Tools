package de.axone.data;

import java.util.Arrays;
import java.util.HashSet;


public enum Encoding {

	utf8( "utf-8" ), latin1( "iso-8859-1", "latin-1" ), latin15( "iso-8859-15", "latin-15" );

	private String isoName;
	private HashSet<String> names = new HashSet<String>();

	Encoding( String isoName, String ... aliases ){

		this.isoName = isoName;
		this.names.add( name() );
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

	public static Encoding forAnyName( String name ){

		name = name.toLowerCase();

		for( Encoding encoding : Encoding.values() ){

			if( encoding.names.contains( name ) )
				return encoding;
		}

		return null;
	}
}
