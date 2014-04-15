package de.axone.function;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;


public class Argument_Date implements Argument<Date> {
	
	private static final SimpleDateFormat dateFormat 
			= new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
	
	private Date value;
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	public static SimpleDateFormat dateFormat(){
		return (SimpleDateFormat)dateFormat.clone();
	}

	@Override
	public void parse( String value ) throws ShellException {
		
		this.value = stringToDate( value );
	}
	
	public static String dateToString( Date date ){
		
		return dateFormat().format( date );
	}
	public static Date stringToDate( String string ) throws ShellException{
		
		try {
			return dateFormat().parse( string );
		} catch( ParseException e ) {
			throw new ArgumentParseException( string,
					dateFormat().format( new Date() ), e );
		}
	}

	@Override
	@SuppressFBWarnings("EI_EXPOSE_REP")
	public Date value() {
		return value;
	}

}
