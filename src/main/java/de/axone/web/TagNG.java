package de.axone.web;

import java.util.Map;

import de.axone.tools.Mapper;

public class TagNG {
	
	private final TagNG parent;
	private final String name;
	private final Map<String,String> args;
	private TagNG content;

	public TagNG(){
		this.parent = null;
		this.name = null;
		this.args = null;
	}
	
	public TagNG( TagNG parent, String name, String ... args ){
		this.parent = parent;
		this.name = name;
		this.args = Mapper.hashMap( args );
		if( parent != null ) parent.content = this;
	}
	
	public TagNG( TagNG parent, String name, TagNG content, String ... args ){
		this.parent = parent;
		this.name = name;
		this.content = content;
		this.args = Mapper.hashMap( args );
		if( parent != null ) parent.content = this;
	}
	
	public TagNG html(){
		return new TagNG( null, "html" );
	}
	
	public TagNG form( String action, String method ){
		return new TagNG( this, "form", "action", action, "method", method );
	}
	
	public TagNG input( String name, String value ){
		return new TagNG( this, "input", "name", name, "value", value );
	}
	
	public TagNG button( String name, String value ){
		return new TagNG( this, "button", "name", name, "value", value );
	}
	
	public TagNG UP(){ return parent; }
	
	public TagNG _( String content ){
		return new TagNG( this, content );
	}
	
	public StringBuilder build( StringBuilder b ){
		
		if( name != null ){
			b.append( '<' ).append( name );
			
			for( Map.Entry<String,String> arg : args.entrySet() ){
				
				b.append( ' ' ).append( arg.getKey() );
			}
			
			if( content != null ){
				
				b.append( '>' );
				content.build( b );
				b.append( "</" ).append( name ).append( '>' );
			} else {
				b.append( "/>" );
			}
		} else {
			content.build( b );
		}
		return b;
	}
	@Override
	public String toString(){
		return build( new StringBuilder() ).toString();
	}
}
