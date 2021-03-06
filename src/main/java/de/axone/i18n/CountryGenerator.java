package de.axone.i18n;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

import de.axone.data.AccessibleLine;
import de.axone.tools.E;

public class CountryGenerator {

	private static final String FILENAME = "/home/flo/workspace/EMogul_Data/import/countries.csv";
	
	private static enum FORMAT {
		no,
		iso2, iso3, isoN, ccTld, currency, currency2, postalcode, postalcode_required, tin, eu, eurozone, lang_EN, lang, lang_fb,	
		vat, vshow, sort_EN, name_EN, sort_DE, name_DE, sort_FR, name_FR, sort_ES, name_ES, sort_IT, name_IT
		;
	}
	
	private static final String enumTemplate = 
		"\t%id%( %name%, %iso2%, %iso3%, %isoN%, %ccTld%, %currency%, %postalcode%, %tin% %lang% ),\n"
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
			
			AccessibleLine<FORMAT> aLine = new AccessibleLine<>( parts );
			
			String id;
			String iso2 = aLine.trimmedToNull( FORMAT.iso2 );
			String iso3 = aLine.trimmedToNull( FORMAT.iso3 );
			String isoN = aLine.trimmedToNull( FORMAT.isoN );
			String ccTld = aLine.trimmedToNull( FORMAT.ccTld );
			String currency = aLine.trimmedToNull( FORMAT.currency );
			String postalcode = aLine.trimmedToNull( FORMAT.postalcode );
			String tin = aLine.trimmedToNull( FORMAT.tin );
			String lang = aLine.trimmedToNull( FORMAT.lang );
			String lang_fb = aLine.trimmedToNull( FORMAT.lang_fb );
			String name = aLine.trimmedToNull( FORMAT.name_EN );
			
			if( ccTld != null && ccTld.startsWith( "." ) ) ccTld=ccTld.substring( 1 );
			
			if( isoN != null ) isoN = "" + Integer.parseInt( isoN, 10 );
			
			if( "--".equals( postalcode ) ) postalcode=null;
			if( "--".equals( tin ) ) tin=null;
			
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
			if( currency != null ) currency = "\""+currency+"\"";
			else currency = "null";
			if( postalcode != null ) postalcode = "\""+postalcode+"\"";
			else postalcode = "null";
			if( tin != null ) tin = "\""+tin+"\"";
			else tin = "null";
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
					langB.append(  langPart );
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
			l = l.replaceAll( "%tin%", tin );
			l = l.replaceAll( "%lang%", lang );
			l = l.replaceAll( "%name%", name );
			
			enums.append( l );
		}
		in.close();
		
		File countryFile = new File( "src/main/java/de/axone/i18n/StaticCountries.java" );
		E.rr( countryFile.getAbsolutePath() );
		List<String> countryContent = new LinkedList<String>();
		
		try( BufferedReader cIn = new BufferedReader( new FileReader( countryFile ) ) ) {
			String cLine;
			while( ( cLine = cIn.readLine() ) != null ){
				countryContent.add( cLine );
			}
		}
		
		try( FileWriter out = new FileWriter( countryFile ) ) {
		
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
		}
		E.rr( "DONE" );
	}
}
