package de.axone.cache.ng;

import de.axone.cache.ng.CacheNG.Cache;

public class CacheWrapperWatched<K,O> extends CacheWrapper<K,O> {
	
	private long hits, misses;
	
	public synchronized void hit(){
		hits++;
	}
	public synchronized void miss(){
		misses++;
	}

	public synchronized long hits(){
		return hits;
	}
	public synchronized long misses(){
		return misses;
	}
	public synchronized long accesses(){
		return hits+misses;
	}

	public CacheWrapperWatched( Cache<K, O> wrapped ) {
		
		super( wrapped );
	}

	@Override
	public synchronized double ratio() {
		
		long sum = hits+misses;
		
		if( sum == 0 ) return 0;
		
		return (double)hits/sum;
		
	}
	@Override
	public CacheNG.Cache.Entry<O> fetchEntry( K key ) {
		
		CacheNG.Cache.Entry<O> result = super.fetchEntry( key );
		
		if( result == null ) miss();
		else hit();
		
		return result;
	}
	@Override
	public O fetch( K key ) {
		
		O result = super.fetch( key );
		
		if( result == null ) miss();
		else hit();
		
		return result;
	}
	
	@Override
	public String info() {
		
		return super.info() + String.format( ", Hits: %d/%d = %.2f%%", 
				 hits(), accesses(), Math.round( ratio() *1000 )/10.0 );
	}
	

}
