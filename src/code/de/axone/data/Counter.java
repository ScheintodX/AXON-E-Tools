package de.axone.data;

public class Counter {
	
	private long count;

	public Counter(){}
	public Counter( long count ){ this.count = count; }
	public void inc(){ count++; }
	public long count(){ return count; };
}
