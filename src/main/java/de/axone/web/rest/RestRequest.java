package de.axone.web.rest;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.json.JsonMapper;

import de.axone.web.Method;

public interface RestRequest extends HttpServletRequest {

	public abstract JsonMapper.Builder jmb();

	default public Method getRestMethod() {
		return Method.valueOf( getMethod() );
	}

}