package de.axone.data.weighted;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractIterableAssert;
import org.testng.annotations.Test;

import de.axone.data.weighted.WeightedCollectionContained.WeightedItem;
import de.axone.tools.E;

// import static de.axone.data.weighted.WeightedItemListAssert.*;

@Test( groups="helper.weighted_collection" )
public class WeightedCollectionContainedTest {
	
	
	public void testOneItem() {
		
		TestWeightedItems<String> list = new TestWeightedItems<>();
		
		list.add( "id1", 10.0 );
		
		assertThat( list )
				.hasSize( 1 )
				.contains( "id1", 10 )
				.hasWeight( 10.0 )
				.hasMaxWeight( 10.0 )
				.hasAvgWeight( 10.0 )
				;
		
	}
		
	public void testDifferentItems() {
		
		TestWeightedItems<String> list = new TestWeightedItems<>();
		
		list.add( "id1", 10.0 );
		list.add( "id2", 10.0 );
		
		assertThat( list )
				.hasSize( 2 )
				.contains( "id1", 10.0 )
				.contains( "id2", 10.0 )
				.hasWeight( 20.0 )
				.hasMaxWeight( 10.0 )
				.hasAvgWeight( 10.0 )
				;
	}
	
	public void testSameItems() {
		
		TestWeightedItems<String> list = new TestWeightedItems<>();
		
		list.add( "id1", 10.0 );
		list.add( "id1", 20.0 );
		
		assertThat( list )
				.hasSize( 1 )
				.contains( "id1", 30.0 )
				.hasWeight( 30.0 )
				.hasMaxWeight( 30.0 )
				.hasAvgWeight( 30.0 )
				;
		
	}
	
	public void testMixedItems() {
		
		TestWeightedItems<String> list = new TestWeightedItems<>();
		
		list.add( "id1", 10.0 );
		list.add( "id1", 20.0 );
		list.add( "id2", 20.0 );
		
		assertThat( list )
				.hasSize( 2 )
				.contains( "id1", 30.0 )
				.contains( "id2", 20.0 )
				.hasWeight( 50.0 )
				.hasMaxWeight( 30.0 )
				.hasAvgWeight( 25.0 )
				;
	}
	
	
	public void testEquals() {
		
		TestWeightedItems<String>
				list1 = new TestWeightedItems<>(),
				list2 = new TestWeightedItems<>()
				;
		
		assertThat( list1 )
				.isEqualTo( list2 )
				;
		
		assertThat( list1.add( "test", 1 ) )
				.isNotEqualTo( list2 )
				;
		
		assertThat( list2.add( "test", 1 ) )
				.isEqualTo( list1 )
				;
		
		assertThat( list1.add( "test", 1 ) )
				.isNotEqualTo( list2 )
				;
		
		assertThat( list2.add( "test", 1 ) )
				.isEqualTo( list1 )
				;
	}
	
	public void testSublists() {
		
		TestWeightedItems<String> list = new TestWeightedItems<>();
		
		assertThat( list )
				.hasSize( 0 )
				;
		
		list.add( "id1", 10.0 );
		list.add( "id2", 20.0 );
		list.add( "id3", 30.0 );
		list.add( "id4", 40.0 );
		
		assertThat( list )
				.hasSize( 4 )
				;
		
		assertThat( list.best( 0 ) )
				.hasSize( 0 )
				;
		
		assertThat( list.best( 1 ) )
				.print()
				.hasSize( 1 )
				.contains( "id4", 40.0 )
				.hasWeight( 40.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 40.0 )
				;
		
		assertThat( list.best( 2 ) )
				.hasSize( 2 )
				.contains( "id4", 40.0 )
				.contains( "id3", 30.0 )
				.hasWeight( 70.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 35.0 )
				;
		
		assertThat( list.best( 3 ) )
				.hasSize( 3 )
				.contains( "id4", 40 )
				.contains( "id3", 30 )
				.contains( "id2", 20 )
				.hasWeight( 90.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 30.0 )
				;
		
		assertThat( list.best( 4 ) )
				.hasSize( 4 )
				.contains( "id4", 40 )
				.contains( "id3", 30 )
				.contains( "id2", 20 )
				.contains( "id1", 10 )
				.hasWeight( 100.0 )
				.hasMaxWeight( 40.0 )
				.hasAvgWeight( 25.0 )
				;
		
		assertThat( list.best( 5 ) )
				.hasSize( 4 )
				;
		
	}
	
	public void testNormalize() {
		
		TestWeightedItems<String> list = new TestWeightedItems<>();
		
		list.add( "id1", 10.0 );
		list.add( "id2", 10.0 );
		list.add( "id2", 10.0 );
		list.add( "id3", 30.0 );
		list.add( "id4", 40.0 );
		
		assertThat( list )
				.hasSize( 4 )
				.hasWeight( 100.0 )
				.hasAvgWeight( 25.0 )
				.hasMaxWeight( 40.0 )
				.contains( "id1", 10.0, 0.25 )
				.contains( "id2", 20.0, 0.5 )
				.contains( "id3", 30.0, 0.75 )
				.contains( "id4", 40.0, 1.0 )
				;
		
	}
	
	private static class TestWeightedItems<S> extends AbstractWeightedCollectionContained<TestWeightedItems<S>,S> {

		public TestWeightedItems() {
			super( TestWeightedItems::new );
		}}
	
	
	public static class WeightedCollectionContainedAssert<
			S,
			W extends AbstractWeightedCollectionContained<W,S>,
			Y extends WeightedItemAssert<S>
			>
	extends AbstractIterableAssert<WeightedCollectionContainedAssert<S,W,Y>, W, WeightedItem<S>, WeightedItemAssert<S>> {

		protected WeightedCollectionContainedAssert( W actual ) {
			super( actual, WeightedCollectionContainedAssert.class );
		}
		
		public WeightedCollectionContainedAssert<S,W,Y> print() {
			
			E.echo( System.err, 2, true, true, false, actual );
			
			return this;
		}

		public WeightedCollectionContainedAssert<S,W,Y> hasWeight( double weight ){
			org.assertj.core.api.Assertions.assertThat( actual.weight() )
					.isEqualTo( weight )
					;
			return this;
		}
		public WeightedCollectionContainedAssert<S,W,Y> hasMaxWeight( double weight ){
			org.assertj.core.api.Assertions.assertThat( actual.maxWeight() )
					.isEqualTo( weight )
					;
			return this;
		}
		public WeightedCollectionContainedAssert<S,W,Y> hasAvgWeight( double weight ){
			org.assertj.core.api.Assertions.assertThat( actual.avgWeight() )
					.isEqualTo( weight )
					;
			return this;
		}
		
		public WeightedCollectionContainedAssert<S,W,Y> contains( S item, double weight ){
			
			WeightedItem<S> it = actual.new WeightedItemImpl( item, weight );
			
			for( WeightedItem<S> check : actual ) {
				
				if( it.equals( check ) ) return this;
			}
			
			failWithMessage( "%s(%s) should be contained, but is not", item, weight );
			
			return this;
		}
		
		public WeightedCollectionContainedAssert<S,W,Y> contains( S item, double weight, double normalized ){
			
			WeightedItem<S> it = new TestWeightedItem<>( item, weight, normalized );
			
			for( WeightedItem<S> check : actual ) {
				
				if( check.equals( it ) ) return this;
			}
			
			failWithMessage( "%s(%s) should be contained, but is not", item, weight );
			
			return this;
		}

		@Override
		protected WeightedItemAssert<S> toAssert( WeightedItem<S> value,
				String description ) {
			return new WeightedItemAssert<>( value ).as(  description );
		}
		
	}
	
	
	public static <S,W extends AbstractWeightedCollectionContained<W,S>, Y extends WeightedItemAssert<S>> WeightedCollectionContainedAssert<S,W,Y> assertThat( W list ) {
			
		
		return new WeightedCollectionContainedAssert<S,W,Y>( list )
				.as( list.toString() )
				;
	}
	
	
	public static class WeightedItemAssert<T> extends AbstractAssert<WeightedItemAssert<T>, WeightedItem<T>> {

		public WeightedItemAssert( WeightedItem<T> actual ) {
			super( actual, WeightedItemAssert.class );
		}
		
	}
	
	private static final class TestWeightedItem<T> implements WeightedItem<T> {
		
		private final T item;
		private double weight;
		private Double normalized;
		
		TestWeightedItem( T item, double weight, double normalized ) {
			this.item = item;
			this.weight = weight;
			this.normalized = normalized;
		}

		@Override
		public T item() {
			return item;
		}

		@Override
		public double weight() {
			return weight;
		}

		@Override
		public double normalized() {
			return normalized != null ? normalized : -1;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( item == null ) ? 0 : item.hashCode() );
			long temp;
			temp = Double.doubleToLongBits( weight );
			result = prime * result + (int) ( temp ^ ( temp >>> 32 ) );
			return result;
		}

		@Override
		public boolean equals( Object obj ) {
			
			if( this == obj ) return true;
			if( obj == null ) return false;
			if( !( obj instanceof TestWeightedItem ) ) return false;
			
			WeightedItem<?> other = (WeightedItem<?>) obj;
			
			if( item == null ) {
				if( other.item() != null )
					return false;
			} else if( !item.equals( other.item() ) )
				return false;
			
			if( Double.doubleToLongBits( weight ) != Double
					.doubleToLongBits( other.weight() ) )
				return false;
			
			if( normalized != null )
				if( ! normalized.equals( other.normalized() ) ) return false;
			
			return true;
		}
		
		@Override
		public String toString() {
			
			return item.toString() +
					'(' + weight() + '/' + normalized() + ')';
		}
		
	}
	
}
