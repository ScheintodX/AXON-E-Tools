package de.axone.tools;

import org.testng.annotations.Test;

import de.axone.data.tupple.Pair;

@Test( groups="tools.pairs" )
public class PairTest {

	public void testPair(){
		
		String aString = "aString";
		String bString = "bString";
		String cString = "cString";
		
		StringBuilder aStringBuilder = new StringBuilder();
		aStringBuilder.append( 'a' );
		aStringBuilder.append( "String" );
		String aNotherString = aStringBuilder.toString();
		
		Pair<String, String> abPair = new Pair<String,String>( aString, bString );
		Pair<String, String> baPair = new Pair<String,String>( bString, aString );
		Pair<String, String> anotherbPair = new Pair<String,String>( aNotherString, bString );
		Pair<String, String> acPair = new Pair<String,String>( aString, cString );
		Pair<String, String> bcPair = new Pair<String,String>( bString, cString );
		
		assert abPair != anotherbPair;
		assert abPair != baPair;
		assert abPair != acPair;
		assert abPair != bcPair;
		assert acPair != bcPair;
		
		assert abPair.hashCode() == anotherbPair.hashCode();
		assert abPair.hashCode() != baPair.hashCode();
		assert abPair.hashCode() != acPair.hashCode();
		assert acPair.hashCode() != bcPair.hashCode();
		
		assert abPair.equals( anotherbPair );
		assert ! abPair.equals( baPair );
		assert ! abPair.equals( acPair );
		assert ! acPair.equals( bcPair );
	}
}
