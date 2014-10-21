package de.axone.data;

import static org.testng.Assert.*;

import java.util.Map;

import org.testng.annotations.Test;

import de.axone.data.MapProxy.Mapping;
import de.axone.data.tupple.PairMap;
import de.axone.data.tupple.TrippleMap;

@Test( groups="tools.pairkey" )
public class TrippleMapTest {
	
	private enum RowKey{ R1, R2, R3 };
	private enum GrpKey{ G1, G2, G3 };
	private enum ColKey{ C1, C2 };

	public void testGeneration() throws Exception {
		
		Object[][] table = new Object[][]{
				{ null,      GrpKey.G1, GrpKey.G1, GrpKey.G2, GrpKey.G3 },
				{ null,      ColKey.C1, ColKey.C2, ColKey.C1, ColKey.C1 },
				{ RowKey.R1,     1,         2,         3,        4      },
				{ RowKey.R2,     5,         6,         7,        8      },
				{ RowKey.R3,     9,        10,        11,       12      }
		};
		
		TrippleMap<RowKey,GrpKey,ColKey,Integer> map = TrippleMap.buildFromTable( Mapping.tree,
				table, RowKey.class, GrpKey.class, ColKey.class, Integer.class );
		
		assertEquals( map.get( RowKey.R1, GrpKey.G1, ColKey.C1 ), i( 1 ) );
		assertEquals( map.get( RowKey.R2, GrpKey.G1, ColKey.C2 ), i( 6 ) );
		assertEquals( map.get( RowKey.R3, GrpKey.G3, ColKey.C1 ), i( 12 ) );
		
		PairMap<GrpKey,ColKey,Integer> row1 = map.getBC( RowKey.R1 );
		assertEquals( row1.size(), 4 );
		assertEquals( row1.get( GrpKey.G1, ColKey.C1 ), i( 1 ) );
		assertEquals( row1.get( GrpKey.G1, ColKey.C2 ), i( 2 ) );
		assertEquals( row1.get( GrpKey.G2, ColKey.C1 ), i( 3 ) );
		assertEquals( row1.get( GrpKey.G3, ColKey.C1 ), i( 4 ) );
		assertFalse( row1.containsKey( GrpKey.G2, ColKey.C2 ) );
		assertFalse( row1.containsKey( GrpKey.G3, ColKey.C2 ) );
		
		PairMap<RowKey,ColKey,Integer> grp1 = map.getAC( GrpKey.G1 );
		assertEquals( grp1.size(), 6 );
		assertEquals( grp1.get( RowKey.R1, ColKey.C1 ), i( 1 ) );
		assertEquals( grp1.get( RowKey.R1, ColKey.C2 ), i( 2 ) );
		assertEquals( grp1.get( RowKey.R2, ColKey.C1 ), i( 5 ) );
		assertEquals( grp1.get( RowKey.R3, ColKey.C1 ), i( 9 ) );
		
		Map<RowKey,Integer> col1 = map.getA( GrpKey.G1, ColKey.C1 );
		assertEquals( col1.size(), 3 );
		assertEquals( col1.get( RowKey.R1 ), i( 1 ) );
		assertEquals( col1.get( RowKey.R2 ), i( 5 ) );
		assertEquals( col1.get( RowKey.R3 ), i( 9 ) );
		
		Map<RowKey,Integer> col3 = map.getA( GrpKey.G2, ColKey.C1 );
		assertEquals( col3.size(), 3 );
		assertEquals( col3.get( RowKey.R1 ), i( 3 ) );
		assertEquals( col3.get( RowKey.R2 ), i( 7 ) );
		assertEquals( col3.get( RowKey.R3 ), i( 11 ) );
	}
	
	private static final Integer i( int i ){ return Integer.valueOf( i ); }
	
}
