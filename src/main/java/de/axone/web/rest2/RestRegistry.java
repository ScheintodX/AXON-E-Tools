package de.axone.web.rest2;


public class RestRegistry {

	// register( "/page/:id", POST, new Page.UPDATE() )
	// register( "/tree/:id", GET, new Tree.READ() );
	// register( "/tree/:parent/:id", POST, new Tree.UPDATE() );
	
	// private Entry base;
	
	public void register( String path, RestFunction function ){
		
		/*
		String [] parts = path.split( "/" );
		
		base.register( 0, parts, function );
		*/
	}
	
	
	/*
	private static class Entry {
		
		private String name;
		private Pattern pattern;
		private RestFunction function;
		private List<Entry> subs;
		
		private void register( int step, String [] path, RestFunction function ){ }
		
		private Entry match( int step, SuperURL url ){ return null; }
	}
	*/
}
		
		