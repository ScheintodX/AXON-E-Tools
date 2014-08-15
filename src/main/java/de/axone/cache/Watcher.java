package de.axone.cache;

import de.axone.cache.Cache.Watched;

public class Watcher implements Watched {
	
	private long hits, misses;
	
	public synchronized void hit(){
		hits++;
	}
	public synchronized void miss(){
		misses++;
	}

	@Override
	public synchronized double ratio() {
		
		long sum = hits+misses;
		
		if( sum == 0 ) return 0;
		
		return (double)hits/sum;
		
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

}
