package de.axone.web.rest;

import com.fasterxml.jackson.annotation.JsonView;

public class ListResult<T> extends JsonResponseImpl.OK {
	
	private long start;
	private long time;
	private long size;
	private Iterable<T> items;
	
	public ListResult() {}
	
	public ListResult( long time, long size, Iterable<T> items ) {
		this.time = time;
		this.size = size;
		this.items = items;
		this.start = System.currentTimeMillis();
	}
	
	public void time(){
		time = System.currentTimeMillis() - start;
	}
	
	@JsonView( JV.List.class )
	public long getTime() { return time; } 
	public void setTime( long time ) { this.time = time; }

	@JsonView( JV.List.class )
	public long getSize() { return size; } 
	public void setSize( long size ) { this.size = size; }

	@JsonView( JV.List.class )
	public Iterable<T> getItems() { return items; } 
	public void setItems( Iterable<T> items ) { this.items = items; }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( "ListResult [start=" ).append( start )
				.append( ", time=" ).append( time ).append( ", size=" )
				.append( size ).append( ", " );
		if( items != null )
			builder.append( "items=" ).append( items );
		builder.append( "]" );
		return builder.toString();
	}
	
}
