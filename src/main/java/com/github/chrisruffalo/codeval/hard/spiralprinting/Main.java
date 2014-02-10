package com.github.chrisruffalo.codeval.hard.spiralprinting;

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
	
	public String processLine(String fullLine) {
		
		String[] initial = fullLine.split(";");
		
		int row = Integer.parseInt(initial[0]);
		int col = Integer.parseInt(initial[1]);
		
		// get values
		String[] values = initial[2].split(" ");
		//System.out.println("initial size: " + values.length);
		
				
		final StringBuilder builder = new StringBuilder();
		
		int pos = -1;

		int colMove = col;
		int rowMove = row - 1;
		
		int rowDirection = col;
		int colDirection = 1;
		
		int processed = 0;
		
		while(rowMove != 0 || colMove != 0) {
			
			if(processed >= values.length) {
				break;
			}
			
			//System.out.println("colmove: " + colMove);
			if(colMove > 0) {
				for(int colMoveCount = 0; colMoveCount < colMove; colMoveCount++) {
					pos += colDirection;
					//System.out.println("pos: " + pos);
					String item = values[pos];
					builder.append(item);
					builder.append(" ");
					processed++;
				}
				colDirection *= -1;
				colMove--;
			}
			//System.out.println("\t" + builder.toString().trim());
			
			if(processed >= values.length) {
				break;
			}
			
			//System.out.println("rowmove: " + rowMove);
			if(rowMove > 0) {
				for(int rowMoveCount = 0; rowMoveCount < rowMove; rowMoveCount++) {
					pos += rowDirection;
					//System.out.println("pos: " + pos);
					String item = values[pos];
					builder.append(item);
					builder.append(" ");
					processed++;
				}
				rowDirection *= -1;
				rowMove--;
			}
			//System.out.println("\t" + builder.toString().trim());
	
			
		}		
		
		return builder.toString().trim();
	}
	
	public static void main(String[] args) {
		// do nothing on null set
		if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty()) {
			return;
		}
		
		String fileName = args[0];
		
		Main printer = new Main();
		List<String> results = printer.processFile(fileName);
		for(String result : results) {
			System.out.println(result);
		}
	}
}
