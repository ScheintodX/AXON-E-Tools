package de.axone.web.encoding;

public class Encoder_PassThrough implements Encoder {

	private Encoder_PassThrough(){}
	private static Encoder_PassThrough instance = new Encoder_PassThrough();
	public static Encoder_PassThrough instance(){
		return instance;
	}
	
	@Override
	public String encode( CharSequence value ) {
		return value.toString();
	}

	@Override
	public Appendable filter( Appendable out ) {
		return out;
	}
	
}
