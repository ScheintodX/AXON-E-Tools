package de.axone.thread;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

@Test
public class ThreadsafeContractorTest {

	ThreadsafeContractor tc = new ThreadsafeContractor();
		
		
	public void basicOperation() {
		
		Result result = new Result();
	
		tc.when( () -> { return true; } )
			.then( () -> { result.value = true; } )
			;
		
		assertTrue( result.value );
		
		tc.when( () -> { return false; } )
			.then( () -> { result.value = false; } )
			;
		
		assertTrue( result.value );
	}
	
	private static final int NUM_THREADS = 100,
	                         NUM_RUNS = 1000;
	
	public void multitreadedOperation() throws Exception {
		
		List<Thread> threads = new ArrayList<>( NUM_THREADS );
		
		for( int i=0; i<NUM_THREADS; i++ ){
			threads.add( new Thread( () -> {
				for( int j=0; j<NUM_RUNS; j++ ){
					basicOperation();
				}
			} ) );
		}
		
		threads.forEach( Thread::start );
		
		for( Thread t : threads ) {
			t.join();
		}
		
	}
	
	class Result {
		boolean value = false;
	}
}
