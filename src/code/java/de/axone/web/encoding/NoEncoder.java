package de.axone.web.encoding;

public class NoEncoder implements Encoder {

	private NoEncoder(){}
	private static NoEncoder instance = new NoEncoder();
	public static NoEncoder instance(){
		return instance;
	}
	
	@Override
	public String encode( String value ) {
		return value;
	}

}
