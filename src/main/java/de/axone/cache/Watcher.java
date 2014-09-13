package de.axone.cache;

import java.util.concurrent.atomic.AtomicLong;


public class Watcher implements Watched {
	
	private AtomicLong hits = new AtomicLong(),
	                   misses = new AtomicLong();
	
	public void hit(){
		hits.incrementAndGet();
	}
	public void miss(){
		misses.incrementAndGet();
	}

	@Override
	public double ratio() {
		
		long hits = this.hits.get();
		long misses = this.misses.get();
		
		long sum = hits+misses;
		
		if( sum == 0 ) return 0;
		
		return (double)hits/sum;
		
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
}
