package de.axone.web.pics;

import java.io.IOException;
import java.nio.file.Path;

import de.axone.web.SuperURL;

public interface ImageDescriptor {

	public enum Type { INDEXED, NAMED };
	//public enum Version { ORIGINAL, INTERMEDIATE, FINAL };
	
	SuperURL toUrl();
	String toEncodableString();
	
	public Path toPath( Path basepath ) throws IOException;
	
	public String name();
}
