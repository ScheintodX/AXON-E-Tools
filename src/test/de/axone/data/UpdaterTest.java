package de.axone.data;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.testng.annotations.Test;

import de.axone.data.Update.AbstractListEqualsUpdater;

@Test( groups="tools.updated" )
public class UpdaterTest {

	public void testUpdate() throws Exception {
		
		List<TC> src = Arrays.asList( new TC( 1, "A" ), new TC( 2, "B" ), new TC( 3, "C" ) );
		
		List<TC> dst = new LinkedList<>();
		dst.add( new TC( 1, "A" ) );
		dst.add( new TC( 99, "B" ) );
		dst.add( new TC( 4, "D" ) );
		
		Update.collection( new TestUpdater(), dst, src );
		
		//E.rr( "src", src );
		//E.rr( "dst", dst );
		
		assertEquals( dst.size(), 3 );
		
		assertEquals( 1, dst.get( 0 ).id );
		assertEquals( "A", dst.get( 0 ).value );
		
		assertEquals( 99, dst.get( 1 ).id );
		assertEquals( "B", dst.get( 1 ).value );
		
		assertEquals( -1, dst.get( 2 ).id );
		assertEquals( "C", dst.get( 2 ).value );
	}
	
	private static class TC {
		
		public TC( int id, String value ) {
			this.id = id;
			this.value = value;
		}
		
		int id;
		String value;
		
		@Override
		public String toString() {
			return "(" + id + ",'" + value + "')";
		}
	}
	
	private class TestUpdater extends AbstractListEqualsUpdater<TC> {

		@Override
		public boolean equals( TC dst, TC src ) {
			boolean res = dst.value.equals( src.value );
			//E.rr( "EQ", dst, src, res );
			return res;
		}

		@Override
		public void update( TC dst, TC src ) {
			//E.rr( "UP", dst, src );
			dst.value = src.value;
		}

		@Override
		public TC copy( TC src ) {
			//E.rr( "CP", src );
			return new TC( -1, src.value );
		}
		
	}
}
