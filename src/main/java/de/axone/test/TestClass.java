package de.axone.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate the class which is used for testing
 * 
 * We would like to use Class<?> instead of String
 * but this causes the compiler to poke
 * 
 * @author flo
 */
@Retention( RetentionPolicy.SOURCE )
@Target( ElementType.TYPE )
public @interface TestClass {
	String [] value();
}
