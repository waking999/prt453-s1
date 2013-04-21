package cdu.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import cdu.io.IInput;
import edu.uci.ics.jung.graph.Graph;

@Component("algorithmGreedy")
public class AlgorithmGreedy implements IAlgorithm {

	private Set<List<String>> dominatingSetSet; // an array contain dominating
												// set

	private int numOfVertex; // number of vertex
	private List<String[]> adjacencyMatrix; // adjacency matrix
	private int k; // the parameter that the size of dominatingSet should less
					// than or equal to it.



	private static Logger log;
	static {
		log = Logger.getLogger(AlgorithmGreedy.class.getName());
		log.setLevel(Level.INFO);
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

		Graph <String, Integer> g = AlgorithmUtil.prepareGraph(this.numOfVertex, this.adjacencyMatrix);
		List<VertexDegree> vertexDegreeList = AlgorithmUtil.sortVertexAccordingToDegree(g,this.numOfVertex);
		
		State state = greedy(g,vertexDegreeList);
		
		
		List<String> certainDS = state.getDs();
		int certainDSLen = certainDS.size();

		dominatingSetSet = new HashSet<List<String>>();
		if (certainDSLen <= k) {
			log.info("By using 'GREEDY ALGORITHM', it could find solutions whose size is less than or equal to parameter k ("+this.k+"). (the mininum size is " + certainDSLen +")");
			dominatingSetSet.add(certainDS);
		}

	}

	/**
	 * @param certainDS
	 * @param complementaryDS
	 * @param T
	 */
	State greedy(Graph <String, Integer> g,List<VertexDegree> vertexDegreeList ) {
		
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
			log.fine(infoSB.toString());
			step++;
		}

		State state = new State(certainDS, complementaryDS);
		return state;
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
		this.numOfVertex = numOfVertex;
		this.adjacencyMatrix = adjacencyMatrix;
		this.k = k;

	}

}
