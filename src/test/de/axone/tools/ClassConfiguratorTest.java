package de.axone.tools;

import org.testng.annotations.Test;

import de.axone.tools.ClassConfigurator;

@Test( groups="tools.classconfiguration" )
public class ClassConfiguratorTest {
	
	public void testSimpleCreation() throws Exception {
		
		ClassConfigurator confi = new ClassConfigurator();
		
		String clazz = "de.axone.tools.ClassConfiguratorTest_TestClass";
		
		Object instance = confi.create( clazz );
		assert instance instanceof ClassConfiguratorTest_TestClass;
	}
	
	public void testParameter() throws Exception {
		
		ClassConfigurator confi = new ClassConfigurator();
		
		String clazz = "de.axone.tools.ClassConfiguratorTest_TestClass( x=5, yyy=6, z12345EneMene=Muh )";
		
		Object instance = confi.create( clazz );
		assert instance instanceof ClassConfiguratorTest_TestClass;
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		
		assert "5".equals( t.x );
		assert "6".equals( t.yyy );
		assert "Muh".equals( t.z12345EneMene );
	}
	
	public void testEmptyParameters() throws Exception {
		
		ClassConfigurator confi = new ClassConfigurator();
		
		String clazz = "de.axone.tools.ClassConfiguratorTest_TestClass( yyy=, z12345EneMene= )";
		
		Object instance = confi.create( clazz );
		assert instance instanceof ClassConfiguratorTest_TestClass;
		
		ClassConfiguratorTest_TestClass t = (ClassConfiguratorTest_TestClass)instance;
		
		assert null == t.x;
		assert "".equals( t.yyy );
		assert "".equals( t.z12345EneMene );
	}
	

}
