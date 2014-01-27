package com.github.chrisruffalo.codeval.hard.commonsubsequence;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Solves https://www.codeeval.com/open_challenges/6/ in Java.
 * 
 * @author Chris Ruffalo
 *
 */
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
		String[] split = line.split(";");
		if(split.length != 2 || split[0] == null || split[0].isEmpty() || split[1] == null || split[1].isEmpty()) {
			// can't process bad strings
			return "";
		}
		String result = this.longestSubequence(split[0], split[1]);
		return result;
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
		
		Main answer = new Main();
		List<String> results = answer.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}
}
