package de.axone.async;

import java.util.LinkedList;

import de.axone.logging.Log;
import de.axone.logging.Logging;

public abstract class AsynchronousQueue<T> extends Thread {
	
	protected final Log log;
	
	private volatile boolean pleaseStop = false;
	
	protected LinkedList<T> queue = new LinkedList<T>();
	
	public AsynchronousQueue( String threadName ){
		super( threadName );
		log = Logging.getLog( this.getClass() );
	}
	
	public void push( T data ){
		
		synchronized( queue ){
			queue.addLast( data );
			queue.notifyAll();
		}
	}
	
	private T pop(){
		
		T data = null;
		
		synchronized( queue ) {

			if( queue.size() > 0 ) {
				data = queue.removeFirst();
			}
		}
		return data;
	}
	
	private void sleep(){
		
		log.trace( "gotoSleep" );
		synchronized( queue ) {
			try {
				if( !pleaseStop && queue.size() == 0 ) queue.wait();
			} catch( InterruptedException e ) {}
		}
		log.trace( "woke up" );
	}
	
	protected abstract String info( T data );
	protected abstract void process( T data ) throws Exception;

	@Override
	public void run() {

		log.debug( "STARTING" );

		do {

			T data = pop();

			if( data != null ) {
				
				if( log.isTrace() ) log.trace( "Processing: " + info( data ) );
				try {
					process( data );
					if( log.isTrace() ) log.trace( "done: " + info( data ) );
				} catch( Exception e ){
					log.error( "Error processing: " + info( data ), e );
				}
			}

			sleep();
			
		} while( !pleaseStop );

		log.debug( "FINISHED" );
	}

	public void pleaseStop(){
		pleaseStop = true;

		synchronized( queue ) {
			queue.notifyAll();
		}
	}
	
	public int size(){
		synchronized( queue ){
			return queue.size();
		}
	}
	
}
	