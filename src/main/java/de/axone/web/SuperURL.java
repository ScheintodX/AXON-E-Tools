package de.axone.web;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;

import de.axone.data.Charsets;
import de.axone.data.Label;
import de.axone.tools.E;
import de.axone.tools.Str;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_Copy;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_Request;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_String;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_URI;
import de.axone.web.SuperURLBuilders.SuperURLBuilder_URL;

/**
 * Extended and more usefull version of Javas URI class whereas
 * it uses URI wherever possible to do de/encoding stuff but
 * privides a wider range of manipulation functions. (Setters!!!)
 *
 * A URI looks like this:
 *
 * scheme://user:pass@host:port/path?query#fragment
 *
 * scheme: e.g. http
 * user: the user
 * pass: the pass or nothing
 * host: www.subdomain.myhost.com
 * port: 80. mostly nothing
 * path: blah/blo/blu/bläh
 * query: key1=value1&amp;key2=value2;key3=value3
 * fragment: something
 *
 * More for URLs: RFC 2396
 *
 * Note that all encoding is done using UTF-8!
 *
 * @see <a href="http://www.ietf.org/rfc/rfc2396.txt">RFC 2396</a>
 * @author flo
 */
public final class SuperURL {

	public enum Part {
		Scheme, UserInfo, Host, Port, Path, Query, Fragment;

		public static final Set<Part>
				ALL_PARTS = Collections.unmodifiableSet( EnumSet.allOf( Part.class ) ),
				NO_PARTS = Collections.unmodifiableSet( EnumSet.noneOf( Part.class ) ),
				UPTO_PATH = Collections.unmodifiableSet( EnumSet.of( Part.Scheme, Part.UserInfo, Part.Host, Part.Port ) ),
				FROM_PATH = Collections.unmodifiableSet( EnumSet.of( Part.Path, Part.Query, Part.Fragment ) )
				;

	}

	public enum Encode {
		Plain, Minimal, Full;
	}

	public enum FinalEncoding {
		Plain, Html, Attribute;
	}

	public interface MimeTypes {
		public String code();
	}
	public enum KnownMimeType implements MimeTypes {

		DIR( "text/directory" ),
		JPEG( "image/jpeg", "jpg", "jpeg" ),
		PNG( "image/png", "png" ),
		GIF( "image/gif", "gif" ),
		ICON( "image/x-icon", "ico" ),
		SVG( "image/svg+xml", "svg" ),
		CSS( "text/css", "css" ),
		JS( "text/javascript", "js" ),
		HTML( "text/html", "htm", "html", "xhtml" ),
		TXT( "text/plain", "txt" );

		private final String code;
		private final String [] extensions;

		private static final Map<String,KnownMimeType> forExtension =
				new HashMap<>();

		static {
			for( KnownMimeType type : KnownMimeType.values() ) {

				for( String extension : type.extensions ) {

					forExtension.put( extension, type );
				}
			}
		}

		KnownMimeType( String code, String ... extensions ) {
			this.code = code;
			this.extensions = extensions;
		}

		public static KnownMimeType forExtension( String extension ) {

			return forExtension.get( extension.toLowerCase() );
		}

		@Override
		public String code() {
			return code;
		}
	}

	private static final class UnknownMimeType implements MimeTypes {
		private final String code;
		UnknownMimeType( String code ){
			this.code = code;
		}
		@Override
		public String code() {
			return code;
		}
	}

	private static final EnumMap<Encode, SuperURLPrinter> printers;
	static {
		printers = new EnumMap<>( Encode.class );
		printers.put( Encode.Plain, SuperURLPrinter.Plain );
		printers.put( Encode.Minimal, SuperURLPrinter.MinimalEncoded );
		printers.put( Encode.Full, SuperURLPrinter.FullEncoded );
	}

	private final EnumSet<Part> include = EnumSet.allOf( Part.class );

	public static final String NOSCHEME = "NOSCHEME";

	String scheme;
	UserInfo userInfo;
	Host host;
	Integer port;
	Path path;
	Query query;
	String fragment;

	public SuperURL(){}

	public SuperURL( String scheme, UserInfo userInfo, Host host, Integer port, Path path, Query query, String fragment ){

		if( scheme != null ){
			this.scheme = scheme;
			include.add( Part.Scheme );
		}
		if( userInfo != null ){
			this.userInfo = userInfo;
			include.add( Part.UserInfo );
		}
		if( host != null ){
			this.host = host;
			include.add( Part.Host );
		}
		if( port != null ){
			this.port = port;
			include.add( Part.Port );
		}
		if( path != null ){
			this.path = path;
			include.add( Part.Path );
		}
		if( query != null ){
			this.query = query;
			include.add( Part.Query );
		}
		if( fragment != null ){
			this.fragment = fragment;
			include.add( Part.Fragment );
		}
	}

	/* --- Direct Access --- */

	public SuperURL setInclude( Part part, boolean include ){
		if( include ){
			this.include.add( part );
		} else {
			this.include.remove( part );
		}
		return this;
	}
	public boolean isInclude( Part part ){
		return include.contains( part );
	}

	public String getScheme() {
		return scheme;
	}
	public SuperURL setScheme( String scheme ) {
		this.scheme = scheme;
		return this;
	}
	public SuperURL setIncludeScheme( boolean include ){
		return setInclude( Part.Scheme, include );
	}
	public boolean isIncludeScheme(){
		return isInclude( Part.Scheme );
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}
	public SuperURL setUserInfo( UserInfo userInfo ) {
		this.userInfo = userInfo;
		return this;
	}
	public SuperURL setIncludeUserInfo( boolean include ){
		return setInclude( Part.UserInfo, include );
	}
	public boolean isIncludeUserInfo(){
		return isInclude( Part.UserInfo );
	}

	public Host getHost() {
		return host;
	}
	public SuperURL setHost( Host host ) {
		this.host = host;
		return this;
	}
	public SuperURL setIncludeHost( boolean include ){
		return setInclude( Part.Host, include );
	}
	public boolean isIncludeHost(){
		return isInclude( Part.Host );
	}

	public Integer getPort() {
		return port;
	}
	public SuperURL setPort( Integer port ) {
		this.port = port;
		return this;
	}
	public SuperURL setIncludePort( boolean include ){
		return setInclude( Part.Port, include );
	}
	public boolean isIncludePort(){
		return isInclude( Part.Port );
	}

	public String getFragment() {
		return fragment;
	}
	public SuperURL setFragment( String fragment ) {
		this.fragment = fragment;
		return this;
	}
	public SuperURL setIncludeFragment( boolean include ){
		return setInclude( Part.Fragment, include );
	}
	public boolean isIncludeFragment(){
		return isInclude( Part.Fragment );
	}

	// TODO: Da ist das automatische erzeugen des Pfades dazu gekommen.
	// Das können jetzt also beim Aufrufer Checks entfernt werden.
	@Nonnull
	public Path getPath() {
		if( path == null ) path = new Path();
		return path;
	}
	public SuperURL setPath( Path path ) {
		this.path = path;
		return this;
	}
	public SuperURL setIncludePath( boolean include ){
		return setInclude( Part.Path, include );
	}
	public boolean isIncludePath(){
		return isInclude( Part.Path );
	}

	public Query getQuery() {
		if( query == null ) query = new Query();
		return query;
	}
	public SuperURL setQuery( Query query ) {
		this.query = query;
		return this;
	}
	public SuperURL setIncludeQuery( boolean include ){
		return setInclude( Part.Query, include );
	}
	public boolean isIncludeQuery(){
		return isInclude( Part.Query );
	}

	/* --- Helpers --- */
	public SuperURL setQueryParameter( String key, String value ){
		getQuery().setValue( key, value );
		return this;
	}

	public String getQueryParameter( String key ){
		return getQuery().getValue( key );
	}

	public boolean hasHost(){
		return getHost() != null;
	}
	public boolean hasPath(){
		return getPath() != null
			&& getPath().toString().length() > 0;
	}

	public SuperURL appendPath( String path ){
		return appendPath( path, true );
	}

	public SuperURL appendPath( String path, boolean decode ){

		Path newPath = SuperURLBuilders.Path().parse( path, decode ).build();
		getPath().append( newPath );
		getPath().endsWithSlash = newPath.endsWithSlash;
		return this;
	}

	/* --- ToString --- */

	public String toStringEncode( Encode encode ){
		return printers.get( encode ).toString( this );
	}

	@Deprecated
	@Override
	public String toString(){
		return toDebug();
	}

	public String toRedirect(){

		return SuperURLPrinter.FullEncoded
				.toString( this );
	}

	public String toHtml(){

		return SuperURLPrinter.MinimalEncoded
				.finishFor( FinalEncoding.Html ).toString( this );
	}

	public String toDebug() {

		return SuperURLPrinter.MinimalEncoded
				.toString( this );
	}

	// Called from AjaxFunction_cart
	public String toAjax() {

		return SuperURLPrinter.MinimalEncoded
				.toString( this );
	}



	public String toAttribute(){

		return SuperURLPrinter.MinimalEncoded
				.finishFor( FinalEncoding.Attribute ).toString( this );
	}

	public String toText() {

		return SuperURLPrinter.Plain.toString( this );
	}

	public String toValue() {

		return SuperURLPrinter.MinimalEncoded.toString( this );
	}

	public String toUnique() {

		return SuperURLPrinter.FullEncoded.toString( this );
	}


	static final String decode( String value ){

		if( value == null ) return null;

		try {
			return URLDecoder.decode( value, Charsets.utf8 );
		} catch( UnsupportedEncodingException e ) {
			throw new IllegalArgumentException( "Undecodeable: '" + value + "'" );
		}
	}

	public URL toURL() {
		String asString = SuperURLPrinter.FullEncoded.toString( this );
		try {
			return new URL( asString );
		} catch( MalformedURLException e ){
			throw new IllegalStateException(
					"Cannot create URL for: " + asString, e );
		}
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( scheme == null ) ? 0 : scheme.hashCode() );
		result = prime * result + ( ( userInfo == null ) ? 0 : userInfo.hashCode() );
		result = prime * result + ( ( host == null ) ? 0 : host.hashCode() );
		result = prime * result + ( ( port == null ) ? 0 : port.hashCode() );
		result = prime * result + ( ( path == null ) ? 0 : path.hashCode() );
		result = prime * result + ( ( query == null ) ? 0 : query.hashCode() );
		result = prime * result + ( ( fragment == null ) ? 0 : fragment.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ){

		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof SuperURL ) ) return false;

		SuperURL other = (SuperURL) obj;

		return equals( other, Part.ALL_PARTS );
	}

	public boolean equals( SuperURL other, Set<Part> parts ) {

		if( parts.contains( Part.Scheme ) ){
			if( scheme == null ) {
				if( other.scheme != null ) return false;
			} else if( !scheme.equals( other.scheme ) ) return false;
		}

		if( parts.contains( Part.UserInfo ) ){
			if( userInfo == null ) {
				if( other.userInfo != null ) return false;
			} else if( !userInfo.equals( other.userInfo ) ) return false;
		}

		if( parts.contains( Part.Host ) ){
			if( host == null ) {
				if( other.host != null ) return false;
			} else if( !host.equals( other.host ) ) return false;
		}

		if( parts.contains( Part.Port ) ){
			if( port == null ) {
				if( other.port != null ) return false;
			} else if( !port.equals( other.port ) ) return false;
		}

		if( parts.contains( Part.Path ) ){
			if( path == null ) {
				if( other.path != null ) return false;
			} else if( !path.equals( other.path ) ) return false;
		}

		if( parts.contains( Part.Query ) ){
			if( query == null ) {
				if( other.query != null ) return false;
			} else if( !query.equals( other.query ) ) return false;
		}

		if( parts.contains( Part.Fragment ) ){
			if( fragment == null ) {
				if( other.fragment != null ) return false;
			} else if( !fragment.equals( other.fragment ) ) return false;
		}

		return true;
	}

	public static final class Host implements Iterable<String>{

		public List<String> parts;

		public Host(){
			parts = new LinkedList<>();
		}

		public Host copy() {
			Host result = new Host();
			result.parts.addAll( parts );
			return result;
		}

    	public List<String> getParts(){
    		return parts;
    	}
    	public Host setParts( String ... parts ){
    		this.parts = new LinkedList<>( Arrays.asList( parts ) );
    		return this;
    	}
    	public Host setParts( LinkedList<String> parts ){
    		this.parts = parts;
    		return this;
    	}
    	public int getSize(){
    		return this.parts.size();
    	}
    	public String getTld(){
    		if( parts != null && parts.size() > 0 ) return parts.get( parts.size()-1 );
    		else return null;
    	}
    	public String getHost(){
    		if( parts != null && parts.size() > 0 ) return parts.get( 0 );
    		else return null;
    	}
    	public List<String> getNet(){
    		if( parts != null && parts.size() > 2 ){
        		LinkedList<String> result = new LinkedList<>();
        		for( int i = 1; i < parts.size()-1; i++ ){
        			result.addLast( parts.get( i ) );
        		}
        		return result;
    		} else {
    			return null;
    		}
    	}

    	public String getNetAsString(){
    		return Str.join( ".", getNet() );
    	}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( parts == null ) ? 0 : parts.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof Host ) ) return false;

			Host other = (Host) obj;

			if( parts == null ) {
				if( other.parts != null ) return false;
			} else if( !parts.equals( other.parts ) ) return false;

			return true;
		}

		@Override
		public String toString() {
			return SuperURLPrinter.Plain.toString( this );
		}

		public String toStringEncode( Encode encode ){
			return printers.get( encode ).toString( this );
		}

		public List<String> toList() {
			return new ArrayList<>( parts );
		}

		@Override
		public Iterator<String> iterator() {
			return parts.iterator();
		}

	}

	public static final class Path implements Iterable<String> {

		LinkedList<String> path;
		boolean endsWithSlash;
		boolean startsWithSlash;

		public Path(){
			this( new LinkedList<String>(), false );
		}
		public Path( LinkedList<String> path, boolean endsWithSlash ){
			this.path = path;
			this.endsWithSlash = endsWithSlash;
		}
		public Path( Collection<String> path, boolean endsWithSlash ){
			this.path = new LinkedList<>( path );
			this.endsWithSlash = endsWithSlash;
		}

		public Path copy() {
			Path result = new Path();
			result.path.addAll( path );
			result.endsWithSlash = endsWithSlash;
			result.startsWithSlash = startsWithSlash;
			return result;
		}

		public Path map( Function<String,String> mapper ){

			Path result = new Path();

			for( String part : path ){
				result.addLast( mapper.apply( part ) );
			}
			result.endsWithSlash = endsWithSlash;
			result.startsWithSlash = startsWithSlash;
			return result;
		}

		public int length(){
			return path.size();
		}

		public String get( int index ){
			return path.get( index );
		}
		public String find( Predicate<String> test ) {

			for( String part : path ) {

				E.rr( part );

				if( test.test( part ) ) return part;
			}
			return null;
		}
		public String findBackwards( Predicate<String> test ) {

			for( int i = path.size()-1; i >= 0; i-- ) {

				String part = path.get( i );
				if( test.test( part ) ) return part;
			}
			return null;
		}

		public boolean isEndsWithSlash(){
			return endsWithSlash;
		}
		public Path setEndsWithSlash( boolean isEndsWithSlashes ){
			endsWithSlash = isEndsWithSlashes;
			return this;
		}

		public boolean isStartsWithSlash(){
			return startsWithSlash;
		}
		public Path setStartsWithSlash( boolean isStartsWithSlashes ){
			startsWithSlash = isStartsWithSlashes;
			return this;
		}

		public boolean isAbsolute(){
			return isStartsWithSlash();
		}
		public boolean isRelative(){
			return !isAbsolute();
		}

		public String getParent(){
			if( path != null && path.size() > 1 ) return path.get( path.size()-2 );
			return null;
		}

		public String getLast(){
			if( path != null && path.size() > 0 ) return path.getLast();
			return null;
		}
		public Path replaceLast( String part ){

			if( path != null && path.size() > 0 ){
				if( part == null || part.length() == 0 ){
					path.removeLast();
				} else {
					path.set( path.size()-1, part );
				}
			}
			return this;
		}
		public Path addLast( String part ){
			if( part == null || part.length() == 0 ) return this;
			if( path == null ) path = new LinkedList<>();
			path.addLast( part );
			return this;
		}
		public Path addAll( Path path ){
			if( path == null ) return this;
			for( String item : path ){
				addLast( item );
			}
			setEndsWithSlash( path.isEndsWithSlash() );
			return this;
		}
		public Path addAll( String ... path ){
			for( String item : path ){
				addLast( item );
			}
			return this;
		}
		public Path removeLast(){
			if( path != null && path.size() > 0 ) path.removeLast();
			return this;
		}

		public String getFirst(){
			if( path != null && path.size() > 0 ) return path.getFirst();
			return null;
		}
		public Path replaceFirst( String part ){

			if( path != null && path.size() > 0 ){
				if( part != null && part.length() > 0 ){
					path.set( 0, part );
				} else {
					path.removeFirst();
				}
			}
			return this;
		}
		public Path addFirst( String part ){
			if( part == null || part.length() == 0 ) return this;
			if( path == null ) path = new LinkedList<>();
			path.addFirst( part );
			return this;
		}
		public Path removeFirst(){
			if( path != null && path.size() > 0 ) path.removeFirst();
			return this;
		}
		public Path removeFirst( int amount ){
			for( ;amount>0; amount-- ){
				removeFirst();
			}
			return this;
		}
		public Path append( Path path ){
			for( String part : path.path ){
				addLast( part );
			}
			endsWithSlash = path.isEndsWithSlash();
			return this;
		}

		public String getExtension(){
			String lastPart = getLast();
			if( lastPart != null ){
				int pos = lastPart.lastIndexOf( '.' );
				if( pos >= 0 ){
					return lastPart.substring( pos+1 );
				}
			}
			return null;
		}

		public String getLastWithoutExtension(){

			String lastPart = getLast();
			if( lastPart != null ){
				int pos = lastPart.lastIndexOf( '.' );
				if( pos >= 0 ){
					return lastPart.substring( 0, pos );
				}
			}
			return null;
		}

		public MimeTypes getMimeType(){

			if( endsWithSlash ){
				return KnownMimeType.DIR;
			}

			String extension = getExtension();
			if( extension != null ) {

				KnownMimeType type = KnownMimeType.forExtension( extension );

				if( type != null ) {

					return type;

				} else {

					// Use Java. (Doesn't know xhtml for example)
					return new UnknownMimeType( URLConnection.getFileNameMap().getContentTypeFor( getLast() ) );
				}
			}

			return null;
		}

		public List<String> toList(){

			return new ArrayList<>( path );
		}

		public String [] toArray(){

			return path.toArray( new String[ path.size() ] );
		}

		/**
		 * @return slice of path as new Path
		 *
		 * @param index to start the slice from. 0 means complete copy. positive integer remove
		 *        from the left. negative from the right;
		 */
		public Path slice( int index ){

			LinkedList<String> newPath = new LinkedList<>( path );

			while( index > 0 && newPath.size() > 0 ){
				newPath.removeFirst();
				index--;
			}
			while( index < 0 && newPath.size() > 0 ){
				newPath.removeLast();
				index--;
			}

			return new Path( newPath, endsWithSlash );
		}

		@Override
		public String toString(){
			return SuperURLPrinter.Plain.toString( this );
		}

		public String toStringEncode( Encode encode ){
			return printers.get( encode ).toString( this );
		}


		@Override
		public Iterator<String> iterator() {
			return path.iterator();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( endsWithSlash ? 1231 : 1237 );
			result = prime * result + ( ( path == null ) ? 0 : path.hashCode() );
			result = prime * result + ( startsWithSlash ? 1231 : 1237 );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof Path ) ) return false;

			Path other = (Path) obj;

			if( startsWithSlash != other.startsWithSlash ) return false;
			if( endsWithSlash != other.endsWithSlash ) return false;

			if( path == null ) {
				if( other.path != null ) return false;
			} else if( !path.equals( other.path ) ) return false;

			return true;
		}
		public Path clear() {
			path.clear();
			startsWithSlash = false;
			endsWithSlash = false;
			return this;
		}
	}

	public static final class Query implements Iterable<Query.QueryPart> {

		HashMap<String,LinkedList<QueryPart>> forName = new HashMap<>();
		LinkedList<QueryPart> path = new LinkedList<>();

		public Query(){}

		public static Query fromLabel( String key, Label value ){
			return new Query( new QueryPart( key, value.label() ) );
		}
		public static Query fromPart( String key, String value ){
			return new Query( new QueryPart( key, value ) );
		}

		public Query( QueryPart ... parts ){

			addAll( parts );
		}

		public Query( Collection<QueryPart> parts ){

			addAll( parts );
		}

		public Query copy(){

			Query result = new Query();
			for( QueryPart part : path ){
				result.add( part.copy() );
			}
			return result;
		}

		public LinkedList<QueryPart> getPath(){
			return path;
		}
		public void setPath( LinkedList<QueryPart> path ){
			this.path = path;
		}
		public int size(){
			return path.size();
		}
		public boolean has( String key ){
			return forName.containsKey( key );
		}

		public LinkedList<QueryPart> getParts( String key ){
			return forName.get( key );
		}
		public QueryPart getPart( String key ){

			if( forName.containsKey( key ) ){
				LinkedList<QueryPart> parts = forName.get( key );
				if( parts.size() > 0 ){
					return parts.getLast();
				}
			}
			return null;
		}
		public String getValue( String key ){

			QueryPart part = getPart( key );
			if( part != null ) return part.getValue();
			else return null;
		}
		public List<String> getValues( String key ){

			LinkedList<QueryPart> parts = getParts( key );
			if( parts != null ){
    			LinkedList<String> result = new LinkedList<>();
    			for( QueryPart part : parts ){
    				result.addLast( part.getValue() );
    			}
    			return result;
			} else {
				return null;
			}
		}

		public Query addValue( String key, Object value ){

			if( ! forName.containsKey( key ) ){
				forName.put( key, new LinkedList<QueryPart>() );
			}
			String valueAsString = null;
			if( value != null ){
				if( value instanceof Label ){
					valueAsString = ((Label)value).label();
				} else {
					valueAsString = value.toString();
				}
			}
			QueryPart part = new QueryPart( key, valueAsString );
			forName.get( key ).addLast( part );
			path.add( part );
			return this;
		}

		public Query addAll( Map<String,Object> values ){

			if( values != null ) for( Map.Entry<String,Object> entry : values.entrySet() ){
				addValue( entry.getKey(), entry.getValue().toString() );
			}
			return this;
		}

		public Query addAll( QueryPart ... parts ){

			for( QueryPart part : parts ){

				add( part );
			}
			return this;
		}

		public Query addAll( Collection<QueryPart> parts ){

			for( QueryPart part : parts ){

				add( part );
			}
			return this;
		}

		public Query setAll( Map<String,Object> values ){

			if( values != null ) for( Map.Entry<String,Object> entry : values.entrySet() ){
				setValue( entry.getKey(), entry.getValue().toString() );
			}
			return this;
		}
		public Query addAll( Query other ){
			for( QueryPart part : other.getPath() ){
				add( part );
			}
			return this;
		}
		public Query add( QueryPart part ){
			addValue( part.getKey(), part.getValue() );
			return this;
		}

		public Query setValue( String key, Object value ){
			remValue( key );
			addValue( key, value );
			return this;
		}

		public Query remValue( String key ){

			if( forName.containsKey( key ) ){

				LinkedList<QueryPart> parts = forName.get( key );

				for( QueryPart part : parts ){
					path.remove( part );
				}
				forName.remove( key );
			}
			return this;
		}

		@Override
		public String toString(){
			return SuperURLPrinter.Plain.toString( this );
		}

		public String toStringEncode( Encode encode ){
			return printers.get( encode ).toString( this );
		}


		// Compatibility

		public Map<String,String[]> toParameterMap() {

			Map<String,String[]> result = new HashMap<>( forName.size() );

			for( Map.Entry<String,LinkedList<QueryPart>> entry : forName.entrySet() ){

				List<QueryPart> valueList = entry.getValue();
				String [] values = new String[ valueList.size() ];
				int i=0;
				for( QueryPart value : valueList ){
					values[ i ] = value.getValue();
				}

				result.put( entry.getKey(), values );
			}

			return result;
		}

		/*
		 * Built to match request.getParameterNames
		 */
		public Enumeration<String> getParameterNames(){
			Vector<String> result = new Vector<>();
			result.addAll( forName.keySet() );
			return result.elements();
		}
		public String [] getParameterValues( String key ){

			List<QueryPart> parts = forName.get( key );
			ArrayList<String> result = new ArrayList<>( parts.size() );
			for( QueryPart part : parts ){
				result.add( part.getValue() );
			}
			return result.toArray( new String[ result.size() ] );
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( path == null ) ? 0 : path.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof Query ) ) return false;

			Query other = (Query) obj;

			if( path == null ) {
				if( other.path != null ) return false;
			} else if( !path.equals( other.path ) ) return false;

			return true;
		}

		@Override
		public Iterator<QueryPart> iterator() {

			// This one is unmodifyable because modifying only the path breaks things
			return Collections.unmodifiableCollection( path ).iterator();
		}

		public static final class QueryPart {

			String key;
			String value;

			public QueryPart( String key, String value ){
				this.key = key;
				this.value = value;
			}

			public static QueryPart parse( String parseMe, boolean decode ){

				String[] parts = Str.splitFastLimited( parseMe, '=', 2 );

				String key = parts[ 0 ];
				String value = null;
				if( parts.length > 1 ){
    				value = parts[ 1 ];
				}
				if( decode ){
					key = decode( key );
					value = decode( value );
				}
				return new QueryPart( key, value );
			}

			public QueryPart copy(){
				return new QueryPart( key, value );
			}

			public String getKey() {
				return key;
			}
			public void setKey( String key ) {
				this.key = key;
			}

			public String getValue() {
				return value;
			}
			public void setValue( String val ) {
				this.value = val;
			}

			@Override
			public String toString(){
				return key + "=" + value;
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ( ( key == null ) ? 0 : key.hashCode() );
				result = prime * result + ( ( value == null ) ? 0 : value.hashCode() );
				return result;
			}

			@Override
			public boolean equals( Object obj ) {
				if( this == obj ) return true;
				if( obj == null ) return false;
				if( !( obj instanceof QueryPart ) ) return false;

				QueryPart other = (QueryPart) obj;

				if( key == null ) {
					if( other.key != null ) return false;
				} else if( !key.equals( other.key ) ) return false;

				if( value == null ) {
					if( other.value != null ) return false;
				} else if( !value.equals( other.value ) ) return false;

				return true;
			}

		}
	}

	public static final class UserInfo {

		String user;
		String pass;

		public UserInfo(){}

		public UserInfo( String user, String pass ){
			this.user = user;
			this.pass = pass;
		}

		public static UserInfo parse( String parseMe ){
			return parse( parseMe, true );
		}
		public static UserInfo parse( String parseMe, boolean decode ){

			String[] parts = Str.splitFastLimited( parseMe, ':', 2 );

			String user = parts[ 0 ];
			String pass = null;
			if( parts.length > 1 )
				pass = parts[ 1 ];

			if( decode ){
				user = decode( user );
				pass = decode( pass );
			}

			return new UserInfo( user, pass );
		}

		public UserInfo copy() {
			return new UserInfo( user, pass );
		}

		public String getUser() {
			return user;
		}
		public UserInfo setUser( String user ) {
			this.user = user;
			return this;
		}

		public String getPass() {
			return pass;
		}
		public UserInfo setPass( String pass ) {
			this.pass = pass;
			return this;
		}

		@Override
		public String toString(){
			return SuperURLPrinter.Plain.toString( this );
		}

		public String toStringEncode( Encode encode ){
			return printers.get( encode ).toString( this );
		}


		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( pass == null ) ? 0 : pass.hashCode() );
			result = prime * result + ( ( user == null ) ? 0 : user.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof UserInfo ) ) return false;

			UserInfo other = (UserInfo) obj;

			if( pass == null ) {
				if( other.pass != null ) return false;
			} else if( !pass.equals( other.pass ) ) return false;

			if( user == null ) {
				if( other.user != null ) return false;
			} else if( !user.equals( other.user ) ) return false;

			return true;
		}

	}


	/* ***************************************************
	 * BUILDER
	 ************************************************** */

	private static final SuperURLBuilder_String BUILDER_STRING = SuperURLBuilders.fromString();
	private static final SuperURLBuilder_Request BUILDER_REQUEST = SuperURLBuilders.fromRequest();
	private static final SuperURLBuilder_URI BUILDER_URI = SuperURLBuilders.fromURI();
	private static final SuperURLBuilder_URL BUILDER_URL = SuperURLBuilders.fromURL();
	private static final SuperURLBuilder_Copy BUILDER_SHALLOW_COPY = SuperURLBuilders.fromSuperURL().usingDeepCopy( false );
	private static final SuperURLBuilder_Copy BUILDER_DEEP_COPY = SuperURLBuilders.fromSuperURL().usingDeepCopy( true );

	public static SuperURL fromString( String parseMe ){
		return BUILDER_STRING.build( parseMe );
	}

	public static SuperURL fromRequest( HttpServletRequest request ){
		return BUILDER_REQUEST.build( request );
	}

	public static SuperURL fromURI( URI uri ){
		return BUILDER_URI.build( uri );
	}

	public static SuperURL fromURL( URL uri ){
		return BUILDER_URL.build( uri );
	}

	public static SuperURL copyShallow( SuperURL other ){
		return BUILDER_SHALLOW_COPY.build( other );
	}

	public static SuperURL copyDeep( SuperURL other ){
		return BUILDER_DEEP_COPY.build( other );
	}

}