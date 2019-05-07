package de.axone.web.rest;

public abstract class AbstractRestFunction<DATA, REQUEST extends RestRequest>
	implements RestFunction<DATA,REQUEST> {

	private final RestFunctionDescription description;

	public AbstractRestFunction( RestFunctionDescription description ) {
		this.description = description;
	}

	@Override
	public String name() {
		return description.getName();
	}

	@Override
	public RestFunctionDescription description() {
		return description;
	}
}
