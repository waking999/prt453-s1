package cdu.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import cdu.io.IInput;
import edu.uci.ics.jung.graph.Graph;

@Component("algorithmHillClimbing")
public class AlgorithmHillClimbing implements IAlgorithm {
	private AlgorithmGreedy ag;

	private Set<List<String>> dominatingSetSet; // an array contain dominating
												// set


	private int numOfVertex; // number of vertex

	private List<String[]> adjacencyMatrix; // adjacency matrix
	private int k; // the parameter that the size of dominatingSet should less
					// than or equal to it.

	private static Logger log;
	static {
		log = Logger.getLogger(AlgorithmHillClimbing.class.getName());
	}

	@Override
	public void setNumOfVertex(int numOfVertex) {
		this.numOfVertex = numOfVertex;
	}

	@Override
	public void setAdjacencyMatrix(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
	}

	@Override
	public void generateDominatingSet() {

		// take use of greedy firstly to generate a valid dominating set
		Graph<String, Integer> g = AlgorithmUtil.prepareGraph(this.numOfVertex, this.adjacencyMatrix);
		List<VertexDegree> vertexDegreeList = AlgorithmUtil.sortVertexAccordingToDegree(g,this.numOfVertex);
		State state = ag.greedy(g, vertexDegreeList);

		dominatingSetSet = new HashSet<List<String>>();

		List<String> certainDS = state.getDs();
		dominatingSetSet.add(certainDS);

		int certainDSLen = certainDS.size();

		if (certainDSLen <= k) {
			dominatingSetSet.add(certainDS);
		}

		// take use of hill climbing idea based on the generated dominating set
		int minDSLen = certainDSLen;

		List<String> complementaryDS = state.getCplDs();
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
			boolean isDS = AlgorithmUtil.isDS(g, certainDSBk, complementaryDSBk);
			if (isDS) {
				int certainDSBkLen = certainDSBk.size();
				if (minDSLen > certainDSBkLen) {

					minDSLen = certainDSBkLen;

					dominatingSetSet.add(certainDSBk);

				}
			}
		}

		if (minDSLen <= k) {
			log.info("By using 'HILL CLIMBING WITH GREEDY', it could find solutions whose size is less than or equal to parameter k ("+this.k+"). (the mininum size is " + minDSLen +")");

		}

	}

	

	@Override
	public void setK(int k) {
		this.k = k;
	}

	@Override
	public Set<List<String>> getDominatingSetSet() {

		return dominatingSetSet;
	}

	@Override
	public void initialization(IInput input, int k) {
		int numOfVertex = input.getNumOfVertex();
		List<String[]> adjacencyMatrix = input.getAdjacencyMatrix();
		initialization(numOfVertex, adjacencyMatrix, k);
	}

	@Override
	public void initialization(int numOfVertex, List<String[]> adjacencyMatrix,
			int k) {
		this.setNumOfVertex(numOfVertex);
		this.setAdjacencyMatrix(adjacencyMatrix);
		this.setK(k);
		ag = new AlgorithmGreedy();
		ag.initialization(numOfVertex, adjacencyMatrix, k);
	}
}
