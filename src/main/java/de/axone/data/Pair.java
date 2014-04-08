package de.axone.data;

public class Pair<L, R> implements Comparable<Pair<L,R>> {
	 
    private final L left;
    private final R right;
 
    public R getRight() {
        return right;
    }
 
    public L getLeft() {
        return left;
    }
 
    public Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }
    
    public static <A, B> Pair<A, B> create(A left, B right) {
        return new Pair<A, B>(left, right);
    }
  
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( left == null ) ? 0 : left.hashCode() );
		result = prime * result + ( ( right == null ) ? 0 : right.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		
		Pair<?,?> other = (Pair<?,?>) obj;
		if( left == null ) {
			if( other.left != null ) return false;
		} else if( !left.equals( other.left ) )
			return false;
		if( right == null ) {
			if( other.right != null ) return false;
		} else if( !right.equals( other.right ) )
			return false;
		
		return true;
	}

	@Override
    public String toString(){
    	return left.toString() + "<->" + right.toString();
    }
	
	private static final int BEFORE = -1, EQUALS = 0, AFTER = 1;

	@Override
	public int compareTo( Pair<L, R> o ) {
		
		int i;
		if( o == this ) return EQUALS;
		
		i = compare( this.left, o.left );
		if( i != 0 ) return i;
		
		return compare( this.right, o.right );
	}
	
	private static int compare( Object a, Object b ){
		
		if( a==null ){
			if( b==null ) return EQUALS;
			else return BEFORE;
		} else {
			if( b==null ) return AFTER;
			else {
				if( !( a instanceof Comparable ) )
					throw new IllegalArgumentException( a.getClass().getSimpleName() + " must implement Comparable to work" );
				
				@SuppressWarnings( { "rawtypes", "unchecked" } )
				int result = ((Comparable)a).compareTo( b );
				return result;
			}
		}
	}
	
}

