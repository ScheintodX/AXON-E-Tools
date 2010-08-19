package de.axone.function;


public class ArgumentDescription_Boolean extends
		AbstractArgumentDescription<Boolean> {

	public ArgumentDescription_Boolean( String name, String description,
			boolean isMandatory, boolean isNamed, Boolean defaultValue ) {
		super(name, description, isMandatory, isNamed, defaultValue != null ? defaultValue.toString() : null );
	}

	@Override
	public Argument<Boolean> argumentInstance() {
		return new Argument_Boolean();
	}

}
