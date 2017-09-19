package de.axone.test;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.AbstractAssert;

import de.axone.test.ExceptionAssertion.FailingExpression;

public class ExceptionAssertion extends AbstractAssert<ExceptionAssertion, FailingExpression> {

	protected ExceptionAssertion( FailingExpression actual ) {
		super( actual, ExceptionAssertion.class );
	}

	@FunctionalInterface
	public interface FailingExpression {
		void execute() throws Exception;
	}
	
	public <T extends Throwable> ExceptionAssertion doesThrow( Class<T> expectedException ) {
		
		checkForException( expectedException, actual );
		
		return this;
	}
	
	public static <T extends Throwable> void checkForException( Class<T> expectedException, FailingExpression expression ) {
		
		try {
			expression.execute();
			failBecauseExceptionWasNotThrown( expectedException );
		} catch( Throwable e ) {
			assertThat( expectedException )
					.isEqualTo( e.getClass() );
		}
	}
	
	public static ExceptionAssertion assertThisExpression( FailingExpression expression ) {
		return new ExceptionAssertion( expression );
	}
	
}
