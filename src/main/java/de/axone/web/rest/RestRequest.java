package de.axone.web.rest;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.axone.web.Method;

public interface RestRequest extends HttpServletRequest {

	public abstract Method getRestMethod();

	public abstract ObjectMapper mapper();

}