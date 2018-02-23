package de.axone.data;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class StreamMapper<SRC, DST> implements Iterable<DST> {
	
	private final Iterable<SRC> src;
	private final int start,
	                  count;

	public StreamMapper( Iterable<SRC> src, int start, int count ) {
		
		this.src = src;
		this.start = start;
		this.count = count;
	}
	public StreamMapper( Iterable<SRC> src ) {
		
		this( src, -1, -1 );
	}

	@Override
	public Iterator<DST> iterator() {
		
		return dst()
				.iterator()
				;
	}
	
	public Stream<DST> dst() {
		
		Stream<SRC> sStream = StreamSupport.stream( src.spliterator(), false )
				;
		if( start >= 0 ) sStream = sStream.skip( start );
		if( count >= 0 ) sStream = sStream.limit( start );
		
		return sStream
				.map( this::map )
				;
	}
	
	protected abstract DST map( SRC source );

}
