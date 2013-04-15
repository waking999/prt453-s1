package cdu.util;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class UtilTest {
	@Ignore
	@Test
	public void testGenerateRandGraph() {
		int numOfVertex = 30;

		List<String[]> adjacencyMatrix = Util.generateRandGraph(numOfVertex);

		for (int i = 0; i < numOfVertex; i++) {
			String[] row = adjacencyMatrix.get(i);
			for (int j = 0; j < numOfVertex; j++) {
				System.out.print(row[j] + ",");
			}
			System.out.println();
		}
	}

	
	@Test
	public void saveToFile() {
		int numOfVertex = 6;

		List<String[]> adjacencyMatrix = Util.generateRandGraph(numOfVertex);
		Util.saveToFile(adjacencyMatrix);
	}

}
