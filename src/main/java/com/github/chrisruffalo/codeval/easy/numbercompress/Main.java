package com.github.chrisruffalo.codeval.easy.numbercompress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {

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
		String[] split = line.split(" ");
		
		//System.out.println("# INPUT : " + line);
		
		StringBuilder builder = new StringBuilder();
		
		int size = 1;
		for(int index = 0; index < split.length; index++) {
			if(index == split.length - 1 && index != 0) {
				if(split[index].equals(split[index-1])) {
					builder.append(size);
					builder.append(" ");
					builder.append(split[index]);
					break;
				} else {
					builder.append("1 ");
					builder.append(split[index]);
					break;
				}
			} else {
				if(index+1 < split.length && split[index].equals(split[index+1])) {
					size++;
				} else {
					builder.append(size);
					builder.append(" ");
					builder.append(split[index]);
					builder.append(" ");
					size = 1;
				}
			}
		}
		
		String result = builder.toString().trim();
		
		//System.out.println("> " + result);
		
		return result;
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main compress = new Main();
		List<String> results = compress.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}
}
