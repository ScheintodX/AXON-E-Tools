package de.axone.cache.ng;

import static de.axone.cache.ng.CacheNGAssert.*;
import static de.axone.cache.ng.CacheNGTestHelpers.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import de.axone.cache.ng.CacheNGTestHelpers.Aid;
import de.axone.cache.ng.CacheNGTestHelpers.TestRealm;
import de.axone.tools.Sets;

@Test( groups="helper.cacheng" )
public class CacheNGTest_AutomaticTwoStepClient {
	
	private Map<String,List<Aid>> map1 = new HashMap<>();
	{
		map1.put( "A", Arrays.asList( aid( "a1" ), aid( "a2" ) ) );
		map1.put( "B", Arrays.asList( aid( "b1" ), aid( "b2" ) ) );
		map1.put( "C", Arrays.asList( aid( "c1" ) ) );
		map1.put( "D", null );
	}
	private Map<Aid,String> map2 = new HashMap<>();
	{
		map2.put( aid( "a1" ), "Art A1" );
		map2.put( aid( "a2" ), "Art A2" );
		map2.put( aid( "a3" ), "Art A3" );
		map2.put( aid( "b1" ), "Art B1" );
		map2.put( aid( "c1" ), null );
		map2.put( aid( "d1" ), null );
	}
	
	private CacheNG.Accessor<String,List<Aid>> String2AidList = new AbstractSingleValueAccessor<String, List<Aid>>() {

		@Override
		public List<Aid> fetch( String key ) {
			return map1.get( key );
		}
		
	};
	
	private CacheNG.Accessor<Aid, String> Aid2String = new AbstractSingleValueAccessor<Aid,String>() {

		@Override
		public String fetch( Aid key ) {
			return map2.get( key );
		}
	};

	public void stringToListBasicOperations(){
		
		CacheNG.AutomaticClient<String, List<Aid>> aidListForString =
				new AutomaticClientImpl<>( new TestRealm<String,List<Aid>>( "S->L:S" ) );
		
		assertThat( aidListForString ).hasNotCached( "A" );
		
		assertThat( aidListForString.fetch( "A", String2AidList ) )
				.contains( aid("a1"), aid("a2") )
				.hasSize( 2 );
		assertThat( aidListForString ).hasCached( "A" );
		
		aidListForString.invalidateAll();
		
		assertThat( aidListForString ).hasNotCached( "A" );
	}
	
	public void aidToStringBasicOperations(){
		
		CacheNG.AutomaticClient<Aid, String> stringForAid =
				new AutomaticClientImpl<>( new TestRealm<Aid,String>( "S->S" ) );
				
		assertThat( stringForAid ).hasNotCached( aid("a1" ) );
		
		assertThat( stringForAid.fetch( aid( "a1" ), Aid2String ) )
				.isEqualTo( "Art A1" );
		assertThat( stringForAid ).hasCached( aid( "a1" ) );
		
		stringForAid.invalidateAll();
		
		assertThat( stringForAid ).hasNotCached( aid( "a1" ) );
	}
	
	public void combinedOperationsHaveCorrectResult(){
	
		CacheNG.AutomaticClient<String, List<Aid>> aidListForString =
				spy( new AutomaticClientImpl<>( new TestRealm<String,List<Aid>>( "S->L:S" ) ) );
				
		CacheNG.AutomaticClient<Aid, String> stringForAid =
				spy( new AutomaticClientImpl<>( new TestRealm<Aid,String>( "S->S" ) ) );
		
		AutomaticTwoStepCache<String, Aid, String> atsc =
				new AutomaticTwoStepCache<>( aidListForString, stringForAid );
		
		List<String> arts = atsc.fetch( "A", String2AidList, Aid2String );
		assertThat( arts )
				.contains( "Art A1", "Art A2" )
				.doesNotContain( "Art A3", "Art B1" )
				.hasSize( 2 );
		
		arts = atsc.fetch( "B", String2AidList, Aid2String );
		assertThat( arts )
				.contains( "Art B1" )
				.hasSize( 1 );
		
		arts = atsc.fetch( "C", String2AidList, Aid2String );
		assertThat( arts )
				.hasSize( 0 );
		
		arts = atsc.fetch( "D", String2AidList, Aid2String );
		assertThat( arts )
				.hasSize( 0 );
		
		arts = atsc.fetch( "E", String2AidList, Aid2String );
		assertThat( arts )
				.hasSize( 0 );
	}
	public void combinedOperationsUseTheRightCaches(){
		
		CacheNG.AutomaticClient<String, List<Aid>> aidListForString =
				spy( new AutomaticClientImpl<>( new TestRealm<String,List<Aid>>( "S->L:S" ) ) );
				
		CacheNG.AutomaticClient<Aid, String> stringForAid =
				spy( new AutomaticClientImpl<>( new TestRealm<Aid,String>( "S->S" ) ) );
		
		AutomaticTwoStepCache<String, Aid, String> atsc =
				new AutomaticTwoStepCache<>( aidListForString, stringForAid );
		
		assertThat( aidListForString ).hasNotCached( "A" );
		assertThat( stringForAid ).hasNotCached( aid("a1") )
				.hasNotCached( aid("a2") );
				
		atsc.fetch( "A", String2AidList, Aid2String );
		
		assertThat( aidListForString ).hasCached( "A" );
		assertThat( stringForAid ).hasCached( aid("a1") )
				.hasCached( aid("a2") );
		
	}
	
	public void combinedOperationsAndAccessorUsage(){
		
		CacheNG.AutomaticClient<String, List<Aid>> aidListForString =
				spy( new AutomaticClientImpl<>( new TestRealm<String,List<Aid>>( "S->L:S" ) ) );
				
		CacheNG.AutomaticClient<Aid, String> stringForAid =
				spy( new AutomaticClientImpl<>( new TestRealm<Aid,String>( "S->S" ) ) );
		
		CacheNG.Accessor<String,List<Aid>> string2AidList = spy( String2AidList );
		
		CacheNG.Accessor<Aid, String> aid2String = spy( Aid2String );
				
		AutomaticTwoStepCache<String, Aid, String> atsc =
				new AutomaticTwoStepCache<>( aidListForString, stringForAid );
		
		verify( aidListForString, never() ).fetch( "A", string2AidList );
		verify( stringForAid, never() ).fetch( aid( "a1" ), aid2String );
		verify( string2AidList, never() ).fetch( "A" );
		verify( aid2String, never() ).fetch( Arrays.asList( aid( "a1" ), aid( "a2" ) ) );
		
		atsc.fetch( "A", string2AidList, aid2String );
			
		verify( aidListForString, times( 1 ) ).fetch( "A", string2AidList );
		verify( stringForAid, times( 1 ) ).fetch( Arrays.asList( aid( "a1" ), aid( "a2" ) ), aid2String );
		verify( string2AidList, times( 1 ) ).fetch( "A" );
		verify( aid2String, times( 1 ) ).fetch( Sets.hashSetOf( aid( "a1" ), aid( "a2" ) ) );
		
		atsc.fetch( "A", string2AidList, aid2String );
			
		// Not more accessor usage
		verify( aidListForString, times( 2 ) ).fetch( "A", string2AidList );
		verify( stringForAid, times( 2 ) ).fetch( Arrays.asList( aid( "a1" ), aid( "a2" ) ), aid2String );
		verify( string2AidList, times( 1 ) ).fetch( "A" );
		verify( aid2String, times( 1 ) ).fetch( Sets.hashSetOf( aid( "a1" ), aid( "a2" ) ) );
		
		atsc.fetch( "B", string2AidList, aid2String );
		
		verify( aidListForString, times( 1 ) ).fetch( "B", string2AidList );
		verify( stringForAid, times( 1 ) ).fetch( Arrays.asList( aid( "b1" ), aid( "b2" ) ), aid2String );
		verify( string2AidList, times( 1 ) ).fetch( "B" );
		verify( aid2String, times( 1 ) ).fetch( Sets.hashSetOf( aid( "b1" ), aid( "b2" ) ) );
	}
		
}
