package cdu.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cdu.io.InputFile;

/**
 * This test class works for test of Algorithm in Stochastic Local Search idea
 * 
 * @author : Kai
 * 
 */
public class AlgorithmStochasticLocalSearchTest {
	private IAlgorithm as;

	private ApplicationContext factory;

	@Before
	public void setUp() {
		factory = new ClassPathXmlApplicationContext("beans.xml");
		as = (IAlgorithm) (factory.getBean("algorithmStochasticLocalSearch"));

	}

	@After
	public void tearDown() {

	}

	@Ignore
	@Test
	public void generateDominatingSet() {

		/*
		 * adjacencyMatrix = { {1,1,1,1,0,0}, {1,1,0,1,0,0}, {1,0,1,1,0,0},
		 * {1,1,1,1,1,1}, {0,0,0,1,1,1}, {0,0,0,1,1,1} };
		 * 
		 * Minimum Dominating Set : {0, 4}, {0, 5}, {3}, {1, 2, 4}, {1, 2, 5}
		 */
		/*
		 * 3, 0,5, 5,0, 4,0, 2,5,1, 1,5,2,
		 */

		int numOfVertex = 6;

		List<String[]> adjacencyMatrix = new ArrayList<String[]>();
		adjacencyMatrix.add(new String[] { "1", "1", "1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "0", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "0", "1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "1", "1", "1", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "0", "1", "1", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "0", "1", "1", "1" });

		as.setNumOfVertex(numOfVertex);

		as.setAdjacencyMatrix(adjacencyMatrix);

		as.generateDominatingSet();

		Set<List<String>> dsSet = as.getDominatingSetSet();
		Iterator<List<String>> dsIt = dsSet.iterator();

		while (dsIt.hasNext()) {
			List<String> dsRow = dsIt.next();
			int cLen = dsRow.size();
			if (cLen == 1) {
				Assert.assertEquals("3", dsRow.get(0));
			}
		}
		
		return;

	}

	


	

	@Test
	public void takeUseOfGeneratedBigRandGraphToCompute() {

		InputFile input = new InputFile();
		input.setInputFile("resource/testcase400.csv");
		input.getAdjacencyInfo();
		List<String[]> adjacencyMatrix = input.getAdjacencyMatrix();
		int numOfVertex = input.getNumOfVertex();

		as.setK(50);
		as.setNumOfVertex(numOfVertex);
		as.setAdjacencyMatrix(adjacencyMatrix);

		as.generateDominatingSet();

	

		return;
	}

}
