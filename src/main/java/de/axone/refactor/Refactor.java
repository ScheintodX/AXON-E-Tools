package de.axone.refactor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.SOURCE )
// Set to @Deprecated in order to show them in eclipse
//@Deprecated
public @interface Refactor {
	
	State value() default State.TODO;
	
	String action() default "";

	String target() default "";
	
	String reason() default "";
	
	String contra() default "";
	
	public enum State { TODO, OK; }
}
