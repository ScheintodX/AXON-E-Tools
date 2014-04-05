package de.axone.function;


public class ArgumentDescription_String extends
		AbstractArgumentDescription<String> {

	public ArgumentDescription_String( String name, String description,
			boolean isMandatory, boolean isNamed, String defaultValue ) {
		super(name, description, isMandatory, isNamed, defaultValue );
	}

	@Override
	public Argument<String> argumentInstance() {
		return new Argument_String();
	}

}
