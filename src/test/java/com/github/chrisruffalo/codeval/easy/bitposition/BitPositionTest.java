package com.github.chrisruffalo.codeval.easy.bitposition;

import org.junit.Assert;
import org.junit.Test;

public class BitPositionTest {

	@Test
	public void testBitPosition() {
		
		Main bit = new Main();
		Assert.assertEquals("true", bit.processLine("86,2,3"));
		Assert.assertEquals("false", bit.processLine("125,1,2"));
		
		// from online data
		Assert.assertEquals("true", bit.processLine("445090,13,14"));	
	}
	
}
