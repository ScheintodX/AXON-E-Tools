package de.axone.tools;

import org.testng.annotations.Test;

@Test( groups="tools.beanconfigurator" )
public class BeanConfiguratorTest {

	public void testShortCreation() throws Exception {

		TestBean tb = new TestBean();

		BeanConfigurator bc = new BeanConfigurator( tb );

		bc.set( "name", "hime" );

		bc.set( "c", "c" );
		bc.set( "s", "123" );
		bc.set( "i", "234" );
		bc.set( "l", "345" );
		bc.set( "f", "456" );
		bc.set( "d", "567" );

		bc.set( "ch", "c" );
		bc.set( "sh", "123" );
		bc.set( "in", "234" );
		bc.set( "lo", "345" );
		bc.set( "fl", "456" );
		bc.set( "db", "567" );

		bc.set( "bct", "de.axone.tools.BeanConfiguratorTest" );

		assert "hime".equals( tb.name );

		assert 'c' == tb.c;
		assert (short)123 == tb.s;
		assert 234 == tb.i;
		assert 345L == tb.l;
		assert 456f == tb.f;
		assert 567.0 == tb.d;

		assert 'c' == tb.ch;
		assert (short)123 == tb.sh;
		assert 234 == tb.in;
		assert 345L == tb.lo;
		assert 456f == tb.fl;
		assert 567.0 == tb.db;

		assert null != tb.bct;
	}

	public static class TestBean {

		private String name;

		private char c;
		private short s;
		private int i;
		private long l;
		private float f;
		private double d;

		private Character ch;
		private Short sh;
		private Integer in;
		private Long lo;
		private Float fl;
		private Double db;

		private BeanConfiguratorTest bct;

		public void setName(String name) { this.name = name; }

		public void setC(char c) { this.c = c; }
		public void setCh(Character ch) { this.ch = ch; }
		public void setD(double d) { this.d = d; }
		public void setDb(Double db) { this.db = db; }
		public void setF(float f) { this.f = f; }
		public void setFl(Float fl) { this.fl = fl; }
		public void setI(int i) { this.i = i; }
		public void setIn(Integer in) { this.in = in; }
		public void setL(long l) { this.l = l; }
		public void setLo(Long lo) { this.lo = lo; }
		public void setS(short s) { this.s = s; }
		public void setSh(Short sh) { this.sh = sh; }

		public void setBct(BeanConfiguratorTest bct) { this.bct = bct; }
	}


}
