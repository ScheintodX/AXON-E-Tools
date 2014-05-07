package de.axone.web.rest;


public interface RestFunctionGroup<DATA, REQUEST extends RestRequest> {

	public String name();
	
	public RestFunctionDescription description();
	
	//public List<RestFunctionRoute> routes( String parent );
	
}
