package com.sandwich.shared.serialiazable.sandwichobject.equals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.sandwich.shared.serialiazable.util.Null;


public class StrictTwoObjectEqualsTest {

	public Object[] _null;
	public Null _Null = new Null("java.lang.String");
	
	@Test
	public void testStrictEquals_Null_null() throws Exception {
		assertFalse(new NullSafeEquals().equals(_Null, _null));
	}
	
	@Test
	public void testStrictEquals_null_Null() throws Exception {
		assertFalse(new NullSafeEquals().equals(_null, _Null));
	}
	
	@Test
	public void testStrictEquals_null_null() throws Exception {
		assertTrue(new NullSafeEquals().equals(_null,_null));
	}
	
	@Test
	public void testStrictEquals_Null_Null() throws Exception {
		assertTrue(new NullSafeEquals().equals(_Null,_Null));
	}
	
	@Test
	public void testStrictEquals_null_1() throws Exception {
		assertFalse(new NullSafeEquals().equals(_null,1));
	}
	
	@Test
	public void testStrictEquals_1_null() throws Exception {
		assertFalse(new NullSafeEquals().equals(1,_null));
	}
	
	@Test
	public void testStrictEquals_Null_1() throws Exception {
		assertFalse(new NullSafeEquals().equals(_Null,1));
	}
	
	@Test
	public void testStrictEquals_1_Null() throws Exception {
		assertFalse(new NullSafeEquals().equals(1,_Null));
	}
	
	@Test
	public void testStrictEquals_1_1() throws Exception {
		assertTrue(new NullSafeEquals().equals(1,1));
	}
	
	@Test
	public void testStrictEquals_1_2() throws Exception {
		assertFalse(new NullSafeEquals().equals(1,2));
	}
	
	@Test
	public void testStrictEquals_2_1() throws Exception {
		assertFalse(new NullSafeEquals().equals(2,1));
	}

	@Test
	public void testObjectEqualsNotInvokedNullArg_1() throws Exception {
		Object meh = new Object(){
			public boolean equals(Object o){
				fail("should not have been invoked when 1st arg is null");
				return false;
			}
		};
		assertFalse(new NullSafeEquals().equals(null, meh));
	}
	
	@Test
	public void testObjectEqualsNotInvokedNullArg_2() throws Exception {
		Object meh = new Object(){
			public boolean equals(Object o){
				fail("should not have been invoked when 2nd arg is null");
				return false;
			}
		};
		assertFalse(new NullSafeEquals().equals(meh, null));
	}
	
	@Test
	public void testObjectEqualsInvokedNonNullArgs() throws Exception {
		final boolean called[] = {false};
		Object meh = new Object(){
			public boolean equals(Object o){
				called[0] = true;
				assertFalse(super.equals(o));
				return true;
			}
		};
		Object meh1 = new Object(){
				public boolean equals(Object o){
				fail();
				return false;
			}
		};
		assertTrue(new NullSafeEquals().equals(meh, meh1));
		assertTrue(called[0]);
	}
	
}
