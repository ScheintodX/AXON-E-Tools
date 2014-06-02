package de.axone.equals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import de.axone.equals.SynchroMapper.DefaultSynchroMapper;

/**
 * This synchromapper is used to handle hibernate runtime replaced lists/sets/maps
 * 
 * This is needed because we have no way of instantiating hibernate's own lists
 * which it puts in place instead of the ones we did spezify.
 * 
 * (Hibernate needs this because it uses them to do lazy loading)
 * 
 * Its a pity there is no way to determine Hash/Tree set / Linked/Array Lists
 * which have different bahaviours
 * because Hibernate has its own Implementations.
 * TODO: Think hard!!!
 * Evtl. können wir mit Testeinträgen testen???
 * 
 * @author flo
 */
public class HibernateSynchroMapper extends DefaultSynchroMapper {

	@Override
	@SuppressWarnings( "rawtypes" )
	public Object emptyInstanceOf( String name, Object object ) {
		
		if( object instanceof List ){
			
			if( object instanceof ArrayList ){
				return new ArrayList();
			} else {
				return new LinkedList();
			}
		} else if( object instanceof Set ){
			
			if( object instanceof TreeSet ){
				return new TreeSet();
			} else {
				// Default
				return new HashSet();
			}
		} else if( object instanceof Map ){
			
			if( object instanceof TreeMap ){
				return new TreeMap();
			} else {
				// Default
				return new HashMap();
			}
		} else {
			
			return super.emptyInstanceOf( name, object );
		}
		
	}
	
}
