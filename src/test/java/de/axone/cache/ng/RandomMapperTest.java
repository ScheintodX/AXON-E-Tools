package de.axone.cache.ng;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.cache.ng.RandomMapper;
import de.axone.tools.E;

@Test( groups="cache.tools" )
public class RandomMapperTest {
	
	private static final int RUNS = 1_000_000;

	public void checkDistributionAndSpeedOfMappedValues(){
		
		long start = System.currentTimeMillis();
		int c = 0;
		for( int i=0; i<RUNS; i++ ){
			
			int rnd = RandomMapper.integer( i );
			
			if( rnd > 0 ) c++;
			else if( rnd < 0 ) c--;
		}
		long end = System.currentTimeMillis();
		
		E.rr( RUNS + " values (" + c + ") in " + (end-start) + " ms" );
		
		assertThat( c ).isBetween( -100, 100 );
		
		assertThat( end-start ).isLessThan( 1000 ); // 1s
	}
	
	public void checkThatSameValuesLeadToSameRandoms(){
		
		for( int i=0; i<RUNS; i++ ){
			
			// Distribute test values deterministically
			int j = RandomMapper.integer( i );
			
			assertThat( RandomMapper.integer( j ) )
				.isEqualTo( RandomMapper.integer( j ) );
			
		}
	}
	
	public void checkAverageValueAndAverageSpacing(){
		
		long sum = 0;
		long posSum = 0;
		for( int i=0; i<RUNS; i++ ){
	
			sum += RandomMapper.integer( i );
			posSum += RandomMapper.positiveInteger( i );
		}
		
		// These are experimental values. But they guarantee some assumption
		// and are good enough.
		// Just make sure changes on RandomMapper don't make this worse.
		
		assertThat( sum ).isBetween( -10L*Integer.MAX_VALUE, 10L*Integer.MAX_VALUE );
		
		long posAvg = posSum / RUNS;
		assertThat( posAvg ).isBetween( Integer.MAX_VALUE/2-2000L, Integer.MAX_VALUE/2+2000L );
		
	}
	
	public void checkThatWeAreStableBetweenRuns(){
		
		assertEquals( RandomMapper.integer( 0 ), 1033096058 );
		assertEquals( RandomMapper.integer( 1 ), 1761283695 );
		assertEquals( RandomMapper.integer( -1 ), 52699159 );
		assertEquals( RandomMapper.integer( Integer.MAX_VALUE ), -649748457 );
		assertEquals( RandomMapper.integer( Integer.MIN_VALUE ), 1735543674 );
		
	}
	
	public void checkThatWeAreUsingTheSameMethodAsRandom(){
		
		for( int i=0; i<RUNS; i++ ){
			
			// Distribute test values deterministically
			int j = RandomMapper.integer( i );
			
			assertEquals( RandomMapper.integer( j ), RandomMapper.integerUsingRandom( j ) );
		}
		
	}
	
}
