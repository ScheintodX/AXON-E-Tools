package de.axone.funky;

import java.util.LinkedList;
import java.util.List;

import de.axone.tools.S;

public class ArgumentImpl<V,AT extends ArgumentType<V>> implements Argument<V,AT> {
	
	private final AT type;
	
	private final String name;
	private String
			shortName,
			description,
			longDescription;
	
	private boolean
			optional,
			positional;
	
	private List<ArgumentValidator<V>>
			validators = new LinkedList<>();
	
	private V defaultValue;
	
	public ArgumentImpl( AT type, String name, String shortName, String description, String longDescription, boolean optional, boolean positional ) {
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
	public AT type() {
		return type;
	}

	@Override
	public String name() {
		return name;
	}

	public ArgumentImpl<V,AT> shortName( String shortName ){
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
	public ArgumentImpl<V,AT> description( String description ){
		this.description = description;
		return this;
	}

	@Override
	public boolean optional() {
		return optional;
	}
	public ArgumentImpl<V,AT> optional( boolean optional ){
		this.optional = optional;
		return this;
	}
	
	@Override
	public boolean positional() {
		return positional;
	}
	public ArgumentImpl<V,AT> positional( boolean positional ){
		this.positional = positional;
		return this;
	}

	@Override
	public String longDescription() {
		return longDescription;
	}
	public ArgumentImpl<V,AT> longDescription( String longDescription ){
		this.longDescription = longDescription;
		return this;
	}
	
	@Override
	public V defaultValue() {
		return defaultValue;
	}
	public ArgumentImpl<V,AT> defaultValue( V defaultValue ){
		this.defaultValue = defaultValue;
		return this;
	}
	
	public ArgumentImpl<V,AT> validate( ArgumentValidator<V> validator ){
		this.validators.add( validator );
		return this;
	}
	@Override
	public List<ArgumentValidator<V>> validators() {
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
			for( ArgumentValidator<V> validator : validators )
				builder.append( validator );
		
		return builder.toString();
	}

}
