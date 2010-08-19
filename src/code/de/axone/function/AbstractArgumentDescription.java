package de.axone.function;


public abstract class AbstractArgumentDescription<T> implements ArgumentDescription<T> {
	
	private final String name;
	private final String description;
	private final boolean isMandatory;
	private final boolean isNamed;
	private final String defaultValue;
	
	
	public AbstractArgumentDescription( String name, String description, boolean isMandatory, boolean isNamed, String defaultValue ){
		
		this.name = name;
		this.description = description;
		this.isMandatory = isMandatory;
		this.isNamed = isNamed;
		this.defaultValue = defaultValue;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public boolean isMandatory(){
		return isMandatory;
	}
	
	@Override
	public boolean isNamed(){
		return isNamed;
	}
	
	@Override
	public String defaultValue(){
		return defaultValue;
	}

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		result
			.append( name.toUpperCase() )
			.append( ": " )
			.append( description );
		if( isNamed ) result.append( " NAMED" );
		if( isMandatory ) result.append( " MANDATORY" );
		if( defaultValue != null ) result.append( " DEFAULT: " + defaultValue );
		
		return result.toString();
	}
}
