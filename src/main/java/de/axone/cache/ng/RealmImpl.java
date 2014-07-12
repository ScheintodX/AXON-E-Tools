package de.axone.cache.ng;


public class RealmImpl<K,O> implements CacheNG.Realm<K,O> {
	
	private final String realm;
	
	public RealmImpl( String realm ){
		this.realm = realm;
	}

	@Override
	public String realm() {
		return realm;
	}

}
