package io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class work for getting input from files
 * @author : Kai
 *
 */
public class InputFile implements IInput {
	private String inputFile;
	private int numOfVertex;
	private List<String[]> adjacencyMatrix;
	private Properties properties;
	
	private static final String GRAPH_ADJACENCY_MATRIX_CSV="graph_adjacency_matrix_csv"; 
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	public void initialization(){
		
		String inputFile = properties.getProperty(GRAPH_ADJACENCY_MATRIX_CSV); 
		this.inputFile = inputFile;
	}

	public void getAdjacencyInfo(){
		
		BufferedReader reader = null;
		try {
				reader = new BufferedReader(new FileReader(this.inputFile));
			
				String line = null;
				
				//the first line is number of vertex
				line=reader.readLine();
				numOfVertex = Integer.parseInt(line);
				//the following lines from the 2nd line are adjacency matrix
				adjacencyMatrix = new ArrayList<String[]>();
				
				while((line=reader.readLine())!=null){
					String item[] = line.split(",");					
					adjacencyMatrix.add(item);
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
		}
		
	}

	public int getNumOfVertex() {
		return numOfVertex;
	}

	public List<String[]> getAdjacencyMatrix() {
		return adjacencyMatrix;
	}
	
	public void setProperties(Properties properties){
		this.properties = properties;
	}
}
