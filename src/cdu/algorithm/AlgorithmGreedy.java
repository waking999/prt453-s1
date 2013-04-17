package cdu.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import cdu.io.IInput;
import cdu.util.Util;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

//import edu.uci.ics.jung.graph.Graph;

@Component("algorithmGreedy")
public class AlgorithmGreedy implements IAlgorithm {
	private IInput input;
	private Set<List<String>> dominatingSetSet; // an array contain dominating
												// set

	private int numOfVertex; // number of vertex
	private List<String[]> adjacencyMatrix; // adjacency matrix
	private int k; // the parameter that the size of dominatingSet should less
					// than or equal to it.

	private Graph<String, Integer> g;

	private static Logger log;
	static {
		log = Logger.getLogger("cdu.algorithm.AlgorithmGreedy");
		log.setLevel(Level.OFF);
	}

	@Override
	public void setInput(IInput input) {
		this.input = input;

	}

	public void setNumOfVertex(int numOfVertex) {
		this.numOfVertex = numOfVertex;
	}

	public void setAdjacencyMatrix(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}

	@Override
	public void generateDominatingSet() {

		// prepare graph
		g = new SparseMultigraph<String, Integer>();
		for (int i = 0; i < numOfVertex; i++) {
			g.addVertex(Integer.toString(i));
		}

		for (int i = 0; i < numOfVertex; i++) {
			String[] rowArr = adjacencyMatrix.get(i);
			for (int j = i + 1; j < numOfVertex; j++) {
				if (Util.CONNECTED.equals(rowArr[j])) {

					int digit = i * numOfVertex + j;
					g.addEdge(digit, Integer.toString(i), Integer.toString(j));

				}
			}
		}
		// get the sorted vertex according their degree
		List<VertexDegree> vertexDegreeList = new ArrayList<VertexDegree>();
		for (int i = 0; i < numOfVertex; i++) {
			int degree = g.degree(Integer.toString(i));
			vertexDegreeList.add(new VertexDegree(i, degree));
		}
		Collections.sort(vertexDegreeList);

		// go through all vertex but follow the order of degree;
		dominatingSetSet = new HashSet<List<String>>();

		List<String> certainDS = new ArrayList<String>();
		List<String> complementaryDS = new ArrayList<String>();
		List<String> T = new ArrayList<String>();
		for (int j = 0; j < numOfVertex; j++) {
			T.add(Integer.toString(vertexDegreeList.get(numOfVertex - 1 - j)
					.getVertex()));
		}

		// idea: Take all vertices of the highest degree as an approximate
		// solution
		int index = 0;
		int step = 0;
		while (!T.isEmpty()) {
			String vertexS = T.get(index);

			certainDS.add(vertexS);
			T.remove(vertexS);

			StringBuffer infoSB = new StringBuffer();

			Collection<String> neighborsOfI = g.getNeighbors(vertexS);
			Iterator<String> nIIt = neighborsOfI.iterator();
			infoSB.append("Step " + step + ":");
			infoSB.append("remove vertex ").append(vertexS).append(" (with ")
					.append(neighborsOfI.size()).append(" neighbors ) firstly");
			infoSB.append(" and its neighbors-");
			while (nIIt.hasNext()) {

				String nIItNextStr = nIIt.next();

				if (T.contains(nIItNextStr)) {
					infoSB.append(nIItNextStr).append(",");
					T.remove(nIItNextStr);
					complementaryDS.add(nIItNextStr);
				}
			}

			index = 0;
			log.info(infoSB.toString());
			step++;
		}

		// local search hill climbing
		int certainDSLen = certainDS.size();
		int minDSLen = certainDSLen;
		if (certainDSLen <= k) {
			dominatingSetSet.add(certainDS);
		}

		int complementaryDSLen = complementaryDS.size();

		for (int i = 0; i < complementaryDSLen; i++) {
			List<String> certainDSBk = new ArrayList<String>(certainDSLen);

			List<String> complementaryDSBk = new ArrayList<String>(
					complementaryDSLen);
			certainDSBk.addAll(certainDS);
			complementaryDSBk.addAll(complementaryDS);
			String temp = complementaryDS.get(i);
			certainDSBk.add(temp);
			complementaryDSBk.remove(temp);
			Collection<String> neighborsOfI = g.getNeighbors(temp);
			Iterator<String> nIIt = neighborsOfI.iterator();
			while (nIIt.hasNext()) {

				String nIItNextStr = nIIt.next();
				if (certainDSBk.contains(nIItNextStr)) {
					complementaryDSBk.add(nIItNextStr);
					certainDSBk.remove(nIItNextStr);
				}

			}

			// verify certainDSBk is a dominating set
			boolean isDS = verifyDS(certainDSBk, complementaryDSBk);
			if (isDS) {
				int certainDSBkLen = certainDSBk.size();
				if (minDSLen > certainDSBkLen) {

					minDSLen = certainDSBkLen;
					if (minDSLen < k) {
						dominatingSetSet.add(certainDSBk);
					}
				}
			}
		}

		return;

	}

	private boolean verifyDS(List<String> ds, List<String> complementaryDS) {

		int complementaryDSLen = complementaryDS.size();

		boolean isDS = false;
		int count = 0;
		for (int j = 0; j < complementaryDSLen; j++) {
			String u = complementaryDS.get(j);
			Collection<String> neighborsOfU = g.getNeighbors(u);
			Iterator<String> nUIt = neighborsOfU.iterator();
			while (nUIt.hasNext()) {

				String nUItNextStr = nUIt.next();
				if (ds.contains(nUItNextStr)) {
					count++;
					break;
				}
			}
		}
		if (count == complementaryDSLen) {
			isDS = true;
		}
		return isDS;

	}

	public void setK(int k) {
		this.k = k;
	}

	@Override
	public Set<List<String>> getDominatingSetSet() {

		return dominatingSetSet;
	}

	@Override
	public void initialization() {
		numOfVertex = input.getNumOfVertex();
		adjacencyMatrix = input.getAdjacencyMatrix();

	}

}

class VertexDegree implements Comparable<VertexDegree> {
	int vertex;
	int degree;

	public VertexDegree(int vertex, int degree) {
		this.vertex = vertex;
		this.degree = degree;
	}

	public int getVertex() {
		return vertex;
	}

	public int getDegree() {
		return degree;
	}

	public int compareTo(VertexDegree arg0) {
		return this.getDegree() - arg0.getDegree();
	}

}
