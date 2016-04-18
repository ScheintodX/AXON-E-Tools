package de.axone.cmp;

import java.util.Collection;

import de.axone.test.TestClass;

@TestClass( "CmpTest" )
public interface Cmp {
	
	
	public static <T> ThingCompare<T> The( T subject ) {
		
		return new ThingCompare<>( subject );
	}
	
	
	public static class ThingCompare<T> {
		
		protected final T subject;

		public ThingCompare( T subject ) {
			this.subject = subject;
		}
		public T subject() {
			return subject;
		}
		
		public boolean is( CompareThing<T> pred ) {
			return pred.test( subject );
		}
		public boolean not( CompareThing<T> pred ) {
			return !pred.test( subject );
		}
	}
	
	
	public static <X, T extends Comparable<X>> CompareThing<T> GreaterThan( X other ) {
		return (mine) -> mine.compareTo( other ) > 0;
	}
	public static <X, T extends Comparable<X>> CompareThing<T> GreaterOrEqualThan( X other ) {
		return (mine) -> mine.compareTo( other ) >= 0;
	}
	public static <X, T extends Comparable<X>> CompareThing<T> LesserThan( X other ) {
		return (mine) -> mine.compareTo( other ) < 0;
	}
	public static <X, T extends Comparable<X>> CompareThing<T> LesserOrEqualThan( X other ) {
		return (mine) -> mine.compareTo( other ) <= 0;
	}
	public static <X, T extends Comparable<X>> CompareThing<T> EqualTo( X other ) {
		return (mine) -> mine.compareTo( other ) == 0;
	}
	
	
	public static <X, T extends Collection<X>> CompareThing<T> NotNullOrEmpty() {
		return (mine) -> mine != null && mine.size() > 0;
	}
	
	
	@FunctionalInterface
	public interface CompareThing<X> {
		public boolean test( X mine );
	}
	
}
