package de.axone.tools;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;

import org.testng.annotations.Test;

@Test( groups="tools.filewatcher" )
public class FileWatcherTest {

	public void testFileWatcher() throws Exception {

		File tmp = File.createTempFile( "temp", ".txt" );

		FileWatcher watcher = new FileWatcher( tmp, 100 );

		assertTrue( watcher.hasChanged() );
		assertFalse( watcher.hasChanged() );

		synchronized( this ){
    		this.wait( 50 );
		}

		assertFalse( watcher.hasChanged() );

		// Touch
		FileOutputStream out = new FileOutputStream( tmp );
		out.write( 'x' );
		out.close();

		assertFalse( watcher.hasChanged() );

		synchronized( this ){
    		this.wait( 60 );
		}

		assertTrue( watcher.hasChanged() );
		assertFalse( watcher.hasChanged() );

		tmp.delete();

		assertFalse( watcher.hasChanged() );

		synchronized( this ){
    		this.wait( 110 );
		}

		assertTrue( watcher.hasChanged() );
		assertFalse( watcher.hasChanged() );
	}
}
