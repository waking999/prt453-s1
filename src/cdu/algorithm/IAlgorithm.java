package cdu.algorithm;

import java.util.List;
import java.util.Set;

import cdu.io.IInput;

/**
 * This interface work for extension of new algorithms
 * 
 * @author : Kai
 * 
 */

public interface IAlgorithm {

	/**
	 * Put the source of data into an algorithm
	 * 
	 * @param input
	 *            the source of data
	 */
	public void setInput(IInput input);

	/**
	 * generate dominating set
	 */
	public void generateDominatingSet();

	/**
	 * return the array of dominating set
	 * 
	 * @return
	 */
	public Set<List<String>> getDominatingSetSet();

	/**
	 * After getting source of data, set some initial variables
	 */
	public void initialization();

	public void setK(int k);

	public void setNumOfVertex(int numOfVertex);

	public void setAdjacencyMatrix(List<String[]> adjacencyMatrix);
}
