package cdu.algorithm;

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

@Component("algorithmStochasticLocalSearchGreedy")
public class AlgorithmStochasticLocalSearchGreedy implements IAlgorithm {
	

	private AlgorithmGreedy ag;
	private Set<List<String>> dominatingSetSet; // an array contain dominating
												// set

	private int numOfVertex; // number of vertex
	private List<String[]> adjacencyMatrix; // adjacency matrix
	private int k; // the parameter that the size of dominatingSet should less
					// than or equal to it.

	private static Logger log;
	static {
		log = Logger.getLogger(AlgorithmStochasticLocalSearchGreedy.class.getName());
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
		State state = ag.greedy(g, vertexDegreeList);
		
		
		dominatingSetSet = new HashSet<List<String>>();
		
		
		
		State minState = (State) state.clone();
		int minEnergy = AlgorithmUtil.energy(g,minState,this.numOfVertex);

		int iterations = 10*numOfVertex;
		for (int i = 0; i < iterations; i++) {
			step(g,state);
			int tempEnergy = AlgorithmUtil.energy(g,state,this.numOfVertex);

			if (tempEnergy == this.numOfVertex) {
				undo(state);
				continue;
			} else {
				if (tempEnergy <= minEnergy) {
					List<String> stateDs = state.getDs();
					if (!dominatingSetSet.contains(stateDs)) {
						minState = (State) state.clone();
						minEnergy = tempEnergy;

						
						dominatingSetSet.add(minState.getDs());
						state.setPrevDs(stateDs);
						state.setPrevCplDs(state.getCplDs());

					}
				}

			}
		}

		if (minEnergy <= k) {
			log.info("By using 'STOCHASTIC LOCAL SEARCH WITH GREEDY', it could find solutions whose size is less than or equal to parameter k ("+this.k+"). (the mininum size is " + minEnergy +")");
		}

	}

	

	@Override
	public Set<List<String>> getDominatingSetSet() {

		return dominatingSetSet;
	}



	@Override
	public void setK(int k) {
		this.k = k;
	}

	private void step(Graph<String,Integer> g, State state) {
		// search local area and change to a new ds
		List<String> ds = state.getDs();
		List<String> cplDs = state.getCplDs();

		int cplDsLen = cplDs.size();
		int position = (int) Math.floor(Math.random() * cplDsLen);

		String randomVertex = cplDs.get(position);

		ds.add(randomVertex);
		cplDs.remove(randomVertex);
		Collection<String> neighborsOfR = g.getNeighbors(randomVertex);
		Iterator<String> nRIt = neighborsOfR.iterator();
		while (nRIt.hasNext()) {
			String nIItNextStr = nRIt.next();
			if (ds.contains(nIItNextStr)) {
				cplDs.add(nIItNextStr);
				ds.remove(nIItNextStr);
			}

		}

	}

	private void undo(State state) {

		List<String> prevDs = state.getPrevDs();
		List<String> prevCplDs = state.getPrevCplDs();

		state.setDs(prevDs);
		state.setCplDs(prevCplDs);
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

