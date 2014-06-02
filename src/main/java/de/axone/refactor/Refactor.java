package de.axone.refactor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.SOURCE )
public @interface Refactor {
	
	State value() default State.TODO;
	
	String action() default "";

	String target() default "";
	
	String reason() default "";
	
	public enum State { TODO, OK; }
}
