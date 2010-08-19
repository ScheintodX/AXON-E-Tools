package de.axone.function;


public class ArgumentDescription_Integer extends
		AbstractArgumentDescription<Integer> {

	public ArgumentDescription_Integer( String name, String description,
			boolean isMandatory, boolean isNamed, Integer defaultValue ) {
		super(name, description, isMandatory, isNamed, defaultValue != null ? defaultValue.toString() : null );
	}

	@Override
	public Argument<Integer> argumentInstance() {
		return new Argument_Integer();
	}

}
