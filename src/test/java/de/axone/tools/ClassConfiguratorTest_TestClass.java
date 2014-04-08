package de.axone.tools;

public class ClassConfiguratorTest_TestClass {

	Integer i;
	String x;
	String yyy;
	String z12345EneMene;
	
	// Empty Constructor, do nothing
	public ClassConfiguratorTest_TestClass(){
	}
	
	// Parameter-Contructor: init vlaues
	public ClassConfiguratorTest_TestClass( Integer i, String x ){
		this.i = i;
		this.x = x;
	}
	
	public void setX( String value ){
		this.x = value;
	}

	public void setYyy( String value ){
		this.yyy = value;
	}
	
	public void setZ12345EneMene( String value ){
		this.z12345EneMene = value;
	}
	
}
