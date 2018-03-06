package de.axone.web;

import java.io.File;

public class PathUtilOffline implements PathUtil {
	
	private final File settingsFile;
	public PathUtilOffline( File settingsFile ){
		this.settingsFile = settingsFile;
	}
	
	@Override
	public File realFile( String file ) {
		return new File( file );
	}

	@Override
	public File settingsFile() {
		return settingsFile;
	}
}
