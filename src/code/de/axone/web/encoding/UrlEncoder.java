package de.axone.web.encoding;

public class UrlEncoder extends AttributeEncoder {

	private static final UrlEncoder instance = new UrlEncoder();
	public static UrlEncoder instance(){
		return instance;
	}
	private UrlEncoder(){}
}
