package de.axone.funky.shell;

import de.axone.funky.Environment;
import de.axone.funky.FunctionDescription;
import de.axone.funky.Manual;

public class Shell implements Environment<Caller_Shell>{
	
	private static final Manual<Caller_Shell> manual = new Manual_Shell();

	@Override
	public void man( FunctionDescription description ) {
		System.err.println( manual.explain( description ) );
	}

}
