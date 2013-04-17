package cdu.algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.springframework.stereotype.Component;

import cdu.io.IInput;
import cdu.util.Util;

/**
 * take use of mathematics to get dominating set
 * 
 * @author : Kai
 * 
 */
@Component("algorithmMathematics")
public class AlgorithmMathematics implements IAlgorithm {
	private IInput input;
	// private static final String CONNECTED = "1";
	private static final String SET_MULTIPLY_SIGN = "*";

	private int numOfVertex; // number of vertex
	private List<String[]> adjacencyMatrix; // adjacency matrix

	private Set<List<String>> dominatingSetSet; // an array contain dominating
												// set

	/**
	 * return the array of dominating set
	 * 
	 * @return
	 */
	public Set<List<String>> getDominatingSetSet() {
		return dominatingSetSet;
	}
	
	public void setK(int k) {
		
	}

	/**
	 * Put the source of data into an algorithm
	 * 
	 * @param input
	 *            the source of data
	 */
	public void setInput(IInput input) {
		this.input = input;
	}

	/**
	 * After getting source of data, set some initial variables
	 */
	public void initialization() {
		numOfVertex = input.getNumOfVertex();
		adjacencyMatrix = input.getAdjacencyMatrix();
	}

	public void generateDominatingSet() {

		// put the adjacency matrix into a queue which contains set. set means
		// no duplicated elements
		Queue<Set<String>> mulList = new LinkedList<Set<String>>();
		for (int i = 0; i < numOfVertex; i++) {

			Set<String> sumList = new HashSet<String>();
			for (int j = 0; j < numOfVertex; j++) {
				String[] adjacencyMatrixRow = adjacencyMatrix.get(i);
				if (Util.CONNECTED.equals(adjacencyMatrixRow[j])) {
					sumList.add(Integer.toString(j));
				}
			}
			mulList.offer(sumList);
		}

		// multiply the sets in the queue two by two and put the result in the
		// end of the queue until only one set left in the queue, which is the
		// final one that could not be cleaned any more
		while (mulList.size() != 1) {
			// poll the first two set from the queue
			Set<String> a = mulList.poll();
			Set<String> b = mulList.poll();
			// multiply the elements in the two sets
			Set<String> result = mul(a, b);
			// clean the result according to laws in set theory
			Set<String> cleanResult = clean(result);
			// put the cleaned set in the end of the queue waiting for later
			// multiply
			mulList.offer(cleanResult);
		}

		Set<String> lastLine = mulList.peek();

		Object[] mulListArr = lastLine.toArray();
		int mulListArrLen = lastLine.size();
		dominatingSetSet = new HashSet<List<String>>(mulListArrLen);

		for (int i = 0; i < mulListArrLen; i++) {
			String ds = (String) mulListArr[i];
			String[] dsEle = ds.split("[*]");

			dominatingSetSet.add(Arrays.asList(dsEle));
		}

	}

	public void setNumOfVertex(int numOfVertex) {
		this.numOfVertex = numOfVertex;
	}

	public void setAdjacencyMatrix(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}

	/**
	 * eliminate duplicated elements according to laws in set theory , for
	 * example, 1*4 and 4*1, eliminate 1*4; 1*4 and 4, eliminate 1*4
	 * 
	 * @param a
	 *            , a set that might contain duplicated elements
	 * @return Set, a set without duplicated elements
	 */
	private Set<String> clean(Set<String> a) {

		// commutative law: X*Y = Y*X
		Object[] aStrArr = a.toArray();
		int aStrArrLen = aStrArr.length;
		for (int i = 0; i < aStrArrLen; i++) {
			String x = (String) aStrArr[i];
			String[] xEle = x.split("[*]");
			// the purpose of sort is to prepare for the judgment of equal
			Arrays.sort(xEle);
			for (int j = i + 1; j < aStrArrLen; j++) {
				String y = (String) aStrArr[j];
				String[] yEle = y.split("[*]");
				Arrays.sort(yEle);
				if (Arrays.equals(xEle, yEle)) {
					String removeStr = contact(yEle, SET_MULTIPLY_SIGN);
					a.remove(removeStr);
				}
				
			}

		}

		// absorption law: X+XY=X
		aStrArr = a.toArray();
		aStrArrLen = aStrArr.length;

		// TODO: the performance of the for loops could be improved since a
		// would remove some elements so as to the length of array could be
		// less, which means less comparasion
		for (int i = 0; i < aStrArrLen; i++) {
			String x = (String) aStrArr[i];
			String[] xEle = x.split("[*]");

			List<String> xList = Arrays.asList(xEle);

			for (int j = 0; j < aStrArrLen; j++) {
				if (i != j) {
					String y = (String) aStrArr[j];
					String[] yEle = y.split("[*]");

					List<String> yList = Arrays.asList(yEle);
					if (xList.containsAll(yList)) {
						String removeStr = contact(xEle, SET_MULTIPLY_SIGN);
						a.remove(removeStr);

					} else if (yList.containsAll(xList)) {
						String removeStr = contact(yEle, SET_MULTIPLY_SIGN);
						a.remove(removeStr);
					}
				}
			}

		}

		return a;
	}

	/**
	 * the opposite operation of split
	 * 
	 * @param strs
	 * @param separator
	 * @return
	 */
	private String contact(String[] strs, String separator) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++) {
			sb.append(strs[i]).append(separator);
		}
		return sb.substring(0, sb.length() - separator.length());
	}

	/**
	 * apply laws of set theory to multiply elements in two sets
	 * 
	 * @param a
	 *            , the first set
	 * @param b
	 *            , the second set
	 * @return
	 */
	private Set<String> mul(Set<String> a, Set<String> b) {
		Iterator<String> aIt = a.iterator();

		Set<String> mulResult = new HashSet<String>();
		while (aIt.hasNext()) {
			String x = aIt.next();
			Iterator<String> bIt = b.iterator();
			while (bIt.hasNext()) {
				String y = bIt.next();
				String temp = contactMul(x, y);
				mulResult.add(temp);
			}
		}

		return mulResult;
	}

	/**
	 * contact two strings which prsent elements in two sets
	 * 
	 * @param x
	 *            , the element in the first set
	 * @param y
	 *            , the element in the second set
	 * @return String, which might look like x*y. If x=y,it looks like x since
	 *         idempotent laws x*x=x
	 */
	private String contactMul(String x, String y) {
		// absorption law: X*X=X
		if (x.equals(y)) {
			return x;
		}

		String[] xEle = x.split("[*]");
		String[] yEle = y.split("[*]");

		Set<String> tempSet = new HashSet<String>();

		tempSet.addAll(Arrays.asList(xEle));
		tempSet.addAll(Arrays.asList(yEle));

		StringBuffer tempSB = new StringBuffer();
		Iterator<String> tempIt = tempSet.iterator();
		while (tempIt.hasNext()) {
			tempSB.append(tempIt.next()).append(SET_MULTIPLY_SIGN);
		}

		return tempSB.substring(0, tempSB.length() - 1);
	}
}
