package de.axone.test;

import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.LongAssert;

import de.axone.tools.E;

public class Bench {
	
	private final String title;
	private final long runs;
	private long lastTime;
	
	public Bench( String title, long runs ) {
		this.title = title;
		this.runs = runs;
	}
	
	public static Bench mark( String title, long runs, Benchable0 c ) {
		return mark( title, runs ).mark( c );
	}
	
	public <T> Bench mark( String title, long runs, Benchable1<T> c, T oneArg ) {
		return mark( title, runs ).mark( c, oneArg );
	}
	
	public static Bench mark( String title, long runs ) {
		
		return new Bench( title, runs );
	}

	public Bench mark( Benchable0 c ) {
		
		long start, end, i;
		
		// warmup
		for( i=0; i<runs; i++ ) c.run();
		
		start = System.nanoTime();
		
		for( i=0; i<runs; i++ ) c.run();
		
		end = System.nanoTime();
		
		this.lastTime = end-start;
		
		return this;
	}
	
	public <T> Bench mark( Benchable1<T> c, T oneArg ) {
		
		long start, end, i;
		
		// warmup
		for( i=0; i<runs; i++ ) c.run( oneArg );
		
		start = System.nanoTime();
		
		for( i=0; i<runs; i++ ) c.run( oneArg );
		
		end = System.nanoTime();
		
		this.lastTime = end-start;
		
		return this;
	}
	
	public Bench print() {
		
		E.rrup( 1, String.format( 
			"%s took %.3fms (%.6fms/run) in %d runs", title, (lastTime/1e6), (float)lastTime/runs/1e6, runs ) );
		
		return this;
	}
	
	public LongAssert time() {
		return new LongAssert( lastTime / 1_000_000 )
				.as( title + " duration" )
				;
	}
	public DoubleAssert average() {
		return new DoubleAssert( lastTime / 1e6 / runs )
				.as( title + " average" )
				;
	}
	
	@FunctionalInterface
	public interface Benchable0 {
		public void run();
	}
	
	@FunctionalInterface
	public interface Benchable1<T> {
		public void run( T arg );
	}
	
}
