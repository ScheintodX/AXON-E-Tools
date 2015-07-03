package de.axone.thread;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class ThreadsafeContractor {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final ReadLock READlock = lock.readLock();
	private final WriteLock WRITElock = lock.writeLock();
	
	public <T extends Throwable> Executor when( Condition condition ) throws T{
		
		READlock.lock();
		try {
			if( condition.test() ){
				return new ExecutorImpl( condition );
			} else {
				return new NoExecutorImpl();
			}
		} finally { READlock.unlock(); }
	}
	
	
	public interface Executor {
		public <T extends Throwable> void then( Operation<T> operation ) throws T;
	}
	
	
	private class ExecutorImpl implements Executor {
		
		final Condition test;
		ExecutorImpl( Condition test ){
			this.test = test;
		}

		@Override
		public <T extends Throwable> void then( Operation<T> operation ) throws T {
			WRITElock.lock();
			try {
				// Retest if the condition was fullfilled in the meantime
				if( test.test() ) {
					operation.run();
				}
			} finally { WRITElock.unlock(); }
		}
	}
	
	
	private class NoExecutorImpl implements Executor {

		@Override
		public <T extends Throwable> void then( Operation<T> op ) throws T {
			//NOP
		}
		
	}
	
	
	@FunctionalInterface
	public interface Condition {
		public boolean test();
	}
	
	@FunctionalInterface
	public interface Operation<T extends Throwable> {
		public void run() throws T;
	}
	
}
