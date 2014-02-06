package com.github.chrisruffalo.codeval.hard.plagiarism;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {
	
	private static final String INPUT_SPLIT = "=====";
	
	private static final String CASE_SPLIT = "*****";
	
	public List<Integer> check(String fileName) {
		List<Integer> scores = new LinkedList<>();
		
		List<String[]> cases = this.processFile(fileName);
		for(String[] instance : cases) {
			int longest = Math.max(instance[0].length(), instance[1].length());
			String common = this.longestSubequence(instance[0], instance[1]);
			int commonLength = common.length();
			
			int score = (int)(((commonLength * 1.0)/(longest * 1.0))*100.0);
			scores.add(score);
		}
		
		return scores;
	}
	
	public List<String[]> processFile(String fileName) {
		// open file
		File file = new File(fileName);
		if(file == null || !file.exists() || !file.isFile() || !file.canRead()) {
			return Collections.emptyList();
		}
		
		// store result set
		List<String[]> results = new LinkedList<>();
		
		try(
			FileReader reader = new FileReader(file); 
			BufferedReader bReader = new BufferedReader(reader);
		) {
			String line = bReader.readLine();
			StringBuilder builder = new StringBuilder();
			while(line != null) {
				if(line.startsWith(Main.CASE_SPLIT)) {
					String[] result = this.processBlock(builder.toString());
					if(result != null && result.length != 0) {
						results.add(result);
					}
					builder = new StringBuilder();
				} else {
					builder.append(line);
					builder.append("\n");
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

	private String[] processBlock(String input) {
		if(!input.contains(Main.INPUT_SPLIT)) {
			return null;
		}
		
		// discard first line
		input = input.substring(input.indexOf("\n")+1);
		
		// split
		String[] split = input.split(Main.INPUT_SPLIT);
		split[0] = split[0].trim();
		split[1] = split[1].trim();

		// remove whitespace
		split[0] = split[0].replaceAll("\\s", "");
		split[1] = split[1].replaceAll("\\s", "");
		
		// remove endlines
		split[0] = split[0].replaceAll("\\n", "");
		split[1] = split[1].replaceAll("\\n", "");
		
		return split;
	}
	
	public String longestSubequence(String input1, String input2) {
		// need a table to store sizes/directions in
	    short[][] substringLengthTable = new short[input1.length()+1][input2.length()+1];
	    
	    // row 0 and column 0 are initialized to 0 already (by java)
	    for (int i = 0; i < input1.length(); i++) {
	        for (int j = 0; j < input2.length(); j++) {
	            if (input1.charAt(i) == input2.charAt(j)) {
	                substringLengthTable[i+1][j+1] = (short)(substringLengthTable[i][j] + 1);
	            } else {
	                substringLengthTable[i+1][j+1] = (short)Math.max(substringLengthTable[i+1][j], substringLengthTable[i][j+1]);
	            }
	        }
	    }
	 
	    // create string builder
	    StringBuilder sb = new StringBuilder();
	    
	    // start indexes at end of table
	    int index1 = input1.length();
	    int index2 = input2.length();
	    
	    // read the substring out from the matrix
	    while(index1 != 0 && index2 != 0) {	    
	        if (substringLengthTable[index1][index2] == substringLengthTable[index1-1][index2]) {
	            index1--;
	        } else if (substringLengthTable[index1][index2] == substringLengthTable[index1][index2-1]) {
	            index2--;
	        } else {
	        	// ensure that the characters are queal before appending
	        	// the answer
	            if(input1.charAt(index1-1) != input2.charAt(index2-1)) {
	            	return "";
	            }
	            // append "answer"
	            sb.append(input1.charAt(index1-1));
	            
	            // drop down both values
	            index1--;
	            index2--;
	        }
	    }
	 
	    // since we started at the end, reverse for correct answer
	    return sb.reverse().toString();
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main checker = new Main();
		List<Integer> results = checker.check(fileName);
		for(Integer result : results) {
			System.out.println(result);
		}
	}
}
