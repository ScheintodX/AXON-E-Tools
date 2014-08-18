package de.axone.data;

public class Tripple<A, B, C> implements Comparable<Tripple<A, B, C>>{
	 
    private final A a;
    private final B b;
    private final C c;
 
    public A getA() {
        return a;
    }
 
    public B getB() {
        return b;
    }
    
    public C getC() {
    	return c;
    }
 
    public Tripple( final A a, final B b, final C c ) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public static <X, Y, Z> Tripple<X, Y, Z> create(X a, Y b, Z c) {
        return new Tripple<X, Y, Z>(a, b, c);
    }
 
  
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( a == null ) ? 0 : a.hashCode() );
		result = prime * result + ( ( b == null ) ? 0 : b.hashCode() );
		result = prime * result + ( ( c == null ) ? 0 : c.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		
		Tripple<?,?,?> other = (Tripple<?,?,?>) obj;
		if( a == null ) {
			if( other.a != null ) return false;
		} else if( !a.equals( other.a ) )
			return false;
		if( b == null ) {
			if( other.b != null ) return false;
		} else if( !b.equals( other.b ) )
			return false;
		if( c == null ) {
			if( other.c != null ) return false;
		} else if( !c.equals( other.c ) )
			return false;
		
		return true;
	}

	@Override
    public String toString(){
    	return a.toString() + "/" + b.toString() + "/" + c.toString();
    }
	
	private static final int BEFORE = -1, EQUALS = 0, AFTER = 1;

	@Override
	public int compareTo( Tripple<A, B, C> o ) {
		
		int i;
		if( o == this ) return EQUALS;
		
		i = compare( this.a, o.a );
		if( i != 0 ) return i;
		
		i = compare( this.b, o.b );
		if( i != 0 ) return i;
		
		return compare( this.c, o.c );
	}
	
	private static <T> int compare( T a, T b ){
		
		if( a==null ){
			if( b==null ) return EQUALS;
			else return BEFORE;
		} else {
			if( b==null ) return AFTER;
			else {
				if( !( a instanceof Comparable ) )
					throw new IllegalArgumentException( a.getClass().getSimpleName() + " must implement Comparable to work" );
				
				// To remove these we could restict A, B, C to extends Comparable
				@SuppressWarnings( { "unchecked" } )
				int result = ((Comparable<T>)a).compareTo( b );
				return result;
			}
		}
	}
	
}

