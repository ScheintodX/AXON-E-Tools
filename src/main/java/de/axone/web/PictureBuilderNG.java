package de.axone.web;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Optional;

public interface PictureBuilderNG {

	/**
	 * @return count of pictures in given directory
	 */
	public int fileCount();
	
	/**
	 * @return true if a picture is available
	 */
	public boolean exists();
	
	/**
	 * @return time of last modification or creation
	 * @throws IOException 
	 */
	public FileTime mtime() throws IOException;
	
	/**
	 * @return optional path in filesystem to picture
	 * 
	 * @param size
	 * @throws IOException
	 */
	public Optional<Path> get( int size ) throws IOException;
	
	/**
	 * @return what we are looking for.
	 * 
	 * This returns a result even if there is no file present.
	 * Used for reporting missing files.
	 */
	public Path lookingAt();

}
