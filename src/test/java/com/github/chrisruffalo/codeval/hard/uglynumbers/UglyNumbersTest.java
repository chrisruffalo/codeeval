package com.github.chrisruffalo.codeval.hard.uglynumbers;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.github.chrisruffalo.codeval.Common;

public class UglyNumbersTest {

	@Test
	public void testParse() {
		Main ugly = new Main();
		
		Assert.assertEquals(1, ugly.parse(new StringBuilder("01").reverse().toString()));
		Assert.assertEquals(20, ugly.parse(new StringBuilder("000020").reverse().toString()));
		Assert.assertEquals(-20, ugly.parse(new StringBuilder("-20").reverse().toString()));
		Assert.assertEquals(-20, ugly.parse(new StringBuilder("0-20").reverse().toString()));
		Assert.assertEquals(0, ugly.parse(new StringBuilder("14-016+00000000000002").reverse().toString()));
	}
	
	@Test
	public void testUglyCount() {
		Main ugly = new Main();
		
		Assert.assertEquals(0, ugly.countUgly("1"));
		Assert.assertEquals(1, ugly.countUgly("9"));
		Assert.assertEquals(6, ugly.countUgly("011"));
		Assert.assertEquals(64, ugly.countUgly("12345"));
	}
	
	@Test
	public void testAndVerify() throws URISyntaxException {
		// get path for file
		URL url = Thread.currentThread().getContextClassLoader().getResource("hard/uglynumbers/data.txt");
		File filePointer = new File(url.toURI());
		String path = filePointer.getAbsolutePath();
		
		Main ugly = new Main();
		List<String> data = Common.readFile(filePointer);
		
		// process file
		List<Integer> answers = ugly.processFile(path);

		// expected
		URL expectedUrl = Thread.currentThread().getContextClassLoader().getResource("hard/uglynumbers/answers.txt");
		File expectedFilePointer = new File(expectedUrl.toURI());
		List<String> expected = Common.readFile(expectedFilePointer);
		
		Assert.assertEquals(expected.size(), answers.size());
		
		for(int i = 0; i < answers.size(); i++) {
			String dataItem = data.get(i);
			Integer exItem = Integer.parseInt(expected.get(i));
			Integer anItem = answers.get(i);
			
			System.out.println("for data:" + dataItem + ", ex:" + exItem + " = an:" + anItem);
			Assert.assertEquals(exItem, anItem);
		}
	}
	
}
