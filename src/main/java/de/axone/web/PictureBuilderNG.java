package de.axone.web;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface PictureBuilderNG {

	public int fileCount();
	
	public boolean exists();
	
	public Optional<Path> get( int size ) throws IOException;
}
