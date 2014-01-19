package de.axone.exception;


public class IllegalNamedArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = -5252132632378096617L;
	
	private final String argumentName;

	public IllegalNamedArgumentException( String argumentName ) {
		super();
		this.argumentName = argumentName;
	}

	public IllegalNamedArgumentException( String argumentName, String message, Throwable cause ) {
		super( message, cause );
		this.argumentName = argumentName;
	}

	public IllegalNamedArgumentException( String argumentName, String s ) {
		super( s );
		this.argumentName = argumentName;
	}

	public IllegalNamedArgumentException( String argumentName, Throwable cause ) {
		super( cause );
		this.argumentName = argumentName;
	}
	
	public String getArgumentName(){
		return argumentName;
	}
	
	@Override
	public String getMessage() {
		return "'" + getArgumentName() + "' " + super.getMessage();
	}


}
