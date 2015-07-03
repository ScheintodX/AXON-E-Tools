package de.axone.thread;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class DoLocker {
	
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final ReadLock readlock = lock.readLock();
	private final WriteLock writelock = lock.writeLock();
	
	public boolean doTested( Predicate test, Operation operation ) {
		
		boolean testResult;
		
		readlock.lock();
		try {
			testResult = test.test();
			
		} finally { readlock.unlock(); }
		
		if( testResult ) {
			
			writelock.lock();
			try {
				if( test.test() ){
					operation.run();
				}
			} finally { writelock.unlock(); }
				
		}
		
		return testResult;
	}
	
	public boolean doTestedIO( PredicateIO test, OperationIO operation ) throws IOException {
		
		boolean testResult;
		
		readlock.lock();
		try {
			testResult = test.test();
			
		} finally { readlock.unlock(); }
		
		if( testResult ) {
			
			writelock.lock();
			try {
				if( test.test() ) {
					operation.run();
				}
			} finally { writelock.unlock(); }
				
		}
		
		return testResult;
	}
	
	@FunctionalInterface
	public interface Predicate {
		public boolean test();
	}
	
	@FunctionalInterface
	public interface Operation {
		public void run();
	}
	
	@FunctionalInterface
	public interface PredicateIO {
		public boolean test() throws IOException;
	}
	
	@FunctionalInterface
	public interface OperationIO {
		public void run() throws IOException;
	}

}
