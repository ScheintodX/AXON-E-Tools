package de.axone.web;


import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

import de.axone.tools.A;
import de.axone.web.SuperURL.Encode;
import de.axone.web.SuperURL.FinalEncoding;
import de.axone.web.SuperURL.Host;
import de.axone.web.SuperURL.Part;
import de.axone.web.SuperURL.Path;
import de.axone.web.SuperURL.Query;
import de.axone.web.SuperURL.Query.QueryPart;
import de.axone.web.SuperURL.UserInfo;
import de.axone.web.encoding.Encoder;
import de.axone.web.encoding.Encoder_Attribute;
import de.axone.web.encoding.Encoder_Html;
import de.axone.web.encoding.TranslatingEncoder;

public abstract class SuperURLPrinter {
	
	public static SuperURLPrinter Plain = new FastPlain();
	public static SuperURLPrinter MinimalEncoded = new FastMinimalEncoded();
	public static SuperURLPrinter FullEncoded = new FastFullEncoded();
	
	public abstract String toString( SuperURL url );
	public abstract String toString( SuperURL.UserInfo userInfo );
	public abstract String toString( SuperURL.Host host );
	public abstract String toString( SuperURL.Path path );
	public abstract String toString( SuperURL.Query query );
	
	public abstract <W extends Appendable> W write( W result, SuperURL url ) throws IOException;
	public abstract <W extends Appendable> W write( W result, SuperURL.Path path ) throws IOException;
	public abstract <W extends Appendable> W write( W result, SuperURL.Query query ) throws IOException;
	public abstract <W extends Appendable> W write( W result, SuperURL.Host host ) throws IOException;
	public abstract <W extends Appendable> W write( W result, SuperURL.UserInfo userInfo ) throws IOException;
	
	public abstract SuperURLPrinter encode( SuperURL.Part part, SuperURL.Encode encode );
	public abstract SuperURLPrinter encode( SuperURL.Encode encode );
	public abstract SuperURLPrinter finishFor( FinalEncoding finalEncoding );
	public abstract SuperURLPrinter exclude( Set<SuperURL.Part> parts );
	public abstract SuperURLPrinter include( Set<SuperURL.Part> parts );
	
	public abstract SuperURL.Encode encoding();
		
	private static abstract class Abstract extends SuperURLPrinter {
	
		@Override
		public String toString( SuperURL url ){
			try {
				return write( new StringWriter(), url ).toString();
			} catch( IOException e ){
				throw new RuntimeException( "SNAFU writing to StringWriter.", e );
			}
		}
	
		@Override
		public String toString( SuperURL.Path path ){
			try {
				return write( new StringWriter(), path ).toString();
			} catch( IOException e ){
				throw new RuntimeException( "SNAFU writing to StringWriter.", e );
			}
		}
	
		@Override
		public String toString( SuperURL.Query query ){
			try {
				return write( new StringWriter(), query ).toString();
			} catch( IOException e ){
				throw new RuntimeException( "SNAFU writing to StringWriter.", e );
			}
		}
	
		@Override
		public String toString( SuperURL.Host host ){
			try {
				return write( new StringWriter(), host ).toString();
			} catch( IOException e ){
				throw new RuntimeException( "SNAFU writing to StringWriter.", e );
			}
		}
	
		@Override
		public String toString( SuperURL.UserInfo userInfo ){
			try {
				return write( new StringWriter(), userInfo ).toString();
			} catch( IOException e ){
				throw new RuntimeException( "SNAFU writing to StringWriter.", e );
			}
		}
		
		@Override
		public SuperURLPrinter encode( SuperURL.Part part, SuperURL.Encode encode ){
			return new SuperURLPrinter.Custom().encode( encoding() ).encode( part, encode );
		}
		@Override
		public SuperURLPrinter encode( SuperURL.Encode encode ){
			return new SuperURLPrinter.Custom().encode( encoding() ).encode( encode );
		}
		@Override
		public SuperURLPrinter finishFor( FinalEncoding finalEncoding ){
			return new SuperURLPrinter.Custom().encode( encoding() ).finishFor( finalEncoding );
		}
		@Override
		public SuperURLPrinter exclude( Set<SuperURL.Part> parts ){
			return new SuperURLPrinter.Custom().encode( encoding() ).exclude( parts );
		}
		@Override
		public SuperURLPrinter include( Set<SuperURL.Part> parts ){
			return new SuperURLPrinter.Custom().encode( encoding() ).include( parts );
		}
		
	}
	
	public static class Custom extends Abstract {
		
		public static EnumMap<Encode,FastPlain> ForEncode = new EnumMap<>( Encode.class );
		static {
			ForEncode.put( Encode.Plain, (FastPlain)Plain );
			ForEncode.put( Encode.Minimal, (FastPlain)MinimalEncoded );
			ForEncode.put( Encode.Full, (FastPlain)FullEncoded );
		}
		
		private Encode encode = Encode.Full;
		
		private EnumMap<Part,Encode> parts;
		
		private EnumSet<Part> include;
		
		@Override
		public SuperURLPrinter encode( SuperURL.Part part, SuperURL.Encode encode ){
			
			if( parts == null ) parts = new EnumMap<>( Part.class );
			
			this.parts.put( part, encode );
			
			return this;
		}
		
		@Override
		public SuperURLPrinter encode( SuperURL.Encode encode ){
			this.encode = encode;
			return this;
		}
		
		@Override
		public SuperURLPrinter finishFor( FinalEncoding finalEncoding ){
			return new Finisher( finalEncoding, this );
		}
		
		@Override
		public SuperURLPrinter exclude( Set<SuperURL.Part> parts ){
			
			if( include == null ) include = EnumSet.allOf( SuperURL.Part.class );
			
			include.removeAll( parts );
			
			return this;
		}
		
		@Override
		public SuperURLPrinter include( Set<SuperURL.Part> parts ){
			
			if( include == null ) include = EnumSet.noneOf( SuperURL.Part.class );
			
			include.addAll( parts );
			
			return this;
		}

		private FastPlain printer( Part part ) {
			
			FastPlain result = null;
			
			if( parts != null )
				 result = ForEncode.get( part );
			
			if( result == null )
				result = ForEncode.get( encode );
			
			return result;
		}
		
		private boolean isInclude( Part part ){
			return include == null || include.contains( part );
		}

		@Override
		public <W extends Appendable> W write( W result, SuperURL url )
				throws IOException {
			
			if( url.scheme != null && isInclude( Part.Scheme ) ){
				result.append( printer( Part.Scheme ).encodeScheme( url.scheme ) );
				result.append( "://" );
			}
			
			if( url.userInfo != null && isInclude( Part.UserInfo ) ){
				write( result, url.userInfo );
				result.append( '@' );
			}
			
			if( url.host != null && isInclude( Part.Host ) ){
				write( result, url.host );
			}
			
			if( url.port != null && isInclude( Part.Port ) ){
				result.append( ':' );
				result.append( Integer.toString( url.port ) );
			}
			
			if( url.path != null && isInclude( Part.Path ) ){
				if( ! url.path.startsWithSlash && url.path.length() > 0 ) result.append( '/' );
				write( result, url.path );
			}
			
			if( url.query != null && url.query.size() > 0 && isInclude( Part.Query ) ){
				result.append( '?' );
				write( result, url.query );
			}
			
			;
			if( url.fragment != null && isInclude( Part.Fragment ) ){
				String fragmentStr = url.fragment;
				fragmentStr = printer( Part.Fragment ).encodeFragment( fragmentStr );
				result.append( '#' ).append( fragmentStr );
			}
			
			return result;
		}

		@Override
		public <W extends Appendable> W write( W result, Path path )
				throws IOException {
			return printer( Part.Path ).write( result, path );
		}

		@Override
		public <W extends Appendable> W write( W result, Query query )
				throws IOException {
			return printer( Part.Query ).write( result, query );
		}

		@Override
		public <W extends Appendable> W write( W result, Host host )
				throws IOException {
			return printer( Part.Host ).write( result, host );
		}

		@Override
		public <W extends Appendable> W write( W result, UserInfo userInfo )
				throws IOException {
			return printer( Part.UserInfo ).write( result, userInfo );
		}

		@Override
		public Encode encoding() {
			return encode;
		}
		
	}
		
	public static class FastPlain extends Abstract {

		@Override
		public <W extends Appendable> W write( W result, SuperURL url ) throws IOException{
			
			if( url.scheme != null ){
				result.append( encodeScheme( url.scheme ) );
				result.append( "://" );
			}
			
			if( url.userInfo != null ){
				write( result, url.userInfo );
				result.append( '@' );
			}
			
			if( url.host != null ){
				write( result, url.host );
			}
			
			if( url.port != null ){
				result.append( ':' );
				result.append( Integer.toString( url.port ) );
			}
			
			if( url.path != null ){
				if( ! url.path.startsWithSlash && url.path.length() > 0 ) result.append( '/' );
				write( result, url.path );
			}
			
			if( url.query != null && url.query.size() > 0 ){
				result.append( '?' );
				write( result, url.query );
			}
			
			if( url.fragment != null ){
				String fragmentStr = url.fragment;
				fragmentStr = encodeFragment( fragmentStr );
				result.append( '#' ).append( fragmentStr );
			}
			
			return result;
		}

		@Override
		public <W extends Appendable> W write( W result, Path path ) throws IOException {
			
			if( path.startsWithSlash ) result.append( '/' );
			
			boolean first = true;
			for( String part : path ){
				
				part = encodePath( part );
				
				if( first ) first = false; 
				else result.append( '/' );
				
				result.append( part );
			}
			
			// Now we need to prevent double slashes if starts && ends and no content
			if( path.endsWithSlash && ( ! path.startsWithSlash || path.path.size() > 0 ) ) result.append( '/' );
			
			return result;
		}

		@Override
		public <W extends Appendable> W write( W result, Host host )
				throws IOException {
			
    		boolean first = true;
    		for( String part : host.parts ){
    			
    			part = encodeHost( part );
    			
    			if( first ) first = false;
    			else result.append( '.' );
    			
    			result.append( part );
    		}
    		return result;
		}
		
		@Override
		public <W extends Appendable> W write( W result, Query query )
				throws IOException {
			
			boolean first = true;
			for( QueryPart part : query.path ){
				
				if( first ) first = false;
				else result.append( '&' );
				
				String keyStr = part.key;
				String valStr = part.value;
				
				if( keyStr != null ) keyStr = encodeQueryKey( keyStr );
				if( valStr != null ) valStr = encodeQueryValue( valStr );
				
				if( keyStr != null ){
    				result.append( keyStr );
				}
				
				if( valStr != null ){
					result.append( '=' ).append( valStr );
				}
			}
			return result;
		}

		@Override
		public <W extends Appendable> W write( W result, UserInfo path )
				throws IOException {
			
			String userStr = path.user;
			String passStr = path.pass;
			
			if( userStr != null ) userStr = encodeUserInfo( userStr );
			if( passStr != null ) passStr = encodeUserInfo( passStr );
			
			if( userStr != null ){
    			result.append( userStr );
			}
			
			if( passStr != null ){
				result.append( ':' ).append( passStr );
			}
			return result;
		}
		
		protected String encodeUserInfo( String value ) {
			return encode( value );
		}
		
		protected String encodeFragment( String value ) {
			return encode( value );
		}

		protected String encodeScheme( String value ) {
			return encode( value );
		}

		protected String encodeQueryValue( String value ) {
			return encode( value );
		}

		protected String encodeQueryKey( String value ) {
			return encode( value );
		}

		protected String encodeHost( String value ) {
			return encode( value );
		}

		protected String encodePath( String value ) {
			return encode( value );
		}

		protected String encode( String value ) {
			// HOPE: The vm will optimize this
			return value; // Dont encode
		}

		@Override
		public Encode encoding() {
			return Encode.Plain;
		}

	}
	
	public static class FastFullEncoded extends FastPlain {
		
		private static final String UTF_8 = "utf-8";

		@Override
		protected String encode( String value ){
			
			try {
				return URLEncoder.encode( value, UTF_8 );
			} catch( UnsupportedEncodingException e ) {
				throw new RuntimeException( "SNAFU encoding", e );
			}
		}
		
		@Override
		public Encode encoding() {
			return Encode.Full;
		}
	}
	
	public static class FastMinimalEncoded extends FastPlain {
		
		private static final char [] gen_delims = new char [] { '%', ':', '/', '?', '#', '[', ']', '@' };
		
		private static final Encoder DEFAULT =
							buildEncoder( gen_delims );
		
		private static final Encoder DEFAULT_plus_query_key =
							buildEncoder( A.union( gen_delims, '&', '=' ) );
		
		private static final Encoder DEFAULT_plus_query_value =
							buildEncoder( A.union( gen_delims, '&' ) );
		
		private static final Encoder DEFAULT_plus_host =
							buildEncoder( A.union( gen_delims, '.' ) );
		
		@Override
		protected String encodeUserInfo( String value ) {
			return DEFAULT.encode( value );
		}
		
		@Override
		protected String encodeFragment( String value ) {
			return DEFAULT.encode( value );
		}

		@Override
		protected String encodeScheme( String value ) {
			return DEFAULT.encode( value );
		}

		@Override
		protected String encodeQueryValue( String value ) {
			return DEFAULT_plus_query_value.encode( value );
		}

		@Override
		protected String encodeQueryKey( String value ) {
			return DEFAULT_plus_query_key.encode( value );
		}

		@Override
		protected String encodeHost( String value ) {
			return DEFAULT_plus_host.encode( value );
		}

		@Override
		protected String encodePath( String value ) {
			return DEFAULT.encode( value );
		}

		@Override
		public Encode encoding() {
			return Encode.Minimal;
		}
		
	}
	
	private static TranslatingEncoder buildEncoder( char ... chars ){
		
		char [] src = new char[ chars.length +1 ];
		String [] target = new String[ chars.length +1 ];
		
		for( int i=0; i<chars.length; i++ ){
			
			char c = chars[ i ];
			src[ i ] = c;
			target[ i ] = String.format( "%%%02x", (int)c );
		}
		
		src[ src.length-1 ] = ' ';
		target[ target.length-1 ] = "+";
		
		return new TranslatingEncoder( src, target );
	}
	
	public static class Wrapper extends SuperURLPrinter{
		
		protected final SuperURLPrinter wrapped;
		
		public Wrapper( SuperURLPrinter wrapped ){
			this.wrapped = wrapped;
		}

		@Override
		public String toString( SuperURL url ) {
			return wrapped.toString( url );
		}

		@Override
		public String toString( UserInfo userInfo ) {
			return wrapped.toString( userInfo );
		}

		@Override
		public String toString( Host host ) {
			return wrapped.toString( host );
		}

		@Override
		public String toString( Path path ) {
			return wrapped.toString( path );
		}

		@Override
		public String toString( Query query ) {
			return wrapped.toString( query );
		}

		@Override
		public <W extends Appendable> W write( W result, SuperURL url )
				throws IOException {
			return wrapped.write( result, url );
		}

		@Override
		public <W extends Appendable> W write( W result, Path path )
				throws IOException {
			return wrapped.write( result, path );
		}

		@Override
		public <W extends Appendable> W write( W result, Query query )
				throws IOException {
			return wrapped.write( result, query );
		}

		@Override
		public <W extends Appendable> W write( W result, Host host )
				throws IOException {
			return wrapped.write( result, host );
		}

		@Override
		public <W extends Appendable> W write( W result, UserInfo userInfo )
				throws IOException {
			return wrapped.write( result, userInfo );
		}

		@Override
		public SuperURLPrinter encode( Part part, Encode encode ) {
			return wrapped.encode( part, encode );
		}

		@Override
		public SuperURLPrinter encode( Encode encode ) {
			return wrapped.encode( encode );
		}

		@Override
		public SuperURLPrinter finishFor( FinalEncoding finalEncoding ) {
			return wrapped.finishFor( finalEncoding );
		}

		@Override
		public SuperURLPrinter exclude( Set<Part> parts ) {
			return wrapped.exclude( parts );
		}

		@Override
		public SuperURLPrinter include( Set<Part> parts ) {
			return wrapped.include( parts );
		}

		@Override
		public Encode encoding() {
			return wrapped.encoding();
		}
		
	}
	
	public static final class Finisher extends SuperURLPrinter.Wrapper {
		
		private static EnumMap<FinalEncoding,Encoder> ENCODER = new EnumMap<>( FinalEncoding.class );
		static {
			//ENCODER.put( FinalEncoding.Plain, NoEncoder.instance() );
			ENCODER.put( FinalEncoding.Html, Encoder_Html.instance() );
			ENCODER.put( FinalEncoding.Attribute, Encoder_Attribute.instance() );
		}
		
		private FinalEncoding finalEncoding;
		

		public Finisher( FinalEncoding finalEncoding, SuperURLPrinter wrapped ) {
			super( wrapped );
			this.finalEncoding = finalEncoding;
		}
		
		@Override
		public String toString( SuperURL url ){
			try {
				return write( new StringWriter(), url ).toString();
			} catch( IOException e ){
				throw new RuntimeException( "SNAFU writing to StringWriter.", e );
			}
		}
	
		@Override
		public <W extends Appendable> W write( W result, SuperURL url )
				throws IOException {
			
			Appendable out = result;
			
			if( finalEncoding != FinalEncoding.Plain ) out = ENCODER.get( finalEncoding ).filter( out );
			
			wrapped.write( out, url );
			
			return result;
		}
		
		@Override
		public SuperURLPrinter finishFor( FinalEncoding finalEncoding ) {
			this.finalEncoding = finalEncoding;
			return this;
		}
	}

		
}
