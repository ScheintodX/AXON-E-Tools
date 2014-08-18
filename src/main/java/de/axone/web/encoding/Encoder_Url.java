package de.axone.web.encoding;

import de.axone.web.SuperURL;
import de.axone.web.SuperURL.FinalEncoding;
import de.axone.web.SuperURLBuilders;
import de.axone.web.SuperURLPrinter;

/**
 * Users SuperURL to print the url.
 * 
 * Final encoding is for Attributes
 * 
 * <em>Dont use but put SuperURL in Holder directly</em>
 * 
 * @author flo
 */
public class Encoder_Url implements Encoder {
	
	private static final SuperURLPrinter printer =
			SuperURLPrinter.MinimalEncoded.finishFor( FinalEncoding.Attribute );

	private static final Encoder_Url instance = new Encoder_Url();
	public static Encoder_Url instance(){
		return instance;
	}
	private Encoder_Url(){}
	
	public static String ENCODE( CharSequence value ) {
		
		SuperURL url = SuperURLBuilders.fromString().build( value );
		
		return printer.toString( url );
	}
	
	@Override
	public String encode( CharSequence value ) {
		return ENCODE( value );
	}
	
	@Override
	public Appendable filter( Appendable out ) {
		
		return new EncodingAppender( this, out );
	}
}
