package de.axone.web.rest;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.axone.web.Method;

public interface RestRequest extends HttpServletRequest {

	public abstract ObjectMapper mapper();

	default public Method getRestMethod() {
		return Method.valueOf( getMethod() );
	}

}