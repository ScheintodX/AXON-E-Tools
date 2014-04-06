package de.axone.funky;

import java.util.LinkedList;
import java.util.List;

import de.axone.tools.S;

public class ArgumentImpl<C,T extends ArgumentType<C>> implements Argument<C,T> {
	
	private final T type;
	
	private final String name;
	private String
			shortName,
			description,
			longDescription;
	
	private boolean
			optional,
			positional;
	
	private List<ArgumentValidator<C>>
			validators = new LinkedList<>();
	
	public ArgumentImpl( T type, String name, String shortName, String description, String longDescription, boolean optional, boolean positional ) {
		this.type = type;
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.longDescription = longDescription;
		this.optional = optional;
		this.positional = positional;
	}
	public static <X, Y extends ArgumentType<X>> ArgumentImpl<X,Y> Optional( Y type, String name, String description ){
		return new ArgumentImpl<X,Y>( type, name, null, null, null, true, false );
	}
	public static <X, Y extends ArgumentType<X>> ArgumentImpl<X,Y> Required( Y type, String name, String description ){
		return new ArgumentImpl<X,Y>( type, name, null, null, null, false, true );
	}
	
	@Override
	public T type() {
		return type;
	}

	@Override
	public String name() {
		return name;
	}

	public ArgumentImpl<C,T> shortName( String shortName ){
		this.shortName = shortName;
		return this;
	}

	@Override
	public String shortName() {
		return shortName;
	}

	@Override
	public String description() {
		return description;
	}
	public ArgumentImpl<C,T> description( String description ){
		this.description = description;
		return this;
	}

	@Override
	public boolean optional() {
		return optional;
	}
	public ArgumentImpl<C,T> optional( boolean optional ){
		this.optional = optional;
		return this;
	}
	
	@Override
	public boolean positional() {
		return positional;
	}
	public ArgumentImpl<C,T> positional( boolean positional ){
		this.positional = positional;
		return this;
	}

	@Override
	public String longDescription() {
		return longDescription;
	}
	public ArgumentImpl<C,T> longDescription( String longDescription ){
		this.longDescription = longDescription;
		return this;
	}
	
	public ArgumentImpl<C,T> validate( ArgumentValidator<C> validator ){
		this.validators.add( validator );
		return this;
	}
	@Override
	public List<ArgumentValidator<C>> validators() {
		return validators;
	}
	
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append( name );
		
		if( type != null )
			builder.append( " [" ).append( type.name() ).append( "]" );
		
		if( shortName != null )
			builder.append( " (" ).append( shortName ).append( ")" );
		
		if( optional ) builder.append( "(o)" );
		if( positional ) builder.append( "(p)" );
		
		if( description != null )
			builder.append( " " ).append( description );
		
		if( longDescription != null )
			builder.append( S.nl ).append( longDescription );
		
		if( validators != null )
			for( ArgumentValidator<C> validator : validators )
				builder.append( validator );
		
		return builder.toString();
	}

}
