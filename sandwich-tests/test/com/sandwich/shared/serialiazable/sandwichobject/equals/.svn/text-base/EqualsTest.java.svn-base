package com.sandwich.shared.serialiazable.sandwichobject.equals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.sandwich.shared.serialiazable.util.Equals;
import com.sandwich.shared.serialiazable.util.Null;
import com.sandwich.test.TestUtils;


public class EqualsTest {
	
	Object[] _null;
	Null _Null = new Null("");
	
	@Before
	public void resetEqualsInstances() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		TestUtils.setValue("STRICT", 
				TestUtils.invokePrivate("getDefaultStrictImpl", Equals.class, (Object[])null), 
				Equals.class);
		TestUtils.setValue("LOOSE", 
				TestUtils.invokePrivate("getDefaultLooseImpl", Equals.class, (Object[])null), 
				Equals.class);
		TestUtils.setValue("NULL_OR_EMPTY", 
				TestUtils.invokePrivate("getDefaultEmptyCheckerImpl", Equals.class, (Object[])null), 
				Equals.class);
	}
	
	@Test
	public void testStrictEqualsInvocationWithZeroArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		EasyMock.replay(p);
		TestUtils.setValue("STRICT", p, Equals.class);
		assertFalse(Equals.equals());
		EasyMock.verify(p);
	}
	
	@Test
	public void testStrictEqualsInvocationWithSingleArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		EasyMock.replay(p);
		TestUtils.setValue("STRICT", p, Equals.class);
		assertFalse(Equals.equals(new Object[]{new Object()}));
		EasyMock.verify(p);
	}
	
	@Test
	public void testStrictEqualsInvocationWithTwoArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Object o0 = new Object();
		Object o1 = new Object();
		EasyMock.expect(p.equals(o0, o1)).andReturn(true).times(1);
		assertFalse(o0.equals(o1));
		EasyMock.replay(p);
		TestUtils.setValue("STRICT", p, Equals.class);
		assertTrue(Equals.equals(o0,o1));
		EasyMock.verify(p);
	}
	
	@Test
	public void testStrictEqualsInvocationWithThreeArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Object o0 = new Object();
		Object o1 = new Object();
		Object o2 = new Object();
		EasyMock.expect(p.equals(o0, o1)).andReturn(true).times(1);
		EasyMock.expect(p.equals(o1, o2)).andReturn(true).times(1);
		assertFalse(o0.equals(o1));
		assertFalse(o1.equals(o2));
		EasyMock.replay(p);
		TestUtils.setValue("STRICT", p, Equals.class);
		assertTrue(Equals.equals(o0,o1,o2));
		EasyMock.verify(p);
	}
	
	@Test
	public void testLooseEqualsInvocationWithZeroArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		EasyMock.replay(p);
		TestUtils.setValue("LOOSE", p, Equals.class);
		assertFalse(Equals.equalsLoosely());
		EasyMock.verify(p);
	}
	
	@Test
	public void testLooseEqualsInvocationWithSingleArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		EasyMock.replay(p);
		TestUtils.setValue("LOOSE", p, Equals.class);
		assertFalse(Equals.equalsLoosely(new Object[]{new Object()}));
		EasyMock.verify(p);
	}
	
	@Test
	public void testLooseEqualsInvocationWithTwoArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Object o0 = new Object();
		Object o1 = new Object();
		EasyMock.expect(p.equals(o0, o1)).andReturn(true).times(1);
		assertFalse(o0.equals(o1));
		EasyMock.replay(p);
		TestUtils.setValue("LOOSE", p, Equals.class);
		assertTrue(Equals.equalsLoosely(o0,o1));
		EasyMock.verify(p);
	}
	
	@Test
	public void testLooseEqualsInvocationWithThreeArg() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Object o0 = new Object();
		Object o1 = new Object();
		Object o2 = new Object();
		EasyMock.expect(p.equals(o0, o1)).andReturn(true).times(1);
		EasyMock.expect(p.equals(o1, o2)).andReturn(true).times(1);
		assertFalse(o0.equals(o1));
		assertFalse(o1.equals(o2));
		EasyMock.replay(p);
		TestUtils.setValue("LOOSE", p, Equals.class);
		assertTrue(Equals.equalsLoosely(o0,o1,o2));
		EasyMock.verify(p);
	}
	
	@Test
	public void testEqualsInvocationWith_null() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Object o = new Object();
		EasyMock.expect(p.equals(o,null)).andReturn(true).times(1);
		EasyMock.replay(p);
		TestUtils.setValue("STRICT", p, Equals.class);
		assertTrue(Equals.equals(o,(Object[])null));
		EasyMock.verify(p);
	}
	
	@Test
	public void testEqualsInvocationWith_emptyArray() throws Exception {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Object[] o = new Object[0];
		EasyMock.expect(p.equals(o,o)).andReturn(true).times(1);
		EasyMock.replay(p);
		TestUtils.setValue("STRICT", p, Equals.class);
		assertTrue(Equals.equals(o,o));
		EasyMock.verify(p);
	}
	
}
