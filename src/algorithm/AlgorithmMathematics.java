package algorithm;

import io.IInput;

/**
 * take use of mathematics to get dominating set
 * 
 * @author : Kai
 * 
 */

public class AlgorithmMathematics implements IAlgorithm {
	private IInput input;
	
	private String[][] dominatingSetArr ;
	/**
	 * return the array of dominating set
	 * @return
	 */
	public String[][] getDominatingSetArr() {
		return dominatingSetArr;
	}

	/**
	 * Put the source of data into an algorithm 
	 * @param input the source of data
	 */
	public void setInput(IInput input) {
		this.input = input;
	}
	
	/**
	 * generate dominating set
	 */
	public void generateDominatingSet(){
		
	}

	/**
	 * After getting source of data, set some initial variables 
	 */
	public void initialization(){
		
	}
}
