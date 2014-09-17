package de.axone.refactor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mark a method/class as untested.
 * 
 * @author flo
 */
@Retention( RetentionPolicy.SOURCE )
public @interface NotTested {

}
