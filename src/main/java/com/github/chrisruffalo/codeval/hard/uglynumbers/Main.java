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

	public List<Integer> processFile(String fileName) {
		// open file
		File file = new File(fileName);
		if(file == null || !file.exists() || !file.isFile() || !file.canRead()) {
			return Collections.emptyList();
		}
		
		// store result set
		List<Integer> results = new LinkedList<>();
		
		try(
			FileReader reader = new FileReader(file); 
			BufferedReader bReader = new BufferedReader(reader);
		) {
			String line = bReader.readLine();
			while(line != null) {
				Integer result = this.processLine(line);
				if(result != null) {
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
	
	public int processLine(CharSequence fullLine) {
		return this.countUgly(fullLine);
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
	
	public StringBuilder tokenize(CharSequence input) {
		StringBuilder output = new StringBuilder();
		
		for(int index = 0; index < input.length(); index++) {
			output.append(input.charAt(index));
			if(index < input.length() - 1) {
				output.append("!");
			}
		}
				
		return output;
	}
	
	public int cycle(StringBuilder builder, int offset) {
		
		if(builder == null || builder.length() == 0) {
			return 0;
		}
		
		// end of the road
		if(offset >= builder.length()) {
			// parse
			long value = this.parse(builder);
			if(this.isUgly(value)) {
				return 1;
			} else {
				return 0;
			}			
		}
		
		int sum = 0;
		
		builder.replace(offset, offset+1, "!");
		sum += this.cycle(builder, offset+2);

		builder.replace(offset, offset+1, "+");
		sum += this.cycle(builder, offset+2);
		
		builder.replace(offset, offset+1, "-");
		sum += this.cycle(builder, offset+2);		
		
		return sum;
	
	}	
	
	public long parse(CharSequence input) {
		return this.parse(0, input);
	}
	
	public long parse(int index, CharSequence input) {
		if(input == null || input.length() == 0) {
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
			} else if('!' == current) {
				index++;
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
	
	public int countUgly(CharSequence input) {
		// rip off leading values and create multiplier
		int multiplier = 1;
		int index = 0;
		boolean deleteLeading = false;
		
		// count leading zeroes
		while(index < input.length() - 1 && '0' == input.charAt(index)) {
			multiplier *= 3;
			index++;
			deleteLeading = true;
		}
		
		// create easily manipulated string builder with tokens
		StringBuilder builder = this.tokenize(input);
		
		// delete leading values
		if(deleteLeading) {
			builder.replace(0, index*2, "");
		}
		
		// reverse now, because cycling and parsing happen in reverse
		builder.reverse();
		
		// count by cycling values
		int count = this.cycle(builder, 1);
		
		// use small optimization multiplier to calculate
		// real value
		return count * multiplier;
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main ugly = new Main();
		List<Integer> results = ugly.processFile(fileName);
		for(Integer result : results) {
			System.out.println(result);
		}
	}	
}
