package cdu.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This test class works for test of Algorithm in Mathematics idea
 * 
 * @author : Kai
 * 
 */
public class AlgorithmMathematicsTest extends TestCase {

	private IAlgorithm am;
	
	private ApplicationContext factory;

	

	@Before
	public void setUp() {
		factory = new ClassPathXmlApplicationContext("beans.xml");
		am = (IAlgorithm) (factory.getBean("algorithmMathematics"));
		
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testGenerateDominatingSet() {

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

		
		am.setNumOfVertex(numOfVertex);
		am.setAdjacencyMatrix(adjacencyMatrix);
		
		am.generateDominatingSet();
		

	

		Set<List<String>> dsSet = am.getDominatingSetSet();
		int rLen = dsSet.size();
		Assert.assertEquals(5, rLen);

		Iterator<List<String>> dsIt = dsSet.iterator();

		while (dsIt.hasNext()) {
			List<String> dsRow = dsIt.next();
			int cLen = dsRow.size();
			if (cLen == 1) {
				Assert.assertEquals("3", dsRow.get(0));
			}
		}

	}

	@Test
	public void testGenerateDominatingSet2() {

		/*
		 * 
		 * Minimum Dominating Set : 3,1,5; 3,0,5; 3,4; 2,0; 2,1; 2,4
		 */
		int numOfVertex = 6;

		List<String[]> adjacencyMatrix = new ArrayList<String[]>();
		adjacencyMatrix.add(new String[] { "1", "1", "0", "0", "1", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "1", "0", "1", "0" });
		adjacencyMatrix.add(new String[] { "0", "1", "1", "1", "0", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "0", "0", "1", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "1", "0", "1", "1" });

		
		am.setNumOfVertex(numOfVertex);
		am.setAdjacencyMatrix(adjacencyMatrix);
		am.generateDominatingSet();

		Set<List<String>> dsSet = am.getDominatingSetSet();
		int rLen = dsSet.size();
		Assert.assertEquals(6, rLen);

		Iterator<List<String>> dsIt = dsSet.iterator();

		while (dsIt.hasNext()) {
			List<String> dsRow = dsIt.next();
			int cLen = dsRow.size();
			if (cLen == 3) {
				boolean contains3 = false;
				for (int j = 0; j < cLen; j++) {
					if ("3".equals(dsRow.get(j))) {
						contains3 = true;
						break;
					}
				}
				Assert.assertTrue(contains3);
			}

		}

	}

	@Test
	public void testGenerateDominatingSet3() {

		/*
		 * 
		 * Minimum Dominating Set : 1,5,4,9,8; 3,1,0; 2,6,4,9,8; 3,1,7,4,9;
		 * 3,10,7,4,9; 2,7,5,4,9; 1,7,5,4,9; 3,10,0; 10,7,6,4,9; 1,7,6,4,9;
		 * 3,2,4,9,8; 1,6,4,9,8; 3,2,0; 10,7,5,4,9; 2,7,6,4,9; 3,1,4,9,8;
		 * 2,5,4,9,8; 10,6,4,9,8; 1,0,5; 1,0,6; 10,5,4,9,8; 3,2,7,4,9; 10,0,5;
		 * 10,0,6; 3,10,4,9,8; 2,0,5; 2,0,6;
		 */
		int numOfVertex = 11;

		List<String[]> adjacencyMatrix = new ArrayList<String[]>();
		adjacencyMatrix.add(new String[] { "1", "1", "0", "0", "0", "0", "0",
				"1", "1", "1", "0" });
		adjacencyMatrix.add(new String[] { "1", "1", "1", "0", "0", "0", "0",
				"0", "0", "0", "1" });
		adjacencyMatrix.add(new String[] { "0", "1", "1", "1", "0", "0", "0",
				"1", "0", "0", "1" });
		adjacencyMatrix.add(new String[] { "0", "0", "1", "1", "0", "1", "1",
				"0", "0", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "0", "0", "0", "1", "0", "0",
				"0", "0", "0", "0" });
		adjacencyMatrix.add(new String[] { "0", "0", "0", "1", "0", "1", "1",
				"0", "0", "0", "0" });

		adjacencyMatrix.add(new String[] { "0", "0", "0", "1", "0", "1", "1",
				"0", "0", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "0", "1", "0", "0", "0", "0",
				"1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "0", "0", "0", "0", "0", "0",
				"1", "1", "0", "0" });
		adjacencyMatrix.add(new String[] { "1", "0", "0", "0", "0", "0", "0",
				"0", "0", "1", "0" });
		adjacencyMatrix.add(new String[] { "0", "1", "1", "0", "0", "0", "0",
				"0", "0", "0", "1" });

		
		am.setNumOfVertex(numOfVertex);
		am.setAdjacencyMatrix(adjacencyMatrix);
		
		am.generateDominatingSet();
		

		
		Set<List<String>> dsSet = am.getDominatingSetSet();
		int rLen = dsSet.size();
		Assert.assertEquals(27, rLen);
		Iterator<List<String>> dsIt = dsSet.iterator();

		while (dsIt.hasNext()) {
			List<String> dsRow = dsIt.next();
			int cLen = dsRow.size();
			if (cLen == 3) {
				boolean containsZero = false;
				for (int j = 0; j < cLen; j++) {
					if ("0".equals(dsRow.get(j))) {
						containsZero = true;
						break;
					}
				}
				Assert.assertTrue(containsZero);
			}

		}

	}
}
