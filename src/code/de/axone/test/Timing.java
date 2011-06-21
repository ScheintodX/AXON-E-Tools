package de.axone.test;

import java.util.LinkedHashMap;
import java.util.Map;

import de.axone.tools.Text;

/**
 * Helper class to record timing
 * 
 * This class is thread-safe
 * 
 * @author flo
 */
public class Timing {
	
	// Preserve first call order
	private final Map<Object, Timer> timers = new LinkedHashMap<Object,Timer>();

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
		
		for( Timer t : timers.values() ){
			grand += t.sum;
		}
		
		String result = "";
		for( Object name : timers.keySet() ){
			Timer t = timers.get( name );
			result += String.format( "%s: %dms (%.1f%%)\n", name, t.sum, 100*t.sum/(double)grand );
		}
		result += Text.line( 60 );
		result += "\nSum: " + grand + "ms";
				
		return result;
	}
	
	private static class Timer {
		long start=0, sum=0;
	}
}
