package de.axone.web.pics;

import static de.axone.web.SuperURLAssertions.*;
import static de.axone.web.pics.ImageDescriptorAsserts.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.Arrays;

import org.testng.annotations.Test;

import de.axone.web.SuperURL;

@Test( groups="helper.web" )
public class ImageDescriptorIndexedTest {

	public void testSerialization() {
		
		ImageDescriptorIndexed idi = new ImageDescriptorIndexed( "12345", 6, 700, "wm", "cd", Instant.ofEpochMilli( 890 ), "name" );
		
		assertThis( idi.toUrl() )
				.isNotNull()
				.thePath()
				.isEqualTo( new SuperURL.Path( Arrays.asList( "cd", "12345", "6", "700", "wm", "890", "name.jpg" ), false ) )
				;
		
		assertThat( idi.toEncodableString() )
				.isEqualTo( "12345:6:700:wm:cd:890" )
				;
		
	}
	
	public void testCreationFromURL() {
		
		SuperURL url = new SuperURL();
		url.getPath().addAll( "cd", "12345", "6", "700", "wm", "890", "name.jpg" );
		
		assertThis( ImageDescriptorIndexed.create( url ) )
				.hasIdentifier( "12345" )
				.hasIndex( 6 )
				.hasDimension( 700 )
				.hasWatermark( "wm" )
				.hasCachedir( "cd" )
				.hasMTime( 890 )
				.hasName( "name" )
				; 
		
	}

}
