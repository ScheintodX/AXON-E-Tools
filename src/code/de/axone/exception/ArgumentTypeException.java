package de.axone.exception;

import de.axone.tools.Str;

public class ArgumentTypeException extends IllegalNamedArgumentException {

	private static final long serialVersionUID = 1327985129832159871L;

	public ArgumentTypeException( String name, String type ){
		super( name, "is not of type '" + type + "'" );
	}
	
	public ArgumentTypeException( String name, String ... types ){
		super( name, "is not of type [" + Str.join( ",", types ) + "]" );
	}
}
