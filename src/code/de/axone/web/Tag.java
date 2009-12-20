package de.axone.web;

import de.axone.web.encoding.AttributeEncoder;
import de.axone.web.encoding.XmlEncoder;


public abstract class Tag {

	public static StringBuilder simpleBB( StringBuilder builder, String name, String content, String ...args ){

		if( args.length % 2 != 0 )
			throw new IndexOutOfBoundsException( "Wrong argument count. format is: NAME AT1 VAL1 ... CONTENT" );

		builder
			.append( "<" )
			.append( name )
		;

		for( int i=0; i<args.length; i+=2 ){

			builder
				.append( " " )
				.append( args[i] )
				.append( "=\"" )
				.append( AttributeEncoder.ENCODE( args[ i+1 ] ) )
				.append( "\"" )
			;
		}

		if( content == null ){

    		builder.append( "/>" );
		} else {
			builder
				.append( ">" )
    			.append( XmlEncoder.ENCODE( content ) )
    			.append( "</" )
    			.append( name )
    			.append( ">" )
			;
		}

		return builder;
	}

	public static String simple( String name, String content, String ... args ){

		return simpleBB( new StringBuilder(), name, content, args ).toString();
	}
	public static String hiddenInput( String name, String value ){

		String [] parameters = new String[]{ "type", "hidden", "name", name, "value", value };

		return simpleBB( new StringBuilder(), "input", null, parameters ).toString();
	}
}
