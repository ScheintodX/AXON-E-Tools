package de.axone.tools;


import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

@Test( groups="tools.classconfiguration" )
public class ClassConfiguratorTest {
	
	private static final String TESTCLASS = "de.axone.tools.ClassConfiguratorTest_TestClass";
	
	public void testSimpleCreation() throws Exception {
		
		Object instance = ClassConfigurator.create( TESTCLASS );
		assertThat( instance ).isInstanceOf( ClassConfiguratorTest_TestClass.class );
	}
	
	public void testParameter() throws Exception {
		
		String clazz = TESTCLASS + "	( x =	'5', yyy= '6'	, z12345EneMene='Muh/Mäh&123\\' ) ";
		
		Object instance = ClassConfigurator.create( clazz );
		assertThat( instance ).isInstanceOf( ClassConfiguratorTest_TestClass.class );
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		
		assertEquals( t.x, "5" );
		assertEquals( t.yyy, "6" );
		assertEquals( t.z12345EneMene, "Muh/Mäh&123\\" );
	}
	
	public void testEmptyParameters() throws Exception {
		
		String clazz = TESTCLASS + "( yyy='', z12345EneMene='' )";
		
		Object instance = ClassConfigurator.create( clazz );
		assertThat( instance ).isInstanceOf( ClassConfiguratorTest_TestClass.class );
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		
		assertNull( t.x );
		assertEquals( t.yyy, "" );
		assertEquals( t.z12345EneMene, "" );
	}

	public void testConstructor() throws Exception {
		
		String clazz = TESTCLASS + "(yyy='test')";
		
		Class<?> [] cClasses = new Class<?>[]{ Integer.class, String.class };
		Object [] cParas = new Object[]{ 5, "abc" };
		
		Object instance = ClassConfigurator.create( clazz, cClasses, cParas );
		
		assertThat( instance ).isInstanceOf( ClassConfiguratorTest_TestClass.class );
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		assertEquals( t.i, Integer.valueOf( 5 ) );
		assertEquals( t.x, "abc" );
		assertEquals( t.yyy, "test" );
	}
}
