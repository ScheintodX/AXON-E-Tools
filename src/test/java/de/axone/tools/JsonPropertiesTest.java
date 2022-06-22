package de.axone.tools;

import static de.axone.tools.StringValueAccessorAssert.*;
import static org.assertj.core.api.Assertions.*;

import java.io.StringReader;

import org.testng.annotations.Test;

@Test
public class JsonPropertiesTest {

	String JSON_AS_STRING = "" +
			"{" +
				"\"A\": \"a--\","+
				"\"B\": [ \"b--1\",\"b--2\", \"b--3\" ],"+
				"\"C\": {"+
					"\"D\": \"cd-\","+
					"\"E\": [ \"ce-1\", \"ce-2\", \"ce-3\" ],"+
					"\"F\": {"+
						"\"G\": \"cfg\","+
						"\"H\": \"cfh\""+
					"}"+
				"}"+
			"}";

	public void testNestedDeserialization() throws Exception {


		J.son( JSON_AS_STRING );


		JsonProperties props;
		try( StringReader in = new StringReader( JSON_AS_STRING ) ){

			props = new JsonProperties();
			props.load( in );
		}

		assertThis( props )
				.containsKey( "A" )
				.valueIs( "A", "a--" )
				.containsKey( "B" )
				.containsKey( "C" )
				.doesNotContainKey( "D" )
				;

		assertThat( props.getList( "B" ) )
				.contains( "b--1", "b--2", "b--3" )
				;


		JsonProperties subprops = props.subset( "C" );

		assertThis( subprops )
				.containsKey( "D" )
				.valueIs( "D", "cd-" )
				.containsKey( "E" )
				.containsKey( "F" )
				.doesNotContainKey( "G" )
				.doesNotContainKey( "H" )
				;

		assertThis( subprops.subset( "F" ) )
				.containsKey( "G" )
				.valueIs( "G", "cfg" )
				.containsKey( "H" )
				.valueIs( "H", "cfh" )
				;

	}


}
