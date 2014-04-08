package de.axone.async;

import java.util.HashSet;

/**
 * Provide a Queue for Threads to wait for resource come available
 * 
 * Paralel threads can get access to different resources, but not
 * to the same.
 * 
 * @author flo
 */
public class ThreadQueue {
	
	private int size;
	private HashSet<String> resources = new HashSet<String>();
	
	public ThreadQueue( int size ){
		this.size=size;
	}

	public synchronized Lock lock( String resource ){
		
		while( resources.size() == size || resources.contains( resource ) ){
			try{
				wait();
			} catch( InterruptedException e ){}
		}
		resources.add( resource );
		return new LockImpl( resource );
	}
	public synchronized void releaseLock( Lock lock ){
		resources.remove( ((LockImpl)lock).resource );
		notifyAll();
	}
	
	public interface Lock {}
	
	private static class LockImpl implements Lock {
		private String resource;
		private LockImpl( String resource ){
			this.resource = resource;
		}
		@Override
		public String toString(){
			return resource;
		}
	}
}
