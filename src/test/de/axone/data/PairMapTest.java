package de.axone.data;

import static org.testng.Assert.*;

import java.util.Map;

import org.testng.annotations.Test;

import de.axone.data.MapProxy.Mapping;

@Test( groups="tools.pairkey" )
public class PairMapTest {
	
	private enum RowKey{ R1, R2, R3 };
	private enum ColKey{ C1, C2, C3, C4 };

	public void testGeneration() throws Exception {
		
		Object[][] table = new Object[][]{
				{ null,      ColKey.C1, ColKey.C2, ColKey.C3, ColKey.C4 },
				{ RowKey.R1,     1,         2,         3,        4      },
				{ RowKey.R2,     5,         6,         7,        8      },
				{ RowKey.R3,     9,        10,        11,       12      }
		};
		
		PairMap<RowKey,ColKey,Integer> map = PairMap.buildFromTable( Mapping.tree,
				table, RowKey.class, ColKey.class, Integer.class );
		
		assertEquals( map.get( RowKey.R1, ColKey.C1 ), Integer.valueOf( 1 ) );
		assertEquals( map.get( RowKey.R2, ColKey.C2 ), Integer.valueOf( 6 ) );
		assertEquals( map.get( RowKey.R3, ColKey.C4 ), Integer.valueOf( 12 ) );
		
		// Additional tests
		Map<ColKey,Integer> row1 = map.getRow( RowKey.R1 );
		assertEquals( row1.size(), 4 );
		assertEquals( row1.get( ColKey.C1 ), Integer.valueOf( 1 ) );
		assertEquals( row1.get( ColKey.C4 ), Integer.valueOf( 4 ) );
		
		Map<ColKey,Integer> row2 = map.getRow( RowKey.R2 );
		assertEquals( row2.size(), 4 );
		assertEquals( row2.get( ColKey.C1 ), Integer.valueOf( 5 ) );
		assertEquals( row2.get( ColKey.C4 ), Integer.valueOf( 8 ) );
		
		Map<RowKey,Integer> col1 = map.getCol( ColKey.C1 );
		assertEquals( col1.size(), 3 );
		assertEquals( col1.get( RowKey.R1 ), Integer.valueOf( 1 ) );
		assertEquals( col1.get( RowKey.R3 ), Integer.valueOf( 9 ) );
		
		Map<RowKey,Integer> col4 = map.getCol( ColKey.C4 );
		assertEquals( col4.size(), 3 );
		assertEquals( col4.get( RowKey.R1 ), Integer.valueOf( 4 ) );
		assertEquals( col4.get( RowKey.R3 ), Integer.valueOf( 12 ) );
	}
}
