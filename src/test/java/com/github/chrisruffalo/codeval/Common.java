package com.github.chrisruffalo.codeval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Common {

	public static List<String> readFile(String fileName) {
		return Common.readFile(new File(fileName));
	}
	
	public static List<String> readFile(File file) {
		try(
			FileReader reader = new FileReader(file); 
			BufferedReader bReader = new BufferedReader(reader);
		) {
			return Common.read(bReader);
		} catch (FileNotFoundException e) {
			return Collections.emptyList();
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}
	
	public static List<String> read(InputStream toRead) {
		try (InputStreamReader reader = new InputStreamReader(toRead)) {
			return Common.read(reader);
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}
	
	private static List<String> read(Reader toRead) throws IOException {
		final BufferedReader reader;
		boolean close = false;
		
		if(toRead instanceof BufferedReader) {
			reader = (BufferedReader)toRead;
		} else {
			reader = new BufferedReader(toRead);
			close = true;
		}
		
		List<String> read = new LinkedList<>();
		String line = reader.readLine();
		while(line != null) {
			read.add(line);
			line = reader.readLine();
		}
		
		if(close) {
			reader.close();
		}
		
		return read;
	}
	
}
