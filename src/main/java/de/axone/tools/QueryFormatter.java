package de.axone.tools;

import java.io.IOException;

public interface QueryFormatter {

	public static boolean canFormat( Object obj ) {

		if( obj == null ) return false;

		switch( obj.getClass().getCanonicalName() ) {
			case "org.hibernate.jpa.criteria.compile.CriteriaQueryTypeQueryAdapter": return true;
			default: return false;
		}
	}

	public static Appendable format( Appendable result, Object obj ) throws IOException {

		if( obj == null ) {
			result.append( S._NULL_ );
			return result;
		}

		switch( obj.getClass().getCanonicalName() ) {

			case "org.hibernate.jpa.criteria.compile.CriteriaQueryTypeQueryAdapter": return HIBERNATE.doFormat( result, obj );

			default: throw new RuntimeException( "Unknown: " + obj.getClass().getCanonicalName() );

		}
	}

	public StringBuilder doFormat( Appendable result, Object obj ) throws IOException;

	public static QueryFormatter HIBERNATE = new QueryFormatter() {

		@Override
		public StringBuilder doFormat( Appendable result, Object obj ) throws IOException {

			try {
				Class<?> x = Class.forName( "org.hibernate.Query" );

				java.lang.reflect.Method unwrap = obj.getClass().getMethod( "unwrap", java.lang.Class.class );

				Object unwrapped = unwrap.invoke( obj, x );

				result.append( F.ormat( unwrapped ) );

			} catch( ReflectiveOperationException | IllegalArgumentException | SecurityException e ) {

				throw new RuntimeException( "Cannot access class to unwrap", e );
			}

			return null;
		}

	};
}
