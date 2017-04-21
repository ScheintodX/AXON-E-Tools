package de.axone.data;

/**
 * Can be implemented by an Enum and provides a little shortcut there
 * 
 * @author flo
 */
public interface Named {
	
	public String name();
	
	public static class NamedString implements Named {
		
		private final String name;
		public NamedString( String name ) {
			this.name = name;
		}
		@Override
		public String name() { return name; }
	}
}
