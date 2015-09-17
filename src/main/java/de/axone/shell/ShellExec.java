package de.axone.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import de.axone.tools.Str;

public class ShellExec {

	public static QuickResult quickexec( Path cmd, String ... args )
	throws IOException, InterruptedException {
		
		if( ! Files.isRegularFile( cmd ) )
				throw new IllegalArgumentException( "Cannot find: " + cmd );
		
		if( ! Files.isExecutable( cmd ) )
				throw new IllegalArgumentException( "Not executable: " + cmd );
				
		
		String commandline = 
				cmd.toFile().getAbsolutePath() + " " + Str.join( " ", args );
		
		Process process = Runtime.getRuntime().exec( commandline );
		
		QuickResultImpl result = new QuickResultImpl();
		
		boolean working = false;
		
		try(
				BufferedReader stdoutReader = new BufferedReader(
						new InputStreamReader( process.getInputStream()) );
				BufferedReader stderrReader = new BufferedReader(
						new InputStreamReader( process.getErrorStream()) );
		) {
		
			String line = null;
			do {
				
				line = null;
				
				if( stdoutReader.ready() ) {
					
					working = true;
						
					line = stdoutReader.readLine();
					
					if( line != null ){
						result.stdout
								.append( line )
								.append( '\n' )
								;
					}
				}
				
				if( stderrReader.ready() ) {
					
					working = true;
					
					line = stderrReader.readLine();
					
					if( line != null ){
						result.stderr
								.append( line )
								.append( '\n' )
								;
					}
				}
				
				 // If nothing to do sleep some time and wait for output
				if( !working ) Thread.sleep( 10 );
				//E._cho_( "." );
					
			} while( line != null || stdoutReader.ready() || stderrReader.ready() || process.isAlive() );
		}
		
		result.exitValue = process.exitValue();
		
		return result;
	}
	
	private static class QuickResultImpl implements QuickResult {
		
		private final StringBuffer stdout = new StringBuffer(),
		                           stderr = new StringBuffer()
		                           ;
		private int exitValue;

		@Override
		public CharSequence getStdOut() {
			return stdout;
		}

		@Override
		public CharSequence getStdErr() {
			return stderr;
		}

		@Override
		public int getExitValue() {
			return exitValue;
		}
		
		
	}
	
	interface QuickResult {
		
		CharSequence getStdOut();
		
		CharSequence getStdErr();
		
		int getExitValue();
	}
}
