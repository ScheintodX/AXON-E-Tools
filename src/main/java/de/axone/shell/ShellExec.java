package de.axone.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.axone.tools.ToString;

public class ShellExec {

	private static final Logger log = LoggerFactory.getLogger( ShellExec.class );

	public interface Arg {
		public String [] commandLine();
	}

	public static Arg Arg( String value ) {
		final String [] arg = new String [] { value };
		return () -> arg;
	}

	public static QuickResult quickexec( Path cmd, Arg ... args )
	throws IOException, InterruptedException {
		return quickexec( -1, cmd, args );
	}

	public static QuickResult quickexec( long timeout, Path cmd, Arg ... args )
	throws IOException, InterruptedException {

		if( ! Files.isRegularFile( cmd ) )
				throw new IllegalArgumentException( "Cannot find: " + cmd );

		if( ! Files.isExecutable( cmd ) )
				throw new IllegalArgumentException( "Not executable: " + cmd );

		// Build one String array to pass to shell exec
		ArrayList<String> joinedArgs = new ArrayList<>();
		joinedArgs.add( cmd.toFile().getAbsolutePath() );
		for( Arg arg : args ) {
			for( String part : arg.commandLine() ) {
				joinedArgs.add( part );
			}
		}
		log.debug( "{}", joinedArgs );


		QuickResultImpl result = new QuickResultImpl();
		boolean working = false;

		final Process process = Runtime.getRuntime().exec( joinedArgs.toArray( new String[ joinedArgs.size() ] ) );

		try(
				BufferedReader stdoutReader = new BufferedReader(
						new InputStreamReader( process.getInputStream()) );
				BufferedReader stderrReader = new BufferedReader(
						new InputStreamReader( process.getErrorStream()) );
				Destroyer destroyer = new Destroyer( process, timeout );
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
				if( !working )
				 {
					Thread.sleep( 10 );
					//E._cho_( "." );
				}

			} while( line != null || stdoutReader.ready() || stderrReader.ready() || process.isAlive() );
		}

		result.exitValue = process.exitValue();

		return result;
	}


	/* This is error prone
	public static QuickResult quickexec( Path cmd, String ... args )
	throws IOException, InterruptedException {

		if( ! Files.isRegularFile( cmd ) )
				throw new IllegalArgumentException( "Cannot find: " + cmd );

		if( ! Files.isExecutable( cmd ) )
				throw new IllegalArgumentException( "Not executable: " + cmd );

		String commandline =
				cmd.toFile().getAbsolutePath() + " " + Str.join( " ", args );

		log.debug( commandline );

		//
		 //* Doesn't work because we need parameters completely separated
		//String [] commandArgs = new String[ args.length + 1 ];
		//commandArgs[ 0 ] = cmd.toFile().getAbsolutePath();
		//System.arraycopy( args, 0, commandArgs, 1, args.length );

		//Process process = Runtime.getRuntime().exec( commandline );
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
				if( !working )
				 {
					Thread.sleep( 10 );
					//E._cho_( "." );
				}

			} while( line != null || stdoutReader.ready() || stderrReader.ready() || process.isAlive() );
		}

		result.exitValue = process.exitValue();

		return result;
	}
	*/

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


		@Override
		public String toString() {

			return ToString.build( QuickResultImpl.class )
					.append( "stdout", stdout.toString() )
					.append( "stderr", stderr.toString() )
					.append( "exitValue", exitValue )
					.toString()
					;
		}

	}

	public interface QuickResult {

		CharSequence getStdOut();

		CharSequence getStdErr();

		int getExitValue();
	}

	public static class ShellException extends RuntimeException {

		public ShellException( QuickResult result ) {

			super( "(" + result.getExitValue() + ") :" + result.getStdErr() );
		}

	}



	private static final class Destroyer extends TimerTask implements AutoCloseable {

		private final Process destroyMe;
		private final Timer timer;

		Destroyer( Process destroyMe, long timeout ){
			if( timeout > 0 ) {
				this.destroyMe = destroyMe;
				this.timer = new Timer();
				timer.schedule( this, timeout );
			} else {
				this.timer = null;
				this.destroyMe = null;
			}
		}

		@Override
		public void run() {
			destroyMe.destroy();
		}

		@Override
		public void close() {
			if( timer != null ) {
				timer.cancel();
				log.trace( "destroyer canceled" );
			} else {
				log.trace( "destroyer not used" );
			}
		}

	}
}
