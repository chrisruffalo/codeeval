package com.github.chrisruffalo.codeval.hard.stringsubstitution;

import org.junit.Assert;
import org.junit.Test;

import com.github.chrisruffalo.codeval.hard.stringsubstitution.Main;

public class StringSubstitutionTest {

	@Test
	public void testLine() {
		
		Main sub = new Main();
		String result = sub.processLine("10011011001;0110,1001,1001,0,10,11");
		Assert.assertEquals("11100110", result);
		
	}
	
}
