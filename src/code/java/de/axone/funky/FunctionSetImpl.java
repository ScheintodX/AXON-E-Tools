package de.axone.funky;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.axone.tools.S;
import de.axone.tools.Text;

public class FunctionSetImpl extends FunctionDescriptionImpl implements FunctionSet {
	
	private List<FunctionDescription> descriptions = new LinkedList<>();

	public FunctionSetImpl( String name ) {
		super( name );
	}
	
	public FunctionSetImpl description( FunctionDescription description ){
		descriptions.add( description );
		return this;
	}

	@Override
	public List<FunctionDescription> functions() {
		return Collections.unmodifiableList( descriptions );
	}
	
	@Override
	public String toString(){
		
		StringBuilder result = new StringBuilder();
		
		result.append( name() ).append( S.nl )
			.append( Text.line( '=', 79 ) ).append( S.nl );
		
		for( FunctionDescription description : functions() ){
			result.append( description ).append( S.nl );
		}
		
		return result.toString();
	}

}
