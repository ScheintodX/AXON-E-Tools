package de.axone.web;

import java.util.List;

import de.axone.tools.Str;
import de.axone.tools.Str.Joiner;

public abstract class Meta {
	
	private static final SuperURLPrinter linkPrinter = SuperURLPrinter.ForAttribute;

	protected final String a, b;
	
	public abstract String toHtml();
	
	@Override
	public String toString(){
		return toHtml();
	}
	
	protected Meta( String a, String b ){
		this.a = a;
		this.b = b;
	}
	
	public static Meta meta( String name, String content ){
		return new AMeta( name, content );
	}
	public static Meta link( String rel, SuperURL content ){
		return new ALink( rel, linkPrinter.toString( content ) );
	}
	public static Meta link( String name, String type, SuperURL content ){
		return new ALink3( name, type, linkPrinter.toString( content ) );
	}
	public static Meta linklang( String name, String hreflang, SuperURL content ){
		return new ALinkLang( name, hreflang, linkPrinter.toString( content ) );
	}
	public static Meta robots( String what ){
		return meta( "robots", what );
	}
	public static Meta canonical( SuperURL href ){
		return link( "canonical", href );
	}
	public static Meta alternate( SuperURL href, String hreflang ){
		return linklang( "alternate", hreflang, href );
	}
	
	/*
	private static String locale2hreflang( Locale locale ) {
		
		String country = locale.getCountry(),
		       language = locale.getLanguage()
		       ;
		
		return country.toLowerCase() + ( language.length() > 0 ? "-" + language : "" );
	}
	*/
	
	private static final Joiner<Meta> META_JOINER = new Joiner<Meta>(){

		@Override
		public String getSeparator() { return "\n"; }

		@Override
		public String toString( Meta meta, int index ) { return meta.toHtml(); }
	};
	
	public static String joinToHtml( List<Meta> metas ){
		return Str.join( META_JOINER, metas );
	}
	
	private static class AMeta extends Meta {

		protected AMeta( String name, String content ) {
			super( name, content );
		}

		@Override
		public String toHtml() {
			return "<meta name=\"" + a + "\" content=\"" + b + "\" />";
		}
	}
	
	private static class ALink extends Meta {
		
		protected ALink( String rel, String href ) {
			super( rel, href );
		}

		@Override
		public String toHtml() {
			return "<link rel=\"" + a + "\" href=\"" + b + "\" />";
		}
		
	}
	
	private static class ALink3 extends Meta {
		
		private final String type;
		
		protected ALink3( String rel, String type, String href ) {
			super( rel, href );
			this.type = type;
		}

		@Override
		public String toHtml() {
			return "<link rel=\"" + a + "\" type=\"" + type + "\" href=\"" + b + "\" />";
		}
	}
	
	private static class ALinkLang extends Meta {
		
		private final String hreflang;
		
		protected ALinkLang( String rel, String hreflang, String href ) {
			super( rel, href );
			this.hreflang = hreflang;
		}

		@Override
		public String toHtml() {
			return "<link rel=\"" + a + "\" hreflang=\"" + hreflang + "\" href=\"" + b + "\" />";
		}
	}
}
