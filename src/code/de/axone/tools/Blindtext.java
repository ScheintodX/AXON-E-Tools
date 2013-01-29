package de.axone.tools;

import java.util.ArrayList;
import java.util.List;

public class Blindtext {
	
	private static final String [] TOM_SAWYER = (
		"Die Sommerabende waren lang. Noch war's nicht dunkel geworden. Toms Pfeifen ver" +
		"stummte plötzlich. Ein Fremder stand vor ihm, ein Junge, nur vielleicht einen Z" +
		"oll größer als er selbst. Die Erscheinung eines Fremden irgendwelchen Alters od" +
		"er Geschlechtes war ein Ereignis in dem armen, kleinen Städtchen St. Petersburg" +
		". Und dieser Junge war noch dazu sauber gekleidet, – sauber gekleidet an einem " +
		"Wochentage! Das war einfach geradezu unfaßlich, überwältigend! Seine Mütze war " +
		"ein niedliches, zierliches Ding, seine dunkelblaue, dicht zugeknöpfte Tuchjacke" +
		" nett und tadellos: auch die Hosen waren ohne Flecken. Schuhe hatte er an, Schu" +
		"he, und es war doch heute erst Freitag, noch zwei ganze Tage bis zum Sonntag! U" +
		"m den Hals trug er ein seidenes Tuch geschlungen. Er hatte so etwas Zivilisiert" +
		"es, so etwas Städtisches an sich, das Tom in die innerste Seele schnitt. Je meh" +
		"r er dieses Wunder von Eleganz anstarrte, je mehr er die Nase rümpfte über den " +
		"»erbärmlichen Schwindel«, wie er sich innerlich ausdrückte, desto schäbiger und" +
		" ruppiger dünkte ihn seine eigene Ausstattung. Keiner der Jungen sprach. Wenn d" +
		"er eine sich bewegte, bewegte sich auch der andere, aber immer nur seitwärts im" +
		" Kreise herum. So standen sie einander gegenüber, Angesicht zu Angesicht, Auge " +
		"in Auge." ).split( "\\s" );

	public static String words( int num ){
		
		List<String> result = new ArrayList<String>( num );
		
		String [] words = TOM_SAWYER;
		
		for( int i=0; i<num; i++ ){
			
			result.add( words[ i % words.length ] );
		}
		return Str.join( " ", result );
	}
	
	public static void main( String [] args ) throws Exception {

		E.rr( Blindtext.words( 10000 ) );
	}
}
