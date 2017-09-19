import java.math.BigDecimal;

import de.axone.tools.E;


public class Test {

	public static void main( String [] args ) {
		
		
		E.rr( new BigDecimal( "1.0000" ).stripTrailingZeros() );
	}
}
