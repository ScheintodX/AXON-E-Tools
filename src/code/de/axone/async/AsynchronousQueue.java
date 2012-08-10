package de.axone.async;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AsynchronousQueue<T> extends Thread {
	
	protected final Logger log;
	
	private volatile boolean pleaseStop = false;
	
	protected LinkedList<T> queue = new LinkedList<T>();
	
	private int inProcess = 0;
	
	public AsynchronousQueue( String threadName ){
		super( threadName );
		log = LoggerFactory.getLogger( this.getClass() );
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
				inProcess = 1;
			}
		}
		return data;
	}
	
	private void sleep(){
		
		log.trace( "gotoSleep" );
		synchronized( queue ) {
			try {
				while( !pleaseStop && queue.size() == 0 ){
					queue.wait();
				}
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
				
				if( log.isTraceEnabled() ) log.trace( "Processing: " + info( data ) );
				try {
					process( data );
					synchronized( queue ){ inProcess=0; }
					if( log.isTraceEnabled() ){
						log.trace( "done: " + info( data ) );
						log.trace( "remaining:" + size() );
					}
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
			return queue.size() + inProcess;
		}
	}
	
}
	