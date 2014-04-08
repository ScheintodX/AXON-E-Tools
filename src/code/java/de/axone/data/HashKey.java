package de.axone.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.axone.tools.Str;

/*
 * A generic class for an hash-key.
 * 
 * Put stuff in here and use it as key.
 * 
 * This only works if the used objects themselves are usable
 * as hash-key.
 * 
 * The hashing-algorithm may be improved.
 */
public class HashKey {

	private final Set<Object> objects;
	private final int hashCode;
	
	public HashKey( Object ... objects ){
		this.objects = new HashSet<Object>( Arrays.asList( objects ) );
		long code=0;
		for( Object object : this.objects ){
			code = code*27 ^ object.hashCode();
		}
		hashCode = (int)(code^(code>>32));
	}
	
	@Override
	public int hashCode(){
		return hashCode;
	}
	
	@Override
	public boolean equals( Object other ){
		if( !( other instanceof HashKey ) ) return false;
		HashKey o = (HashKey)other;
		return 
			o.objects.containsAll( this.objects ) &&
			this.objects.containsAll( o.objects );
	}
	
	@Override
	public String toString(){
		return Str.join( ", ", objects );
	}
}
