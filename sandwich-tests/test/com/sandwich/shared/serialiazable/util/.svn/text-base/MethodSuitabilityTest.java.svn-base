package com.sandwich.shared.serialiazable.util;

import java.lang.reflect.Method;

import org.junit.Test;

import com.sandwich.test.TestUtils;

public class MethodSuitabilityTest {

	@Test
	public void testMethodSuitability_equalsAndHashCodeReference() throws Exception {
		MethodSuitability suitability = new MethodSuitability(Object.class.getMethods()[0]);
		TestUtils.assertHashCodeEqualsContracts(suitability, suitability, suitability);
	}
	
	@Test
	public void testMethodSuitability_equalsAndHashCodeInstance() throws Exception {
		Method objectMethod = Object.class.getMethods()[0];
		TestUtils.assertHashCodeEqualsContracts(
				new MethodSuitability(objectMethod), 
				new MethodSuitability(objectMethod),
				new MethodSuitability(objectMethod));
	}
}
