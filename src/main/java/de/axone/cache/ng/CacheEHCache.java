package de.axone.cache.ng;

import java.io.File;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import de.axone.cache.Watcher;
import de.axone.cache.ng.CacheNG.Cache;

public class CacheEHCache<K,O>
		extends AbstractEntryCache<K,O>
		implements CacheNG.Cache<K,O>, CacheNG.Cache.Watched {
	
	private final net.sf.ehcache.Cache backend;
	
	public CacheEHCache( net.sf.ehcache.Cache ehCache ){
		this.backend = ehCache;
	}
	
	private Watcher watcher = new Watcher();
	
	public static <I,J> CacheEHCache<I,J>  instance( File tmpDir, String name, long size ){
		
		CacheConfiguration config = new CacheConfiguration();
		config.setName( name );
		config.setDiskPersistent( true );
		config.setDiskStorePath( tmpDir.getAbsolutePath() );
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
	public void put( K key, O value ) {
		
		backend.put( new Element( key, value ) );
	}

	@Override
	public Cache.Entry<O> fetchEntry( K key ) {
		
		Element element = backend.get( key );
		
		if( element == null ){
			watcher.miss();
			return null;
		}
		
		watcher.hit();
		
		@SuppressWarnings( "unchecked" )
		O result = (O)element.getValue();
		
		return new DefaultEntry<O>( result, element.getCreationTime() );
	}

	@Override
	public boolean isCached( K key ) {
		
		boolean result = backend.isKeyInCache( key );
		
		if( result ) watcher.hit();
		else watcher.miss();
		
		return result;
	}

	@Override
	public void invalidate( K key ) {
		
		O result = fetch( key );
		if( result != null ){
			backend.remove( key );
		}
	}

	@Override
	public void invalidateAll() {
		backend.removeAll();
	}

	@Override
	public int size() {
		return backend.getSize();
	}

	@Override
	public int capacity() {
		return -1;
	}

	@Override
	public String info() {
		return backend.toString();
	}
	@Override
	public double ratio() {
		return watcher.ratio();
	}

}
