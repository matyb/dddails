package com.sandwich.test.equals;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.sandwich.shared.serialiazable.sandwichobject.equals.EqualsTest;
import com.sandwich.shared.serialiazable.sandwichobject.equals.LooseTwoObjectEqualsTest;
import com.sandwich.shared.serialiazable.sandwichobject.equals.NullOrEmptyTest;
import com.sandwich.shared.serialiazable.sandwichobject.equals.StrictTwoObjectEqualsTest;
import com.sandwich.shared.serialiazable.util.MethodSuitabilityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	EqualsTest.class,
	LooseTwoObjectEqualsTest.class,
	MethodSuitabilityTest.class,
	NullOrEmptyTest.class,
	StrictTwoObjectEqualsTest.class
})

public class EqualsTests {
	
}
