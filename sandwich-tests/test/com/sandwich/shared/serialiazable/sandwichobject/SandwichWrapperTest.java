package com.sandwich.shared.serialiazable.sandwichobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.easymock.EasyMock;
import org.junit.Test;

import com.sandwich.shared.serialiazable.sandwichobject.equals.TwoObjectEquals;
import com.sandwich.shared.serialiazable.util.Null;

public class SandwichWrapperTest {

	@Test
	public void testHashCode_object() {
		final String toString = "meh";
		assertEquals(toString, new SandwichWrapper<String>(""){
			/**
			 * 
			 */
			private static final long serialVersionUID = 5065398948880552211L;

			@Override
			public String toString(){
				assertEquals(super.hashCode(), hashCode());
				return toString;
			}
		}.toString());
	}
	
	@Test
	public void testHashCode_wrappedObject() {
		final String toString = "meh";
		assertEquals(toString.hashCode(), new SandwichWrapper<Serializable>(toString).hashCode());
	}

	@Test
	public void testHashCode_passedToWrappedInstance() {
		ReadOnly<Null> readOnly = new ReadOnly<Null>(new Null("Null"));
		SandwichWrapper<ReadOnly<Null>> wrapper = new SandwichWrapper<ReadOnly<Null>>(readOnly);
		assertEquals(readOnly.hashCode(), wrapper.hashCode());
	}
	
	@Test
	public void testGetValue() {
		ReadOnly<Null> readOnly = new ReadOnly<Null>(new Null("Null"));
		SandwichWrapper<ReadOnly<Null>> wrapper = new SandwichWrapper<ReadOnly<Null>>(readOnly);
		assertEquals(readOnly, wrapper.getValue());
		assertEquals(readOnly.hashCode(), wrapper.hashCode());
	}

	@Test
	public void testEquals_object() {
		
		SandwichWrapper<String> wrapper = new SandwichWrapper<String>("");
		
		wrapper.p = new TwoObjectEquals() {
			private static final long serialVersionUID = -3983567054747568335L;
			@Override
			public boolean equals(Object o0, Object o1){
				return false;
			}
		};
		assertFalse(wrapper.equals(null));
		
		wrapper.p = new TwoObjectEquals() {
			private static final long serialVersionUID = 4280676060251789782L;
			@Override
			public boolean equals(Object o0, Object o1){
				return true;
			}
		};
		assertTrue(wrapper.equals(null));
	}
	
	@Test
	public void testEquals_wrappedObject() {
		SandwichWrapper<ReadOnly<Null>> equals = new SandwichWrapper<ReadOnly<Null>>(new ReadOnly<Null>(new Null("Null")));
		assertEquals(new ReadOnly<Null>(new Null("Null")), equals);
	}

	
	@Test
	public void testToString() {
		SandwichWrapper<ReadOnly<Null>> toString = new SandwichWrapper<ReadOnly<Null>>(new ReadOnly<Null>(new Null("Null")));
		assertEquals(new ReadOnly<Null>(new Null("Null")).toString(), 
				toString.toString());
	}

	@Test
	public void testEquals() {
		TwoObjectEquals p = EasyMock.createStrictMock(TwoObjectEquals.class);
		Serializable s = new Serializable(){private static final long serialVersionUID = 1L;};
		EasyMock.expect(p.equals(s,null)).andReturn(true).times(1);
		EasyMock.replay(p);
		assertTrue(new SandwichWrapper<Serializable>(s, p).equals((Object[])null));
		EasyMock.verify(p);
	}
}
