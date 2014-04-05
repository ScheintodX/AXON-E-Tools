package de.axone.equals;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.TYPE )
public @interface EqualsClass {
	public enum WorkOn { FIELDS, METHODS; }
	public enum Select { ALL, DECLARED; }
	public WorkOn workOn();
	public Select select() default Select.ALL;
	public boolean includePrivate() default false;
	
}
