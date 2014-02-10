package com.github.chrisruffalo.codeval.hard.spiralprinting;

import org.junit.Assert;
import org.junit.Test;

public class SpiralPrintingTest {


	@Test
	public void testLine() {
		
		Main sprint = new Main();
		
		// 1 2 3
		// 4 5 6
		// 7 8 9 
		Assert.assertEquals("1 2 3 6 9 8 7 4 5", sprint.processLine("3;3;1 2 3 4 5 6 7 8 9"));
		
		// a b c d
		// e f g h
		// i j k l
		Assert.assertEquals("a b c d h l k j i e f g", sprint.processLine("3;4;a b c d e f g h i j k l"));
		
	}
	
}
