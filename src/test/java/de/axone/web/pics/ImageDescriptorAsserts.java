package de.axone.web.pics;

import static org.testng.Assert.*;

import org.assertj.core.api.AbstractAssert;

public interface ImageDescriptorAsserts {

	public static class ImageDescriptorAssert<SELF extends ImageDescriptorAssert<SELF,IMAGED>, IMAGED extends ImageDescriptorAbstract>
	extends AbstractAssert<SELF, IMAGED> {
	
		public ImageDescriptorAssert( IMAGED actual, Class<SELF> selfType ) {
			super( actual, selfType );
		}
		
		public SELF hasCachedir( String cachedir ) {
			assertEquals( actual.cachedir(), cachedir );
			return myself;
		}
	
		public SELF hasDimension( int dimension ) {
			assertEquals( actual.dimension(), dimension );
			return myself;
		}
		
		public SELF hasWatermark( String watermark ) {
			assertEquals( actual.watermark(), watermark );
			return myself;
		}
		
		public SELF hasMTime( long mTime ) {
			assertEquals( actual.mTime().toEpochMilli()/1000, mTime );
			return myself;
		}
		
		public SELF hasName( String name ) {
			assertEquals( actual.name(), name );
			return myself;
		}
	}

	public static class ImageDescriptorIndexedAssert
	extends ImageDescriptorAssert<ImageDescriptorIndexedAssert, ImageDescriptorIndexed> {
	
		public ImageDescriptorIndexedAssert( ImageDescriptorIndexed actual ) {
			super( actual, ImageDescriptorIndexedAssert.class );
		}
		
		public ImageDescriptorIndexedAssert hasIdentifier( String identifier ) {
			assertEquals( actual.identifier(), identifier );
			return myself;
		}
	
		public ImageDescriptorIndexedAssert hasIndex( int index ) {
			assertEquals( actual.index(), index );
			return myself;
		}
		
		
		
	}
	
	public static ImageDescriptorIndexedAssert assertThis( ImageDescriptorIndexed actual ) {
		return new ImageDescriptorIndexedAssert( actual );
	}

}
