package de.axone.function;

import java.util.Date;

public class ArgumentDescription_Date extends
		AbstractArgumentDescription<Date> {

	public ArgumentDescription_Date( String name, String description,
			boolean isMandatory, boolean isNamed, Date defaultValue ) {
		super(name, description, isMandatory, isNamed, defaultValue != null ? Argument_Date.dateToString( defaultValue ) : null );
	}

	@Override
	public Argument<Date> argumentInstance() {
		return new Argument_Date();
	}

}
