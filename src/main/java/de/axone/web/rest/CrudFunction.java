package de.axone.web.rest;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.web.Method;
import de.axone.web.SuperURL;

public interface CrudFunction<ID, DATA, REQUEST extends RestRequest>
extends RestFunctionGroup<DATA, REQUEST> {
	
	@Override
	public String name();
	
	public ID parseId( DATA data, Method method, String id, Map<String,String> parameters, REQUEST req ) throws Exception;
	
	public void doCreate( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doRead( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doUpdate( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doDelete( DATA data, Method method, ID id, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	
	public void doList( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doConfig( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	
	public void doOther( DATA data, Method method, Map<String, String> parameters,
			SuperURL url, PrintWriter out, REQUEST req, HttpServletResponse resp ) throws Exception;
	
	public void registerMe( RestFunctionRegistry<DATA,REQUEST> reg );
	
}
