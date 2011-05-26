package de.axone.equals;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.equals.EqualsClass.Select;
import de.axone.equals.EqualsClass.WorkOn;

@Test
public class EqualsTest {
	
	O o1 = new O( 1, "s1", new X( "x1" ), true );
	O o11 = new O( 1, "s1", new X( "x1" ), true );
	
	O o2 = new O( 2, "s1", new X( "x1" ), true );
	O o3 = new O( 1, "s2", new X( "x1" ), true );
	O o4 = new O( 1, "s2", new X( "x2" ), true );
	O o5 = new O( 1, "s2", new X( "x2" ), false );
	
	O o20 = new O( 0, "s2", new X( "x2" ), true );
	O o30 = new O( 1, null, new X( "x1" ), true );
	O o40 = new O( 1, "s2", null, true );
		
	public static void main( String [] args ) throws Exception {
		
		new EqualsTest().testAnnotations();
		new EqualsTest().testBuilder();
		new EqualsTest().testHashcode();
	}
	
	public void testAnnotations(){
		
		EqualsClass ec = O.class.getAnnotation( EqualsClass.class );
		assertNotNull( ec );
		assertEquals( ec.select(), Select.ALL );
		assertEquals( ec.workOn(), WorkOn.METHODS );
	}

	public void testBuilder(){
		
		assertTrue( o1.equals( o1 ) );
		assertTrue( o11.equals( o11 ) );
		assertTrue( o1.equals( o11 ) );
		assertTrue( o11.equals( o1 ) );
		
		assertFalse( o1.equals( o2 ) );
		assertFalse( o1.equals( o3 ) );
		assertFalse( o1.equals( o4 ) );
		assertFalse( o1.equals( o5 ) );
		
		assertFalse( o1.equals( o20 ) );
		assertFalse( o1.equals( o30 ) );
		assertFalse( o1.equals( o40 ) );
	}
	
	public void testHashcode(){
		
		assertTrue( o1.hashCode() == o1.hashCode() );
		assertTrue( o11.hashCode() == o11.hashCode() );
		assertTrue( o1.hashCode() == o11.hashCode() );
		assertTrue( o11.hashCode() == o1.hashCode() );
		
		assertFalse( o1.hashCode() == o2.hashCode() );
		assertFalse( o1.hashCode() == o3.hashCode() );
		assertFalse( o1.hashCode() == o4.hashCode() );
		assertFalse( o1.hashCode() == o5.hashCode() );
		
		assertFalse( o1.hashCode() == o20.hashCode() );
		assertFalse( o1.hashCode() == o30.hashCode() );
		assertFalse( o1.hashCode() == o40.hashCode() );
	}
	
	@SuppressWarnings( "unused" )
	@EqualsClass( select = Select.ALL, workOn = WorkOn.METHODS )
	private static class O {
		
		private final int i;
		private final String s;
		private final X x;
		private final boolean b;
		private final double ignore = Math.random();
		
		O( int i, String s, X x, boolean b ){
			this.i=i;
			this.s=s;
			this.x=x;
			this.b=b;
		}
		
		public int getInt(){ return i; }
		public String getString(){ return s; }
		public X getX(){ return x; }
		public boolean isB(){ return b; }
		
		@EqualsField( include=false )
		public double getIgnore1(){ return ignore; };
		public double getignore2(){ return ignore; };
		public double getIgnore3( int x ){ return ignore; }
		
		@Override
		public boolean equals( Object other ){
			return Equals.equals( this, other );
		}
		@Override
		public int hashCode(){
			return Equals.hash( this );
		}
	}
	
	@SuppressWarnings( "unused" )
	@EqualsClass( select = Select.DECLARED, workOn = WorkOn.METHODS )
	private static class X {
		
		private String x;
		private double ignore = Math.random();
		
		X( String x ){ this.x=x; }
		
		@EqualsField( include=true )
		public String getX(){ return x; }
		
		public double getIgnore(){ return ignore; }
		
		@Override
		public boolean equals( Object other ){
			return Equals.equals( this, other );
		}
		@Override
		public int hashCode(){
			return Equals.hash( this );
		}
	}
}
