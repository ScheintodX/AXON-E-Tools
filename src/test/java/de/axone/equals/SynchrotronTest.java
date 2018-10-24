package de.axone.equals;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import de.axone.equals.Synchrotron.SynOp;



@Test( groups="tools.equals" )
public class SynchrotronTest {

	public void emptyDestination() {
		
		List<X> src = Arrays.asList( X( "a", 1 ), X( "b", 2 ), X( "c", 3 ) );
		List<Y> dst = new ArrayList<>();
		
		Synchrotron.sync( dst, src, SYNOP );
		
		assertThat( dst )
				.hasSize( 3 )
				.contains( Y( "a", 1 ), Y( "b", 2 ), Y( "c", 3 ) )
				;
	}
	
	public void emptySource() {
		
		List<X> src = new ArrayList<>();
		// Must be modifiable
		List<Y> dst = new ArrayList<>( Arrays.asList( Y( "a", 1 ), Y( "b", 2 ), Y( "c", 3 ) ) );
		
		Synchrotron.sync( dst, src, SYNOP );
		
		assertThat( dst )
				.hasSize( 0 )
				;
	}
	
	public void mixed() {
		
		Y Y_b2 = Y( "b", 2 ),
		  Y_c0 = Y( "c", 0 );
		
		List<X> src = Arrays.asList( X( "a", 1 ), X( "b", 2 ), X( "c", 3 ) );
		List<Y> dst = new ArrayList<>( Arrays.asList( Y_b2, Y_c0, Y( "d", 4 ) ) );
		
		Synchrotron.sync( dst, src, SYNOP );
		
		assertThat( dst )
				.hasSize( 3 )
				.contains( Y( "a", 1 ), Y( "b", 2 ), Y( "c", 3 ) )
				;
		
		
		assertThat( dst.get( dst.indexOf( Y_b2 ) ) )
				.as( "b2" )
				.isEqualTo( Y( "b", 2 ) )
				.isSameAs( Y_b2 )
				;
		
		assertThat( dst.get( dst.indexOf( Y( "c", 3 ) ) ) )
				.as( "c0" )
				.isEqualTo( Y( "c", 3 ) )
				.isSameAs( Y_c0 )
				;
		
		assertThat( Y_c0.getValue() )
				.isEqualTo( 3 )
				;
	}
	
	private SynOp<Y,X,String> SYNOP = new SynOp<Y,X,String>() {

		@Override
		public Y create( X src ) {
			return new Y( src.getKey() );
		}

		@Override
		public void update( Y dst, X src ) {
			dst.setValue( src.getValue() );
		}

		@Override
		public String dstKey( Y dst ) {
			return dst.getKey();
		}

		@Override
		public String srcKey( X src ) {
			return src.getKey();
		}
	};
	
	private static X X( String key, int value ){ return new X( key, value ); }
		
	private static class X {
		
		private final String key;
		private int value;
		
		public X( String key ) {
			this.key = key;
		}
		public X( String key, int value ) {
			this.key = key;
			this.value = value;
		}
		public void setValue( int value ) {
			this.value = value;
		}
		public String getKey(){ return key; }
		public int getValue(){ return value; }
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ( ( key == null ) ? 0 : key.hashCode() );
			result = prime * result + (int) ( value ^ ( value >>> 32 ) );
			return result;
		}
		
		@Override
		public boolean equals( Object obj ) {
			if( this == obj )
				return true;
			if( obj == null )
				return false;
			if( !( obj instanceof X ) )
				return false;
			X other = (X) obj;
			if( key == null ) {
				if( other.key != null )
					return false;
			} else if( !key.equals( other.key ) )
				return false;
			if( value != other.value )
				return false;
			return true;
		}
		
		@Override
		public String toString() {
			return getClass().getSimpleName() + key+value;
		}
	}
	
	private static Y Y( String key, int value ){ return new Y( key, value ); }
		
	private static final class Y extends X {
		
		public Y( String key ) {
			super( key );
		}
		public Y( String key, int value ) {
			super( key, value );
		}
	}

}
