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
import cdu.util.Util;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

@Component("algorithmStochasticLocalSearch")
public class AlgorithmStochasticLocalSearch implements IAlgorithm {
	public static final int NOT_DS = -1;

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
		log = Logger.getLogger("cdu.algorithm.AlgorithmStochasticLocalSearch");
		log.setLevel(Level.INFO);
	}

	@Override
	public void setInput(IInput input) {
		this.input = input;

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

		State state;
		dominatingSetSet = new HashSet<List<String>>();
		
		// random initial solutions
		List<String> ds = new ArrayList<String>();
		List<String> cplDs = new ArrayList<String>();
		for (int i = 0; i < numOfVertex; i++) {
			if (i % 2 == 0) {
				ds.add(Integer.toString(i));
			} else {
				cplDs.add(Integer.toString(i));
			}
		}

		// // greedy
		// // get the sorted vertex according their degree
		// List<VertexDegree> vertexDegreeList = new ArrayList<VertexDegree>();
		// for (int i = 0; i < numOfVertex; i++) {
		// int degree = g.degree(Integer.toString(i));
		// vertexDegreeList.add(new VertexDegree(i, degree));
		// }
		// Collections.sort(vertexDegreeList);
		//
		// List<String> ds = new ArrayList<String>();
		// List<String> cplDs = new ArrayList<String>();
		// List<String> T = new ArrayList<String>();
		// for (int j = 0; j < numOfVertex; j++) {
		// T.add(Integer.toString(vertexDegreeList.get(numOfVertex - 1 - j)
		// .getVertex()));
		// }
		//
		// // idea: Take all vertices of the highest degree as an approximate
		// // solution
		// int index = 0;
		// int step = 0;
		// while (!T.isEmpty()) {
		// String vertexS = T.get(index);
		//
		// ds.add(vertexS);
		// T.remove(vertexS);
		//
		// StringBuffer infoSB = new StringBuffer();
		//
		// Collection<String> neighborsOfI = g.getNeighbors(vertexS);
		// Iterator<String> nIIt = neighborsOfI.iterator();
		// infoSB.append("Step " + step + ":");
		// infoSB.append("remove vertex ").append(vertexS).append(" (with ")
		// .append(neighborsOfI.size()).append(" neighbors ) firstly");
		// infoSB.append(" and its neighbors-");
		// while (nIIt.hasNext()) {
		//
		// String nIItNextStr = nIIt.next();
		//
		// if (T.contains(nIItNextStr)) {
		// infoSB.append(nIItNextStr).append(",");
		// T.remove(nIItNextStr);
		// cplDs.add(nIItNextStr);
		// }
		// }
		//
		// index = 0;
		// log.info(infoSB.toString());
		// step++;
		// }
		// //
		
		// dominatingSetSet.add(ds);

		// -----------
		state = new State(ds, cplDs);
		State minState = (State) state.clone();
		int minEnergy = energy(minState);

		int iterations = 10*numOfVertex;
		//int iterations = 10000;
		for (int i = 0; i < iterations; i++) {
			step(state);
			int tempEnergy = energy(state);

			if (tempEnergy == NOT_DS) {
				undo(state);
				continue;
			} else {
				if (tempEnergy <= minEnergy) {
					List<String> stateDs = state.getDs();
					if (!dominatingSetSet.contains(stateDs)) {
						minState = (State) state.clone();
						minEnergy = tempEnergy;

						// List<String> save = new ArrayList<String>();
						// save.addAll(stateDs);
						dominatingSetSet.add(minState.getDs());
						state.setPrevDs(stateDs);
						state.setPrevCplDs(state.getCplDs());

					}
				}

			}
		}

		if (minEnergy <= k) {
			log.info("It could find a solution whose size (" + minEnergy +") is less than or equal to parameter k.");
		}

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

	@Override
	public void setK(int k) {
		this.k = k;
	}

	private void step(State state) {
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

	private int energy(State state) {
		List<String> ds = state.getDs();
		List<String> cplDs = state.getCplDs();

		if (!isDS(ds, cplDs)) {
			return NOT_DS;
		}
		return ds.size();
	}

	private boolean isDS(List<String> ds, List<String> cplDs) {

		int cplDsLen = cplDs.size();

		int count = 0;
		for (int j = 0; j < cplDsLen; j++) {
			int preCount = count;
			String u = cplDs.get(j);
			Collection<String> neighborsOfU = g.getNeighbors(u);
			Iterator<String> nUIt = neighborsOfU.iterator();
			while (nUIt.hasNext()) {

				String nUItNextStr = nUIt.next();
				if (ds.contains(nUItNextStr)) {
					count++;
					break;
				}
			}
			if (count == preCount) {
				// if there is not any vertex in the complementary set linked to
				// a vertex in the set, it is not a dominating set
				return false;
			}
		}

		return true;

	}
}

class State implements Cloneable {

	private List<String> ds = null;

	private List<String> cplDs = null;

	private List<String> prevDs = null;
	private List<String> prevCplDs = null;

	public void setDs(List<String> ds) {
		if (this.ds == null) {
			this.ds = new ArrayList<String>();
			this.ds.addAll(ds);
		} else {
			this.ds.clear();
			this.ds.addAll(ds);
		}
	}

	public void setCplDs(List<String> cplDs) {

		if (this.cplDs == null) {
			this.cplDs = new ArrayList<String>();
			this.cplDs.addAll(cplDs);
		} else {
			this.cplDs.clear();
			this.cplDs.addAll(cplDs);
		}
	}

	public void setPrevDs(List<String> prevDs) {
		if (this.prevDs == null) {
			this.prevDs = new ArrayList<String>();
			this.prevDs.addAll(prevDs);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(prevDs);
		}
	}

	public void setPrevCplDs(List<String> prevCplDs) {
		if (this.prevCplDs == null) {
			this.prevCplDs = new ArrayList<String>();
			this.prevCplDs.addAll(prevCplDs);
		} else {
			this.prevCplDs.clear();
			this.prevCplDs.addAll(prevCplDs);
		}
	}

	public List<String> getPrevDs() {
		return prevDs;
	}

	public List<String> getPrevCplDs() {
		return prevCplDs;
	}

	public State() {

	}

	public State(List<String> ds, List<String> cplDs) {
		this.ds = ds;
		this.cplDs = cplDs;

		if (this.prevDs == null) {
			this.prevDs = new ArrayList<String>();
			this.prevDs.addAll(ds);
		} else {
			this.prevDs.clear();
			this.prevDs.addAll(ds);
		}
		if (this.prevCplDs == null) {
			this.prevCplDs = new ArrayList<String>();
			this.prevCplDs.addAll(cplDs);
		} else {
			this.prevCplDs.clear();
			this.prevCplDs.addAll(cplDs);
		}
	}

	public State(List<String> ds, List<String> cplDs, List<String> prevDs,
			List<String> prevCplDs) {
		this.ds = ds;
		this.cplDs = cplDs;
		this.prevDs = prevDs;
		this.prevCplDs = prevCplDs;
	}

	public List<String> getDs() {
		return ds;
	}

	public List<String> getCplDs() {
		return this.cplDs;
	}

	public Object clone() {
		State copy = new State();
		copy.setDs(this.ds);
		copy.setCplDs(this.cplDs);
		copy.setPrevCplDs(this.prevCplDs);
		copy.setPrevDs(this.prevDs);

		return copy;
	}

}
