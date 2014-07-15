package de.axone.cache.ng;

import java.util.Random;

import org.testng.annotations.Test;

import de.axone.equals.Jenkins96Backend;
import de.axone.tools.E;

@Test( groups="cacheng.random" )
public class CacheNGTest_GoodRandomValueFromHashes {

	Jenkins96Backend j = new Jenkins96Backend();
			
	public void generateSomeHashesAndTestTheirDistribution() {
		
		Random rnd = new Random();
		
		int distJavaHash=0,
			distJava2Byte=0,
			distShuffle=0,
		    distJenkinsHash=0,
		    distRandom=0,
		    stableRandom=0,
		    stableRandom5=0;
		
		for( int i=0; i< 12000; i++ ){
			
			String s = String.format( "%05d", i );
			
			int hash = s.hashCode();
			short java2 = (short)hash;
			int jenk = (int)j.hash( s );
			int shuf = RandomMapper.integer( hash );
			
			if( rnd.nextInt() > 0 ) distRandom++;
			else distRandom--;
			
			if( hash > 0 ) distJavaHash++;
			else distJavaHash--;
			
			if( java2 > 0 ) distJava2Byte++;
			else distJava2Byte--;
			
			if( jenk > 0 ) distJenkinsHash++;
			else distJenkinsHash--;
			
			if( shuf > 0 ) distShuffle++;
			else distShuffle--;
			
			Random rnd2 = new Random( hash );
			if( rnd2.nextInt() > 0 ) stableRandom++;
			else stableRandom--;
			
			for( int x=0; x<5; x++ ) rnd2.nextInt();
			
			if( rnd2.nextInt() > 0 ) stableRandom5++;
			else stableRandom5--;
		}
		
		E.cho( "DISTRIBUTION of RANDOMNESS. Smaller is (somewhat) better." );
		E.cho( "Random: " + distRandom );
		E.cho( "Stable-Random: " + stableRandom );
		E.cho( "Stable-Random-5: " + stableRandom5 );
		E.cho( "Java-Hash: " + distJavaHash );
		E.cho( "Java-2Byte-Hash: " + distJava2Byte );
		E.cho( "*** ShuffleMapper ***: " + distShuffle );
		E.cho( "Jenkins-Hash: " + distJenkinsHash );
		
	}
}
