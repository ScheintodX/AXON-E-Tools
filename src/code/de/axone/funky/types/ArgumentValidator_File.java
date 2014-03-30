package de.axone.funky.types;

import java.io.File;

import de.axone.exception.Assert;
import de.axone.funky.ArgumentValidator;

public class ArgumentValidator_File implements ArgumentValidator<File> {
	
	private final Boolean exists;
	private final Boolean isDir;
	private final Boolean isFile;
	private final Boolean canRead;
	
	public ArgumentValidator_File( Boolean exists, Boolean isDir,
			Boolean isFile, Boolean canRead ) {
		
		Assert.isFalse( isDir && isFile, "Can only be either dir or file" );
		
		if( isDir || isFile || canRead ) exists = true;
		
		this.exists = exists;
		this.isDir = isDir;
		this.isFile = isFile;
		this.canRead = canRead;
	}
	
	@Override
	public String description() {
		return "Must" +
				(exists ? " exist" : "") +
				(isDir ? " and be a directory " : "") +
				(isFile ? " and be a file " : "") +
				(canRead ? " and be readable" : "");
	}

	@Override
	public String validate( File file ) {
		if( exists && ! file.exists() )
			return "does not exist";
		if( isDir && ! file.isDirectory() )
			return "is not a directory";
		if( isDir && ! file.isFile() )
			return "is not a file";
		if( isDir && ! file.canRead() )
			return "cannot read";
		return null;
	}


}
