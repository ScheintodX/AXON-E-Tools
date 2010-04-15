package de.axone.web.encoding;


/**
 * Encoding escapes html entities: &lt; &gt; &amp;
 * 
 * @author flo
 */
public class HtmlEncoder extends XmlEncoder {
	
	private HtmlEncoder(){
		super();
	}
	private static final HtmlEncoder instance = new HtmlEncoder();
	public static HtmlEncoder instance(){
		return instance;
	}
}
