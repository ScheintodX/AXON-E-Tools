package de.axone.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProperties implements StringValueAccessor<String,RuntimeException>{

	private Map<String,Object> backend;
	private final Path rootDir;
	private final String prefix;

	public JsonProperties() {
		this( Paths.get( "/" ) );
	}

	public JsonProperties( Path rootDir ) {
		this( null, new HashMap<>(), rootDir );
	}

	public JsonProperties( String prefix, Map<String,Object> backend, Path rootDir ) {
		this.prefix = prefix;
		this.rootDir = rootDir;
		this.backend = backend;
	}


	// StringValueAccessor interface
	@Override
	public String access( String key ) throws NoSuchElementException {
		if( ! backend.containsKey( key ))
				throw new NoSuchElementException( key );
		return Objects.toString( backend.get( key ) );
	}

	@Override
	public String accessChecked( String key ) {
		if( !has( key ) ) return null;
		return (String)backend.get( key );
	}

	@Override
	public IllegalArgumentException exception( String key ) {
		return new IllegalArgumentException( key );
	}

	@Override
	public boolean has( @Nonnull String key ) {
		return backend.containsKey( key ) && backend.get( key ) != null;
	}


	// From Properties
	public void load( InputStream in ) throws IOException{

		try( InputStreamReader inr = new InputStreamReader( in ) ){
			load( inr );
		}
	}

	public void load( Reader reader ) throws IOException {

		ObjectMapper om = new ObjectMapper();

		TypeReference<HashMap<String, Object>> typeRef
			  = new TypeReference<HashMap<String, Object>>() {};

		backend = om.readValue( reader, typeRef );
	}

	@Override
	public String toString() {

		ObjectMapper om = new ObjectMapper();
		try {
			return om.writeValueAsString( backend );
		} catch( JsonProcessingException e ) {
			throw new Error( e );
		}
	}

	// Convenience methods
	@SuppressWarnings( "unchecked" )
	public List<Object>  getList( String key ) {
		return (List<Object>) backend.get( key );
	}

	@SuppressWarnings( "unchecked" )
	public Map<String,Object> getMap( String key ) {
		return (Map<String,Object>) backend.get( key );
	}

	public JsonProperties subset( String key ) throws NoSuchElementException {

		String prefix = this.prefix != null ? this.prefix + "." + key : key;

		if( ! backend.containsKey( key ) )
				throw new NoSuchElementException( key );

		@SuppressWarnings( "unchecked" )
		Map<String,Object> newBackend = (Map<String,Object>)backend.get( key );

		return new JsonProperties( prefix, newBackend, rootDir );
	}

}
