package de.axone.login;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

import de.axone.login.LoginToken.LoginTokenImpl;
import de.axone.tools.E;
import de.axone.tools.HEX;

@Test( groups="tools.login" )
public class LoginTokenTest {

	private LoginToken.Builder builder = new LoginToken.Builder( "1BAD2BAD3BAD4BAD" );
		
	public void testCreation() {
		
		LoginToken token = builder.build( 0x12345678L );
		assertThat( token ).isInstanceOf( LoginTokenImpl.class );
		
		assertEquals( token.getId(), 0x12345678L );
		
		long now = System.currentTimeMillis();
		assertTrue( token.getCreation() < now && token.getCreation() > now-1000 );
		
		assertTrue( HEX.encode( ((LoginTokenImpl)token).combined() ).contains( "12345678" ) );
		assertFalse( HEX.encode( ((LoginTokenImpl)token).combined() ).contains( "1BAD2BAD" ) );
		
		String secured = builder.secure( token );
		
		assertEquals( secured.length(), 64 ); 
		assertFalse( secured.contains( "12345678" ) );
	}
	
	public void testParsing() {
		
		LoginToken token = builder.build( 0x12345678L );
		
		String secured = builder.secure( token );
		
		E.rr( secured );
		
		LoginToken decoded = builder.decode( secured );
		
		assertEquals( decoded.getId(), token.getId() );
		
	}

}
