package de.axone.cache.ng;

import java.util.concurrent.atomic.AtomicLong;

import de.axone.cache.ng.CacheNG.Cache;

public class CacheWrapperWatched<K,O> extends CacheWrapper<K,O> {
	
	private AtomicLong hits = new AtomicLong(),
	                   misses = new AtomicLong()
	                   ;
	
	public void hit(){
		hits.incrementAndGet();
	}
	public void miss(){
		misses.incrementAndGet();
	}

	public long hits(){
		return hits.get();
	}
	public long misses(){
		return misses.get();
	}
	public long accesses(){
		return hits.get()+misses.get();
	}

	public CacheWrapperWatched( Cache<K, O> wrapped ) {
		
		super( wrapped );
	}

	@Override
	public double ratio() {
		
		long h = hits.get(), m = misses.get(),
		     sum = h+m;
		
		if( sum == 0 ) return 0;
		
		return (double)h/sum;
		
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
