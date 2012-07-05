package de.axone.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

public class CacheEHCache<K,V> implements Cache<K,V> {
	
	private final net.sf.ehcache.Cache backend;
	
	public CacheEHCache( net.sf.ehcache.Cache ehCache ){
		this.backend = ehCache;
	}
	
	public static <I,J> CacheEHCache<I,J>  instance( String name, long size ){
		
		CacheConfiguration config = new CacheConfiguration();
		config.setName( name );
		config.setDiskPersistent( true );
		config.setDiskStorePath( "/tmp/ehcache" );
		config.setMaxEntriesLocalHeap( size );
		CacheManager manager = CacheManager.getInstance();
		
		net.sf.ehcache.Cache newCache = new net.sf.ehcache.Cache( config );
		manager.addCacheIfAbsent( newCache );
		
		return new CacheEHCache<I,J>( newCache );
	}
	public static void shutdown(){
		CacheManager.getInstance().shutdown();
	}

	@Override
	public V put( K key, V value ) {
		
		backend.put( new Element( key, value ) );
		//backend.flush();
		return value;
	}

	@Override
	public V get( Object key ) {
		
		Element element = backend.get( key );
		
		if( element == null ) return null;
		
		@SuppressWarnings( "unchecked" )
		V result = (V)element.getValue();
		return result;
	}

	@Override
	public boolean containsKey( Object key ) {
		
		return backend.isKeyInCache( key );
	}

	@Override
	public V remove( Object key ) {
		
		V result = get( key );
		if( result != null ){
			backend.remove( key );
		}
		return result;
	}

	@Override
	public void clear() {
		backend.removeAll();
	}

	@Override
	public int size() {
		return backend.getSize();
	}

	@Override
	public int capacity() {
		return Integer.MAX_VALUE;
	}

	@Override
	public String info() {
		return backend.toString();
	}

}
