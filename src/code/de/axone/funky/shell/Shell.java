package de.axone.funky.shell;

import de.axone.funky.Environment;
import de.axone.funky.FunctionDescription;

public class Shell implements Environment<Caller_Shell>{
	
	private static final Manual_Shell manual = new Manual_Shell();
	private static final ArgumentParser_Shell parser = new ArgumentParser_Shell();

	@Override
	public void man( FunctionDescription description ) {
		System.err.println( manual.explain( description ) );
	}

	@Override
	public ArgumentParser_Shell parser() {
		return parser;
	}


}
