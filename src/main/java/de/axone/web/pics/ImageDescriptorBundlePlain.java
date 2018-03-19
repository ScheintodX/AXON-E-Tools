package de.axone.web.pics;

import java.io.IOException;
import java.nio.file.Path;

import de.axone.web.SuperURL;

public class ImageDescriptorBundlePlain implements ImageDescriptorBundle {
	
	private final Path basedir;
	private final SuperURL basepath;
	
	@SuppressWarnings( "unused" )
	private final ImageDescriptor.Type type;
	
	public ImageDescriptorBundlePlain( SuperURL basepath, Path basedir, ImageDescriptor.Type type ){
		this.basepath = basepath;
		this.basedir = basedir;
		this.type = type;
	}

	@Override
	public ImageDescriptor fromURL( SuperURL url ) {
		
		SuperURL copy = SuperURL.copyDeep( url );
		
		copy.getPath().removeFirst( basepath.getPath().length() );
		
		return ImageDescriptorIndexed.create( copy );
	}

	@Override
	public SuperURL toURL( ImageDescriptor descriptor ) {
		
		SuperURL result = SuperURL.copyDeep( basepath );
		SuperURL descriptorUrl = descriptor.toUrl();
		
		result.getPath().addAll( descriptorUrl.getPath() );
		result.getQuery().addAll( descriptorUrl.getQuery() );
		
		return result;
	}
	
	public Path toPath( ImageDescriptor descriptor ) throws IOException {
		
		return basedir.resolve( descriptor.toPath( basedir ) );
	}

}
