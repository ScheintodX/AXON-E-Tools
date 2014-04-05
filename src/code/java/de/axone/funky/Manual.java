package de.axone.funky;

public interface Manual<C extends Caller> {

	public CharSequence explain( FunctionDescription description );
	public CharSequence explain( Argument<?,?> arg, boolean longVersion );
}
