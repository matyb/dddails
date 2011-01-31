package com.sandwich.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.sandwich.server.Utils;
import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.shared.serialiazable.util.Predicate;


public class UtilsTest {

	public Object[] _null;
	public Null _Null;
	
	@Test
	public void testCopyArrays_empty() throws Exception {
		List<String> l = Utils.copyArrays(new String[]{}, new String[]{});
		assertTrue(l.isEmpty());
	}
	
	@Test
	public void testCopyArrays_null() throws Exception {
		List<?> l = Utils.copyArrays(new Object[]{_null}, new Object[]{_null});
		assertEquals(2,l.size());
		Iterator<?> i = l.iterator();
		assertNull(i.next());
		assertNull(i.next());
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testCopyArrays_noiNullElements() throws Exception {
		List<?> l = Utils.copyArraysNoNullElements(new Object[]{_null}, new Object[]{_null});
		assertTrue(l.isEmpty());
	}
	
	@Test
	public void testCopyArrays_typeMismatch() throws Exception {
		@SuppressWarnings("unchecked")
		Iterator<?> i = Utils.copyArrays(new Integer[]{0}, new String[]{""}).iterator();
		assertEquals(0, i.next());
		assertEquals("", i.next());
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testCopyArrays_compilesWithGenerics() throws Exception {
		@SuppressWarnings("unused")
		List<String> strings = Utils.copyArrays((String[])null, (String[])null);
	}
	
	@Test
	public void testCopyArrays_withPredicate() throws Exception {
		Iterator<?> i = Utils.copyArrays(new Predicate<Object>(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean evaluate(Object obj) {
				return "".equals(obj);
			}
		}, new Integer[]{0}, new String[]{""}, new String[]{" "}).iterator();
		assertEquals("", i.next());
		assertFalse(i.hasNext());
	}
	
	@Test
	public void testCopyArrays_withPredicate_justToFindElement() throws Exception {
		Iterator<?> i = Utils.copyArrays(new Predicate<Object>(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean evaluate(Object obj) {
				return "".equals(obj);
			}
		}, new String[]{""," "}).iterator();
		assertEquals("", i.next());
		assertFalse(i.hasNext());
	}
	
}
