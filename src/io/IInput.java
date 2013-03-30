package io;

import java.util.List;
import java.util.Properties;

/**
 * This interface work as the common interface of different data input such as
 * database and file
 * 
 * @author : Kai
 * 
 */
public interface IInput {
	/**
	 * 
	 */
	public void getAdjacencyInfo();

	/**
	 * set properties got from properties file
	 * 
	 * @param properties
	 */
	public void setProperties(Properties properties);

	/**
	 * After getting properties, do some initialization actions according to
	 * different source of data
	 */
	public void initialization();

	/**
	 * get number of vertex
	 * 
	 * @return
	 */
	public int getNumOfVertex();

	/**
	 * get the adjacency matrix of the graph
	 * @return
	 */
	public List<String[]> getAdjacencyMatrix();

}
