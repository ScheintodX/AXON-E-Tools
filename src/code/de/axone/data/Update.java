package de.axone.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Update one object with the data of another object
 * 
 * @author flo
 */
public abstract class Update {

	/**
	 * Update 'dst' collection so that it equals 'src' collection
	 * 
	 * In contrasst to deleting and recreating the colletion this leaves
	 * unchanged members untouched and copys the content of on 'src' member
	 * into one 'dst' member.
	 * 
	 * This ignores order
	 * 
	 * Uses <code>Updated</code> 
	 * 
	 * @param updater to use
	 * @param dst destination collection
	 * @param src source collection
	 */
	public static <T> void collection( Updater<T> updater, Collection<T> dst, Collection<T> src ){
		
		List<T> srcTs = new ArrayList<>( src );
		List<T> delTs = new LinkedList<>();
		
		for( T d : dst ){
			
			T s;
			// Find in src
			if( ( s = updater.find( srcTs, d  ) ) != null ){
				
				// Update if needed
				if( ! updater.equals( d, s ) ){
					// E.rr( "upd: ", s );
					updater.update( d, s );
				}
				// Remove found from source
				//E.rr( "rem: ", s );
				srcTs.remove( s );
				
			} else {
				// If not found in source mark for deletion
				//E.rr( "add: ", d );
				delTs.add( d );
			}
		}
		// Delete market
		for( T d : delTs ){
			// E.rr( "del: ", d );
			dst.remove( d );
		}
		// Copy
		for( T s : srcTs ){
			// E.rr( "cpy: ", s );
			dst.add( updater.copy( s ) );
		}
	}
	
	public static void list( Collection<String> dst, Collection<String> src ){
		
		List<String> add = new LinkedList<>();
		List<String> rem = new LinkedList<>();
		
		for( String s : src ){
			if( ! dst.contains( s ) ) add.add( s );
		}
		for( String d : dst ){
			if( ! src.contains( d ) ) rem.add( d );
		}
		
		dst.removeAll( rem );
		dst.addAll( add );
	}
	
	public interface Updater<T> {
		
		public T find( Collection<T> col, T member );
		public boolean equals( T dst, T src );
		public void update( T dst, T src );
		public T copy( T src );
	}
	
	public abstract static class AbstractListEqualsUpdater<T> implements Updater<T> {
		
		@Override
		public T find( Collection<T> col, T member ){
			
			for( T t : col ){
				
				if( equals( t, member ) )
					return t;
			}
			return null;
		}
	}
	
}
