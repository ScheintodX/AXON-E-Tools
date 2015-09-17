package de.axone.web;

import java.util.LinkedList;
import java.util.Map;

import de.axone.exception.Assert;
import de.axone.tools.Mapper;
import de.axone.web.encoding.Encoder_Attribute;
import de.axone.web.encoding.Encoder_Xml;


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
				.append( Encoder_Attribute.ENCODE( args[ i+1 ] ) )
				.append( "\"" )
			;
		}

		if( content == null ){

    		builder.append( "/>" );
		} else {
			builder
				.append( ">" )
    			.append( encodeContent ? Encoder_Xml.ENCODE( content ) : content )
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

		if( args != null ) for( Map.Entry<String,String> entry : args.entrySet() ){
			
			String argName = entry.getKey();
			String argValue = entry.getValue();

			builder
				.append( " " )
				.append( argName )
				.append( "=\"" )
				.append( Encoder_Attribute.ENCODE( argValue ) )
				.append( "\"" )
			;
		}

		if( content == null ){

    		builder.append( "/>" );
		} else {
			builder
				.append( ">" )
    			.append( encodeContent ? Encoder_Xml.ENCODE( content ) : content )
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
	
	public static String simple( 
			String name, String content, boolean encodeContent , Map<String,String> args ){
		return simpleBB( new StringBuilder(), name, content, encodeContent, args ).toString();
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
					.append( Encoder_Attribute.ENCODE( args[i+1] ) )
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
	
	/*
	 * Just a test for now. But we could insert simplified versions here.
	 */
	/**
	 * No stable interface from here on
	 * 
	 * @param content 
	 * @param args 
	 * @return tag as string
	 */
	public static String a( String content, String ... args ){
		return simple( "a", content, args );
	}
	public static String a( String content, String href ){
		return simple( "a", content, "href", href );
	}
	public static String javascript( String id, String content ){
		return simple( "script",
				"\n<!--\n\"use strict\";\n\n " + content + "\n-->\n",
				false,
				Mapper.hashMap( "id", id, "type", "text/javascript" ) );
	}
	
	public static String button( String name, String value, String content ){
		
		return simple( "button", content, "name", name, "value", value );
	}
	public static String form( String method, String action, String content ){
		
		return simple( "form", content, false, "method", method, "action", action );
	}
	
	public static String hiddenInput( String name, String value ){

		return simple( "input", null, "type", "hidden", "name", name, "value", value );
	}
	
	public static String link( String target, String text, String clazz, String ... args ){
		return linkBB( new StringBuilder(), target, text, clazz, args ).toString();
	}
	
	public static String iframe( String src, String width, String height ){
		return simple( "iframe", null, "src", src, "width", width, "height", height );
	}
	
	public static String div( String content ){
		return simple( "div", content, false );
	}
	public static String div( String id, String clazz, String content ){
		return simple( "div", content, false, "id", id, "class", clazz );
	}
	public static String divId( String id, String content ){
		return simple( "div", content, false, "id", id );
	}
	public static String divClass( String clazz, String content ){
		return simple( "div", content, false, "class", clazz );
	}
	
}
