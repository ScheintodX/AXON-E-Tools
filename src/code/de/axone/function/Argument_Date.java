package de.axone.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Argument_Date implements Argument<Date> {
	
	public static SimpleDateFormat dateFormat 
			= new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
	
	private Date value;
	
	@Override
	public String toString() {
		return value.toString();
	}

	@Override
	public void parse( String value ) throws ShellException {
		
		this.value = stringToDate( value );
	}
	
	public static String dateToString( Date date ){
		
		return dateFormat.format( date );
	}
	public static Date stringToDate( String string ) throws ShellException{
		
		try {
			return dateFormat.parse( string );
		} catch( ParseException e ) {
			throw new ArgumentParseException( string,
					dateFormat.format( new Date() ), e );
		}
	}

	@Override
	public Date value() {
		return value;
	}

}
