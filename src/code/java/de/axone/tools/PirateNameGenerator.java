package de.axone.tools;

import java.util.Random;

public class PirateNameGenerator implements NameGenerator {
	
	// Random is synchronized
	private Random rand = new Random();
	
	private boolean umlaut = false;
	private boolean flowery = true;
		
	// male
	private static final String [] titleM = {
			"Schwarzer", "Roter", "Gelber",
			"Kapitän", "Sir", "Magister",
			"Einäugiger", "Blutiger", "Durstiger",
			"Verdammter", "Verfluchter", "Schrecklicher", "Ruchloser", "Furchtloser"
	};
	
	private static final String [] firstM =
			{ "Jack", "Jockel", "Nicolao", "Heinrich", "Gödeke", "Klaus", "Hennig", "Arnd", "Ephraim" };
	
	private static final String [] surP1 = {
			"Rot", "Feuer", "Schwarz", "Blau", "Moos", "Dotter",
			"Silber", "Gold", "Stahl", "Knochen", "Blei",
			"Bier", "Rum",
			"Feuer", "See",
			"Hinke", "Rauh", "Bitter", "Blind",
			"Messer", "Säbel", "Enter", "Schwert",
			"Ein", "Drei", "Dreizehn",
			"Elb", "Rhein", "Weser"
	};
	
	private static final String [] surP2M =
			{ "bart", "messer", "säbel", "finger", "zehe", "arm", "bein", "knochen", "zahn", "haken" };

	// female
	private static final String [] titleF = {
			"Schwarze", "Rote", "Gelbe",
			"Kapitän", "Lady", "Magistratin",
			"Einäugige", "Blutige", "Durstige", "Furchtlose",
			"Verdammte", "Verfluchte", "Schreckliche", "Ruchlose"
	};
	
	private static final String [] firstF =
			{ "Claudia", "Dana", "Katja", "Lysann", "Daniela", "Smilla" };
	
	private static final String [] surP2F =
			{ "sirene", "messer", "säbel", "finger", "zehe", "arm", "bein", "knochen", "zahn", "haken" };

	public static void main( String [] args ){
		
		PirateNameGenerator png = new PirateNameGenerator();
		png.setFlowery( true );
		
		for( int i=0; i<1000; i++ ){
			
			Name name = png.generate( null );
			E.rr( i, name );
			if( name.getSurname().equals( "Elbsirene" )
					&& name.getFirstname().equals( "Smilla" ) ) break;
		}
	}
	
	public void setUmlaut( boolean umlaut ){
		this.umlaut = umlaut;
	}
	public void setFlowery( boolean flowery ){
		this.flowery = flowery;
	}
	
	@Override
	public Name generate( Boolean male ) {
		
		if( male == null ) male = (rand.nextInt( 2 ) == 0 ? true : false);
		
		String title=null,first, sur;
		
		if( male ){
			if( flowery ) title = select( titleM );
			else title = "Herr";
			
			first = select( firstM );
			sur = select( surP1 ) + select( surP2M );
		} else {
			if( flowery ) title = select( titleF );
			else title = "Frau";
			
			first = select( firstF );
			sur = select( surP1 ) + select( surP2F );
		}
		
		if( ! umlaut ){
			title = removeUmlaut( title );
			first = removeUmlaut( first );
			sur = removeUmlaut( sur );
		}
		
		return new Name( male, title, first, sur );
	}
	
	private String select( String [] from ){
		
		return from[ rand.nextInt( from.length ) ];
	}
	private String removeUmlaut( String value ){
		
		if( value == null || value.length() == 0 ) return value;
		
		value = value.replace( "ä", "ae" );
		value = value.replace( "ö", "oe" );
		value = value.replace( "ü", "ue" );
		value = value.replace( "Ä", "Ae" );
		value = value.replace( "Ö", "Oe" );
		value = value.replace( "Ü", "Ue" );
		value = value.replace( "ß", "ss" );
		
		return value;
	}

}
