package com.github.chrisruffalo.codeval.hard.stringsubstitution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
	
	public static final String RANDOM_SPACE = "ABCDEFGHIJKLMNOP";
	
	public static final int MARKER_SIZE = 4;
	
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
	
	public String processLine(String fullLine) {
		
		// create split
		String[] full = fullLine.split(";");
		
		if(full == null || full.length != 2) {
			return null;
		}
		
		// now we have the replacements
		String[] replacements = full[1].split(",");		
		
		// pass into next step
		String result = this.replaceAll(full[0], replacements);
		
		// return results
		return result;
	}
	
	public String replaceAll(String full, String[] replacements) {
		// hash map for tokens
		Map<String, String> tokenMap = new HashMap<>();
		List<String> markers = new LinkedList<>();
		
		// replace and such
		for(int index = 0; index < replacements.length; index += 2) {
			// get target and replace
			String target = replacements[index];
			String replace = replacements[index+1];
			
			// get random marker
			String marker = this.randomMarker();
			
			// and use as target for replacement
			String modified = full.replaceAll(target, marker);
			
			// if the string does not change, bounce
			if(modified.equals(full)) {
				continue;
			}
			
			// update string
			full = modified;
			
			// save marker
			markers.add(marker);
			
			// then store in map pointing to final replacement
			tokenMap.put(marker, replace);
		}
		
		// now use markers to fix replacement in full string
		for(String marker : markers) {
			full = full.replaceAll(marker, tokenMap.get(marker));
		}
		
		// return with replacements sting
		return full;
	}
	
	private String randomMarker() {
		StringBuilder builder = new StringBuilder();
		
		Random random = new Random();
		
		for(int index = 0; index < Main.MARKER_SIZE; index++) {
			int part = random.nextInt(Main.RANDOM_SPACE.length());
			builder.append(Main.RANDOM_SPACE.charAt(part));
		}		
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main answer = new Main();
		List<String> results = answer.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}
}
