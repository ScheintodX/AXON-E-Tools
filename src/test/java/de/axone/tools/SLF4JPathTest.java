package de.axone.tools;


/**
 * Test eclipse build-path settings for slf4j setting.
 * 
 * In order to function properly slf4j needs following libraries
 * in eclipse's "Java Build Path":
 * 
 * slf4j-api-xxx.jar:		Basic API Interface
 * slf4j-log4j12-xxx.jar:	Implementation of slf4j using log4j
 * log4j-xxx.jar:			Log4J libraries
 * jcl-over-slf4j-xxx.jar:	Interface from JCL (Apache Commons Logging) to slf4j
 * 
 * @author flo
 */
public class SLF4JPathTest {
	
	static org.slf4j.Logger slf4j =
			org.slf4j.LoggerFactory.getLogger( SLF4JPathTest.class );
	
	static org.apache.commons.logging.Log jcl =
			org.apache.commons.logging.LogFactory.getLog( SLF4JPathTest.class );

	public static void main( String [] args ){
		
		// This needs to output an error via slf4j -> log4j
		slf4j.info( "Here I am." );
		
		// This needs to output an error jcl -> slf4j -> log4j
		jcl.info( "Here I am, too." );
		
	}
}
