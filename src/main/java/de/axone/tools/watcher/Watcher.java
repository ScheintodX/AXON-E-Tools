package de.axone.tools.watcher;

public interface Watcher<W> {

	public abstract boolean haveChanged();
	
	public abstract W getWatched();

}