package de.axone.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.axone.tools.Mapper;
import de.axone.tools.PasswordBuilder;
import de.axone.web.Tag;


public class RestFunctionDescription {

	private String name;
	private RestFunctionRoute route;
	private String description;
	private LinkedHashMap<String, Method> methods = new LinkedHashMap<String, Method>();
	private String group;
	private String template;

	public RestFunctionDescription( String name, String description ) {
		this.name = name;
		this.description = description;
	}

	public Method addMethod( String name, String description ) {
		Method method = new Method( name, description );
		methods.put( name, method );
		return method;
	}
	
	public RestFunctionDescription setTemplate( String group, String template ){
		this.group = group;
		this.template = template;
		return this;
	}
	
	public void setRoute( RestFunctionRoute route ){
		this.route = route;
	}
	
	public String getTemplateGroup(){
		return group;
	}
	public String getTemplate(){
		return template;
	}


	public String toHtml( boolean detailed ) {

		StringBuilder result = new StringBuilder();

		result.append( "<div class=\"function\">\n" );
		result.append( "<h1>" ).append( name ).append( "</h1>\n" );
		if( route != null )
			result.append( "<div class=\"route\">" ).append( route.toString() ).append( "</div>" );
		result.append( "<p>" ).append( description ).append( "</p>\n" );

		if( methods.size() > 0 ) {

			result.append( "<ul class=\"methods\">" );

			for( Method method : methods.values() ) {
				result.append( "<li>" ).append( method.toHtml() ).append(
						"</li>\n" );
			}

			result.append( "</ul>" );
		}

		result.append( "</div>" );

		return result.toString();
	}

	public static class Method {

		private String name;
		private String description;
		private LinkedHashMap<String, Parameter> parameters = new LinkedHashMap<String, Parameter>();
		private Parameter returnValue;
		private List<Example> examples = new LinkedList<Example>();
		private String html;

		public Method( String name, String description ) {
			this.name = name;
			this.description = description;
		}

		public Method addParameter( Parameter parameter ){
			parameters.put( parameter.name, parameter );
			return this;
		}
		public Method addParameter( String name, String description ){
			parameters.put( name, new Parameter( name, description ) );
			return this;
		}
		public Method addParameter( String name, String description, String defaultValue ) {
			parameters.put( name, new Parameter( name, description, defaultValue ) );
			return this;
		}

		public Method setReturnValue( String name, String description ) {
			returnValue = new Parameter( name, description );
			return this;
		}
		
		public Method addExample( Example example ) {
			this.examples.add( example );
			return this;
		}
		
		public Method addExample( Example example, String explain ) {
			example.setExplain( explain );
			return addExample( example );
		}
		
		public Method setHtml( String html ){
			this.html = html;
			return this;
		}
		
		public String toHtml() {

			StringBuilder result = new StringBuilder();

			result
				.append( "<div class=\"method\">\n" )
				.append( "<h2>" ).append( name ).append( "</h2>\n" )
				.append( "<p>" ).append( description ).append( "</p>\n" )
			;

			if( parameters.size() > 0 ) {

				result.append( "<ul class=\"parameters\">" );

				for( Parameter parameter : parameters.values() ) {
					
					result
						.append( "<li>" )
						.append( parameter.toHtml() )
						.append( "</li>\n" );
				}

				result.append( "</ul>\n" );
			}

			if( returnValue != null ) {

				result
					.append( "<p>Returns: " )
					.append( returnValue.toHtml() )
					.append( "</p>\n" );
			}

			if( examples.size() > 0 ){
				
				for( Example example : examples ){

					result.append( "<p class=\"example\">Example:" );
					example.toHtml( result );
					result.append( "</p>\n" );
				}
			}
			
			if( html != null ){
				
				result.append( html );
			}
			
			result.append( "</div>" );

			return result.toString();
		}
	}

	public static class Parameter {

		private String name;
		private String description;
		private String defaultValue;

		public Parameter( String name, String description ) {
			
			this( name, description, null );
		}

		public Parameter( String name, String description, String defaultValue ) {
			
			this.name = name;
			this.description = description;
			this.defaultValue = defaultValue;
		}

		public StringBuilder toHtml() {

			StringBuilder result = new StringBuilder();

			result
				.append( "<span class=\"parameter\">" )
				.append( "    <em class=\"name\">" ).append( name ).append( "</em>" )
				.append( "    <span class=\"description\">" ).append( description ).append( "</span>" )
			;

			if( defaultValue != null ) {
				result.append( "    <tt class=\"defaultValue\">" ).append( name ).append( "</tt>" );
			}

			result.append( "</span>" );

			return result;
		}
	}
	
	public interface Example {
		
		public abstract StringBuilder toHtml( StringBuilder result );
		public void setExplain( String explain );
		public String explain();
	}
	
	public abstract static class AbstractExample implements Example {
		
		protected final String method;
		protected final String path;
		protected String explain;
	
		public AbstractExample( String method, String path ){
			this( method, path, null );
		}
		public AbstractExample( String method, String path, String explain ){
			this.method = method;
			this.path = path;
			this.explain = explain;
		}
		
		@Override
		public void setExplain( String explain ){
			this.explain = explain;
		}
		@Override
		public String explain(){
			return explain;
		}
		@Override
		public StringBuilder toHtml( StringBuilder result ){
			return result;
		}
	}
	
	public static class GetExample extends AbstractExample {
		
		public GetExample( String path ){
			super( "GET", path );
		}

		@Override
		public StringBuilder toHtml( StringBuilder result ) {
			
			Tag.simpleBB( result, "a", path, "href", path );
			
			return super.toHtml( result );
		}
	}
	
	
	public static class FormExample extends AbstractExample {
		
		protected Map<String,String> arguments;
		private final String id;
		
		public FormExample( String method, String path, Map<String,String> arguments ){
			super( method, path );
			this.arguments = arguments;
			this.id = PasswordBuilder.makeSimplaPasswd();
		}
		public FormExample( String method, String path, String ... arguments ){
			this( method, path, Mapper.treeMap( arguments ) );
		}

		@Override
		public StringBuilder toHtml( StringBuilder result ) {
			
			StringBuilder content = new StringBuilder();
			
			for( String name : arguments.keySet() ){
				
				String idStr = id + "_" + name;
				
				Tag.simpleBB( content, "label", name,
						"for", idStr );
				
				Tag.simpleBB( content, "input", null,
						"id", idStr, "name", name, "value", arguments.get( name ) );
			}
			
			Tag.simpleBB( content, "input", null,
					"type", "hidden", "name", "action", "value", method );
		
			Tag.simpleBB( content, "input", null, "type", "submit" );
			
			Tag.simpleBB( result, "form", content.toString(), false, 
					"name", id, "action", path, "method", "POST" );
			
			return super.toHtml( result );
		}
	}
	
	public static class JsonExample extends AbstractExample {
		
		protected Object json;
		
		public JsonExample( String method, String path, Object json ){
			super( method, path );
			this.json = json;
		}

		@Override
		public StringBuilder toHtml( StringBuilder result ) {
			
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );
			
			String data;
			try( StringWriter s = new StringWriter() ){
				mapper.writeValue( s, json );
				data = s.toString();
			} catch( IOException e ) {
				throw new IllegalArgumentException( "Cannot encode Json" );
			};
			
			StringBuilder content = new StringBuilder();
			
			Tag.simpleBB( content, "input", null, "name", "data", "value", data );
			
			Tag.simpleBB( content, "input", null, "type", "submit" );
			
			Tag.simpleBB( result, "form", content.toString(), false, 
					"class", "jsonForm", "action", path, "method", method );
			
			return super.toHtml( result );
		}
	}
	
}
