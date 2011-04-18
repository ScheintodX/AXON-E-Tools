package de.axone.tools;


import static de.axone.test.Assert.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.classconfiguration" )
public class ClassConfiguratorTest {
	
	private static final String TESTCLASS = "de.axone.tools.ClassConfiguratorTest_TestClass";
	
	public void testSimpleCreation() throws Exception {
		
		Object instance = ClassConfigurator.create( TESTCLASS );
		assertIsInstance( instance, ClassConfiguratorTest_TestClass.class );
	}
	
	public void testParameter() throws Exception {
		
		String clazz = TESTCLASS + "	( x =	'5', yyy= '6'	, z12345EneMene='Muh' ) ";
		
		Object instance = ClassConfigurator.create( clazz );
		assertIsInstance( instance, ClassConfiguratorTest_TestClass.class );
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		
		assertEquals( t.x, "5" );
		assertEquals( t.yyy, "6" );
		assertEquals( t.z12345EneMene, "Muh" );
	}
	
	public void testEmptyParameters() throws Exception {
		
		String clazz = TESTCLASS + "( yyy='', z12345EneMene='' )";
		
		Object instance = ClassConfigurator.create( clazz );
		assertIsInstance( instance, ClassConfiguratorTest_TestClass.class );
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		
		assertNull( t.x );
		assertEmpty( t.yyy );
		assertEmpty( t.z12345EneMene );
	}

}
