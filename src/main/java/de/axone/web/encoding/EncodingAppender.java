package de.axone.web.encoding;

import java.io.IOException;

public class EncodingAppender implements Appendable {
	
	private final Encoder encoder;
	private final Appendable sub;
	
	public EncodingAppender( Encoder encoder, Appendable sub ) {
		this.encoder = encoder;
		this.sub = sub;
	}

	@Override
	public Appendable append( CharSequence csq ) throws IOException {
		
		sub.append( encoder.encode( csq ) );
		return this;
	}

	@Override
	public Appendable append( CharSequence csq, int start, int end )
			throws IOException {
		
		sub.append( encoder.encode( csq.subSequence( start, end ) ) );
		return this;
	}

	@Override
	public Appendable append( char c ) throws IOException {
		
		// TODO: Doch den encoder mit char ausrüsten?
		sub.append( encoder.encode( ""+c ) );
		return this;
	}

}
