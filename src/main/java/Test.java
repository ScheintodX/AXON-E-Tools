import java.math.BigDecimal;
import java.util.Locale;

import de.axone.tools.E;


public class Test {

	public static void main( String [] args ) {
		
		E.rr( String.format( Locale.GERMANY, "%,.3f", new BigDecimal( "9999.999" ) ) );
	}
}
