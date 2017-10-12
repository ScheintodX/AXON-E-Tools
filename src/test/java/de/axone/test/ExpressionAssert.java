package de.axone.test;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractThrowableAssert;

import de.axone.test.ExpressionAssert.FailingExpression;

public class ExpressionAssert extends AbstractAssert<ExpressionAssert, FailingExpression> {

	protected ExpressionAssert( FailingExpression actual ) {
		super( actual, ExpressionAssert.class );
	}

	@FunctionalInterface
	public interface FailingExpression {
		void execute() throws Exception;
	}
	
	public ExpressionAssert works() {
		try {
			actual.execute();
			return this;
		} catch( Throwable e ) {
			fail( "Did throw an Exception", e );
			throw null; // not reached
		}
	}
	
	public <T extends Throwable> AbstractThrowableAssert<?,? extends Throwable> throwsException( Class<T> expectedException ) {
		
		try {
			actual.execute();
			failBecauseExceptionWasNotThrown( expectedException );
			throw null; // not reached
			
		} catch( Throwable e ) {
			
			return assertThat( e )
					.as( this.descriptionText() )
					.isInstanceOf( expectedException )
					;
		}
	}
	
	public static ExpressionAssert assertThisExpression( FailingExpression expression ) {
		return new ExpressionAssert( expression );
	}
	
}
