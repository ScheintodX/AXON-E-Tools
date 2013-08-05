package de.axone.web.rest;

import java.util.LinkedHashMap;


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


	public String toHtml() {

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
		private Example example;
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
		public Method addParameter( String name, String description,
				String defaultValue ) {
			parameters.put( name, new Parameter( name, description,
					defaultValue ) );
			return this;
		}

		public Method setReturnValue( String name, String description ) {
			returnValue = new Parameter( name, description );
			return this;
		}
		public Method setExample( String method, String path ){
			this.example = new Example( method, path, null, null );
			return this;
		}
		public Method setExample( String method, String path, String comment ){
			this.example = new Example( method, path, comment, null );
			return this;
		}
		public Method setExample( String method, String path, Example.Argument argument ){
			this.example = new Example( method, path, null, argument );
			return this;
		}
		public Method setHtml( String html ){
			this.html = html;
			return this;
		}
		public String toHtml() {

			StringBuilder result = new StringBuilder();

			result.append( "<div class=\"method\">\n" );
			result.append( "<h2>" ).append( name ).append( "</h2>\n" );
			result.append( "<p>" ).append( description ).append( "</p>\n" );

			if( parameters.size() > 0 ) {

				result.append( "<ul class=\"parameters\">" );

				for( Parameter parameter : parameters.values() ) {
					result.append( "<li>" ).append( parameter.toHtml() )
							.append( "</li>\n" );
				}

				result.append( "</ul>\n" );
			}

			if( returnValue != null ) {

				result.append( "<p>Returns: " ).append( returnValue.toHtml() )
						.append( "</p>\n" );
			}

			if( example != null ){

				result.append( "<p class=\"example\">Example: <tt>" ).append( example.toHtml() )
						.append( "</tt></p>\n" );
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

			result.append( "<span class=\"parameter\">" ).append(
					"<em class=\"name\">" ).append( name ).append( "</em>" )
					.append( " <span class=\"description\">" ).append(
							description ).append( "</span>" );

			if( defaultValue != null ) result.append(
					" <tt class=\"defaultValue\">" ).append( name ).append( "</tt>" );

			result.append( "</span>" );

			return result;
		}
	}

	public static class Example {
		
		private final String method, path, comment;
		private final Argument argument;

		public Example( String method, String path, String comment, Argument argument ){

			this.method = method;
			this.path = path;
			this.comment = comment;
			this.argument = argument;
		}
		
		public StringBuilder toHtml(){

			StringBuilder result = new StringBuilder();

			result
				.append( "<span class=\"example\">" )
				.append( method )
				.append( ": <tt>" )
				.append( path )
			;
			if( argument != null ) result
				.append( ": " )
				.append( argument.value )
			;
			result
				.append( "</tt>" )
			;
			
			if( comment != null ) result
				.append( " // " )
				.append( comment )
			;
				
			result
    			.append( "</span>\n" )
    		;

			if( argument != null ){
    			result
    				.append( "<form method=\"" ).append( method )
    				.append( "\" action=\"" ).append( path ).append( "\">" )
    				.append( argument.toHtml().toString() )
    				.append( "<input type=\"submit\" />" )
    				.append( "</form>\n" )
    			;
			}

			return result;
		}
		
		public static class Argument {
			
			private final String name, value;
			
			public Argument( String name, String value ){
				this.name = name;
				this.value = value;
			}
			
			public StringBuilder toHtml(){
				
				StringBuilder result = new StringBuilder();
    			result
    				.append( "<input type=\"text\" name=\"" )
	    			.append( name ).append( "\" value=\"" ).append( value ).append( "\" />");
    			
    			return result;
			}
			
		}
	}
	
}
