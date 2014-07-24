package de.axone.cache.ng;

import java.text.DateFormat;
import java.util.Date;

import de.axone.cache.ng.CacheNG.Cache;
import de.axone.data.Duration;

public class CacheWrapperDelayedInvalidation<K,O> extends CacheWrapper<K,O> {

	private final long timeoutDuration;
	private long timeoutStart;
	
	public CacheWrapperDelayedInvalidation( Cache<K, O> wrapped, long timeoutDuration ) {
		super( wrapped );
		this.timeoutDuration = timeoutDuration;
	}
	
	@Override
	public void invalidateAll( boolean force ) {
		
		if( force ){
			wrapped.invalidateAll( force );
		} else {
			timeoutStart = System.currentTimeMillis();
		}
	}

	private boolean isAlive( K key, CacheNG.Cache.Entry<O> entry ){
		
		// Entry is newer than starting of timeout.
		// This happend regularily if the entry is re-fetched after invalidation
		if( entry.creation() > timeoutStart ) return true;
		
		
		long elapsed = System.currentTimeMillis() - timeoutStart;
		
		// Invalid immediately if > duration
		if( elapsed >= timeoutDuration ) return false;
		
		long stretch = Integer.MAX_VALUE / timeoutDuration;
		
		int random = RandomMapper.positiveInteger( key.hashCode() );
		
		if( stretch * elapsed < random ){
			
			return false;
		}
		
		return true;
	}
	
	@Override
	public Entry<O> fetchEntry( K key ) {
		
		Entry<O> entry = super.fetchEntry( key );
		
		if( entry == null ) return null;
	
		if( ! isAlive( key, entry ) ){
			wrapped.invalidate( key );
			return null;
		}
		
		return entry;
	}

	@Override
	public boolean isCached( K key ) {
		
		// This is quick
		if( ! super.isCached( key ) ) return false;
		
		// Fetch for further checks
		Cache.Entry<O> entry = super.fetchEntry( key );
		
		return entry != null && isAlive( key, entry );
	}

	@Override
	public String info() {
		
		return super.info() + String.format( "[Delayed (%.0fs) %s]",
				Duration.inSeconds( timeoutDuration ), DateFormat.getDateTimeInstance().format( new Date( timeoutStart+timeoutDuration ) ) );
	}

}
