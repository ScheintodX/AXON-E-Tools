package de.axone.tools.watcher;

import static org.testng.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.testng.annotations.Test;

/**
 * Test FileSetWatcher
 * 
 * For all file-operations keep in mind that the unix filesystem has 
 * a granularity of 1 second. This means that changes withhin this one
 * second would not result in a changed timestamp!
 */

@Test( groups="tools.filesetwatcher" )
public class FileSetWatcherTest {
	
	private static int count=0;
	
	private void touch( File file ) throws IOException {
		PrintWriter out = new PrintWriter( new FileWriter( file ) );
		out.print( count++ );
		out.flush();
		out.close();
	}
	private void sleep( int ms ) throws InterruptedException{
		synchronized( this ){
			//Thread.sleep( ms ); // Wait for sync
			this.wait( ms );
		}
	}

	public void testFileWatcher() throws Exception {

		File tmp = File.createTempFile( "temp1", ".txt" );
		File tmp2 = File.createTempFile( "temp2", ".txt" );
		
		sleep( 1100 ); // Wait for filesystems 1s
		
		FileListWatcher watcher = new FileListWatcher( 100, Arrays.asList( tmp, tmp2 ) );

		assertTrue( watcher.haveChanged() );
		assertFalse( watcher.haveChanged() );
		
		sleep( 1100 );

		// no change even after long wait
		assertFalse( watcher.haveChanged() );
		
		// Touch --------------------------------------------------
		touch( tmp );
		
		// no direct change. have to wait a little
		assertFalse( watcher.haveChanged() );

		sleep( 110 );

		// changed after time > 100
		assertTrue( watcher.haveChanged() );

		sleep( 1100 );

		// no change even after long wait
		assertFalse( watcher.haveChanged() );

		/*
		E.rr( "delete" );
		tmp.delete();
		// no direct reaction
		assertFalse( watcher.hasChanged() );
		
		E.rr( "wait 110" );
		synchronized( this ){
			Thread.sleep( 110 );
		}
		
		// has changed after delete
		assertTrue( watcher.hasChanged() );
		// but reports only once
		assertFalse( watcher.hasChanged() );
		*/

		// Touch2 --------------------------------------------------
		assertFalse( watcher.haveChanged() );
		touch( tmp2 );
		
		// no direct change
		assertFalse( watcher.haveChanged() );

		/*
		synchronized( this ){
			//Thread.sleep( 110 );
			this.wait( 110 );
		}
		*/
		sleep( 110 );

		// but after wait > 100ms
		assertTrue( watcher.haveChanged() );

		/*
		synchronized( this ){
			//Thread.sleep( 1100 );
			this.sleep( 1100 );
		}
		*/
		sleep( 1100 );

		// still not after another wait time
		assertFalse( watcher.haveChanged() );

		boolean ok = tmp2.delete();
		assertTrue( ok );
		// not direct 
		assertFalse( watcher.haveChanged() );
		
		/*
		synchronized( this ){
			//Thread.sleep( 110 );
			this.wait( 110 );
		}
		*/
		sleep( 110 );

		// but after wait
		assertTrue( watcher.haveChanged() );
		assertFalse( watcher.haveChanged() );

	}
}
