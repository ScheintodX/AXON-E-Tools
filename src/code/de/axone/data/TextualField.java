package de.axone.data;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Marks the field which should be used for conversion
 * to an textual value.
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface TextualField {

	public boolean primary() default false;
}
