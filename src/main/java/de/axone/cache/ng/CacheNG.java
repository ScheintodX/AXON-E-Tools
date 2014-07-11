package de.axone.cache.ng;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Mögliche Cache-Arten nach Key
 * 
 * TID
 *   -> TreeItem
 *   -> L:AID : Articles
 *   -> L:AID : TopSeller
 * [ -> L:Comments z.B. neueste ]
 * [ -> L:Tags ] <-Articles
 * [ -> M:Parameters ] <-Articles
 *   -> L:PID : Pages
 * 
 * Top (tranlates to List<TID>)
 *   s. o. ausser TreeItems, Pages
 * 
 * AID
 *   -> Article
 *   -> L:Comments / Rating
 *   -> Bestand
 *   -> XREF: XSell: L:Articles
 * 
 * Article beinhaltet schon: (deswegen nicht f. AID)
 *   -> L:Para
 *   -> L:Tags
 * 
 * Sonstiges:
 *   
 * Query:
 * * para.name
 * * tag
 * * DESC.x
 * * TID
 * * Top
 *     -> alle L:Article
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
 * invalidate( tid=123 )-> invalidate( root )
 * genau wie
 * invalidate( para.name=blah )->invalidate( root )
 * invalidate( tag=bier )->invalidate( root )
 * 
 * XRef
 *		ART_XSELL, ART_TOP,
 *		TREE_VIEWS, ART_VIEWS,
 *		TREE_SEARCH, ART_SEARCH,
 *		ART_USER, TREE_USER
 * 
 * Templates:
 *   File -> Pair<FileWatcher,DataHolder>
 *   URL -> Pair<HttpWatcher,DataHolder>
 *   .
 *   @see WebTemplateFactory
 * 
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
 *  WebTemplate -> register( Article:123 )
 *              -> register( 
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
 * TID->TreeItem
 * TID->L:AID
 * TID->L:AID(Topseller)
 * TID->L:Comments
 * TID->L:Comments(Newest)
 * TID->L:Tags
 * TID->M:Parameters (???)
 * TID->L:PID
 *  
 * AID->Article
 * AID->L:Comments
 * 
 * 
 * 
 * 
 * @author flo
 * 
 */
public interface CacheNG {
	
	/**
	 * Identifies a realm for the backend
	 * 
	 * @author flo
	 */
	public interface Realm {
		
		public String realm();
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
		 * Tells if the object identified by key exists
		 * 
		 * @param key
		 * @return
		 */
		public boolean isCached( K key );
		
		/**
		 * Remove the entry identified by key
		 * 
		 * @param key
		 */
		public void invalidate( K key );
		
		/**
		 * Put the given object in the cache
		 * 
		 * @param key
		 * @param object
		 */
		//public void putEntry( K key, Entry<O> entry );
		
		/**
		 * Put the given entry in the cache
		 * 
		 * @param key
		 * @param object
		 */
		public void put( K key, O object );
		
		/**
		 * Clear the complete cache
		 */
		public void invalidateAll();
		
		/**
		 * Return the used size of the cache
		 * 
		 * @return the size in number of entries
		 */
		public int size();
		
		/**
		 * Return the available capacity of the cache
		 * 
		 * @return the capacity in number of entries or -1 for infinite
		 */
		public int capacity();
		
		/**
		 * Get some meaningful information. 
		 * 
		 * (At least the size should be returned)
		 * 
		 * @return
		 */
		public String info();
		
		/**
		 * Implements more of Map interface for direct access
		 * 
		 * This is additional because we cannot expect distributed caches
		 * to have a keyset fast (if at all) available.
		 * 
		 * @author flo
		 *
		 * @param <K>
		 * @param <V>
		 */
		public interface Direct<K,V> extends Cache<K,V> {
			
			/**
			 * @see java.util.Map#keySet()
			 * @return A set of all keys
			 */
		    Set<K> keySet();
		    
		    /**
			 * @see java.util.Map#values()
		     * @return A collection of all values
		     */
		    Iterable<V> values();
		}
		
		
		/**
		 * One cache entry
		 * 
		 * @author flo
		 *
		 * @param <O>
		 */
		public interface Entry<O> {
			
			/**
			 * The entries data
			 * 
			 * @return
			 */
			public O data();
			
			/**
			 * The entries creation time
			 * 
			 * @return
			 */
			public long creation();
		}
	
		/**
		 * Can be implemented optionally and provides additional runtime information
		 * for this cache
		 * 
		 * @author flo
		 */
		public interface Watched {
			
			public double ratio();
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
	public interface AutomaticClient<K,O> extends 
			CacheEventListener<K>, CacheEventProvider<K> {
		
		/**
		 * Get one entry from cache. If the entry is not cached try
		 * to get it using the Accessor
		 * 
		 * @param key
		 * @return
		 */
		public O fetch( K key, Accessor<K,O> accessor );
		
		
		/**
		 * Get a bunch of entries from the cache. If the entries are
		 * not cached try to fetch them using the Accessor.
		 * 
		 * @param keys
		 * @param accessor
		 * @return
		 */
		public Map<K,O> fetch( Collection<K> keys, Accessor<K,O> accessor );
		
		
		/**
		 * Returns true if this entry is already stored.
		 * 
		 * Dues not try to fetch from Accessor
		 * 
		 * @param key
		 * @return
		 */
		public boolean isCached( K key );
		
		/**
		 * Remove this entry from the cache.
		 * 
		 * @param key
		 */
		public void invalidate( K key );
		
		/**
		 * Removes all entries from the cache within a given timespan
		 * 
		 * This method can be used to avoid heavy backend load if all
		 * entries where removed at once.
		 * 
		 * @param milliSeconds
		 */
		public void invalidateAllWithin( int milliSeconds );
		
		
		public abstract int size();
		
		public abstract int capacity();

		public abstract void invalidateAll();

		public abstract Stats stats();
		
		
		public interface Stats {
			
			void hit();
			void miss();
		}
		
	}
	
	/**
	 * Accesses the Backend and get one value
	 * 
	 * @author flo
	 *
	 * @param <K> Key-Type
	 * @param <O> Value-Type
	 */
	public interface Accessor<K,O> {
		
		/**
		 * Fetch a single entry
		 * 
		 * @param key
		 * @return
		 */
		public O fetch( K key );
		
		/**
		 * Fetch multiple entries at once.
		 * 
		 * Frequently this is more efficient than fetching single values
		 * 
		 * @param keys
		 * @return
		 */
		public Map<K,O> fetch( Collection<K> keys );
	}
	
	/**
	 * 
	 * @author flo
	 *
	 * @param <K> Key-Type
	 * @param <MV> MultiValue store type
	 * @param <O> Value type
	 */
	public interface BackendAccessor<K,O> extends Cache<K,O> {
		// NOP
	}
	
	/**
	 * Manages cache invalidation.
	 * 
	 * @author flo
	 *
	 * @param <K> Key-type
	 * @param <O> Value-type
	 */
	public interface InvalidationManager<K,O> {
		
		public boolean isValid( K key, Cache.Entry<O> value );
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
		
		void notifyListeners( K key );
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
	}
	
	
	/**
	 * Connects two caches and translates invalidation events
	 * 
	 * @author flo
	 *
	 * @param <S> Source-type
	 * @param <T> Target-type
	 */
	public abstract class CacheBridge<S,T> implements CacheEventListener<S> {
		
		private final CacheEventListener<T> target;
		
		public CacheBridge( CacheEventListener<T> target ){
			this.target = target;
		}

		@Override
		public void invalidateEvent( S sourceKey ) {
			
			T targetKey = bridge( sourceKey );
			
			target.invalidateEvent( targetKey );
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
	
}
