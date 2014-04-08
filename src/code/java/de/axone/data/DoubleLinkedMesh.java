package de.axone.data;

import java.util.HashSet;
import java.util.Set;


public class DoubleLinkedMesh<T> {

	private T payload;
	private Set<DoubleLinkedMesh<T>> prev = new HashSet<DoubleLinkedMesh<T>>();
	private Set<DoubleLinkedMesh<T>> next = new HashSet<DoubleLinkedMesh<T>>();

	public T get(){ return payload; }
	public void put(T t){ this.payload = t; }

	public void addNext( DoubleLinkedMesh<T> mesh ){

		mesh.prev.add( this );
		next.add( mesh );
	}
	public void remNext( DoubleLinkedMesh<T> mesh ){

		mesh.prev.remove( this );
		next.remove( mesh );
	}
	public void remAllNext(){
		for( DoubleLinkedMesh<T> mesh : next ){
			mesh.prev.remove( this );
		}
		next.clear();
	}

	public void addPrev( DoubleLinkedMesh<T> mesh ){
		mesh.next.add( this );
		prev.add( mesh );
	}
	public void remPrev( DoubleLinkedMesh<T> mesh ){
		mesh.next.remove( this );
		prev.remove( mesh );
	}
	public void remAllPrev(){
		for( DoubleLinkedMesh<T> mesh : prev ){
			mesh.next.remove( this );
		}
		prev.clear();
	}

	public void insertBetween( DoubleLinkedMesh<T> prev, DoubleLinkedMesh<T> next ){

		prev.remNext( next );
		prev.addNext( this );
		this.addNext( next );
	}

	public void disconnect(){
		remAllPrev();
		remAllNext();
	}

	public void resolve(){

		for( DoubleLinkedMesh<T> prevMesh : prev ){

			prevMesh.next.remove( this );

			for( DoubleLinkedMesh<T> nextMesh : next ){

				prevMesh.addNext( nextMesh );
			}
		}

		for( DoubleLinkedMesh<T> nextMesh : next ){

			nextMesh.prev.remove( this );

			for( DoubleLinkedMesh<T> prevMesh : prev ){

				nextMesh.addPrev( prevMesh );
			}
		}
	}
}
