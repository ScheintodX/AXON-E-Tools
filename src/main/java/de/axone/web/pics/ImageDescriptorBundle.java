package de.axone.web.pics;

import de.axone.web.SuperURL;

public interface ImageDescriptorBundle {
	
	//public ImageDescriptorBundle type( ImageDescriptor.Type type );
	//public ImageDescriptorBundle encrypted( String iv, String key );

	public ImageDescriptor fromURL( SuperURL url );
	public SuperURL toURL( ImageDescriptor descriptor );
}
