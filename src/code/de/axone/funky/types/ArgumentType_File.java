package de.axone.funky.types;

import java.io.File;

import de.axone.funky.ArgumentType;

public class ArgumentType_File implements ArgumentType<File> {

	@Override
	public Class<File> type() { return File.class; }
	
	@Override
	public String name() { return "File"; }

	@Override
	public File parse( String value ) {
		return new File( value );
	}


}
