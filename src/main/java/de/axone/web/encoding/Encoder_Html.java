package de.axone.web.encoding;


/**
 * Encoding escapes html entities: &lt; &gt; &amp;
 * 
 * @author flo
 */
public class Encoder_Html extends Encoder_Xml {
	
	private Encoder_Html(){
		super();
	}
	private static final Encoder_Html instance = new Encoder_Html();
	public static Encoder_Html instance(){
		return instance;
	}
}
