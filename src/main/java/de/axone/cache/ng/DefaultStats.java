package de.axone.cache.ng;

import java.util.Formatter;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultStats implements CacheNG.AutomaticClient.Stats {

	private AtomicLong accesses = new AtomicLong(), hits = new AtomicLong();
	private CacheNG.AutomaticClient<?,?> cache;
	
	public DefaultStats( CacheNG.AutomaticClient<?,?> cache ){
		this.cache = cache;
	}
	
	@Override
	public void hit(){
		accesses.incrementAndGet();
		hits.incrementAndGet();
	}
	
	@Override
	public void miss(){
		accesses.incrementAndGet();
	}
	
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		@SuppressWarnings( "resource" )
		Formatter format = new Formatter( result );
		long hits = this.hits.get();
		long accesses = this.accesses.get();
		format.format( "Ratio: %d/%d hits = %.1f%% | Full: %d/%d entries = %.1f%%",
				hits, accesses, 100.0*hits/accesses,
				cache.size(), cache.capacity(), 100.0*cache.size()/cache.capacity() );
		return result.toString();
	}
}
