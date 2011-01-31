package com.sandwich.shared.serialiazable.sandwichobject.equals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.sandwich.shared.serialiazable.util.Equals;
import com.sandwich.shared.serialiazable.util.Null;


public class NullOrEmptyTest {
	
	public Object[] _null;
	public Null _Null = new Null("java.lang.String");
	
	@Test
	public void testNullIsNull() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, _Null));
	}
	
	@Test
	public void testnullIsNull() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, _null));
	}
	
	@Test
	public void testCollection() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, Collections.EMPTY_LIST));
	}
	
	@Test
	public void testMap() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, Collections.EMPTY_MAP));
	}
	
	@Test
	public void testString() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, ""));
	}
	
	@Test
	public void testTrimString() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, "     "));
	}	
	
	@Test
	public void testArray() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, new Object[]{}));
	}	
	
	@Test
	public void testnestedArray() throws Exception {
		assertTrue(Equals.isNullOrEmpty(true, new Object[]{new Object[][]{}}));
	}	
	
	@Test
	public void testKitchenSink_allEqual_nullElements() throws Exception {
		Object[][] obs = new Object[][]{new Object[]{Collections.EMPTY_LIST}};
		List<?> l = new ArrayList<Object>();
		l.add(null);
		Set<Object> s = new HashSet<Object>(l);
		s.add(obs);
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put(null, null);
		m.put(l, s);
		m.put(obs, s);
		m.put("   ", "        ");
		assertTrue(Equals.isNullOrEmpty(true, m));
	}
	
	@Test
	public void testKitchenSink_oneObjectInstanceInArray() throws Exception {
		// new Object() is what is causing to evaluate false
		Object[][] obs = new Object[][]{new Object[]{new Object()}};
		List<Object> list = new ArrayList<Object>();
		list.add(null);
		list.add(obs);
		Set<Object> s = new HashSet<Object>(list);
		s.add(null);
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put(null, null);
		m.put("", s);
		m.put("   ", "        ");
		assertFalse(Equals.isNullOrEmpty(true, m));
	}
	
}
