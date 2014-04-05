package de.axone.i18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import de.axone.tools.E;

public class CountryGenerator {

	private static final String FILENAME = "/home/flo/workspace/EMogul_Data/import/countries.csv";
	
	private static enum FORMAT {
		no, use,	
		iso2, iso3, isoN, ccTld, currency, postalcode, eu, eurozone, lang_EN, lang, lang_fb,	
		vat, vshow, sort_EN, name_EN, sort_DE, name_DE, sort_FR, name_FR
		;
	}
	
	private static final String enumTemplate = 
		"\t%id%( %name%, %iso2%, %iso3%, %isoN%, %ccTld%, %currency%, %postalcode% %lang% ),\n"
	;
	
	public static void main( String [] args ) throws Exception {
		
		String filename;
		
		if( args.length > 1 ){
			filename = args[1];
		} else {
			filename = FILENAME;
		}
		
		File file = new File( filename );
		
		BufferedReader in = new BufferedReader( new FileReader( file ) );
		
		StringBuilder enums = new StringBuilder();
		
		int c=0;
		String line;
		while( ( line = in.readLine() ) != null ){
			
			if( c++ == 0 ) continue;
			
			String [] parts = line.split(";");
			
			for( int i=0; i<parts.length; i++ ){
				
				String part = parts[ i ];
				if( part.startsWith("\"") && part.endsWith("\"") ){
					parts[ i ] = part.substring( 1, part.length()-1 );
				}
			}
			
			String id;
			String iso2 = parts[ FORMAT.iso2.ordinal() ].trim();
			String iso3 = parts[ FORMAT.iso3.ordinal() ].trim();
			String isoN = parts[ FORMAT.isoN.ordinal() ].trim();
			String ccTld = parts[ FORMAT.ccTld.ordinal() ].trim();
			String currency = parts[ FORMAT.currency.ordinal() ].trim();
			String postalcode = parts[ FORMAT.postalcode.ordinal() ].trim();
			String lang = parts[ FORMAT.lang.ordinal() ].trim();
			String lang_fb = parts[ FORMAT.lang_fb.ordinal() ].trim();
			String name = parts[ FORMAT.name_EN.ordinal() ].trim();
			
			if( ccTld.startsWith( "." ) ) ccTld=ccTld.substring( 1 );
			
			isoN = "" + Integer.parseInt( isoN, 10 );
			
			if( iso2.length() == 0 ) iso2=null;
			if( iso3.length() == 0 ) iso3=null;
			if( isoN.length() == 0 ) isoN=null;
			if( ccTld.length() == 0 ) ccTld=null;
			if( currency.length() == 0 ) currency=null;
			if( postalcode.length() == 0 ) postalcode=null;
			if( "--".equals( postalcode ) ) postalcode=null;
			if( lang.length() == 0 ) lang=null;
			if( lang_fb.length() == 0 ) lang_fb=null;
			if( name.length() == 0 ) name=null;
			
			if( lang_fb != null ){
				if( lang != null ){
					lang += ","+lang_fb;
				} else {
					lang = lang_fb;
				}
			}
			
			if( iso2 != null ) iso2 = iso2.toUpperCase();
			id = iso2;
			
			if( iso2 != null ) iso2 = "\""+iso2+"\"";
			else iso2 = "null";
			
			if( iso3 != null ) iso3 = "\""+iso3+"\"";
			else iso3 = "null";
			
			if( isoN == null ) isoN = "null";
			
			if( ccTld != null ) ccTld = "\""+ccTld+"\"";
			else ccTld = "null";
			if( currency != null ) currency = "Currency.getInstance( \""+currency+"\" )";
			else currency = "null";
			if( postalcode != null ) postalcode = "\""+postalcode+"\"";
			else postalcode = "null";
			if( lang == null ) lang = "";
			else {
				String [] langParts = lang.split( "," );
				StringBuilder langB = new StringBuilder();
				//lang = ", ";
				langB.append( ", " );
				boolean first = true;
				for( String langPart : langParts ){
					if( first ) first = false;
					else langB.append( ", " );//lang += ", ";
					//lang += "new Locale( \"" + langPart + "\" )";
					langB.append( "new Locale( \"" + langPart + "\" )" );
				}
				lang = langB.toString();
			}
			if( name != null ) name = "\""+name+"\"";
			else name = "null";
			
			String l = enumTemplate;
			l = l.replaceAll( "%id%", id );
			l = l.replaceAll( "%iso2%", iso2 );
			l = l.replaceAll( "%iso3%", iso3 );
			l = l.replaceAll( "%isoN%", isoN );
			l = l.replaceAll( "%ccTld%", ccTld );
			l = l.replaceAll( "%currency%", currency );
			l = l.replaceAll( "%postalcode%", postalcode );
			l = l.replaceAll( "%lang%", lang );
			l = l.replaceAll( "%name%", name );
			
			enums.append( l );
		}
		in.close();
		
		File countryFile = new File( "src/code/de/axone/i18n/Country.java" );
		E.rr( countryFile.getAbsolutePath() );
		List<String> countryContent = new LinkedList<String>();
		BufferedReader cIn = new BufferedReader( new FileReader( countryFile ) );
		String cLine;
		while( ( cLine = cIn.readLine() ) != null ){
			countryContent.add( cLine );
		}
		cIn.close();
		
		FileWriter out = new FileWriter( countryFile );
		
		boolean inGenerated = false;
		for( String l : countryContent ){
			
			if( l.matches( ".*START GENERATED.*" ) ){
				inGenerated = true;
				out.write( l );
				out.write( "\n" );
				out.write( enums.toString() );
			} else if( l.matches( ".*END GENERATED.*" ) ){
				inGenerated = false;
			} 
			if( !inGenerated ){
				out.write( l );
				out.write( "\n" );
			}
		}
		out.close();
	}
}
