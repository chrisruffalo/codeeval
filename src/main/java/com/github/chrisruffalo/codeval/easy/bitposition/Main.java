package com.github.chrisruffalo.codeval.easy.bitposition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {

	private static final boolean DEBUG = false;
	
	public List<String> processFile(String fileName) {
		// open file
		File file = new File(fileName);
		if(file == null || !file.exists() || !file.isFile() || !file.canRead()) {
			return Collections.emptyList();
		}
		
		// store result set
		List<String> results = new LinkedList<>();
		
		try(
			FileReader reader = new FileReader(file); 
			BufferedReader bReader = new BufferedReader(reader);
		) {
			String line = bReader.readLine();
			while(line != null) {
				String result = this.processLine(line);
				if(result != null && !result.isEmpty()) {
					results.add(result);
				}
				line = bReader.readLine();
			}
		} catch (FileNotFoundException e) {
			// nothing we can do about this
			return Collections.emptyList();
		} catch (IOException e) {
			// nothing we can do about this
			return Collections.emptyList();
		}
		
		return results;
	}
	
	public String processLine(String line) {
		String[] split = line.split(",");
		
		BigInteger integer = BigInteger.valueOf(Long.parseLong(split[0]));
		
		
		
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < integer.bitLength(); i++) {
			if(integer.testBit(i)) {
				builder.append("1");
			} else {
				builder.append("0");
			}
		}
		builder.append("b0");
		builder.reverse();
		
		
		// are the two specified bits the same?
		int spot1 = Integer.parseInt(split[1]);
		boolean first = integer.testBit(spot1-1);
		
		int spot2 = Integer.parseInt(split[2]);
		boolean second = integer.testBit(spot2-1);
		
		boolean output = (first == second);

		if(Main.DEBUG) {
			System.out.println("input: " + line);
			System.out.println("integer: " + integer.toString());
			System.out.println("binary: " + builder.toString());
			System.out.println("first (" + spot1 + "): " + first);
			System.out.println("second (" + spot2 + "): " + second);
			System.out.println("output: " + output);
			System.out.println("====================================\n");
		}
		
		return String.valueOf(output);
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main bit = new Main();
		List<String> results = bit.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}
}
