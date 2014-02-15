package de.axone.data;


public class IntegerRange {
	
	private final Integer min, max;

	public IntegerRange( Integer min, Integer max ){
		
		if( min != null && max != null && min > max ){
			throw new IllegalArgumentException( "Min > Max" );
		}
		
		this.min = min;
		this.max = max;
	}
	
	public boolean overlaps( IntegerRange other ){
		
		int aMin = min != null ? min : Integer.MIN_VALUE;
		int aMax = max != null ? max : Integer.MAX_VALUE;
		int bMin = other.min != null ? other.min : Integer.MIN_VALUE;
		int bMax = other.max != null ? other.max : Integer.MAX_VALUE;
		
		return aMax > bMin && aMin < bMax || bMax > aMin && bMin < aMax;
	}
	
	public boolean touches( IntegerRange other ){
		
		int aMin = min != null ? min : Integer.MIN_VALUE;
		int aMax = max != null ? max : Integer.MAX_VALUE;
		int bMin = other.min != null ? other.min : Integer.MIN_VALUE;
		int bMax = other.max != null ? other.max : Integer.MAX_VALUE;
		
		return aMax >= bMin && aMin <= bMax || bMax >= aMin && bMin <= aMax;
	}
	
	public Integer getMin() {
		return min;
	}

	public Integer getMax() {
		return max;
	}

}
