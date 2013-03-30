package algorithm;

import io.IInput;

/**
 * This interface work for extension of new algorithms
 * @author : Kai
 *
 */
public interface IAlgorithm {
	/**
	 * Put the source of data into an algorithm 
	 * @param input the source of data
	 */
	public void setInput(IInput input);
	/**
	 * generate dominating set
	 */
	public void generateDominatingSet();
	/**
	 * return the array of dominating set
	 * @return
	 */
	public String[][] getDominatingSetArr();
	/**
	 * After getting source of data, set some initial variables 
	 */
	public void initialization();
}
