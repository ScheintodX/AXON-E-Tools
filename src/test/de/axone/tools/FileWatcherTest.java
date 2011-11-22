package de.axone.tools;

import static org.testng.Assert.*;

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
			this.wait( 1100 ); //Note: Unix ctime min unit is 1s
		}

		assertFalse( watcher.hasChanged() );
		
		// Touch
		FileOutputStream out = new FileOutputStream( tmp );
		out.write( 'x' );
		out.close();
		
		assertFalse( watcher.hasChanged() );

		synchronized( this ){
			this.wait( 110 );
		}

		assertTrue( watcher.hasChanged() );

		synchronized( this ){
			this.wait( 1100 );
		}

		assertFalse( watcher.hasChanged() );

		boolean ok = tmp.delete();
		assertTrue( ok );
		assertFalse( watcher.hasChanged() );
		
		synchronized( this ){
			this.wait( 110 );
		}

		assertTrue( watcher.hasChanged() );
		assertFalse( watcher.hasChanged() );

	}
}
