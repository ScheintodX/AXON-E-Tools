package de.axone.exception;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.ex" )
public class ExTest {

	public void testExUp(){
		
		assertMethod( 0, 0, 0, "except" );
		assertMethod( 1, 0, 0, "except" );
		assertMethod( 1, 0, 1, "except1" );
		assertMethod( 2, 0, 0, "except" );
		assertMethod( 2, 0, 1, "except1" );
		assertMethod( 2, 0, 2, "except2" );
		
		assertMethod( 1, 1, 0, "except1" );
		assertMethod( 2, 1, 0, "except1" );
		assertMethod( 2, 1, 1, "except2" );
		
		assertMethod( 2, 2, 0, "except2" );
		
		try{
			throw Ex.up( new ClassCastException(), 100 );
		} catch( Exception e ){
			assertEquals( e.getClass(), IllegalArgumentException.class );
		}
		
		try{
			throw Ex.up( new ClassCastException(), -1 );
		} catch( Exception e ){
			assertEquals( e.getClass(), ArrayIndexOutOfBoundsException.class );
		}
	}
	
	private void assertMethod( int depth, int backtrace, int pos, String methodName ){
		
		try{
			switch( depth ){
			case 0: except( backtrace ); break;
			case 1: except1( backtrace ); break;
			case 2: except2( backtrace ); break;
			}
		} catch( ClassCastException e ){
			assertEquals( e.getStackTrace()[pos].getMethodName(), methodName );
		}
	}
	
	private void except1( int backtrace ){
		except( backtrace );
	}
	private void except2( int backtrace ){
		except1( backtrace );
	}
	private void except( int backtrace ){
		
			throw Ex.up( new ClassCastException(), backtrace );
	}
}
