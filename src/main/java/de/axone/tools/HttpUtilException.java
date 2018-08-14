package de.axone.tools;

import de.axone.tools.HttpUtil.HttpUtilResponse;
import de.axone.web.HttpStatusCode;

public class HttpUtilException extends RuntimeException {
	
	private final HttpStatusCode code;
	private final HttpUtilResponse response;

	public HttpUtilException( HttpStatusCode code, HttpUtilResponse response ) {
		
		this.code = code;
		this.response = response;
	}

	public HttpStatusCode getCode() {
		return code;
	}
	
	public HttpUtilResponse getResponse() {
		return response;
	}
}
