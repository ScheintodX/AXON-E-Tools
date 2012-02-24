package de.axone.tools;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.camelcase" )
public class CamelCaseTest {

	public enum Cases {
		aaaaaa( "aaaaaa", "aaaaaa" ),
		Bbbbbb( "bbbbbb", "bbbbbb" ),
		cccccC( "ccccc_c", "cccccC" ),
		DdDdDd( "dd_dd_dd", "ddDdDd" ),
		eEeEeE( "e_ee_ee_e", "eEeEeE" ),
		fffFFF( "fff_fff", "fffFff" ),
		GGGggg( "gg_gggg", "ggGggg" ),
		HHhhHH( "h_hhh_hh", "hHhhHh" ),
		iiIIii( "ii_i_iii", "iiIIii" ),
		jj12jj( "jj_12_jj", "jj12Jj" ),
		KK12KK( "kk_12_kk", "kk12Kk" )
		;
		private final String underscored;
		private final String camelCased;
		Cases( String underscored, String camelCased ){
			this.underscored = underscored;
			this.camelCased = camelCased;
		}
		public String underscored(){ return underscored; }
		public String camelCased(){ return camelCased; }
	}
	
	public void testCases(){
		
		for( Cases c : Cases.values() ){
			assertEquals( CamelCase.toUnderscored( c.name() ), c.underscored() );
			assertEquals( CamelCase.toCamelCase( c.underscored(), false ), c.camelCased() );
		}
	}
}
