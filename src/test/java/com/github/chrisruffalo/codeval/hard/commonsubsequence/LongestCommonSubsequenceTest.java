package com.github.chrisruffalo.codeval.hard.commonsubsequence;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.chrisruffalo.codeval.hard.commonsubsequence.Main;

public class LongestCommonSubsequenceTest {

	@Test
	public void testLongestSubstring() {
		Main solver = new Main();
		
		String result = solver.longestSubequence("XMJYAUZ", "MZJAWXU");
		Assert.assertEquals("MJAU", result);
		
		result = solver.longestSubequence("MZJAWXU", "XMJYAUZ");
		Assert.assertEquals("MJAU", result);
	}
	
	@Test
	public void testProcessLine() {
		Main solver = new Main();
		
		String result = solver.processLine("XMJYAUZ;MZJAWXU");
		Assert.assertEquals("MJAU", result);
		
		result = solver.processLine("MZJAWXU;XMJYAUZ");
		Assert.assertEquals("MJAU", result);
	}
	
	@Test
	public void testProcessFile() throws URISyntaxException {
		// get path for file
		URL url = Thread.currentThread().getContextClassLoader().getResource("hard/commonsubsequence/sample.txt");
		File filePointer = new File(url.toURI());
		String path = filePointer.getAbsolutePath();
		
		Main solver = new Main();
		List<String> results = solver.processFile(path);
		
		Assert.assertEquals(2, results.size());
		Assert.assertEquals("MJAU", results.get(0));
		Assert.assertEquals("MJAU", results.get(1));
	}
	
}
