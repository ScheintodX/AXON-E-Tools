package de.axone.web.rest;



public class RestFunctionException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5803188308493603831L;

	private static final int DEFAULT_CODE = 500;
	
	private int code;

	public RestFunctionException( String message ) {
		this( DEFAULT_CODE, message );
	}
	public RestFunctionException( int code, String message ) {
		super( message );
		this.code = code;
	}

	public RestFunctionException( Throwable cause ) {
		this( DEFAULT_CODE, cause );
	}
	public RestFunctionException( int code, Throwable cause ) {
		super( cause );
		this.code = code;
	}

	public RestFunctionException( String message, Throwable cause ) {
		this( DEFAULT_CODE, message, cause );
	}
	public RestFunctionException( int code, String message, Throwable cause ) {
		super( message, cause );
		this.code = code;
	}
	
	public int code(){
		return code;
	}

}
