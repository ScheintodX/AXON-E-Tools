package de.axone.test;

import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map;

import de.axone.tools.E;
import de.axone.tools.Text;

/**
 * Helper class to record timing
 * 
 * This class is thread-safe
 * 
 * Usage
 * <pre>
 * Timing t = new Timing();
 * t.start(); // Must be done
 * t.start( "Timing1" ); // Named timing
 * t.stop( "Timing1" ); // cannot cascade
 * t.start( "Timing2" );
 * t.stop( "Timing2" );
 * t.stop(); // Must be done
 * print( t.summary() );
 * </pre>
 * 
 * @author flo
 */
public class Timing {
	
	public static final String TOTAL = "Timing TOTAL Timer";
	
	// Preserve first call order
	private final Map<Object, Timer> timers = new LinkedHashMap<Object,Timer>();
	
	public synchronized void start(){
		start( TOTAL );
	}
	
	public synchronized void start( Object name ){
		Timer t = timers.get( name );
		if( t == null ){
			t = new Timer();
			timers.put( name, t );
		}
		if( t.start != 0 )
			throw new IllegalStateException( "Timer " + name + " was already running" );
		
		t.start = System.currentTimeMillis();
	}
	
	public synchronized void stop(){
		stop( TOTAL );
	}
	
	public synchronized void stop( Object name ){
		
		// remember time here because this is more excact
		long time = System.currentTimeMillis();
		
		Timer t = timers.get( name );
		if( t == null )
			throw new IllegalStateException( "Cannot find timer: " + name );
		
		if( t.start == 0 )
			throw new IllegalStateException( "Timer " + name + " was not running" );
		
		t.sum += time - t.start;
		t.start = 0;
	}
	
	public synchronized long time( Object name ){
		Timer t = timers.get( name );
		if( t == null )
			throw new IllegalStateException( "Cannot find timer: " + name );
		return t.sum;
	}
	
	public synchronized void reset( Object name ){
		
		Timer t = timers.get( name );
		if( t == null )
			throw new IllegalStateException( "Cannot find timer: " + name );
		
		t.start = t.sum = 0;
	}
	
	public synchronized void clear(){
		timers.clear();
	}
	
	public synchronized String summary(){
		
		long grand=0;
		
		Timer total = timers.remove( TOTAL );
		
		for( Object key : timers.keySet() ){
			Timer t = timers.get( key );
			grand += t.sum;
			if( t.start != 0 )
				throw new IllegalStateException( "You forgot to stop timer: " + key );
		}
		
		StringBuilder result = new StringBuilder();
		@SuppressWarnings( "resource" )
		Formatter formatter = new Formatter( result );
		result.append( "Timing:\n" );
		for( Object name : timers.keySet() ){
			Timer t = timers.get( name );
			result.append( Text.left( name.toString(), 30 ) );
			formatter.format( "% 5dms (%4.1f%%/%4.1f%%)\n", t.sum,
					100.0*t.sum/grand, 100.0*t.sum/total.sum );
		}
		result.append( Text.line( '=', 50 ) ).append( '\n' );
		result.append( Text.left( "Sum", 30 ) ); formatter.format( "% 5dms (%4.1f%%)\n", grand, 100.0*grand/total.sum );
		result.append( Text.left( "Diff", 30 ) ); formatter.format( "% 5dms\n", total.sum - grand );
		result.append( Text.line( '-', 50 ) ).append( '\n' );
		result.append( Text.left( "Total", 30 ) ); formatter.format( "% 5dms\n", total.sum );
				
		return result.toString();
	}
	
	private static class Timer {
		long start=0, sum=0;
	}
	
	public static void main( String [] args ) throws Exception {
		
		Timing t = new Timing();
		
		t.start();
		Thread.sleep( 100 );
		t.start( "A" );
		Thread.sleep( 100 );
		t.stop( "A" );
		t.start( "B" );
		Thread.sleep( 200 );
		t.stop( "B" );
		Thread.sleep( 100 );
		t.stop();
		
		E.cho( t.summary() );
		
	}
}
