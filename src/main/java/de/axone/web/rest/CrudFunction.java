package de.axone.web.rest;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import de.axone.web.Method;

public interface CrudFunction<OBJECT, DATA, REQUEST extends RestRequest> {
	
	
	public String name();
	
	
	public void doCreate( DATA data, Method method,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doRead( DATA data, Method method, long id,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doUpdate( DATA data, Method method, Long id,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	public void doDelete( DATA data, Method method, long id,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	
	public void doList( DATA data, Method method, Map<String,String> parameters,
			PrintWriter out, REQUEST req, HttpServletResponse resp) throws Exception;
	
	
	/*
	public interface ObjectResult<O> {
		public O getItem();
		public ValidatorResult getErrors();
	}
	public static class ObjectResultImpl<O> implements ObjectResult<O> {
		private final O item;
		private final ValidatorResult errors;
		public ObjectResultImpl( O item, ValidatorResult errors ){
			this.item = item;
			this.errors = errors;
		}
		@Override
		public O getItem() { return item; }
		@Override
		public ValidatorResult getErrors() { return errors; }
	}
	
	public interface ListResult<O> {
		public List<O> getList();
		public int getSize();
	}
	public static class ListResultImpl<O> implements ListResult<O> {
		private List<O> list;
		private int size;
		public ListResultImpl( List<O> list, int size ){
			this.list = list;
			this.size = size;
		}
		@Override
		public List<O> getList() { return list; }
		@Override
		public int getSize() { return size; }
	}
	*/
	
}
