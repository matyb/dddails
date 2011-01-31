package com.sandwich.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sandwich.shared.DoTimes;

public class DoTimesTest {

	@Test(expected=IllegalArgumentException.class)
	public void testDoTimes_negNumberOfTimes() throws Exception {
		new DoTimes(-1, new Runnable(){@Override public void run() {}});
	}
	
	@Test
	public void testDoTimes_zeroNumberOfTimes() throws Exception {
		assertNumberOfTimes(0);
	}
	
	@Test
	public void testDoTimes_once() throws Exception {
		assertNumberOfTimes(1);
	}

	@Test
	public void testDoTimes_twice() throws Exception {
		assertNumberOfTimes(2);
	}
	
	private void assertNumberOfTimes(int times) {
		final int[] calls = {0};
		final DoTimes doTimes = new DoTimes(times, new Runnable(){@Override public void run() {
			calls[0] = calls[0] + 1;
		}});
		assertEquals(0, calls[0]);
		doTimes.run();
		assertEquals(times, calls[0]);
	}
	
}
