package de.axone.web;

import java.util.LinkedList;
import java.util.Map;

import de.axone.exception.Assert;
import de.axone.web.encoding.AttributeEncoder;
import de.axone.web.encoding.XmlEncoder;


public abstract class Tag {

	public static StringBuilder simpleBB( StringBuilder builder,
			String name, String content, String ...args){
		return simpleBB( builder, name, content, true, args );
	}
	public static StringBuilder simpleBB( StringBuilder builder,
			String name, String content, boolean encodeContent , String ...args){
		
		Assert.notNull( builder, "builder" );
		Assert.notNull( name, "name" );
		// Null allowed: Content, args

		if( args != null && args.length % 2 != 0 )
			throw new IndexOutOfBoundsException( "Wrong argument count. format is: NAME AT1 VAL1 ... CONTENT" );

		builder
			.append( "<" )
			.append( name )
		;

		if( args != null ) for( int i=0; i<args.length; i+=2 ){

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
    			.append( encodeContent ? XmlEncoder.ENCODE( content ) : content )
    			.append( "</" )
    			.append( name )
    			.append( ">" )
			;
		}

		return builder;
	}
	
	public static StringBuilder simpleBB( StringBuilder builder,
			String name, String content, Map<String,String> args){
		return simpleBB( builder, name, content, true, args );
	}
	public static StringBuilder simpleBB( StringBuilder builder,
			String name, String content, boolean encodeContent , Map<String,String> args ){

		Assert.notNull( builder, "builder" );
		Assert.notNull( name, "name" );
		// Null allowed: Content, args

		builder
			.append( "<" )
			.append( name )
		;

		if( args != null ) for( String argName : args.keySet() ){
			
			String argValue = args.get( argName );

			builder
				.append( " " )
				.append( argName )
				.append( "=\"" )
				.append( AttributeEncoder.ENCODE( argValue ) )
				.append( "\"" )
			;
		}

		if( content == null ){

    		builder.append( "/>" );
		} else {
			builder
				.append( ">" )
    			.append( encodeContent ? XmlEncoder.ENCODE( content ) : content )
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
	public static String simple( String name, String content, boolean encodeContent, String ... args ){
		return simpleBB( new StringBuilder(), name, content, encodeContent, args ).toString();
	}
	
	public static String simple( String name, String content, Map<String,String> args ){
		return simpleBB( new StringBuilder(), name, content, args ).toString();
	}
	public static String simpleBB( 
			String name, String content, boolean encodeContent , Map<String,String> args ){
		return simpleBB( new StringBuilder(), name, content, encodeContent, args ).toString();
	}
	
	public static String hiddenInput( String name, String value ){

		String [] parameters = new String[]{ "type", "hidden", "name", name, "value", value };

		return simpleBB( new StringBuilder(), "input", null, parameters ).toString();
	}
	
	public static String link( String target, String text, String clazz, String ... args ){
		return linkBB( new StringBuilder(), target, text, clazz, args ).toString();
	}
	public static StringBuilder linkBB( StringBuilder result, String target, String text, String clazz, String ... args ){
		
		if( args != null && args.length % 2 != 0 )
			throw new IllegalArgumentException( "Arg count must be a multiple of 2" );
		Assert.notNull( result, "result" );
		Assert.notNull( target, "target" );
		
		StringBuilder href = new StringBuilder();
		
		href.append( target );
		
		if( args != null && args.length > 0 ){
			
			href.append( "?" );
			
			for( int i=0; i<args.length; i+=2 ){
				
				if( i > 0 ) href.append( "&" );
				
				href
					.append( args[i] )
					.append( "=" )
					.append( AttributeEncoder.ENCODE( args[i+1] ) )
				;
			}
		}
		
		LinkedList<String> tagArgs = new LinkedList<String>();
		tagArgs.add( "href" ); tagArgs.add( href.toString() );
		if( clazz != null ){
			tagArgs.add( "class" ); tagArgs.add( clazz );
		}
		
		return simpleBB( result, "a", text, false, tagArgs.toArray( new String[ tagArgs.size() ] ) );
	}
}
