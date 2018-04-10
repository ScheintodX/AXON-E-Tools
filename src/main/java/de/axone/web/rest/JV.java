package de.axone.web.rest;

public interface JV {
	
	// from hvm
	public interface Basic {}
	public interface List extends Basic {}
	public interface Extended extends List {}
	public interface Full extends Extended {}
	public interface Search extends Basic {}
}
