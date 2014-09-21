package de.axone.cache.ng;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mögliche Cache-Arten nach Key
 * 
 * <pre>
 * TID
 *   -* TreeItem
 *   -* L:AID : Articles
 *   -* L:AID : TopSeller
 * [ -* L:Comments z.B. neueste ]
 * [ -* L:Tags ] &lt;-Articles
 * [ -* M:Parameters ] &lt;-Articles
 *   -* L:PID : Pages
 * 
 * Top (tranlates to List&lt;TID&gt;)
 *   s. o. ausser TreeItems, Pages
 * 
 * AID
 *   -* Article
 *   -* L:Comments / Rating
 *   -* Bestand
 *   -* XREF: XSell: L:Articles
 * 
 * Article beinhaltet schon: (deswegen nicht f. AID)
 *   -* L:Para
 *   -* L:Tags
 * 
 * Sonstiges:
 *   
 * Query:
 * * para.name
 * * tag
 * * DESC.x
 * * TID
 * * Top
 *     -* alle L:Article
 * caching verm. nur auf root-query ebene.
 * Die subqueries können aber wieder auf gecachte backends
 * zugreifen wenn nötig.
 * Die *ganz große* Frage hier ist, wie invalidieren.
 * Was wir da brauchen ist invalidation propagation upward
 * Das geht vermutlich sowieso nicht. Besser: Timeout
 * für die Cache-Entries. Der sollte aber "freundlich"#
 * sein und das cache-invalidate möglichst über einen 
 * Zeitrum verteilen.
 * 
 * D.h.
 * ROOT
 *   AND
 *     TID=123
 *     PARA.name=blah
 *     tag=bier
 * 
 * invalidate( tid=123 )-* invalidate( root )
 * genau wie
 * invalidate( para.name=blah )-*invalidate( root )
 * invalidate( tag=bier )-*invalidate( root )
 * 
 * XRef
 *		ART_XSELL, ART_TOP,
 *		TREE_VIEWS, ART_VIEWS,
 *		TREE_SEARCH, ART_SEARCH,
 *		ART_USER, TREE_USER
 * 
 * Templates:
 *   File -* Pair&lt;FileWatcher,DataHolder&gt;
 *   URL -* Pair&lt;HttpWatcher,DataHolder&gt;
 *   .
 * 
 * MISSING:
 * 
 *  * country.csv
 *  V.A. Template caching.
 *  Das geht auch nur, wenn da eine "dynamische" invalidation-Chain aufgebaut werden kann,
 *  d.h. änderungne am template-inhalt den template-cache invalidieren
 *  
 *  
 *  
 *  
 *  IDEE:
 *  
 *  InvalidationListener wird mit dem cachen des ergebnisses
 *  registriert und bei invalidation ereigenissen informiert.
 *  
 *  also
 *  WebTemplate -* register( Article:123 )
 *              -* register( 
 *              
 *              
 *  CacheDataHolder {
 *  
 *  	data : WebTemplate
 *      inval : Article:123 : count_4711
 *      inval : Comment:123 : count_0815
 *  }
 *  
 *  notify evtl. über einen invalidation-counter im object und im gecachten der dann überprüft wird.
 *  
 * More:
 *   * include Passive kann teil des Keys sein.
 *  
 *  
 *  
 * Cache-Names
 * -------------------------------------------------------------------------------
 * TID-*TreeItem
 * TID-*L:AID
 * TID-*L:AID(Topseller)
 * TID-*L:Comments
 * TID-*L:Comments(Newest)
 * TID-*L:Tags
 * TID-*M:Parameters (???)
 * TID-*L:PID
 *  
 * AID-*Article
 * AID-*L:Comments
 * </pre>
 * 
 * @author flo
 */
public abstract class CacheNG {
	
	/**
	 * Identifies a realm for the backend
	 * 
	 * @author flo
	 * @param <K> The key type
	 * @param <O> The value type
	 */
	public interface Realm<K,O> extends Comparable<Realm<?,?>>{
		
		/**
		 * @return String representation of this realm.
		 * Can be used by cache backends to identify the cache
		 */
		public String name();
		
		/**
		 * @return String representaitons of this realm as used
		 * for the config
		 */
		public String[] config();
		
	}
	
	
	// TODO: Evtl. DataHolder o.ä. um mit einem CacheQuery z.B. nach TID
	// gleich noch die restlichen einträge (topselller etc.) zu kommen. Die werden nämlich i.d.R
	// sowieso gebraucht.
	
	// Keep it simple for starters
	
	/**
	 * Interface for a Cache client. A.k.a. an Cache.
	 * 
	 * Note that multiple clients can operate on the same backend cache.
	 * TODO: Ist das so?
	 * 
	 * @author flo
	 *
	 * @param <K> Cache key
	 * @param <O> Cached Object
	 */
	public interface Cache<K,O> {
		
		/**
		 * Get the chache entry identified by key
		 * 
		 * @param key
		 * @return the cached object or null if not found
		 */
		public Entry<O> fetchEntry( K key );
		
		/**
		 * Get the chache object identified by key
		 * 
		 * @param key
		 * @return the cached object or null if not found
		 */
		public O fetch( K key );
		
		/**
		 * @return true if the object identified by key exists
		 * 
		 * @param key
		 */
		public boolean isCached( K key );
		
		/**
		 * Remove the entry identified by key
		 * 
		 * @param key
		 */
		public void invalidate( K key );
		
		/**
		 * Put the given entry in the cache
		 * 
		 * @param key
		 * @param object
		 */
		public void put( K key, O object );
		
		/**
		 * Clear the complete cache
		 * 
		 * TODO: What does 'force' do?
		 * 
		 * @param force 
		 */
		public void invalidateAll( boolean force );
		
		/**
		 * @return the used size of the cache
		 */
		public int size();
		
		/**
		 * @return the available capacity of the cache
		 * or -1 for infinite
		 */
		public int capacity();
		
		/**
		 * @return the usage ratio or -1 if not supported
		 */
		public double ratio();
		
		/**
		 * @return some meaningful information. 
		 * 
		 * (At least the size should be returned)
		 */
		public String info();
		
		/**
		 * Implements more of Map interface for direct access
		 * 
		 * This is additional because we cannot expect distributed caches
		 * to have a keyset fast (if at all) available.
		 * 
		 * @see java.util.Map#keySet()
		 * @return A set of all keys
		 * @throws UnsupportedOperationException if not supported
		 */
	    public Set<K> keySet();
	    
	    /**
	     * @return an Iterable instance for this caches values
	     * 
		 * @see java.util.Map#values()
		 * @throws UnsupportedOperationException if not supported
	     */
	    public Iterable<O> values();
		
		/**
		 * One cache entry
		 * 
		 * @author flo
		 *
		 * @param <O>
		 */
		public interface Entry<O> {
			
			/**
			 * @return The entries data
			 */
			public O data();
			
			/**
			 * @return The entries creation time
			 */
			public long creation();
		}
	
	}
	
	/**
	 * Cache client which automatically fetches it's content
	 * 
	 * @author flo
	 *
	 * @param <K> Cache key
	 * @param <O> Cached Object
	 */
	public interface AutomaticClient<K,O> {
		
		/**
		 * @return Get one entry from cache. If the entry is not cached try
		 * to get it using the Accessor
		 * 
		 * @param key
		 * @param accessor to use to fetch the value
		 */
		public O fetch( K key, SingleValueAccessor<K,O> accessor );
		
		
		/**
		 * @return a bunch of entries from the cache. If the entries are
		 * not cached try to fetch them using the Accessor.
		 * 
		 * @param keys
		 * @param accessor
		 */
		public Map<K,O> fetch( Collection<K> keys, MultiValueAccessor<K,O> accessor );
		
		
		/**
		 * @return true if this entry is already stored.
		 * 
		 * Dues not try to fetch from Accessor
		 * 
		 * @param key
		 */
		public boolean isCached( K key );
		
		/**
		 * Remove this entry from the cache.
		 * 
		 * @param key
		 */
		public void invalidate( K key );
		
		public abstract int size();
		
		public abstract int capacity();

		public abstract void invalidateAll( boolean force );

		public abstract Stats stats();
		
		public interface Stats {
			
			void hit();
			void miss();
		}
		
	}
	
	/**
	 * Accesses the Backend and get multiple values as Map
	 * 
	 * @author flo
	 *
	 * @param <K> Key-Type
	 * @param <O> Value-Type
	 */
	@FunctionalInterface
	public interface MultiValueAccessor<K,O> {
		
		/**
		 * Fetch multiple entries at once.
		 * 
		 * Frequently this is more efficient than fetching single values
		 * 
		 * @param keys
		 * @return the entries found
		 */
		public Map<K,O> fetch( Collection<K> keys ) ;
	}
	
	/**
	 * Accesses the Backend and get one value
	 * 
	 * @author flo
	 *
	 * @param <K> Key-Type
	 * @param <O> Value-Type
	 */
	@FunctionalInterface
	public interface SingleValueAccessor<K,O> {
		
		/**
		 * @return a single entry
		 * 
		 * @param key
		 */
		public O fetch( K key ) ;
	}
	
	/**
	 * Accesses the Backend and get one value
	 * 
	 * @author flo
	 *
	 * @param <K> Key-Type
	 * @param <O> Value-Type
	 */
	public interface UniversalAccessor<K,O> 
			extends SingleValueAccessor<K,O>, MultiValueAccessor<K,O> {
		
		@Override
		default public O fetch( K key ) {
			
			return fetch( Arrays.asList( key ) ).get( key );
		}
		
		@Override
		default public Map<K, O> fetch( Collection<K> keys ) {
			
			return keys.stream()
					.collect( Collectors.toMap(
							key -> key,
							key -> fetch( key ) ) );
		}
	}
	
	public static <K,O> SingleValueAccessor<K,O> multi2single( MultiValueAccessor<K,O> multi ){
		
		return key -> multi.fetch( Arrays.asList( key ) ).get( key );
	}
	
	public static <K,O> MultiValueAccessor<K,O> single2multi( SingleValueAccessor<K,O> single ){
		
		return keys -> keys.stream()
				.collect( Collectors.toMap(
						k -> k,
						k -> single.fetch( k ) ) );
	}
	
	public static <K,O> UniversalAccessor<K,O> multi2universal( MultiValueAccessor<K,O> multi ){
		
		return new UniversalAccessor<K,O>() {
	
			@Override
			public O fetch( K key ) {
				
				return multi.fetch( Arrays.asList( key ) ).get( key );
			}
			
			@Override
			public Map<K, O> fetch( Collection<K> keys ) {
				
				return multi.fetch( keys );
			}
		};
		
	}
	
	public static <K,O> UniversalAccessor<K,O> single2universal( SingleValueAccessor<K,O> single ){
		
		return new UniversalAccessor<K,O>() {
			
			@Override
			public O fetch( K key ) {
				
				return single.fetch( key );
			}
			
			@Override
			public Map<K, O> fetch( Collection<K> keys ) {
				
				return keys.stream()
						.collect( Collectors.toMap(
								k -> k,
								k -> single.fetch( k ) ) );
			}
		};
		
	}
		
	
	/**
	 * 
	 * @author flo
	 *
	 * @param <K> Key-Type
	 * @param <O> Value type
	 */
	public interface BackendAccessor<K,O> extends Cache<K,O> {
		// NOP
	}
	
	/**
	 * Passes cache invalidation events to listeners
	 * 
	 * @author flo
	 *
	 * @param <K>
	 */
	public interface CacheEventProvider<K> {
		
		public void registerListener( CacheEventListener<K> listener );
		
		void listenersInvalidate( K key );
		void listenersInvalidateAll( boolean force );
	}
	
	/**
	 * receives cache invalidation events
	 * 
	 * @author flo
	 *
	 * @param <K>
	 */
	public interface CacheEventListener<K> {
		
		public void invalidateEvent( K key );
		public void invalidateAllEvent( boolean force );
	}
	
	/**
	 * Connects two caches and translates invalidation events
	 * 
	 * @author flo
	 *
	 * @param <S> Source-type
	 * @param <T> Target-type
	 */
	public abstract static class CacheBridge<S,T> implements CacheEventListener<S> {
		
		private final CacheEventListener<T> target;
		
		public CacheBridge( CacheEventListener<T> target ){
			this.target = target;
		}

		@Override
		public void invalidateEvent( S sourceKey ) {
			
			T targetKey = bridge( sourceKey );
			
			target.invalidateEvent( targetKey );
		}
		
		@Override
		public void invalidateAllEvent( boolean force ) {
			
			target.invalidateAllEvent( force );
		}
		
		/**
		 * Convert source key to target key
		 * 
		 * Override this
		 * 
		 * @param key
		 * @return
		 */
		protected abstract T bridge( S key );
		
	}
	
	/**
	 * Helper interface for other classes.
	 * 
	 * <em>Not used here!</em>
	 * 
	 * @author flo
	 */
	public interface HasCacheKey {
		/**
		 * @return an object which will be used as cache key. Note that this object must fullfill the contract from 'CacheKey'
		 */
		public Object cacheKey();
	}
	
	/**
	 * Reminder interface what needs to be implemented in an cache key.
	 * 
	 * <em>Not used here!</em>
	 * 
	 * @author flo
	 */
	public interface CacheKey extends Serializable {
		@Override
		public int hashCode();
		@Override
		public boolean equals( Object other );
	}
	
}
