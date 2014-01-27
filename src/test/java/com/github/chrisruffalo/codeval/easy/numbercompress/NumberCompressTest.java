package com.github.chrisruffalo.codeval.easy.numbercompress;

import org.junit.Assert;
import org.junit.Test;

public class NumberCompressTest {

	@Test
	public void testSimpleCompress() {
		Main compress = new Main();
		String result = compress.processLine("40 40 40 40 29 29 29 29 29 29 29 29 57 57 92 92 92 92 92 86 86 86 86 86 86 86 86 86 86 73 73 73 73 41 41 41 41 41 41 41 41 41 41 1 1 3 3 3 2 2 2 2 14 14 14 11 11 11 2 7");
		Assert.assertEquals("4 40 8 29 2 57 5 92 10 86 4 73 10 41 2 1 3 3 4 2 3 14 3 11 1 2 1 7", result);
	}
	
}
