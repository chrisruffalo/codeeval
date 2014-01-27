package com.github.chrisruffalo.codeval.hard.minesweeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Main {

	private static final int MINE = -1;
	
	private static final int MINE_CHAR = '*';
	
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
		
		// do splits
		String[] split1 = fullLine.split(";");
		String[] size = split1[0].split(",");
		
		// values
		String cols = size[0];
		String rows = size[1];
		String input = split1[1];
		
		// get integers
		int rInt = Integer.parseInt(rows);
		int cInt = Integer.parseInt(cols);
		
		// create output array
		int[][] output = new int[cInt+2][rInt+2];
		
		// start in corner, not on edge
		int cIndex = 1;
		int rIndex = 1;
		
		
		// go through string
		for(int i = 0; i < input.length(); i++) {
			char current = input.charAt(i);
			
			// check character
			if(Main.MINE_CHAR == current) {
				this.incrementAdjacent(output, rIndex, cIndex);
				output[cIndex][rIndex] = Main.MINE;
			}
			
			// increment row
			rIndex++;
			// and check for column increment
			if(rIndex > rInt) {
				rIndex = 1;
				cIndex++;
			}			
		}
		
		// string builder
		StringBuilder builder = new StringBuilder();
		
		// read to string
		for(int cReadIndex = 1; cReadIndex <= cInt; cReadIndex++) {
			for(int rReadIndex = 1; rReadIndex <= rInt; rReadIndex++) {
				// get value
				int current = output[cReadIndex][rReadIndex];
				// decide what to output
				if(Main.MINE == current) {
					builder.append("*");
				} else {
					builder.append(current);
				}
			}
		}
	
		return builder.toString();
	}
	
	private void incrementAdjacent(int[][] input, int row, int col) {
		for(int rAdjust = -1; rAdjust <= 1; rAdjust++) {
			for(int cAdjust = -1; cAdjust <= 1; cAdjust++) {
				// do not increment current position
				if(0 == rAdjust && 0 == cAdjust) {
					continue;
				}
				
				//System.out.println("adjusting [" + col + "+" + cAdjust + "][" + row + "+" + rAdjust + "]");
				
				// get the value at the current spot
				int current = input[col+cAdjust][row+rAdjust];				
				// otherwise
				if(current > Main.MINE) {
					input[col+cAdjust][row+rAdjust]++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main sweeper = new Main();
		List<String> results = sweeper.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}	
}
