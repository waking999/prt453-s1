package cdu.io;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import cdu.io.InputFile;
/**
 * This test class works for test of InputFile
 * @author : Kai
 *
 */
public class InputFileTest  {
	
	@Test
	public void testGetAdjacencyMatrix(){
		//testcase1.csv
		/*
		 * String[][] adjacencyMatrix = {
				{1,1,1,1,0,0},
				{1,1,0,1,0,0},
				{1,0,1,1,0,0},
				{1,1,1,1,1,1},
				{0,0,0,1,1,1},
				{0,0,0,1,1,1}
				
		}
		*/
		InputFile input = new InputFile();
		input.setInputFile("resource/testcase1.csv");
		input.getAdjacencyInfo();
		List<String[]> adjacencyMatrix = input.getAdjacencyMatrix();
		Assert.assertArrayEquals(new String[]{"1","1","1","1","0","0"}, adjacencyMatrix.get(0));
	}
}
