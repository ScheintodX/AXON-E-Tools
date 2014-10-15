package de.axone.data;

import java.util.stream.Collectors;

public class WeightedString {
	
	protected final double weight;
	protected final String text;
	
	public WeightedString( String text , double weight  ) {
		this.weight = weight;
		this.text = text;
	}
	
	public double weight(){ return weight; }
	
	public String text(){ return text; }

	@Override
	public int hashCode() {
		return text.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( !( obj instanceof WeightedString ) ) return false;
		
		WeightedString other = (WeightedString) obj;
		
		return text.equals( other.text );
	}
	
	@Override
	public String toString(){
		return String.format( "%s(%.2f)", text, weight );
	}
	
	public abstract static class List<L extends List<L,S>, S extends WeightedString> extends WeightedCollectionAbstract<L, S> {

		@Override
		protected double weight( S item ) {
			return item.weight;
		}

		@Override
		protected S clone( S item, double newWeight ) {
			return null;
		}
		
		public java.util.List<String> toStringList(){
			
			return stream()
					.map( s -> s.text )
					.collect( Collectors.toList() )
					;
		}
	}

}
