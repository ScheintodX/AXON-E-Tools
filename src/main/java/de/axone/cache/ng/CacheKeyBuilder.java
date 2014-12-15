package de.axone.cache.ng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

import de.axone.cache.ng.CacheNG.CacheKey;
import de.axone.cache.ng.CacheNG.HasCacheKey;

public final class CacheKeyBuilder implements CacheKey, Serializable, HasCacheKey {
	
	private final Collection<Object> theKey;

	private CacheKeyBuilder( Collection<Object> newKey ){
		this.theKey = newKey;
	}
	private static CacheKeyBuilder unordered(){
		return new CacheKeyBuilder( new HashSet<Object>() );
	}
	public static CacheKeyBuilder unordered( Class<?> clazz ){
		return new CacheKeyBuilder( new HashSet<Object>( 8 ) )
				.add( clazz );
	}
	public static CacheKeyBuilder ordered( Class<?> clazz ){
		return new CacheKeyBuilder( new ArrayList<Object>( 8 ) )
				.add( clazz );
	}
	
	public CacheKeyBuilder add( Object o ){
		theKey.add( o );
		return this;
	}
	public <T> CacheKeyBuilder addMayBeNull( T o, Function<T,?> accessor ){
		if( o == null ) add( null );
		return add( accessor.apply( o ) );
	}
			
	public CacheKeyBuilder addKey( HasCacheKey o ){
		theKey.add( o != null ? o.cacheKey() : null );
		return this;
	}
	public CacheKeyBuilder addAll( Object ... os ){
		for( Object o : os ) add( o );
		return this;
	}
	public CacheKeyBuilder addKeysSorted( HasCacheKey ... os ){
		for( HasCacheKey o : os ) addKey( o );
		return this;
	}
	public CacheKeyBuilder addKeysIgnoreOrder( HasCacheKey ... os ){
		CacheKeyBuilder unordered = unordered();
		for( HasCacheKey o : os ) unordered.addKey( o );
		return addKey( unordered );
	}
	public CacheKeyBuilder addKeysSorted( Iterable<? extends HasCacheKey> os ){
		for( HasCacheKey o : os ) addKey( o );
		return this;
	}
	public CacheKeyBuilder addKeysIgnoreOrder( Iterable<? extends HasCacheKey> os ){
		CacheKeyBuilder unordered = unordered();
		for( HasCacheKey o : os ) unordered.addKey( o );
		return addKey( unordered );
	}
	
	@Override
	public int hashCode() {
		return theKey.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if(  obj.getClass() != this.getClass() ) return false;
		
		CacheKeyBuilder other = (CacheKeyBuilder) obj;
		
		return theKey.equals( other.theKey );
	}
	@Override
	public Object cacheKey() {
		return theKey;
	}
	
	@Override
	public String toString() {
		return String.format( "[0x%08x]", theKey.hashCode() );
	}
}
