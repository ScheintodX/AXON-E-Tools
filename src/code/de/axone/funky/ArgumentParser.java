package de.axone.funky;

import java.util.Map;

public interface ArgumentParser {

	public Map<String,Object> parse( FunctionDescription function, String line );

}
