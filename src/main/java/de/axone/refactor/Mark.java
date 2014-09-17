package de.axone.refactor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.SOURCE )
public @interface Mark {
	String value() default "";
}
