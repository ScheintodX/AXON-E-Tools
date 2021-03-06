package de.axone.web.rest;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.web.Method;
import de.axone.web.SuperURL;

public interface RestFunction<DATA, REQUEST extends RestRequest> {

	public void run( DATA data, Method method, Map<String,String> parameters,
			SuperURL url, PrintWriter out, REQUEST req, HttpServletResponse resp ) throws Exception ;

	public String name();

	public RestFunctionDescription description();


	// Simple HELPER
	public static final RestFunctionDescription.Builder DESC
			= new RestFunctionDescription.Builder();
}
