package de.axone.cache.ng;


public class TimeoutClient<K,O> extends ClientWrapper<K,O>{

	private final long maxLifeTime;
	
	TimeoutClient( CacheNG.Client<K,O> backend, long maxLifeTime ){
		super( backend );
		this.maxLifeTime = maxLifeTime;
	}

	@Override
	public CacheNG.Client.Entry<O> fetchEntry( K key ) {
		CacheNG.Client.Entry<O> entry = wrapped.fetchEntry( key );
		if( entry == null ) return null;
		if( ! isAlive( key, entry ) ) return null;
		return entry;
	}
	
	@Override
	public O fetch( K key ) {
		CacheNG.Client.Entry<O> entry = fetchEntry( key );
		if( entry == null ) return null;
		return entry.data();
	}

	@Override
	public boolean isCached( K key ) {
		CacheNG.Client.Entry<O> entry = wrapped.fetchEntry( key );
		if( entry == null ) return false;
		return isAlive( key, entry );
	}
	
	protected boolean isAlive( K key, CacheNG.Client.Entry<O> entry ){
		
		if( maxLifeTime >= 0 ){
			
			long age = System.currentTimeMillis() - entry.creation();
				
			return age < maxLifeTime;
		}
		
		return true;
	}
}
