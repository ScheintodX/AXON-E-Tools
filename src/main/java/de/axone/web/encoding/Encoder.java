package de.axone.web.encoding;

public interface Encoder {
	
	public String encode( CharSequence value );
	public Appendable filter( Appendable out );
}
