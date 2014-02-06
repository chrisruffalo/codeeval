package com.github.chrisruffalo.codeval.hard.uglynumbers;

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
	
	public String processLine(String fullLine) {
		return ""+this.countUgly(fullLine);
	}
	
	public boolean isUgly(long check) {
		if(check % 2 == 0) {
			return true;
		} else if(check % 3 == 0) {
			return true;
		} else if(check % 5 == 0) {
			return true;
		} else if(check % 7 == 0) {
			return true;
		}
		
		return false;
	}
	
	public int proliferateAndCheck(String input) {
		return this.proliferateAndCheck(0, input);
	}
	
	public int proliferateAndCheck(int index, String input) {
		// can't do anything at the end of the string
		if(index >= input.length() - 1) {
			// if there is some value, parse it
			if(input != null && !input.isEmpty()) {
				Long value = this.parse(input);
				if(this.isUgly(value)) {
					return 1;
				}
			}
			return 0;
		}
		
		int sum = 0;
		
		// proliferate with "nothing" input
		sum += this.proliferateAndCheck(index+1, input);
		
		// modify for + and -
		String inputPlus = input.substring(0,index+1) + "+" + input.substring(index+1);
		String inputMinus = input.substring(0, index+1) + "-" + input.substring(index+1);
		
		// proliferate with those inputs
		sum += this.proliferateAndCheck(index+2, inputPlus);
		sum += this.proliferateAndCheck(index+2, inputMinus);
		
		// return sum
		return sum;
	}
	
	public long parse(String input) {
		String reverse = (new StringBuilder(input)).reverse().toString();
		return this.parse(0, reverse);
	}
	
	public long parse(int index, String input) {
		if(input == null || input.isEmpty()) {
			return 0;
		}
		
		String value = "";
		int carryValue = 0;
		while(index < input.length()) {
			char current = input.charAt(index);
			if('-' == current || '+' == current) {
				// parse value
				long parsedValue = Long.parseLong(value);
				
				index++;
				value = "";
				
				// do maths
				if('-' == current) {
					parsedValue = (0 - parsedValue);
				} 
					
				carryValue += parsedValue;
			} else {
				value = current + value;
				index++;
			}			
		}

		if(value != null && !value.isEmpty()) {
			carryValue += Long.parseLong(value);
		} 
		
		return carryValue;
	}
	
	public int countUgly(String input) {
		int count = this.proliferateAndCheck(input);
		return count;
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main ugly = new Main();
		List<String> results = ugly.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}	
}
