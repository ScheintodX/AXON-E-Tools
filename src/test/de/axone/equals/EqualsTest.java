package de.axone.equals;

import static org.testng.Assert.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.testng.annotations.Test;

import de.axone.equals.Equals.Synchronizable;
import de.axone.equals.EqualsClass.Select;
import de.axone.equals.EqualsClass.WorkOn;

@Test( groups="tools.equals" )
public class EqualsTest {
	
	private O o1 = new O( 1, "s1", new X( "x1" ), true, "2.0" );
	private O o11 = new O( 1, "s1", new X( "x1" ), true, "2.0" );
	
	private O o2 = new O( 2, "s1", new X( "x1" ), true, "2.0" );
	private O o3 = new O( 1, "s2", new X( "x1" ), true, "2.0" );
	private O o4 = new O( 1, "s2", new X( "x2" ), true, "2.0" );
	private O o5 = new O( 1, "s2", new X( "x2" ), false, "2.0" );
	private O o6 = new O( 1, "s2", new X( "x2" ), true, "2.00" );
	
	private O o20 = new O( 0, "s1", new X( "x1" ), true, "2.0" );
	private O o30 = new O( 1, null, new X( "x1" ), true, "2.0" );
	private O o40 = new O( 1, "s1", null,          true, "2.0" );
	private O o50 = new O( 1, "s1", new X( "x1" ), true, null );
		
	// This method tests, that EqualsBuilder from Commons uses equals on
	// BigDecimal to compare to BigDecimals. Note that this was changed
	// in the past and could change again.
	public void testCommonsForBigDecimalHandling(){
		
		BigDecimal a = new BigDecimal( "2.0" );
		BigDecimal a2 = new BigDecimal( 2 ).setScale( 1 );
		BigDecimal b = new BigDecimal( "2.00" );
		
		assertEquals( a.scale(), 1 );
		assertEquals( a.precision(), 2 );
		
		assertEquals( b.scale(), 2 );
		assertEquals( b.precision(), 3 );
		
		// Test equals direct on BigDecimal
		assertTrue( a.equals( a2 ) );
		assertTrue( a2.equals( a ) );
		assertFalse( a.equals( b ) );
		assertFalse( b.equals( a ) ); //reverse
		
		// Test TestNGs equals
		assertEquals( a, a2 );
		assertEquals( a2, a );
		
		try {
			assertEquals( a, b );
			assertTrue( false );
		} catch( AssertionError e ){
			assertTrue( true );
		}
		
		assertTrue( a.compareTo( a2 ) == 0 );
		assertTrue( a.compareTo( b ) == 0 );
		assertTrue( b.compareTo( a ) == 0 ); //reverse
		
		assertTrue( a.hashCode() == a2.hashCode() );
		assertFalse( a.hashCode() == b.hashCode() );
		
		// The test itself
		EqualsBuilder eb = new EqualsBuilder();
		eb.append( a, b );
		assertFalse( eb.isEquals() );
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
		assertFalse( o1.equals( o6 ) );
		
		assertFalse( o1.equals( o20 ) );
		assertFalse( o1.equals( o30 ) );
		assertFalse( o1.equals( o40 ) );
		assertFalse( o1.equals( o50 ) );
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
		assertFalse( o1.hashCode() == o6.hashCode() ); // BigDecimals scale affect hashCode
		
		assertFalse( o1.hashCode() == o20.hashCode() );
		assertFalse( o1.hashCode() == o30.hashCode() );
		assertFalse( o1.hashCode() == o40.hashCode() );
		assertFalse( o1.hashCode() == o50.hashCode() );
	}
	
	public void testSynchronize(){
		
		O o = new O( 0, null, null, false, null );
		
		assertEquals( Equals.synchronize( o, o1 ), o1 );
		assertEquals( Equals.synchronize( o, o11 ), o11 );
		
		assertEquals( Equals.synchronize( o, o2 ), o2 );
		assertEquals( Equals.synchronize( o, o3 ), o3 );
		assertEquals( Equals.synchronize( o, o4 ), o4 );
		assertEquals( Equals.synchronize( o, o5 ), o5 );
		assertEquals( Equals.synchronize( o, o6 ), o6 );
		
		assertEquals( Equals.synchronize( o, o20 ), o20 );
		assertEquals( Equals.synchronize( o, o30 ), o30 );
		assertEquals( Equals.synchronize( o, o40 ), o40 );
		assertEquals( Equals.synchronize( o, o50 ), o50 );
		
	}
	
	@SuppressWarnings( "unused" )
	@EqualsClass( workOn = WorkOn.METHODS )
	private static class O implements Synchronizable {
		
		private int i;
		private String s;
		private X x;
		private boolean b;
		private BigDecimal bd;
		private Set<Object> set;
		private List<Object> list;
		private Map<Object,Object> map;
		
		private double ignore = Math.random();
		
		O( int i, String s, X x, boolean b, String bd ){
			
			this.i=i;
			this.s=s;
			this.x=x;
			this.b=b;
			this.bd=(bd==null)?null:new BigDecimal( bd );
			
			List<Object> setList = new LinkedList<Object>();
			setList.add( i );
			if( s!=null )setList.add( s );
			if( x!=null )setList.add( x );
			setList.add( b );
			Collections.shuffle( setList );
			this.set = new HashSet<Object>( setList );
			
			list = new LinkedList<Object>();
			list.add( i );
			if( s!=null )list.add( s );
			if( x!=null )list.add( x );
			list.add( b );
			
			map = new HashMap<Object,Object>();
			map.put( "i", i );
			map.put( "s", s );
			map.put( "x", x );
			map.put( "b", b );
			map.put( "bd", bd );
		}
		
		public int getInt(){ return i; }
		public String getString(){ return s; }
		public X getX(){ return x; }
		public boolean isB(){ return b; }
		public Set<Object> getSet(){ return set; }
		public List<Object> getList(){ return list; }
		public BigDecimal getBd(){ return bd; }
		
		public void setInt( int i ){ this.i=i; }
		public void setString( String s ){ this.s=s; }
		public void setX( X x ){ this.x=x; }
		public void setB( boolean b ){ this.b=b; }
		public void setSet( Set<Object> set ){ this.set=set; }
		public void setList( List<Object> list ){ this.list=list; }
		public void setBd( BigDecimal bd ){ this.bd=bd; }
		
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
