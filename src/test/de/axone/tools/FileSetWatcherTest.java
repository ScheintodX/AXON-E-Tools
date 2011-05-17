package de.axone.tools;

import static org.testng.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
	
	private void touch( File file ) throws IOException, InterruptedException{
		PrintWriter out = new PrintWriter( new FileWriter( file ) );
		out.print( count++ );
		out.flush();
		out.close();
	}
	private void wait( int ms ) throws InterruptedException{
		E.rr( "wait: " + ms + "ms" );
		synchronized( this ){
			Thread.sleep( ms ); // Wait for sync
		}
	}

	public void testFileWatcher() throws Exception {

		File tmp = File.createTempFile( "temp1", ".txt" );
		File tmp2 = File.createTempFile( "temp2", ".txt" );
		
		wait( 1100 ); // Wait for filesystems 1s
		
		FileSetWatcher watcher = new FileSetWatcher( 100, tmp, tmp2 );

		assertTrue( watcher.hasChanged() );
		assertFalse( watcher.hasChanged() );
		
		wait( 1100 );

		// no change even after long wait
		assertFalse( watcher.hasChanged() );
		
		// Touch --------------------------------------------------
		touch( tmp );
		
		// no direct change. have to wait a little
		assertFalse( watcher.hasChanged() );

		wait( 110 );

		// changed after time > 100
		assertTrue( watcher.hasChanged() );

		wait( 1100 );

		// no change even after long wait
		assertFalse( watcher.hasChanged() );

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
		assertFalse( watcher.hasChanged() );
		touch( tmp2 );
		
		// no direct change
		assertFalse( watcher.hasChanged() );

		synchronized( this ){
			Thread.sleep( 110 );
		}

		// but after wait > 100ms
		assertTrue( watcher.hasChanged() );

		synchronized( this ){
			Thread.sleep( 1100 );
		}

		// still not after another wait time
		assertFalse( watcher.hasChanged() );

		tmp2.delete();
		// not direct 
		assertFalse( watcher.hasChanged() );
		
		E.rr( "wait 110" );
		synchronized( this ){
			Thread.sleep( 110 );
		}

		// but after wait
		assertTrue( watcher.hasChanged() );
		assertFalse( watcher.hasChanged() );

	}
}
