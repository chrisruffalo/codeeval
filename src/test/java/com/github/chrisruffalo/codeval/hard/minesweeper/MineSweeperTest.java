package com.github.chrisruffalo.codeval.hard.minesweeper;

import org.junit.Assert;
import org.junit.Test;

public class MineSweeperTest {

	@Test
	public void basicTest() {
		Main mine = new Main();
		
		Assert.assertEquals("**100332001*100", mine.processLine("3,5;**.........*..."));
		Assert.assertEquals("*10022101*101110", mine.processLine("4,4;*........*......"));
	}
	
}
