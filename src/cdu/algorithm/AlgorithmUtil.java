package cdu.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import cdu.util.Util;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class AlgorithmUtil {
	
	public static Graph<String, Integer> prepareGraph(int numOfVertex,List<String[]> adjacencyMatrix) {
		// prepare graph
		Graph<String, Integer> g = new SparseMultigraph<String, Integer>();
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
		return g;
	}
	
	
	/**
	 * @return
	 */
	public static List<VertexDegree> sortVertexAccordingToDegree(Graph<String, Integer> g,int numOfVertex) {
		// get the sorted vertex according their degree
		List<VertexDegree> vertexDegreeList = new ArrayList<VertexDegree>();
		for (int i = 0; i < numOfVertex; i++) {
			int degree = g.degree(Integer.toString(i));
			vertexDegreeList.add(new VertexDegree(i, degree));
		}
		Collections.sort(vertexDegreeList);
		return vertexDegreeList;
	}
	
	public static boolean isDS(Graph<String, Integer> g, List<String> ds,
			List<String> complementaryDS) {

		int complementaryDSLen = complementaryDS.size();

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
			if (count == j) {
				// if there is not any vertex in the complementary set linked to
				// a vertex in the set, it is not a dominating set
				return false;
			}
		}

		return true;

	}
	
	/**
	 * @return
	 */
	public static State generateARandomSolution(int numOfVertex) {
		State state;
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

	
	
		state = new State(ds, cplDs);
		return state;
	}
	
	public static int energy(Graph<String,Integer> g,State state, int maxEnergy) {
		List<String> ds = state.getDs();
		List<String> cplDs = state.getCplDs();

		if (!AlgorithmUtil.isDS(g,ds, cplDs)) {
			return maxEnergy;
		}
		return ds.size();
	}
}
