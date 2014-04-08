
public class SyntaxTest {

	
	class A<T extends CharSequence> {
		
	}
	
	class B extends A<String> {
		
	}
	
	class C<T extends CharSequence> extends A<T> {
		
	}
	
}
