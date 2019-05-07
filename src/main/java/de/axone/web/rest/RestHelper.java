package de.axone.web.rest;

import java.io.IOException;

public abstract class RestHelper {

	public static <T> T readData( RestRequest req, Class<T> type )
			throws RestFunctionException {

		T jsonData;

		String data = req.getParameter( "data" );

		try {
			if( data != null ){
				jsonData = req.mapper().readValue( data, type );
			} else {
				jsonData = req.mapper().readValue( req.getInputStream(), type );
			}

		} catch( IOException e ) {
			e.printStackTrace();
			throw new RestFunctionException( "Cannot read JSON String", e );
		}

		return jsonData;
	}

}
