package de.axone.i18n;

public enum Language {
	
	ar( "ar", "fr" ),
	be( "be", "ru", "en" ),
	bg( "bg", "de", "en" ),
	bs( "bs", "hr", "sr", "de", "en" ),
	ca( "ca", "es", "fr" ),
	cs( "cs", "en" ),
	da( "da", "en" ),
	de( "de" ),
	el( "el", "tr", "en" ),
	en( "en" ),
	es( "es", "en" ),
	et( "et", "en", "de" ),
	fa( "fa", "en" ),
	fi( "fi", "sv", "en" ),
	fo( "fo", "da", "en" ),
	fr( "fr", "it", "en" ),
	hu( "hu", "de", "en" ),
	hr( "hr" ),
	is( "is", "en", "da" ),
	it( "it", "en", "fr" ),
	lb( "lb", "de", "fr" ),
	lt( "lt", "ru", "en" ),
	lv( "lv", "de", "en" ),
	mk( "mk", "en", "it" ),
	mt( "mt", "en" ),
	nl( "nl", "de", "en" ),
	pl( "pl", "ru", "en", "de" ),
	pt( "pt", "sp", "en", "fr" ),
	ro( "ro", "en", "fr", "de", "it", "es" ),
	ru( "ru", "en" ),
	sk( "sk", "en", "de" ),
	sl( "sl", "hr", "en", "de" ),
	sm( "sm", "en" ),
	sq( "sq", "en" ),
	sr( "sr", "de", "en" ),
	sv( "sv", "en", "de", "fr" ),
	tr( "tr" ),
	;
		
	private final String code;
	private final String[] fallback;
	
	Language( String code, String ... fallback ){
		
		this.code = code;
		this.fallback = fallback;
	}
	
	public String code(){ return code; }
	public String [] fallback(){ return fallback; }
}
		