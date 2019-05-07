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

	private final String name;
	private final String description;
	private RestFunctionRoute route;
	private final LinkedHashMap<String, Method> methods = new LinkedHashMap<>();
	private String group;
	private String template;

	public RestFunctionDescription( String name, String description ) {
		this.name = name;
		this.description = description;
	}

	public static Method M( String name, String description ) {
		return new Method( name, description );
	}

	public Method addMethod( String name, String description ) {
		Method method = M( name, description );
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

	public String getName() {
		return name;
	}


	public String toHtml( boolean detailed ) {

		StringBuilder result = new StringBuilder();

		result.append( "<article class=\"function\">\n" );
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

		result.append( "</article>" );

		return result.toString();
	}

	public static class Method {

		private final String name;
		private final String description;
		private final LinkedHashMap<String, Parameter> parameters = new LinkedHashMap<>();
		private Parameter returnValue;
		private final List<Example<?>> examples = new LinkedList<>();
		private String html;

		public Method( String name, String description ) {
			this.name = name;
			this.description = description;
		}

		public Method par( Parameter parameter ){
			parameters.put( parameter.name, parameter );
			return this;
		}
		public Method par( String name, String description ){
			return par( new Parameter( name, description ) );
		}
		public Method par( String name, String description, String defaultValue ) {
			return par( new Parameter( name, description, defaultValue ) );
		}

		public Method returns( String name, String description ) {
			returnValue = new Parameter( name, description );
			return this;
		}

		public Method forExample( Example<?> example ) {
			this.examples.add( example );
			return this;
		}

		public Method forExampleGet( String path ) {
			return forExample( new GetExample( path ) );
		}

		public Method forExampleGet( String path, String explain ) {
			return forExample( new GetExample( path ).setExplain( explain ) );
		}

		public Method html( String html ){
			this.html = html;
			return this;
		}

		public String toHtml() {

			StringBuilder result = new StringBuilder();

			result
				.append( "<section class=\"method\">\n" )
				.append( "<h2>" ).append( name ).append( "</h2>\n" )
				.append( "<p>" ).append( description ).append( "</p>\n" )
			;

			if( parameters.size() > 0 ) {

				result.append( "<ul class=\"parameters\">" );

				for( Parameter parameter : parameters.values() ) {

					result
						.append( parameter.toHtml() )
						.append( "\n" );
				}

				result.append( "</ul>\n" );
			}

			if( returnValue != null ) {

				result
					.append( "<div class=\"return\">Returns: " )
					.append( returnValue.toHtml() )
					.append( "</div>\n" );
			}

			if( examples.size() > 0 ){

				for( Example<?> example : examples ){

					result.append( "<div class=\"example\">Example:" );
					example.toHtml( result );
					result.append( "</div>\n" );
				}
			}

			if( html != null ){

				result.append( html );
			}

			result.append( "</section>" );

			return result.toString();
		}
	}

	public static class Parameter {

		private final String name;
		private final String description;
		private final String defaultValue;

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
				.append( "<li class=\"parameter\">" )
				.append( "    <em class=\"name\">" ).append( name ).append( "</em>" )
				.append( "    <span class=\"description\">" ).append( description ).append( "</span>" )
			;

			if( defaultValue != null ) {
				result.append( "    <tt class=\"defaultValue\">" ).append( name ).append( "</tt>" );
			}

			result.append( "</li>" );

			return result;
		}
	}

	public interface Example<T extends Example<T>> {

		public abstract StringBuilder toHtml( StringBuilder result );
		public T setExplain( String explain );
		public String explain();
	}

	public abstract static class AbstractExample<T extends AbstractExample<T>> implements Example<T> {

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

		@SuppressWarnings( "unchecked" )
		@Override
		public T setExplain( String explain ){
			this.explain = explain;
			return (T)this;
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

	public static class GetExample extends AbstractExample<GetExample> {

		public GetExample( String path ){
			super( "GET", path );
		}

		@Override
		public StringBuilder toHtml( StringBuilder result ) {

			Tag.simpleBB( result, "a", path, "href", path );

			return super.toHtml( result );
		}
	}


	public static class FormExample extends AbstractExample<FormExample> {

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

	public static class JsonExample extends AbstractExample<JsonExample> {

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

	public static final class Builder {

		public RestFunctionDescription of( String name, String description ) {
			return new RestFunctionDescription( name, description );
		}

		public RestFunctionDescription of( String name, String description, Method ... methods ) {

			RestFunctionDescription  result = of( name, description );

			for( Method m : methods ) {
				result.methods.put( m.name, m );
			}

			return result;
		}

		public Method M( String method, String name, String description ) {
			return new Method( method + ": " + name, description );
		}
		/*
		public Method M( String name, String description ) {
			return new Method( name, description );
		}
		*/
	}


}
