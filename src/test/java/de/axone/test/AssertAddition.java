package de.axone.test;

import static org.testng.Assert.*;

import java.util.Collection;

public class AssertAddition {

	public static void assertNotEmpty( Collection<?> collection, String message ) {
		
		assertNotNull( collection, message );
		assertNotEquals( collection.size(), 0, message );
		
	}
	public static void assertNotEmpty( Collection<?> collection ) {
		
		assertNotNull( collection );
		assertNotEquals( collection.size(), 0 );
	}
}
