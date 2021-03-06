package de.axone.data.tupple;

import java.io.Serializable;

public class Pair<L, R> implements Comparable<Pair<L,R>>, Serializable {

	private static final long serialVersionUID = 42L;

    private final L left;
    private final R right;

    public static final Pair<?,?> EMPTY = Pair.empty();

    public R getRight() {
        return right;
    }

    public L getLeft() {
        return left;
    }

    public Pair( L left, R right ) {
        this.left = left;
        this.right = right;
    }

    public static <A, B> Pair<A, B> of(A left, B right) {
        return new Pair<>(left, right);
    }

    @SuppressWarnings( "unchecked" )
	public static <A,B> Pair<A, B> empty() {
        return (Pair<A,B>)EMPTY;
    }

    private int hashCode = 0;

    @Override
    public int hashCode() {
	    	if( hashCode == 0 ){
	    		final int prime = 31;
	    		hashCode = 1;
	    		hashCode = prime * hashCode + ( ( left == null ) ? 0 : left.hashCode() );
	    		hashCode = prime * hashCode + ( ( right == null ) ? 0 : right.hashCode() );
	    	}
	    	return hashCode;
    }

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;

		Pair<?,?> other = (Pair<?,?>) obj;
		if( left == null ) {
			if( other.left != null ) return false;
		} else if( !left.equals( other.left ) ) return false;

		if( right == null ) {
			if( other.right != null ) return false;
		} else if( !right.equals( other.right ) ) return false;

		return true;
	}

	@Override
    public String toString(){
    	return left.toString() + "<->" + right.toString();
    }

	@Override
	public int compareTo( Pair<L, R> o ) {

		int i;
		if( o == this ) return 0;

		i = compare( this.left, o.left );
		if( i != 0 ) return i;

		return compare( this.right, o.right );
	}

	private static <T> int compare( T a, T b ){

		if( a==null ){
			if( b==null ) return 0;
			else return -1;
		} else {
			if( b==null ) return 1;
			else {
				if( !( a instanceof Comparable ) )
					throw new IllegalArgumentException( a.getClass().getSimpleName() + " must implement Comparable to work" );

				@SuppressWarnings( "unchecked" )
				int result = ((Comparable<T>)a).compareTo( b );
				return result;
			}
		}
	}

}