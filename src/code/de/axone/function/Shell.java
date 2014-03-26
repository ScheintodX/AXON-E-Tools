package de.axone.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;

public class Shell {
	
	private static final String HELP = "help";
	private static final String INTERACTIVE = "--interactive";
	private static final String RUN = "--run";
	
	public static final String systemHelp = 
			"Type help COMMAND to get further help.";
	
	private static CommandMap commands = 
		new CommandMap();
	static {
		/*
		commands.put( new UserListCommand() );
		*/
	}
	
	private static ArgumentParser argumentParser = new ArgumentParserShell();
	
	public static Collection<Command> commands(){
		return commands.values();
	}
	
	public static void main( String [] args ) throws Exception {
		
		//args = new String[]{ INTERACTIVE };
		
		Shell shell = new Shell();
		PrintStream out = System.out;
		
		Result result;
		try {
			
			if( args.length == 0 ){
				result = shell.run( HELP );
			} else if( args.length == 1 && args[ 0 ].equals( INTERACTIVE ) ){
				//ExitCommand exit = new ExitCommand();
				//commands.put( exit.name(), exit);
				result = shell.interactive( System.in );
			} else if( args.length == 2 && args[ 0 ].equals( RUN ) ){
				File f = new File( args[ 1 ] );
				FileInputStream fIn = new FileInputStream( f );
				result = shell.interactive( fIn );
			} else {
				result = shell.run( args );
			}
			
			if( ! result.isOk() ){
				out.println( "ERROR" );
			}
			out.println( result.message() );
			
		} catch( ShellException e ) {
			
			out.println( "ERROR" );
			out.println( e.getMessage() );
			e.printStackTrace();
		}
		
	}
	
	public Result run( String ... args ) throws ShellException {
		
		if( args.length == 0 ) throw new WrongArgumentCountException( 0 );
		
		String name = args[0];
		Command command = find( name );
		
		if( command == null ) throw new CommandNotFoundException( name );
		
		ArgumentVector arguments = 
			argumentParser.parseArgs( command.arguments(), args );
		
		Result result = command.run( arguments );
		
		return result;
	}
	
	public static Command find( String name ) {
		
		return commands.get( name );
	}
	
	public Result interactive( InputStream ins ) throws IOException {
		
		BufferedReader in = new BufferedReader( new InputStreamReader( ins ) );
		Result result = null;
		
		System.out.println( systemHelp );
		System.out.print( "> " );
		
		String line;
		while( (line = in.readLine()) != null){
			
			try {
				String [] args = argumentParser.parseLine( line );
				result = run( args );
				
				if( !result.isOk() ){
					System.err.println( "ERROR" );
					System.err.println( result.message() );
				} else {
					System.out.println( result.message() );
				}
				
				System.out.print( "> " );
			} catch( Exception e ){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public static class CommandMap extends HashMap<String,Command> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2055035604320220554L;

		public void put( Command command ){
			put( command.name(), command );
		}
	}
}
