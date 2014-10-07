package de.axone.data;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Converters {
	
	public <S,T, E extends Exception> List<T> convertList( List<S> source, ThrowingFunction<S,T,E> converter ) {
		
		return source.stream()
				.map( catchEx( converter ) )
				.collect( Collectors.toList() )
				;
		
	}

	public static <T,E extends Exception> Predicate<T> catchEx( ThrowingPredicate<T,E> predicate ) {
		
		return (T t) -> {
			try {
				return predicate.test( t );
			} catch( Throwable e ) {
				throw new RuntimeException( e );
			} };
	}
	
	@FunctionalInterface
	public interface ThrowingPredicate<T, E extends Exception> {
	    public boolean test(T t) throws E;
	}

	public static <T,R,E extends Exception> Function<T,R> catchEx( ThrowingFunction<T,R,E> predicate ) {
		
		return (T t) -> {
			try {
				return predicate.apply( t );
			} catch( Throwable e ) {
				throw new RuntimeException( e );
			} };
	}
	
	@FunctionalInterface
	public interface ThrowingFunction<T, R, E extends Exception> {
	    public R apply(T t) throws E;
	}

	public static <R,E extends Exception> Supplier<R> catchEx( ThrowingSupplier<R,E> predicate ) {
		
		return () -> {
			try {
				return predicate.get();
			} catch( Throwable e ) {
				throw new RuntimeException( e );
			} };
	}
	
	@FunctionalInterface
	public interface ThrowingSupplier<R, E extends Exception> {
	    public R get() throws E;
	}
}
