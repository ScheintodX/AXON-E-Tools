package de.axone.cache.ng;

import java.io.Serializable;

import de.axone.cache.ng.CacheNG.Realm;


public class RealmImpl<K,O> implements CacheNG.Realm<K,O>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected final String realm;
	protected final Hint hint;
	
	public RealmImpl( String realm ){
		this( realm, Hint.STRICT );
	}
	public RealmImpl( String realm, Hint hint ){
		assert realm != null;
		this.realm = realm;
		this.hint = hint;
	}

	@Override
	public String name() {
		return realm;
	}
	@Override
	public String realm() {
		return realm;
	}
	@Override
	public Hint hint() {
		return hint;
	}
	@Override
	public String [] config() {
		return new String[]{ realm };
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( realm == null ) ? 0 : realm.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( !( obj instanceof RealmImpl ) )
			return false;
		RealmImpl<?,?> other = (RealmImpl<?,?>) obj;
		if( realm == null ) {
			if( other.realm != null )
				return false;
		} else if( !realm.equals( other.realm ) )
			return false;
		return true;
	}

	@Override
	public String toString(){ return name() + "(" + hint() + ")"; }

	@Override
	public int compareTo( Realm<?, ?> o ) {
		
		String [] A = config();
		String [] B = o.config();
		
		for( int i=0; i<Math.min( A.length, B.length ); i++ ){
			
			String a = A[ i ];
			String b = B[ i ];
			
			if( a == b ) continue;
			if( a == null ) return -1;
			if( b == null ) return 1;
			
			int result = a.compareTo( b );
			if( result != 0 ) return result;
		}
		return 0;
	}

}
