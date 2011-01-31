package com.sandwich.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.sandwich.server.oneholder.OneHolderEntity;
import com.sandwich.shared.oneholder.OneHolderTO;
import com.sandwich.shared.oneholder.OneHolderValidation;

public class StrippedNameTest {

	@Test
	public void testCreation_0LengthString() throws Exception {
		new StrippedName("");
	}
	
	@Test
	public void testCreation_nullString() throws Exception {
		new StrippedName((String)null);
	}
	
	@Test
	public void testCreation_nullClass() throws Exception {
		new StrippedName((Class<?>)null);
	}
	
	@Test
	public void testCreation_0LengthString_originalAndStripped() throws Exception {
		StrippedName name = new StrippedName("");
		assertEquals("", name.fullName);
		assertEquals("", name.strippedName);
	}
	
	@Test
	public void testCreation_null_originalAndStripped() throws Exception {
		StrippedName name = new StrippedName((String)null);
		assertNull(name.fullName);
		assertEquals("", name.strippedName);
	}
	
	@Test
	public void testProperClass() throws Exception {
		StrippedName name = new StrippedName(String.class);
		assertEquals(String.class.getName(), name.fullName);
		assertEquals("String", name.strippedName);
	}
	
	@Test
	public void testValidationClass() throws Exception {
		StrippedName name = new StrippedName(OneHolderValidation.class);
		assertEquals(OneHolderValidation.class.getName(), name.fullName);
		assertEquals("OneHolder", name.strippedName);
	}
	
	@Test
	public void testEntityClass() throws Exception {
		StrippedName name = new StrippedName(OneHolderEntity.class);
		assertEquals(OneHolderEntity.class.getName(), name.fullName);
		assertEquals("OneHolder", name.strippedName);
	}
	
	@Test
	public void testTOClass() throws Exception {
		StrippedName name = new StrippedName(OneHolderTO.class);
		assertEquals(OneHolderTO.class.getName(), name.fullName);
		assertEquals("OneHolder", name.strippedName);
	}
}
