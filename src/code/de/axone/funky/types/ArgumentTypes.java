package de.axone.funky.types;

import java.io.File;

import de.axone.funky.ArgumentType;

public abstract class ArgumentTypes {

	public static ArgumentType<Boolean> BOOLEAN = new ArgumentType_Boolean();
	public static ArgumentType<String> STRING = new ArgumentType_String();
	public static ArgumentType<File> FILE = new ArgumentType_File();
	
	public static <T extends Enum<T>> ArgumentType<T> ENUM( Class<T> clazz ){
		return new ArgumentType_Enum<>( clazz );
	}
	
}
