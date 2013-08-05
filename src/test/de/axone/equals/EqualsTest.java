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

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.testng.annotations.Test;

import de.axone.equals.Equals.Synchronizable;
import de.axone.equals.EqualsClass.Select;
import de.axone.equals.EqualsClass.WorkOn;

@Test( groups="tools.equals" )
public class EqualsTest {
	
	private static final class OO {
		
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
	}

	// @Test( groups="tools.equals" )
	public void verySimpleTest(){
		
		O o = new O( 1, "s1", null, true, null );
		assertNotNull( o.hashCode() );
	}
		
	// This method tests, that EqualsBuilder from Commons uses equals on
	// BigDecimal to compare to BigDecimals. Note that this was changed
	// in the past and could change again.
	// @Test( enabled=false )
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
	
	// @Test( enabled=false )
	public void testAnnotations(){
		
		EqualsClass ec = O.class.getAnnotation( EqualsClass.class );
		assertNotNull( ec );
		assertEquals( ec.select(), Select.ALL );
		assertEquals( ec.workOn(), WorkOn.METHODS );
	}
	
	// @Test( enabled=false )
	public void testBuilder(){
		
		OO O = new OO();
		
		assertTrue( O.o1.equals( O.o1 ) );
		assertTrue( O.o11.equals( O.o11 ) );
		assertTrue( O.o1.equals( O.o11 ) );
		assertTrue( O.o11.equals( O.o1 ) );
		
		assertFalse( O.o1.equals( O.o2 ) );
		assertFalse( O.o1.equals( O.o3 ) );
		assertFalse( O.o1.equals( O.o4 ) );
		assertFalse( O.o1.equals( O.o5 ) );
		assertFalse( O.o1.equals( O.o6 ) );
		
		assertFalse( O.o1.equals( O.o20 ) );
		assertFalse( O.o1.equals( O.o30 ) );
		assertFalse( O.o1.equals( O.o40 ) );
		assertFalse( O.o1.equals( O.o50 ) );
	}
	
	// @Test( enabled=false )
	public void testHashcode(){
		
		OO O = new OO();
		
		assertTrue( O.o1.hashCode() == O.o1.hashCode() );
		assertTrue( O.o11.hashCode() == O.o11.hashCode() );
		assertTrue( O.o1.hashCode() == O.o11.hashCode() );
		assertTrue( O.o11.hashCode() == O.o1.hashCode() );
		
		assertFalse( O.o1.hashCode() == O.o2.hashCode() );
		assertFalse( O.o1.hashCode() == O.o3.hashCode() );
		assertFalse( O.o1.hashCode() == O.o4.hashCode() );
		assertFalse( O.o1.hashCode() == O.o5.hashCode() );
		assertFalse( O.o1.hashCode() == O.o6.hashCode() ); // BigDecimals scale affect hashCode
		
		assertFalse( O.o1.hashCode() == O.o20.hashCode() );
		assertFalse( O.o1.hashCode() == O.o30.hashCode() );
		assertFalse( O.o1.hashCode() == O.o40.hashCode() );
		assertFalse( O.o1.hashCode() == O.o50.hashCode() );
	}
	
	// @Test( enabled=false )
	public void testStrongHashCode(){
		
		OO O = new OO();
		
		assertTrue( O.o1.strongHashCode().equals( O.o1.strongHashCode() ) );
		assertTrue( O.o11.strongHashCode().equals( O.o11.strongHashCode() ) );
		assertTrue( O.o1.strongHashCode().equals( O.o11.strongHashCode() ) );
		assertTrue( O.o11.strongHashCode().equals( O.o1.strongHashCode() ) );
		
		assertFalse( O.o1.strongHashCode().equals( O.o2.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o3.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o4.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o5.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o6.strongHashCode() ) ); // BigDecimals scale affect strongHashCode
		
		assertFalse( O.o1.strongHashCode().equals( O.o20.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o30.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o40.strongHashCode() ) );
		assertFalse( O.o1.strongHashCode().equals( O.o50.strongHashCode() ) );
	}
	
	// @Test( enabled=false )
	public void testSynchronize(){
		
		OO O = new OO();
		
		O o = new O( 0, null, null, false, null );
		
		assertEquals( Equals.synchronize( o, O.o1, null ), O.o1 );
		assertFalse( o.getList() == O.o1.getList() );
		assertFalse( o.getSet() == O.o1.getSet() );
		assertFalse( o.getMap() == O.o1.getMap() );
		assertEquals( Equals.synchronize( o, O.o11, null ), O.o11 );
		
		assertEquals( Equals.synchronize( o, O.o2, null ), O.o2 );
		assertEquals( Equals.synchronize( o, O.o3, null ), O.o3 );
		assertEquals( Equals.synchronize( o, O.o4, null ), O.o4 );
		assertEquals( Equals.synchronize( o, O.o5, null ), O.o5 );
		assertEquals( Equals.synchronize( o, O.o6, null ), O.o6 );
		
		assertEquals( Equals.synchronize( o, O.o20, null ), O.o20 );
		assertEquals( Equals.synchronize( o, O.o30, null ), O.o30 );
		assertEquals( Equals.synchronize( o, O.o40, null ), O.o40 );
		assertEquals( Equals.synchronize( o, O.o50, null ), O.o50 );
		
	}
	
	// Remember to re-enable tests above
	public void testSynchronizeMapper(){
		
		OO O = new OO();
		
		O o = new O( 0, null, null, false, null );
		
		Equals.synchronize( o, O.o1, new TestSynchroMapper() );
		
		assertFalse( o.equals( O.o1 ) );
		assertFalse( o.getSet() == O.o1.getSet() );
		assertFalse( o.getList() == O.o1.getList() );
		assertFalse( o.getMap() == O.o1.getMap() );
		
		assertNotNull( o.getSet() );
		assertNotNull( o.getList() );
		assertNotNull( o.getMap() );
		
		assertEquals( o.getString(), "s1X" );
		
		assertFalse( o.getSet().equals( O.o1.getSet() ) );
		assertTrue( o.getSet().contains( "s1X" ) );
		assertFalse( o.getSet().contains( "s1" ) );
		
		assertFalse( o.getList().equals( O.o1.getList() ) );
		assertTrue( o.getList().contains( "s1X" ) );
		assertFalse( o.getList().contains( "s1" ) );
		
		assertFalse( o.getMap().equals( O.o1.getMap() ) );
		assertEquals( o.getMap().get( "s" ), "s1X" );
		
		O o_ = new O();
		
		Equals.synchronize( o_, O.o1, new TestSynchroMapper() );
		
		assertFalse( o_.equals( O.o1 ) );
		assertFalse( o_.getSet() == O.o1.getSet() );
		assertFalse( o_.getList() == O.o1.getList() );
		assertFalse( o_.getMap() == O.o1.getMap() );
		
		assertNotNull( o_.getSet() );
		assertNotNull( o_.getList() );
		assertNotNull( o_.getMap() );
		
		assertEquals( o_.getString(), "s1X" );
		
		assertFalse( o_.getSet().equals( O.o1.getSet() ) );
		assertTrue( o_.getSet().contains( "s1X" ) );
		assertFalse( o_.getSet().contains( "s1" ) );
		
		assertFalse( o_.getList().equals( O.o1.getList() ) );
		assertTrue( o_.getList().contains( "s1X" ) );
		assertFalse( o_.getList().contains( "s1" ) );
		
		assertFalse( o_.getMap().equals( O.o1.getMap() ) );
		assertEquals( o_.getMap().get( "s" ), "s1X" );
		
	}
	
	static class TestSynchroMapper implements SynchroMapper {

		@Override
		public Object copyOf( Object object ) {
			if( object instanceof String ){
				return ((String)object) + "X";
			}
			return object;
		}

		@Override
		public Object emptyInstanceOf( Object object )
				throws InstantiationException, IllegalAccessException {
			
			return object.getClass().newInstance();
		}
		
	}
	
	@SuppressWarnings( "unused" )
	@EqualsClass( workOn = WorkOn.METHODS )
	private static class O implements Synchronizable<O>, StrongHash {
		
		private int i;
		private String s;
		private X x;
		private boolean b;
		private BigDecimal bd;
		private Set<Object> set;
		private List<Object> list;
		private Map<Object,Object> map;
		
		private double ignore = Math.random();
		
		O(){}
		
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
		public Map<Object,Object> getMap(){ return map; }
		public BigDecimal getBd(){ return bd; }
		
		public void setInt( int i ){ this.i=i; }
		public void setString( String s ){ this.s=s; }
		public void setX( X x ){ this.x=x; }
		public void setB( boolean b ){ this.b=b; }
		public void setSet( Set<Object> set ){ this.set=set; }
		public void setList( List<Object> list ){ this.list=list; }
		public void setMap( Map<Object,Object> map ){ this.map=map; }
		public void setBd( BigDecimal bd ){ this.bd=bd; }
		
		@EqualsField( include=false )
		public double getIgnore1(){ return ignore; };
		public double getignore2(){ return ignore; };
		public double getIgnore3( int x ){ return ignore; }
		
		@Id
		public double getIgnore4(){ return ignore; };
		@Transient
		public double getIgnore5(){ return ignore; };
		
		@Override
		public boolean equals( Object other ){
			return Equals.equals( this, other );
		}
		@Override
		public int hashCode(){
			return Equals.hash( this );
		}
		@Override
		public String strongHashCode(){
			return Equals.strongHashString( this );
		}

		@Override
		public void synchronizeFrom( O other ) {
			Equals.synchronize( this, other, null );
		}
		
		@Override
		public String toString() {
			return "O [i=" + i + ", s=" + s + ", x=" + x + ", b=" + b + ", bd="
					+ bd + ", set=" + set + ", list=" + list + ", map=" + map
					+ ", ignore=" + ignore + "]";
		}

		@Override
		public O emptyInstance() {
			return new O();
		}

	}
	
	@EqualsClass( select = Select.DECLARED, workOn = WorkOn.METHODS )
	private static class X implements StrongHash {
		
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
		@Override
		public String strongHashCode(){
			return Equals.strongHashString( this );
		}

		@Override
		public String toString() {
			return "X [x=" + getX() + ", ignore=" + getIgnore() + "]";
		}
	}
}
