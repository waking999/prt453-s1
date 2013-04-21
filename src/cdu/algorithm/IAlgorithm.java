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

	public static final int NOT_DS = Integer.MAX_VALUE; //take use of an extremely big number to indicate that the size of dominating set is impossible

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
	public void initialization(IInput input, int k) ;
	public void initialization(int numOfVertex,List<String[]> adjacencyMatrix, int k);
	
	public void setK(int k);

	public void setNumOfVertex(int numOfVertex);

	public void setAdjacencyMatrix(List<String[]> adjacencyMatrix);
}
