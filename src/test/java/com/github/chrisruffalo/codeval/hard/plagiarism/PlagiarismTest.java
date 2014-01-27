package com.github.chrisruffalo.codeval.hard.plagiarism;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;

import com.github.chrisruffalo.codeval.hard.plagiarism.Main;

public class PlagiarismTest {

	@Test
	public void testRead() throws URISyntaxException {
		// get path for file
		URL url = Thread.currentThread().getContextClassLoader().getResource("hard/plagiarism/cases.txt");
		File filePointer = new File(url.toURI());
		String path = filePointer.getAbsolutePath();
		
		Main plag = new Main();
		plag.check(path);
	}
	
}
