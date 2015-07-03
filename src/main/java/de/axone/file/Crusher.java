package de.axone.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class Crusher {

	public static void crushDirIfExists( Path path ) throws IOException {
		
		if( Files.isDirectory( path ) ) {
			
			if( ! Files.isWritable( path.getParent() ) )
					throw new IOException( "Not writable: " + path.getParent() );
			
			crushDir( path );
		}
	}
	
	public static void crushDir( Path path ) throws IOException {
		
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
	    	
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
				//E.rr( "DELETE", file );
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
				// try to delete the file anyway, even if its attributes
				// could not be read, since delete-only access is
				// theoretically possible
				//E.rr( "DELETE", file );
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}
			
			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
				
				if (exc == null){
					//E.rr( "DELETE", dir );
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				} else {
					// directory iteration failed; propagate exception
					throw exc;
				}
			}
		});
	}
}
	