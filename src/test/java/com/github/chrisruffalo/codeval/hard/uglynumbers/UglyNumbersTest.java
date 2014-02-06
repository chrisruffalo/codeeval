package com.github.chrisruffalo.codeval.hard.uglynumbers;

import junit.framework.Assert;

import org.junit.Test;

public class UglyNumbersTest {

	@Test
	public void testParse() {
		Main ugly = new Main();
		
		Assert.assertEquals(20, ugly.parse("20"));
		Assert.assertEquals(20, ugly.parse("0+20"));
		Assert.assertEquals(-20, ugly.parse("-20"));
		Assert.assertEquals(-20, ugly.parse("0-20"));
		
		Assert.assertEquals(0, ugly.parse("14-016+00000000000002"));
	}
	
	@Test
	public void testUglyCount() {
		Main ugly = new Main();
		
		Assert.assertEquals(0, ugly.countUgly("1"));
		Assert.assertEquals(1, ugly.countUgly("9"));
		Assert.assertEquals(6, ugly.countUgly("011"));
		Assert.assertEquals(64, ugly.countUgly("12345"));
	}
}
