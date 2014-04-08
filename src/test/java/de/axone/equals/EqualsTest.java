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
import java.util.TreeSet;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.testng.annotations.Test;

import de.axone.equals.Equals.Synchronizable;
import de.axone.equals.EqualsClass.Select;
import de.axone.equals.EqualsClass.WorkOn;
import de.axone.tools.Sets;

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

	private static final class oo {
		
		private o o1 = new o( 1, "s1", new X( "x1" ), true, "2.0" );
		private o o11 = new o( 1, "s1", new X( "x1" ), true, "2.0" );
		
		private o o2 = new o( 2, "s1", new X( "x1" ), true, "2.0" );
		private o o3 = new o( 1, "s2", new X( "x1" ), true, "2.0" );
		private o o4 = new o( 1, "s2", new X( "x2" ), true, "2.0" );
		private o o5 = new o( 1, "s2", new X( "x2" ), false, "2.0" );
		private o o6 = new o( 1, "s2", new X( "x2" ), true, "2.00" );
		
		private o o20 = new o( 0, "s1", new X( "x1" ), true, "2.0" );
		private o o30 = new o( 1, null, new X( "x1" ), true, "2.0" );
		private o o40 = new o( 1, "s1", null,          true, "2.0" );
		private o o50 = new o( 1, "s1", new X( "x1" ), true, null );
	}

	//@Test( enabled=false )
	public void verySimpleTest(){
		
		O o = new O( 1, "s1", null, true, null );
		assertNotNull( o.hashCode() );
	}
	
	//@Test( enabled=false )
	public void verySimpleTest_(){
		
		o o = new o( 1, "s1", null, true, null );
		assertNotNull( o.hashCode() );
	}
		
	// This method tests, that EqualsBuilder from Commons uses equals on
	// BigDecimal to compare to BigDecimals. Note that this was changed
	// in the past and could change again.
	//@Test( enabled=false )
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
	
	//@Test( enabled=false )
	public void testAnnotations(){
		
		EqualsClass ec = O.class.getAnnotation( EqualsClass.class );
		assertNotNull( ec );
		assertEquals( ec.select(), Select.ALL );
		assertEquals( ec.workOn(), WorkOn.METHODS );
	}
	
	public void testKeepUntouched() throws Exception {
		
		O s1 = new O( 1, "s1", new X( "x1" ), true, "2.0" );
		O x1 = new O();
		
		//E.rr( s1 );
		//E.rr( x1 );
		
		assertNotEquals( x1, s1 );
		
		Equals.synchronize( x1, s1, null );
		assertEquals( x1, s1 ); // Must equal according to Equals rules
		
		// Must still differ
		assertNotEquals( x1.ignore, s1.ignore );
		
		//E.rr( s1 );
		//E.rr( x1 );
		
	}
	
	
	//@Test( enabled=false )
	public void testBuilder(){
		
		OO o = new OO();
		
		assertEquals( o.o1, o.o1 );
		assertEquals( o.o11, o.o11 );
		assertEquals( o.o1, o.o11 );
		assertEquals( o.o11, o.o1 );
		
		assertNotEquals( o.o1, o.o2 );
		assertNotEquals( o.o1, o.o3 );
		assertNotEquals( o.o1, o.o4 );
		assertNotEquals( o.o1, o.o5 );
		assertNotEquals( o.o1, o.o6 );
		
		assertNotEquals( o.o1, o.o20 );
		assertNotEquals( o.o1, o.o30 );
		assertNotEquals( o.o1, o.o40 );
		assertNotEquals( o.o1, o.o50 );
	}
	
	//@Test( enabled=false )
	public void testBuilder_(){
		
		oo o = new oo();
		
		assertEquals( o.o1, o.o1 );
		assertEquals( o.o11, o.o11 );
		assertEquals( o.o1, o.o11 );
		assertEquals( o.o11, o.o1 );
		
		assertNotEquals( o.o1, o.o2 );
		assertNotEquals( o.o1, o.o3 );
		assertNotEquals( o.o1, o.o4 );
		assertNotEquals( o.o1, o.o5 );
		assertNotEquals( o.o1, o.o6 );
		
		assertNotEquals( o.o1, o.o20 );
		assertNotEquals( o.o1, o.o30 );
		assertNotEquals( o.o1, o.o40 );
		assertNotEquals( o.o1, o.o50 );
	}
	
	//@Test( enabled=false )
	public void testHashcode(){
		
		OO o = new OO();
		
		assertTrue( o.o1.hashCode() == o.o1.hashCode() );
		assertTrue( o.o11.hashCode() == o.o11.hashCode() );
		assertTrue( o.o1.hashCode() == o.o11.hashCode() );
		assertTrue( o.o11.hashCode() == o.o1.hashCode() );
		
		assertFalse( o.o1.hashCode() == o.o2.hashCode() );
		assertFalse( o.o1.hashCode() == o.o3.hashCode() );
		assertFalse( o.o1.hashCode() == o.o4.hashCode() );
		assertFalse( o.o1.hashCode() == o.o5.hashCode() );
		assertFalse( o.o1.hashCode() == o.o6.hashCode() ); // BigDecimals scale affect hashCode
		
		assertFalse( o.o1.hashCode() == o.o20.hashCode() );
		assertFalse( o.o1.hashCode() == o.o30.hashCode() );
		assertFalse( o.o1.hashCode() == o.o40.hashCode() );
		assertFalse( o.o1.hashCode() == o.o50.hashCode() );
	}
	
	//@Test( enabled=false )
	public void testHashcode_(){
		
		oo o = new oo();
		
		assertTrue( o.o1.hashCode() == o.o1.hashCode() );
		assertTrue( o.o11.hashCode() == o.o11.hashCode() );
		assertTrue( o.o1.hashCode() == o.o11.hashCode() );
		assertTrue( o.o11.hashCode() == o.o1.hashCode() );
		
		assertFalse( o.o1.hashCode() == o.o2.hashCode() );
		assertFalse( o.o1.hashCode() == o.o3.hashCode() );
		assertFalse( o.o1.hashCode() == o.o4.hashCode() );
		assertFalse( o.o1.hashCode() == o.o5.hashCode() );
		assertFalse( o.o1.hashCode() == o.o6.hashCode() ); // BigDecimals scale affect hashCode
		
		assertFalse( o.o1.hashCode() == o.o20.hashCode() );
		assertFalse( o.o1.hashCode() == o.o30.hashCode() );
		assertFalse( o.o1.hashCode() == o.o40.hashCode() );
		assertFalse( o.o1.hashCode() == o.o50.hashCode() );
	}
	
	//@Test( enabled=false )
	public void testStrongHashCode(){
		
		OO o = new OO();
		
		assertTrue( o.o1.strongHashCode().equals( o.o1.strongHashCode() ) );
		assertTrue( o.o11.strongHashCode().equals( o.o11.strongHashCode() ) );
		assertTrue( o.o1.strongHashCode().equals( o.o11.strongHashCode() ) );
		assertTrue( o.o11.strongHashCode().equals( o.o1.strongHashCode() ) );
		
		assertFalse( o.o1.strongHashCode().equals( o.o2.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o3.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o4.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o5.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o6.strongHashCode() ) ); // BigDecimals scale affect strongHashCode
		
		assertFalse( o.o1.strongHashCode().equals( o.o20.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o30.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o40.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o50.strongHashCode() ) );
	}
	
	//@Test( enabled=false )
	public void testStrongHashCode_(){
		
		oo o = new oo();
		
		assertTrue( o.o1.strongHashCode().equals( o.o1.strongHashCode() ) );
		assertTrue( o.o11.strongHashCode().equals( o.o11.strongHashCode() ) );
		assertTrue( o.o1.strongHashCode().equals( o.o11.strongHashCode() ) );
		assertTrue( o.o11.strongHashCode().equals( o.o1.strongHashCode() ) );
		
		assertFalse( o.o1.strongHashCode().equals( o.o2.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o3.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o4.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o5.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o6.strongHashCode() ) ); // BigDecimals scale affect strongHashCode
		
		assertFalse( o.o1.strongHashCode().equals( o.o20.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o30.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o40.strongHashCode() ) );
		assertFalse( o.o1.strongHashCode().equals( o.o50.strongHashCode() ) );
	}
	
	//@Test( enabled=false )
	public void testSynchronize(){
		
		OO oo = new OO();
		
		O o = new O( 0, null, null, false, null );
		
		assertEquals( Equals.synchronize( o, oo.o1, null ), oo.o1 );
		assertFalse( o.getList() == oo.o1.getList() );
		assertFalse( o.getSet() == oo.o1.getSet() );
		assertFalse( o.getMap() == oo.o1.getMap() );
		assertEquals( Equals.synchronize( o, oo.o11, null ), oo.o11 );
		
		assertEquals( Equals.synchronize( o, oo.o2, null ), oo.o2 );
		assertEquals( Equals.synchronize( o, oo.o3, null ), oo.o3 );
		assertEquals( Equals.synchronize( o, oo.o4, null ), oo.o4 );
		assertEquals( Equals.synchronize( o, oo.o5, null ), oo.o5 );
		assertEquals( Equals.synchronize( o, oo.o6, null ), oo.o6 );
		
		assertEquals( Equals.synchronize( o, oo.o20, null ), oo.o20 );
		assertEquals( Equals.synchronize( o, oo.o30, null ), oo.o30 );
		assertEquals( Equals.synchronize( o, oo.o40, null ), oo.o40 );
		assertEquals( Equals.synchronize( o, oo.o50, null ), oo.o50 );
		
	}
	
	//@Test( enabled=false )
	public void testSynchronize_(){
		
		oo oo = new oo();
		
		o o = new o( 0, null, null, false, null );
		
		assertEquals( Equals.synchronize( o, oo.o1, null ), oo.o1 );
		assertFalse( o.list == oo.o1.list );
		assertFalse( o.set == oo.o1.set );
		assertFalse( o.map == oo.o1.map );
		assertEquals( Equals.synchronize( o, oo.o11, null ), oo.o11 );
		
		assertEquals( Equals.synchronize( o, oo.o2, null ), oo.o2 );
		assertEquals( Equals.synchronize( o, oo.o3, null ), oo.o3 );
		assertEquals( Equals.synchronize( o, oo.o4, null ), oo.o4 );
		assertEquals( Equals.synchronize( o, oo.o5, null ), oo.o5 );
		assertEquals( Equals.synchronize( o, oo.o6, null ), oo.o6 );
		
		assertEquals( Equals.synchronize( o, oo.o20, null ), oo.o20 );
		assertEquals( Equals.synchronize( o, oo.o30, null ), oo.o30 );
		assertEquals( Equals.synchronize( o, oo.o40, null ), oo.o40 );
		assertEquals( Equals.synchronize( o, oo.o50, null ), oo.o50 );
		
	}
	
	//@Test( enabled=false )
	public void testSynchronizeMapper(){
		
		OO oo = new OO();
		
		O o = new O( 0, null, null, false, null );
		
		TestSynchroMapper sm = new TestSynchroMapper();
		
		Equals.synchronize( o, oo.o1, sm );
		
		assertFalse( o.equals( oo.o1 ) );
		assertFalse( o.getSet() == oo.o1.getSet() );
		assertFalse( o.getList() == oo.o1.getList() );
		assertFalse( o.getMap() == oo.o1.getMap() );
		
		assertNotNull( o.getSet() );
		assertNotNull( o.getList() );
		assertNotNull( o.getMap() );
		
		assertEquals( o.getString(), "s1X" );
		
		assertFalse( o.getSet().equals( oo.o1.getSet() ) );
		assertTrue( o.getSet().contains( "s1X" ) );
		assertFalse( o.getSet().contains( "s1" ) );
		
		assertFalse( o.getList().equals( oo.o1.getList() ) );
		assertTrue( o.getList().contains( "s1X" ) );
		assertFalse( o.getList().contains( "s1" ) );
		
		assertFalse( o.getMap().equals( oo.o1.getMap() ) );
		assertEquals( o.getMap().get( "s" ), "s1X" );
		
		O o_ = new O();
		
		assertEquals( sm.fields, Sets.treeSetOf( "b", "bd", "int", "list", "map", "set", "string", "x", "b") );
		
		sm = new TestSynchroMapper();
		
		Equals.synchronize( o_, oo.o1, sm );
		
		assertFalse( o_.equals( oo.o1 ) );
		assertFalse( o_.getSet() == oo.o1.getSet() );
		assertFalse( o_.getList() == oo.o1.getList() );
		assertFalse( o_.getMap() == oo.o1.getMap() );
		
		assertNotNull( o_.getSet() );
		assertNotNull( o_.getList() );
		assertNotNull( o_.getMap() );
		
		assertEquals( o_.getString(), "s1X" );
		
		assertFalse( o_.getSet().equals( oo.o1.getSet() ) );
		assertTrue( o_.getSet().contains( "s1X" ) );
		assertFalse( o_.getSet().contains( "s1" ) );
		
		assertFalse( o_.getList().equals( oo.o1.getList() ) );
		assertTrue( o_.getList().contains( "s1X" ) );
		assertFalse( o_.getList().contains( "s1" ) );
		
		assertFalse( o_.getMap().equals( oo.o1.getMap() ) );
		assertEquals( o_.getMap().get( "s" ), "s1X" );
		
		assertEquals( sm.fields, Sets.treeSetOf( "b", "bd", "int", "list", "map", "set", "string", "x", "b") );
		
	}
	
	////@Test( enabled=false )
	public void testSynchronizeMapper_(){
		
		oo oo = new oo();
		
		o o = new o( 0, null, null, false, null );
		
		Equals.synchronize( o, oo.o1, new TestSynchroMapper() );
		
		assertFalse( o.equals( oo.o1 ) );
		assertFalse( o.set == oo.o1.set );
		assertFalse( o.list == oo.o1.list );
		assertFalse( o.map == oo.o1.map );
		
		assertNotNull( o.set );
		assertNotNull( o.list );
		assertNotNull( o.map );
		
		assertEquals( o.s, "s1X" );
		
		assertFalse( o.set.equals( oo.o1.set ) );
		assertTrue( o.set.contains( "s1X" ) );
		assertFalse( o.set.contains( "s1" ) );
		
		assertFalse( o.list.equals( oo.o1.list ) );
		assertTrue( o.list.contains( "s1X" ) );
		assertFalse( o.list.contains( "s1" ) );
		
		assertFalse( o.map.equals( oo.o1.map ) );
		assertEquals( o.map.get( "s" ), "s1X" );
		
		o o_ = new o();
		
		Equals.synchronize( o_, oo.o1, new TestSynchroMapper() );
		
		assertFalse( o_.equals( oo.o1 ) );
		assertFalse( o_.set == oo.o1.set );
		assertFalse( o_.list == oo.o1.list );
		assertFalse( o_.map == oo.o1.map );
		
		assertNotNull( o_.set );
		assertNotNull( o_.list );
		assertNotNull( o_.map );
		
		assertEquals( o_.s, "s1X" );
		
		assertFalse( o_.set.equals( oo.o1.set ) );
		assertTrue( o_.set.contains( "s1X" ) );
		assertFalse( o_.set.contains( "s1" ) );
		
		assertFalse( o_.list.equals( oo.o1.list ) );
		assertTrue( o_.list.contains( "s1X" ) );
		assertFalse( o_.list.contains( "s1" ) );
		
		assertFalse( o_.map.equals( oo.o1.map ) );
		assertEquals( o_.map.get( "s" ), "s1X" );
		
	}
	
	static class TestSynchroMapper implements SynchroMapper {
		
		Set<String> fields = new TreeSet<>();

		@Override
		public Object copyOf( String name, Object object ) {
			
			fields.add( name );
			
			if( object instanceof String ){
				return ((String)object) + "X";
			}
			return object;
		}

		@Override
		public Object emptyInstanceOf( String name, Object object )
				throws InstantiationException, IllegalAccessException {
			
			fields.add( name );
			
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
		private double getignore2(){ return ignore; };
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
	
	@EqualsClass( workOn = WorkOn.FIELDS )
	private static class o implements Synchronizable<o>, StrongHash {
		
		public int i;
		public String s;
		public X x;
		public boolean b;
		public BigDecimal bd;
		public Set<Object> set;
		public List<Object> list;
		public Map<Object,Object> map;
		
		@EqualsField( include=false )
		public double ignore1 = Math.random();
		//Private should be ignored if not told otherwise
		private double ignore2 = Math.random();
		@Id
		public double ignore4 = Math.random();
		@Transient
		public double ignore5 = Math.random();
		
		
		o(){}
		
		o( int i, String s, X x, boolean b, String bd ){
			
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
		public void synchronizeFrom( o other ) {
			Equals.synchronize( this, other, null );
		}
		
		@Override
		public o emptyInstance() {
			return new o();
		}

		@Override
		public String toString() {
			return "o [i=" + i + ", " + ( s != null ? "s=" + s + ", " : "" )
					+ ( x != null ? "x=" + x + ", " : "" ) + "b=" + b + ", "
					+ ( bd != null ? "bd=" + bd + ", " : "" )
					+ ( set != null ? "set=" + set + ", " : "" )
					+ ( list != null ? "list=" + list + ", " : "" )
					+ ( map != null ? "map=" + map + ", " : "" ) + "ignore1="
					+ ignore1 + ", ignore2=" + ignore2 + ", ignore4=" + ignore4
					+ ", ignore5=" + ignore5 + "]";
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
