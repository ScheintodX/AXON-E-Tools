package de.axone.tools;

public interface S {

	public static final String
			_NULL_ = "(-null-)",
			NULL = "NULL";
	
	public static final String EMPTY = "";
	
	public static final String NL = "\n";
	public static final char nl = '\n',
	                         tab = '\t',
	                         quot = '\'',
	                         dquot = '"';
	
	public static final String
			TRUE = "true",
			FALSE = "false";
	
	public static final char esc = (char)27;
	
	public static final char 
			figure_dash = '\u2012',
			en_dash = '\u2013',
			em_dash = '\u2014',
			                         
			figure_space = '\u2007',
			thinsp = '\u2009',
			nbsp = '\u00a0',
			thin_nbsp = '\u202f'
			;
	
	public static final String 
			FIGURE_DASH = ""+figure_dash,
			EN_DASH = ""+en_dash,
			EM_DASH = ""+em_dash,
			                         
			FIGURE_SPACE = ""+figure_space,
			THINSP = ""+thinsp,
			NBSP = ""+nbsp,
			THIN_NBSP = ""+thin_nbsp
			;
	
	public static final String
			HTML_BR = "<br/>\n";
	
	public static final String
			HTML_NBSP = "&nbsp;",
			HTML_THINSP = "&thinsp;",
			HTML_THIN_NBSP = "&nnbsp;",
			HTML_EN_DASH = "&ndash;",
			HTML_EM_DASH = "&mdash;"
			;

}
	