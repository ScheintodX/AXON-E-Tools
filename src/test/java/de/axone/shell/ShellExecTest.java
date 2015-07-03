package de.axone.shell;

import static org.testng.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import de.axone.shell.ShellExec.QuickResult;

@Test
public class ShellExecTest {
	
	// Known commands
	private static final Path ECHO = Paths.get( "/bin/echo" ),
	                          LS = Paths.get( "/bin/ls" );

	public void testNormalOutput() throws Exception {
		
		QuickResult result = ShellExec.quickexec( ECHO, "Hello World" );
		
		assertEquals( result.getStdOut().toString(), "Hello World\n" );
		assertEquals( result.getStdErr().toString(), "" );
		assertEquals( result.getExitValue(), 0 );
		
	}
	public void testErrorOutput() throws Exception {
		
		QuickResult result = ShellExec.quickexec( LS, "-y" );
		
		assertEquals( result.getStdOut().toString(), "" );
		assertEquals( result.getStdErr().toString(), "/bin/ls: invalid option -- 'y'\nTry '/bin/ls --help' for more information.\n" );
		assertNotEquals( result.getExitValue(), 0 );
	}
}
