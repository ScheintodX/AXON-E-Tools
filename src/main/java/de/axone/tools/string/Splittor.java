package de.axone.tools.string;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SuppressWarnings( "unused" )
public abstract class Splittor {

	public static interface ArraySplittor {
		
		public String [] split( String value );
	}
	
	public static interface ListSplittor {
		
		public List<String> split( String value );
	}
	
	public static interface SetSplittor {
		
		public Set<String> split( String value );
	}
	
	public static interface StreamSplittor {
		
		public Stream<String> split( String value );
	}
	
	public static class SplittorBuilder {
		
		private final Character splitAtChar;
		private final String splitAtString;
		private final Pattern splitAtPattern;
		
		public SplittorBuilder( Character splitAtChar, String splitAtString, Pattern splitAtPattern ) {
			super();
			this.splitAtChar = splitAtChar;
			this.splitAtString = splitAtString;
			this.splitAtPattern = splitAtPattern;
		}
		
		public OnceSplittorBuilder once() {
			return new OnceSplittorBuilder();
		}
		public SplittorBuilder limited( int num ){
			return null;
		}
		public SplittorBuilder trimmed(){
			return null;
		}
		public SplittorBuilder startAt( int num ){
			return null;
		}
		
		public ArraySplittor asArray(){
			return null;
		}
		public ListSplittor asList() {
			return null;
		}
		public SetSplittor asSet() {
			return null;
		}
		public StreamSplittor asStream() {
			return null;
		}
	}
	
	private static class OnceSplittorBuilder {
		
		public ArraySplittor asArray(){
			return null;
		}
		public ListSplittor asList() {
			return new ListSplittorWrapper( asArray() );
		}
		public StreamSplittor asStream() {
			return new StreamSplittorWrapper( asArray() );
		}
		
		public OnceTrimmedSplittorBuilder trimmed(){
			return new OnceTrimmedSplittorBuilder();
		}
	}
	
	private static class OnceTrimmedSplittorBuilder {
		
		public ArraySplittor asArray(){
			return null;
		}
		public ListSplittor asList() {
			return new ListSplittorWrapper( asArray() );
		}
		public StreamSplittor asStream() {
			return new StreamSplittorWrapper( asArray() );
		}
		
	}
	
	private static class StreamSplittorWrapper implements StreamSplittor {
		
		private final ArraySplittor wrapped;

		StreamSplittorWrapper( ArraySplittor wrapped ){
			this.wrapped = wrapped;
		}
		
		@Override
		public Stream<String> split( String value ) {
			return Arrays.stream( wrapped.split( value ) );
		}
		
	}
	
	private static class ListSplittorWrapper implements ListSplittor {
		
		private final ArraySplittor wrapped;

		ListSplittorWrapper( ArraySplittor wrapped ){
			this.wrapped = wrapped;
		}
		
		@Override
		public List<String> split( String value ) {
			return Arrays.asList( wrapped.split( value ) );
		}
		
	}
	
	public static SplittorBuilder by( String splitAt ){
		return new SplittorBuilder( null, splitAt, null );
	}
	public static SplittorBuilder by( char splitAt ){
		return new SplittorBuilder( splitAt, null, null );
	}
	public static SplittorBuilder byPattern( String pattern ){
		return new SplittorBuilder( null, null, Pattern.compile( pattern ) );
	}
	public static SplittorBuilder byWhitespace(){
		return new SplittorBuilder( null, null, null );
	}
	
	public static void main( String [] args ) throws Exception {
		
		String value = "";
		
		Stream<String> s1 = Splittor.by( '/' ).asStream().split( value );
		List<String> l1 =   Splittor.by( "-,-" ).trimmed().asList().split( value );
		String [] a1 =      Splittor.by( "-,-" ).once().trimmed().asArray().split( value );
		Splittor.by( "-,-" ).trimmed().once().asArray();
		Splittor.by( "-,-" ).limited( 5 ).trimmed().asArray();
		
		Splittor.byWhitespace().trimmed().startAt( 1 ).asSet();
		
	}
}
