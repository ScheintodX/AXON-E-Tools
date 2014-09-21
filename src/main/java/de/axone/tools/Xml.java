package de.axone.tools;

public abstract class Xml {

	/**
	 * Encode string using xml entities
	 *
	 * @param xmlText to encode
	 * @return the encoded text
	 * @deprecated Use HtmlEncoder
	 */
	@Deprecated
	public static String encodeText( String xmlText ){

		if( xmlText == null ) return null;

		return xmlText
			.replace( "&", "&amp;" )
			.replace( "<", "&lt;" )
		;
	}

	/**
	 * Encode string using xml entities
	 *
	 * @param xmlText to encode
	 * @return the encoded text
	 * @deprecated Use HtmlEncoder
	 */
	@Deprecated
	public static String encodeAttr( String xmlText ){

		if( xmlText == null ) return null;

		return xmlText
			.replace( "&", "&amp;" )
			.replace( "<", "&lt;" )
			.replace( "\"", "&quot;" )
		;

		//&apos;
	}
}
