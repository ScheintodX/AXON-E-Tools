package de.axone.tools;

public abstract class Xml {

	/**
	 * Encode string using xml entities
	 *
	 * @param xml
	 * @return
	 * @deprecated Use HtmlEncoder
	 */
	@Deprecated
	public static String encodeText( String xml ){

		if( xml == null ) return null;

		return xml
			.replace( "&", "&amp;" )
			.replace( "<", "&lt;" )
		;
	}

	/**
	 * Encode string using xml entities
	 *
	 * @param xml
	 * @return
	 * @deprecated Use HtmlEncoder
	 */
	@Deprecated
	public static String encodeAttr( String xml ){

		if( xml == null ) return null;

		return xml
			.replace( "&", "&amp;" )
			.replace( "<", "&lt;" )
			.replace( "\"", "&quot;" )
		;

		//&apos;
	}
}
