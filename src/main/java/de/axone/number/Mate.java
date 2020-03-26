package de.axone.number;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Mate {

	@SuppressWarnings( "unchecked" )
	public static <N extends Number> N add( N num1, N num2 ) {

		if( num1 == null ) return num2;
		if( num2 == null ) return num1;

		if( num1 instanceof Integer ) {
			return (N) Integer.valueOf( ((Integer)num1) + ((Integer)num2) );
		}

		if( num1 instanceof Long ) {
			return (N) Long.valueOf( ((Long)num1) + ((Long)num2) );
		}

		if( num1 instanceof Float ) {
			return (N) Float.valueOf( ((Float)num1) + ((Float)num2) );
		}

		if( num1 instanceof Double ) {
			return (N) Double.valueOf( ((Double)num1) + ((Double)num2) );
		}

		if( num1 instanceof BigDecimal ) {
			return (N) ( (BigDecimal)num1 ).add( (BigDecimal)num2 );
		}

		if( num1 instanceof BigInteger ) {
			return (N) ( (BigInteger)num1 ).add( (BigInteger)num2 );
		}

		throw new ArithmeticException( "Unknown number format: " + num1.getClass() );
	}

	@SuppressWarnings( "unchecked" )
	public static <N extends Number> N sub( N num1, N num2 ) {

		if( num1 == null ) throw new ArithmeticException( "num1 NULL" );
		if( num2 == null ) return num1;

		if( num1 instanceof Integer ) {
			return (N) Integer.valueOf( ((Integer)num1) - ((Integer)num2) );
		}

		if( num1 instanceof Long ) {
			return (N) Long.valueOf( ((Long)num1) - ((Long)num2) );
		}

		if( num1 instanceof Float ) {
			return (N) Float.valueOf( ((Float)num1) - ((Float)num2) );
		}

		if( num1 instanceof Double ) {
			return (N) Double.valueOf( ((Double)num1) - ((Double)num2) );
		}

		if( num1 instanceof BigDecimal ) {
			return (N) ( (BigDecimal)num1 ).subtract( (BigDecimal)num2 );
		}

		if( num1 instanceof BigInteger ) {
			return (N) ( (BigInteger)num1 ).subtract( (BigInteger)num2 );
		}

		throw new ArithmeticException( "Unknown number format: " + num1.getClass() );
	}

	@SuppressWarnings( "unchecked" )
	public static <N extends Number> N div( N num1, N num2 ) {

		if( num1 == null ) return null;
		if( num2 == null ) throw new ArithmeticException( "Division by null (ha!)" );

		if( num1 instanceof Integer ) {
			return (N) Integer.valueOf( ((Integer)num1) / ((Integer)num2) );
		}

		if( num1 instanceof Long ) {
			return (N) Long.valueOf( ((Long)num1) / ((Long)num2) );
		}

		if( num1 instanceof Float ) {
			return (N) Float.valueOf( ((Float)num1) / ((Float)num2) );
		}

		if( num1 instanceof Double ) {
			return (N) Double.valueOf( ((Double)num1) / ((Double)num2) );
		}

		if( num1 instanceof BigDecimal ) {
			return (N) ( (BigDecimal)num1 ).divide( (BigDecimal)num2 );
		}

		if( num1 instanceof BigInteger ) {
			return (N) ( (BigInteger)num1 ).divide( (BigInteger)num2 );
		}

		throw new ArithmeticException( "Unknown number format: " + num1.getClass() );
	}

	@SuppressWarnings( "unchecked" )
	public static <N extends Number> N mul( N num1, N num2 ) {

		if( num1 == null ) return null;
		if( num2 == null ) return null;

		if( num1 instanceof Integer ) {
			return (N) Integer.valueOf( ((Integer)num1) * ((Integer)num2) );
		}

		if( num1 instanceof Long ) {
			return (N) Long.valueOf( ((Long)num1) * ((Long)num2) );
		}

		if( num1 instanceof Float ) {
			return (N) Float.valueOf( ((Float)num1) * ((Float)num2) );
		}

		if( num1 instanceof Double ) {
			return (N) Double.valueOf( ((Double)num1) * ((Double)num2) );
		}

		if( num1 instanceof BigDecimal ) {
			return (N) ( (BigDecimal)num1 ).multiply( (BigDecimal)num2 );
		}

		if( num1 instanceof BigInteger ) {
			return (N) ( (BigInteger)num1 ).multiply( (BigInteger)num2 );
		}

		throw new ArithmeticException( "Unknown number format: " + num1.getClass() );
	}

}
