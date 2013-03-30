package client;

import io.IInput;
import io.InputFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import algorithm.IAlgorithm;


/**
 * The class works as the main entrance of the application in command window
 * @author : Kai
 *
 */
public class CmdMain {
	/*
	 * The system parameters in properties file
	 */
	/**
	 * The algorithm class that will be selected
	 */
	private static final String ALGORITHM="algorithm";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//1. welcome and help

		
		//2.decide which is the data resource
		
		
		//3. get data from the selected resource in accordance suitable data format,adjacency matrix or adjacency linkedlist
		//assume taking use of file
		Properties properties = loadProperties();
		IInput input = new InputFile();
		input.setProperties(properties);
		input.initialization();
		input.getAdjacencyInfo();
		
		//4. take use of a kind of algorithm to solve it
		IAlgorithm algorithm=null;
		String algorithmClass = properties.getProperty(ALGORITHM); 
		try {
			algorithm = (IAlgorithm) Class.forName(algorithmClass).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		algorithm.setInput(input);
		algorithm.initialization();
		
		algorithm.generateDominatingSet();
		String[][] dsarr = algorithm.getDominatingSetArr();
		
		

	}
	

	/**
	 * load properties file
	 * @return
	 */
	private static Properties loadProperties(){
		 	String propertiesFile = "resource/sysinfo.properties";
	        Properties properties = new Properties();  
	        try {  
	            InputStream in = new BufferedInputStream(new FileInputStream(propertiesFile));  
	            properties.load(in);  
	            in.close();  
	           
	           
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return properties;
	}

}