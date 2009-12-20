package de.axone.web.encoding;

public class NoEncoder implements Encoder {

	@Override
	public String encode( String value ) {
		return value;
	}

}
