package de.axone.funky;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.axone.funky.types.ArgumentTypes;
import de.axone.tools.S;
import de.axone.tools.Text;

public class FunctionDescriptionImpl implements FunctionDescription {
	
	private final String name;
	private String description;
	
	private List<Argument<?,?>> arguments = new LinkedList<>();

	public FunctionDescriptionImpl( String name ){
		
		this.name = name;
	}
	
	public FunctionDescriptionImpl description( String description ){
		
		this.description = description;
		
		return this;
	}

	public FunctionDescriptionImpl argument( Argument<?,?> argument ) {
		
		arguments.add( argument );
		
		return this;
	}
	
	public <C,T extends ArgumentType<C>> FunctionDescriptionImpl required( T type, String name, String description ) {
		
		ArgumentImpl<C,T> arg = ArgumentImpl.Required( type, name, description );
		
		return argument( arg );
	}
	public <C,T extends ArgumentType<C>> FunctionDescriptionImpl required( String name, String description ) {
		
		return required( ArgumentTypes.STRING, name, description );
	}
	public <C,T extends ArgumentType<C>> FunctionDescriptionImpl required( T type, String name ) {
		
		return required( type, name, null );
	}
	
	public <C,T extends ArgumentType<C>> FunctionDescriptionImpl optional( T type, String name, String description ) {
		
		ArgumentImpl<C,T> arg = ArgumentImpl.Optional( type, name, description );
		
		return argument( arg );
	}
	public <C,T extends ArgumentType<C>> FunctionDescriptionImpl optional( String name, String description ) {
		
		return optional( ArgumentTypes.STRING, name, description );
	}
	public <C,T extends ArgumentType<C>> FunctionDescriptionImpl optional( T type, String name ) {
		
		return optional( type, name, null );
	}
	

	@Override
	public String name() {
		return name;
	}
	
	@Override
	public String description() {
		return description;
	}

	@Override
	public List<Argument<?, ?>> arguments() {
		return Collections.unmodifiableList( arguments );
	}
	
	@Override
	public String toString(){
		
		StringBuilder result = new StringBuilder();
		
		result.append( name() );
		
		for( Argument<?,?> argument : arguments ){
			result.append( " " ).append( argument.name() );
		}
		
		result.append( S.nl ).append( Text.line( '-', 79 ) ).append( S.nl );
		
		for( Argument<?,?> argument : arguments ){
			result.append( argument ).append( S.nl );
		}
		
		return result.toString();
		
	}

}
