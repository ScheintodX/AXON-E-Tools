package de.axone.tools.watcher;

import static org.testng.Assert.*;

import java.io.File;
import java.io.FileOutputStream;

import org.testng.annotations.Test;

import de.axone.tools.watcher.FileWatcher;

@Test( groups="tools.filewatcher" )
public class FileWatcherTest {

	public void testFileWatcher() throws Exception {

		File tmp = File.createTempFile( "temp", ".txt" );

		FileWatcher watcher = new FileWatcher( tmp, 100 );

		assertTrue( watcher.haveChanged() );
		assertFalse( watcher.haveChanged() );
		
		synchronized( this ){
			this.wait( 1100 ); //Note: Unix ctime min unit is 1s
		}

		assertFalse( watcher.haveChanged() );
		
		// Touch
		FileOutputStream out = new FileOutputStream( tmp );
		out.write( 'x' );
		out.close();
		
		assertFalse( watcher.haveChanged() );

		synchronized( this ){
			this.wait( 110 );
		}

		assertTrue( watcher.haveChanged() );

		synchronized( this ){
			this.wait( 1100 );
		}

		assertFalse( watcher.haveChanged() );

		boolean ok = tmp.delete();
		assertTrue( ok );
		assertFalse( watcher.haveChanged() );
		
		synchronized( this ){
			this.wait( 110 );
		}

		assertTrue( watcher.haveChanged() );
		assertFalse( watcher.haveChanged() );

	}
}
