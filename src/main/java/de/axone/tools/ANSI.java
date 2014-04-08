package de.axone.tools;

public enum ANSI {

	BLACK( "[0;30m" ),
	BLUE( "[0;34m" ),
	GREEN( "[0;32m" ),
	CYAN( "[0;36m" ),
	RED( "[0;31m" ),
	PURPLE( "[0;35m" ),
	BROWN( "[0;33m" ),
	GRAY( "[0;37m" ),
	DARK_GRAY( "[1;30m" ),
	LIGHT_BLUE( "[1;34m" ),
	LIGHT_GREEN( "[1;32m" ),
	LIGHT_CYAN( "[1;36m" ),
	LIGHT_RED( "[1;31m" ),
	LIGHT_PURPLE( "[1;35m" ),
	YELLOW( "[1;33m" ),
	WHITE( "[1;37m" ),
	
	BOLD( "[1m" ),
	NORMAL( "[0m" ),
	UNDERLINE( "[4m" ),
	BLINK( "[5m" ),
	REVERSE( "[7m" ),
	;
	
	private final String code;
	
	ANSI( String code ){
		this.code = code;
	}
	
	public String code(){
		return code;
	}
	
	@Override
	public String toString(){
		return S.ESC + code();
	}
}
