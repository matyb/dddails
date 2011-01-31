package com.sandwich.shared.serialiazable.sandwichobject.equals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.shared.serialiazable.util.collections.ArrayList;

public class LooseTwoObjectEqualsTest {
	public Object _null;
	public Null _Null;
	
	@Test
	public void testLooseEquals_null_null() throws Exception {
		assertTrue(new LooseEquals().equals(_null, _null));
	}
	
	@Test
	public void testLooseEquals_Null_null() throws Exception {
		assertTrue(new LooseEquals().equals(_Null, _null));
	}
	
	@Test
	public void testLooseEquals_null_Null() throws Exception {
		assertTrue(new LooseEquals().equals(_null,_Null));
	}
	
	@Test
	public void testLooseEquals_Null_Null() throws Exception {
		assertTrue(new LooseEquals().equals(_Null,_Null));
	}
	
	@Test
	public void testLooseEquals_null_1() throws Exception {
		assertFalse(new LooseEquals().equals(_null,1));
	}
	
	@Test
	public void testLooseEquals_1_null() throws Exception {
		assertFalse(new LooseEquals().equals(1,_null));
	}
	
	@Test
	public void testLooseEquals_Null_1() throws Exception {
		assertFalse(new LooseEquals().equals(_Null,1));
	}
	
	@Test
	public void testLooseEquals_1_Null() throws Exception {
		assertFalse(new LooseEquals().equals(1,_Null));
	}
	
	@Test
	public void testLooseEquals_1_1() throws Exception {
		assertTrue(new LooseEquals().equals(1,1));
	}
	
	@Test
	public void testLooseEquals_1_2() throws Exception {
		assertFalse(new LooseEquals().equals(1,2));
	}
	
	@Test
	public void testLooseEquals_2_1() throws Exception {
		assertFalse(new LooseEquals().equals(2,1));
	}
	
	@Test
	public void testEmptyListEmptySet() throws Exception {
		assertTrue(new LooseEquals().equals(Collections.emptyList(),Collections.emptySet()));
	}
	
	@Test
	public void testEmptySetEmptyMap() throws Exception {
		assertTrue(new LooseEquals().equals(Collections.emptySet(),Collections.emptyMap()));
	}
	
	@Test
	public void testEmptyMapEmptyString() throws Exception {
		assertTrue(new LooseEquals().equals(Collections.emptyMap(), ""));
	}
	
	@Test
	public void testEmptyListTrimmedString() throws Exception {
		assertTrue(new LooseEquals().equals(Collections.emptyList(), "     "));
	}
	
	@Test
	public void testEmptyListNull() throws Exception {
		assertTrue(new LooseEquals().equals(Collections.emptyList(), new Null("")));
	}
	
	@Test
	public void testEmptyList_null() throws Exception {
		assertTrue(new LooseEquals().equals(Collections.emptyList(), null));
	}
	
	@Test
	public void testEmptyArray_null() throws Exception {
		assertTrue(new LooseEquals().equals(new Object[]{}, null));
	}
	
	@Test
	public void testArrayPaddedWithNullsEmptyList() throws Exception {
		assertTrue(new LooseEquals().equals(new Object[]{null,null}, Collections.emptyList()));
	}
	
	@Test
	public void testArrayContainsOneObjectEmptyList() throws Exception {
		assertFalse(new LooseEquals().equals(new Object[]{new Object(),null}, Collections.emptyList()));
	}
	
	@Test
	public void testLooseEquals_Object_null() throws Exception {
		assertFalse(new LooseEquals().equals(new Object(), _null));
	}
	
	@Test
	public void testLooseEquals_Null_Object() throws Exception {
		assertFalse(new LooseEquals().equals(_Null, new Object()));
	}
	
	@Test
	public void testEmptyListOneObjectInSet() throws Exception {
		Set<Object> set = new HashSet<Object>();
		set.add(new Object());
		assertFalse(new LooseEquals().equals(Collections.emptyList(), set));
	}
	
	@Test
	public void testEmptySetOneObjectInMap() throws Exception {
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put(null, null);
		assertTrue(new LooseEquals().equals(Collections.emptySet(), m));
	}
	
	@Test
	public void testEmptyMapStringWithOneNonWhiteSpaceChar() throws Exception {
		assertFalse(new LooseEquals().equals(Collections.emptyMap(), "!"));
	}
	
	@Test
	public void testEmptyListTrimmedStringWithOneNonWhiteSpaceChar() throws Exception {
		assertFalse(new LooseEquals().equals(Collections.emptyList(), "     !"));
	}
	
	@Test
	public void testNullWithOneObjectInList() throws Exception {
		assertFalse(new LooseEquals().equals(Arrays.asList(new Object()), new Null("")));
	}
	
	@Test
	public void testNestedEmptyString_list() throws Exception {
		assertTrue(new LooseEquals().equals(Arrays.asList(""), null));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testNestedEmptyList_list() throws Exception {
		assertTrue(new LooseEquals().equals(Arrays.asList(new ArrayList<Object>()), null));
	}
}