package de.axone.tools;

public class D {
	
	private static volatile boolean isEnabled = true;
	
	public static void enable(){
		isEnabled = true;
	}
	public static void disable(){
		isEnabled = false;
	}
	
	public static boolean o(){
		return isEnabled;
	}

	public static void bug( Object ...objects ){
		E.rr( objects );
	}
}
