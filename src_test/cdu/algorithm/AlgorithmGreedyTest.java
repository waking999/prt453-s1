package cdu.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cdu.io.InputFile;
import cdu.util.Util;

/**
 * This test class works for test of Algorithm in Greedy idea
 * 
 * @author : Kai
 * 
 */
public class AlgorithmGreedyTest {
	private IAlgorithm a;

	private static ApplicationContext factory;
	@BeforeClass
	public static void setUpBeforeClass(){
		factory = new ClassPathXmlApplicationContext("beans.xml");
	}
	@Before
	public void setUp() {
		a = (IAlgorithm) (factory.getBean("algorithmGreedy"));
	}

	@After
	public void tearDown() {

	}

	@Test
	public void generateDominatingSet() {

		/*
		 * adjacencyMatrix = { {1,1,1,1,0,0}, {1,1,0,1,0,0}, {1,0,1,1,0,0},
		 * {1,1,1,1,1,1}, {0,0,0,1,1,1}, {0,0,0,1,1,1} };
		 * 
		 * Minimum Dominating Set : {0, 4}, {0, 5}, {3}, {1, 2, 4}, {1, 2, 5}
		 */
		int numOfVertex = 6;

		List<String[]> adjacencyMatrix = new ArrayList<String[]>();
		adjacencyMatrix.add(new String[] { "1", "1", "1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "0", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "0", "1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "1", "1", "1", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "0", "1", "1", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "0", "1", "1", "1" });

		a.setNumOfVertex(numOfVertex);
		a.setAdjacencyMatrix(adjacencyMatrix);
		a.setK(numOfVertex / 2);

		a.generateDominatingSet();

		Set<List<String>> dsSet = a.getDominatingSetSet();
		Iterator<List<String>> dsIt = dsSet.iterator();

		while (dsIt.hasNext()) {
			List<String> dsRow = dsIt.next();
			int cLen = dsRow.size();
			if (cLen == 1) {
				Assert.assertEquals("3", dsRow.get(0));
			}
		}

	}

	@Ignore
	@Test
	public void takeUseOfUtilToGenerateBigRandGraphToCompute() {

		int numOfVertex = 400;

		List<String[]> adjacencyMatrix = Util.generateRandGraph(numOfVertex);
		Util.saveToFile(adjacencyMatrix);

		a.setK(numOfVertex / 2);
		a.setNumOfVertex(numOfVertex);
		a.setAdjacencyMatrix(adjacencyMatrix);

		a.generateDominatingSet();
	}

	@Test
	public void takeUseOfGeneratedBigRandGraphToCompute() {

		InputFile input = new InputFile();
		input.setInputFile("resource/testcase400.csv");
		input.getAdjacencyInfo();
		int testCase400_Greedy_DS_size = 11;

		a.initialization(input, testCase400_Greedy_DS_size);

		a.generateDominatingSet();

		Set<List<String>> dsSet = a.getDominatingSetSet();
		int dsSetLen = dsSet.size();
		Assert.assertEquals(1, dsSetLen);

		Object[] dsArr = (dsSet.toArray());
		@SuppressWarnings("unchecked")
		List<String> ds = (List<String>) dsArr[0];
		int dsLen = ds.size();
		Assert.assertEquals(testCase400_Greedy_DS_size, dsLen);
	}

}
