package cdu.algorithm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

	private Graph<Integer, Integer> g;

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
		g = new SparseMultigraph<Integer, Integer>();
		for (int i = 0; i < numOfVertex; i++) {
			g.addVertex(i);
		}
	
		for (int i = 0; i < numOfVertex; i++) {
			String[] rowArr = adjacencyMatrix.get(i);
			for (int j = i + 1; j < numOfVertex; j++) {
				if (Util.CONNECTED.equals(rowArr[j])) {
					
					int digit = i*numOfVertex+j;
					g.addEdge(digit, i, j);
				
				
				
					
				}
			}
		}

		// get the sorted vertex according their degree

		List<VertexDegree> vertexDegreeList = new ArrayList<VertexDegree>();
		for (int i = 0; i < numOfVertex; i++) {
			int degree = g.degree(i);
			vertexDegreeList.add(new VertexDegree(i, degree));
		}
		Collections.sort(vertexDegreeList);

		// go through all vertex but follow the order of degree;
		dominatingSetSet = new HashSet<List<String>>();

		//for (int i = 0; i < numOfVertex; i++) {
			List<String> certainDS = new ArrayList<String>();
			List<String> T = new ArrayList<String>();
			for (int j = 0; j < numOfVertex; j++) {
				T.add(Integer.toString(vertexDegreeList
						.get(numOfVertex - 1 - j).getVertex()));
			}
			
			//int index = i;
			int index = 0;
			int step =0;
			while (!T.isEmpty()) {
				String vertexS = T.get(index);

				certainDS.add(vertexS);
				T.remove(vertexS);
				
				
				StringBuffer infoSB = new StringBuffer();
				
				Collection<Integer> neighborsOfI = g.getNeighbors(Integer
						.valueOf(vertexS));
				Iterator<Integer> nIIt = neighborsOfI.iterator();
				infoSB.append("Step "+step+":");
				infoSB.append("remove vertex ").append(vertexS).append(" (with ").append(neighborsOfI.size()).append(" neighbors ) firstly");
				infoSB.append(" and its neighbors-");
				while (nIIt.hasNext()) {
					Integer nIItNext = nIIt.next();
					String nIItNextStr = nIItNext.toString();
					
					if(T.contains(nIItNextStr)){
						infoSB.append(nIItNextStr).append(",");
						T.remove(nIItNextStr);
					}
				}

				index = 0;
				System.out.println(infoSB);
				step++;
			}
			// do not add the dominating set which is the same as before;
			//Collections.sort(certainDS);
			dominatingSetSet.add(certainDS);
			
		//}
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
