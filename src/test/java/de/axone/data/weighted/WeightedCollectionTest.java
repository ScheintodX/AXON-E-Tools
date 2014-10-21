package de.axone.data.weighted;

import org.assertj.core.api.AbstractIterableAssert;
import org.testng.annotations.Test;

public class WeightedCollectionTest {
	
	private TestItem id1_10 = item( 1, 10.0 ),
	                 id1_20 = item( 1, 20.0 ),
	                 id1_30 = item( 1, 30.0 ),
	                 id2_10 = item( 2, 10.0 ),
	                 id2_20 = item( 2, 20.0 ),
	                 id2_30 = item( 2, 30.0 ),
	                 id3_30 = item( 3, 30.0 ),
	                 id4_40 = item( 4, 40.0 )
	                 ;
	
	@Test
	public void testOneItem() {
		
		WeightedTestItems list = new WeightedTestItems();
		
		list.add( id1_10 );
		
		assertThat( list )
				.hasSize( 1 )
				.contains( id1_10 )
				.contains( id1_20 )
				.contains( id1_30 )
				.reallyContains( id1_10 )
				.reallyDoesNotContain( id1_20 ) // Tests just the tests
				.hasWeight( 10.0 )
				.hasMaxWeight( 10.0 )
				.hasAvgWeight( 10.0 )
				;
		
	}
		
	@Test
	public void testDifferentItems() {
		
		WeightedTestItems list = new WeightedTestItems();
		
		list.add( id1_10 );
		list.add( id2_10 );
		
		assertThat( list )
				.hasSize( 2 )
				.reallyContains( id1_10 )
				.contains( id1_20 )
				.contains( id1_30 )
				.reallyContains( id2_10 )
				.contains( id2_20 )
				.contains( id2_30 )
				.hasWeight( 20.0 )
				.hasMaxWeight( 10.0 )
				.hasAvgWeight( 10.0 )
				;
	}
	
	@Test
	public void testSameItems() {
		
		WeightedTestItems list = new WeightedTestItems();
		
		list.add( id1_10 );
		list.add( id1_20 );
		
		assertThat( list )
				.hasSize( 1 )
				.contains( id1_10 )
				.contains( id1_20 )
				.contains( id1_30 )
				.reallyContains( id1_30 )
				.reallyDoesNotContain( id1_10 )
				.reallyDoesNotContain( id1_20 )
				.hasWeight( 30.0 )
				.hasMaxWeight( 20.0 )
				.hasAvgWeight( 15.0 )
				;
		
	}
	
	@Test
	public void testMixedItems() {
		
		WeightedTestItems list = new WeightedTestItems();
		
		list.add( id1_10 );
		list.add( id1_20 );
		list.add( id2_20 );
		
		assertThat( list )
				.hasSize( 2 )
				.contains( id1_10 )
				.contains( id1_20 )
				.reallyDoesNotContain( id1_10 )
				.reallyDoesNotContain( id1_20 )
				.reallyContains( id1_30 )
				.contains( id2_10 )
				.reallyContains( id2_20 )
				.contains( id2_30 )
				.hasWeight( 50.0 )
				.hasMaxWeight( 20.0 )
				.hasAvgWeight( 25.0 )
				;
	}
	
	@Test
	public void testSublists() {
		
		WeightedTestItems list = new WeightedTestItems();
		
		assertThat( list )
				.hasSize( 0 )
				;
		
		list.add( id1_10 );
		list.add( id2_20 );
		list.add( id3_30 );
		list.add( id4_40 );
		
		assertThat( list )
				.hasSize( 4 )
				;
		
		assertThat( list.best( 0 ) )
				.hasSize( 0 )
				;
		
		assertThat( list.best( 1 ) )
				.hasSize( 1 )
				.reallyContains( id4_40 )
				.hasWeight( 40.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 40.0 )
				.isNotEqualTo( list.best( 0 ) )
				;
		
		assertThat( list.best( 2 ) )
				.hasSize( 2 )
				.reallyContains( id4_40 )
				.reallyContains( id3_30 )
				.hasWeight( 70.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 35.0 )
				.isNotEqualTo( list.best( 1 ) )
				;
		
		assertThat( list.best( 3 ) )
				.hasSize( 3 )
				.reallyContains( id4_40 )
				.reallyContains( id3_30 )
				.reallyContains( id2_20 )
				.hasWeight( 90.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 30.0 )
				.isNotEqualTo( list.best( 2 ) )
				;
		
		assertThat( list.best( 4 ) )
				.hasSize( 4 )
				.reallyContains( id4_40 )
				.reallyContains( id3_30 )
				.reallyContains( id2_20 )
				.reallyContains( id1_10 )
				.hasWeight( 100.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 25.0 )
				.isNotEqualTo( list.best( 3 ) )
				;
		
		assertThat( list.best( 5 ) )
				.isEqualTo( list.best( 4 ) )
				;
		
	}
	
	@Test
	public void testNormalize() {
		
		WeightedTestItems list = new WeightedTestItems();
		
		list.add( id1_10 );
		list.add( id2_10 );
		list.add( id2_10 );
		list.add( id3_30 );
		list.add( id4_40 );
		
		assertThat( list )
				.hasSize( 4 )
				.hasAvgWeight( 100.0 )
				;
		
		assertThat( list.normalized() )
				.hasSize( 4 )
				.reallyContains( item( 1, 0.25 ) )
				.reallyContains( item( 2, 0.5 ) )
				.reallyContains( item( 3, 0.75 ) )
				.reallyContains( item( 4, 1.0 ) )
				;
		
	}
	
	
	private static class WeightedTestItems extends AbstractWeightedCollection<WeightedTestItems, TestItem> {

		public WeightedTestItems(){
			super( WeightedTestItems::new, item -> item.weight, (item,newWeight) -> new TestItem( item.name, newWeight ) );
		}

	}
	
	private static final TestItem item( int name, double weight ){
		return new TestItem( "id"+name, weight );
	}
	
	
	/**
	 * Item with hashcode and equals only on payload and not on weight
	 * 
	 * @author flo
	 */
	private static class TestItem {
		
		private final String name;
		private final double weight;
		
		public TestItem( String name, double weight ) {
			this.name = name;
			this.weight = weight;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			if( this == obj )
				return true;
			if( obj == null )
				return false;
			if( !( obj instanceof TestItem ) )
				return false;
			TestItem other = (TestItem) obj;
			if( name == null ) {
				if( other.name != null )
					return false;
			} else if( !name.equals( other.name ) )
				return false;
			return true;
		}

		@Override
		public String toString(){
			return name + "(" + weight + ")";
		}

	}
	

	public static class WeightedCollectionAsssert<T, W extends AbstractWeightedCollection<W,T>>
	extends AbstractIterableAssert<WeightedCollectionAsssert<T,W>, W, T> {

		protected WeightedCollectionAsssert( W actual ) {
			super( actual, WeightedCollectionAsssert.class );
		}
		
		protected WeightedTestItems actual(){ return (WeightedTestItems) actual; }
		
		public WeightedCollectionAsssert<T,W> hasWeight( double weight ){
			org.assertj.core.api.Assertions.assertThat( actual().weight() );
			return this;
		}
		public WeightedCollectionAsssert<T,W> hasMaxWeight( double weight ){
			org.assertj.core.api.Assertions.assertThat( actual().maxWeight() );
			return this;
		}
		public WeightedCollectionAsssert<T,W> hasAvgWeight( double weight ){
			org.assertj.core.api.Assertions.assertThat( actual().avgWeight() );
			return this;
		}
		
		public WeightedCollectionAsssert<T,W> reallyContains( T item ){
			
			for( T it : actual ){
				
				if( it.equals( item ) && actual.weighter().weight( it ) == actual.weighter().weight( item ) )
						 return this;
				
			}
			
			failWithMessage( "<%s> does not contain <%s>", actual.asList(), item );
			
			return this;
		}
		
		public WeightedCollectionAsssert<T,W> reallyDoesNotContain( T item ){
			
			for( T it : actual ){
				
				if( it.equals( item ) && actual.weighter().weight( it ) == actual.weighter().weight( item ) )
						failWithMessage( "<%s> contains <%s>", actual.asList(), item );
				
			}
			
			return this;
		}
		
	}
	
	public static <T, W extends AbstractWeightedCollection<W,T>> WeightedCollectionAsssert<T,W> assertThat( W list ){
		
		return new WeightedCollectionAsssert<T,W>( list );
	}
	
	/*
	public static class WeightedStringsAssert<S extends WeightedString, L extends WeightedStrings<L,S>>
	extends WeightedCollectionAsssert<S,L> {
	}
	
	
	public static <L extends WeightedStrings<L,S>, S extends WeightedString> WeightedCollectionAsssert<S,L> assertThat( L list ){
		
		return new WeightedStringsAssert<S,L>( list );
	}
	*/
	
}
