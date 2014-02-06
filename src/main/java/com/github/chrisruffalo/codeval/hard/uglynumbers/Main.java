package com.github.chrisruffalo.codeval.hard.uglynumbers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
		if(input == null || input.isEmpty()) {
			return 0;
		}

		// this is a minor optimization that removes leading 
		// zeroes and replaces them with a multiplier that
		// accounts for the three probabilities at each 0
		// removed, one for "nothing", one for "+" and 
		// one for "-"
		int multiplier = 1;
		while(!input.isEmpty() && input.charAt(0) == '0') {
			input = input.substring(1);
			multiplier *= 3;
		}
		
		// for some reason "0" is 1, i don't get it...
		if(input == null || input.isEmpty()) {
			return 1;
		}		
		
		Queue<String> workingQueue = new LinkedList<>();
		workingQueue.add(input);
		
		int max = input.length();
		int index = max - 1;
		while(index > 0) {
			
			int limit = workingQueue.size();
			for(int i = 0; i < limit; i++) {
				String item = workingQueue.poll();
				if(item == null) {
					break;
				}
								
				// add item to back of queue
				workingQueue.add(item);
				
				// calculate
				int itemLength = item.length();
				int offset = itemLength - index;				
				
				// create sub-parts
				String part1 = item.substring(0, offset);
				String part2 = item.substring(offset);
				
				// modify for + and -
				String itemPlus = part1 + "+" + part2;
				String itemMinus = part1 + "-" + part2;
				
				// and add to queue
				workingQueue.add(itemPlus);
				workingQueue.add(itemMinus);
			}
			
			index--;
		}
		
		int sum = 0;
		while(!workingQueue.isEmpty()) {
			String item = workingQueue.poll();
			if(item == null) {
				break;
			}
			if(item == null || item.isEmpty()) {
				continue;
			}
			if(this.isUgly(this.parse(item))) {
				sum++;
			}
		}
		
		return sum * multiplier;
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
