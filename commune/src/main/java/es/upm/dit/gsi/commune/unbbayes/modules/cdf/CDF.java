package es.upm.dit.gsi.commune.unbbayes.modules.cdf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;


import unbbayes.io.NetIO;
import unbbayes.prs.INode;
import unbbayes.prs.Node;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;


/**
 * <p>
 * Class CDF: Calculates the CDF value of a node in a Bayesian network
 * </p>
 * 
 * @version 0.1 (04/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

public class CDF {
	
	public static void main(String args[])throws Exception {
		// open a .net file
		ProbabilisticNetwork net 
		= (ProbabilisticNetwork)new NetIO().load(new File("./bayesianNetwork/prueba.net")); //Load the bayesian network

		ArrayList<Node> nodesList = net.getNodes();	//Get all nodes from BN
		System.out.println(nodesList.size());
		Iterator<Node> nodesIterator = nodesList.iterator();
		String nodeName = "";
		while(nodesIterator.hasNext()){
			Node n = nodesIterator.next();
			System.out.println("Node name: "+n.getName()+" Number states : "+n.getStatesSize());
			nodeName = n.getName();
			ProbabilisticNode node = (ProbabilisticNode) net.getNode(nodeName); 
			calculateNodeCDFs(node); //For each node, calculate the CDF
			
		}
	}

	/**
	 * calculateNodeCDFs: Calculates the node cdf
	 * 
	 * @param node
	 * @return Returns a hashmap with the parents nodes and the correspondent cdf value
	 */
	public static HashMap<ProbabilisticNode, Double> calculateNodeCDFs(ProbabilisticNode node){
		System.out.println("--------------------> Calculate CDF for node: "+node.getName());
		PotentialTable table = node.getProbabilityFunction();
		HashMap<ProbabilisticNode, Double> cdfHashMap = new HashMap<ProbabilisticNode, Double>();
		double resultCDF = 0.0;
		int cdfsCalculated = 0;

		// If there are more than 1 parent:
		if(node.getParentNodes().size() > 1){
			//For each parent node, we get the marginal probabilities table
			List<INode> inodeList = node.getParentNodes();
			Iterator<INode> inodeIterator = inodeList.iterator();
			System.out.println("Number of parent nodes: "+inodeList.size());
			
			while(inodeIterator.hasNext()){
				ProbabilisticNode parentNode = (ProbabilisticNode) inodeIterator.next();
				System.out.println("----> Parent Node: "+parentNode.getName());
				int parentIndex = table.indexOfVariable(parentNode);

				//Create the marginal table:
				double[][] marginalTable = new double[parentNode.getStatesSize()][node.getStatesSize()];
				int[] position = new int[table.getVariablesSize()]; 
				double value=0.0;


				for (int j= 0; j<node.getStatesSize();j++){ // all states of node
					for(int k=0; k<parentNode.getStatesSize(); k++){ //all states of parent node
						for(int l = 0; l< table.tableSize(); l++){ //get all elements of the table and
							position=table.getMultidimensionalCoord(l); 
							
							if(position[0] == j && position[parentIndex] == k){ //checks if corresponds to the one we're looking for
								Iterator<INode> inodeIterator2 = inodeList.iterator();
								double aux = 1;
								while (inodeIterator2.hasNext()){ // get all parents states probabilities
									ProbabilisticNode parentNodeAux = (ProbabilisticNode) inodeIterator2.next();

									if(parentNodeAux != parentNode){

										PotentialTable parentTableAux = parentNodeAux.getProbabilityFunction();
										for(int m = 0; m<parentNodeAux.getStatesSize();m++){
											if(position[table.indexOfVariable(parentNodeAux)] == m){
												aux = aux * parentTableAux.getValue(m); 
											}
											

										}
									}
								}
								
								value += (table.getValue(l) * aux); //table value * each parent state probability ;
							}
						}	
						System.out.println("value = "+value +" position : "+k+" "+j);
						marginalTable[k][j]=value; //Transpose
						value = 0.0;
					}
				}

				//calculateCDF for the marginal table...
				for(int i=0; i<marginalTable.length; i++){
					for(int j=0; j<marginalTable.length; j++){
						if(i !=j){
							resultCDF += calculateCDF(marginalTable[i],marginalTable[j]);
							cdfsCalculated++;
						}
					}
				}

				resultCDF = resultCDF / cdfsCalculated ;
				System.out.println("-----> CDF Result = "+resultCDF);
				cdfsCalculated = 0;
				resultCDF = 0.0;
				cdfHashMap.put(parentNode, resultCDF);
			}
		}else{ //Just one parent
			//Create the 2D table.
			if(node.getParentNodes().size() != 0){ // If it doesn't have a parent, continue
				int numberStatesAdjacentNode = (table.tableSize()/node.getStatesSize());
				double[][] table2D = new double[numberStatesAdjacentNode][node.getStatesSize()];
				int counter = 0;
				for(int j=0; j<numberStatesAdjacentNode; j++){
					for(int k = 0; k<node.getStatesSize();k++){
						table2D[j][k]=table.getValue(counter);
						counter++;
					}
				}

				for(int i=0; i<numberStatesAdjacentNode; i++){
					for(int j=0; j<numberStatesAdjacentNode; j++){
						if(i !=j){
							resultCDF += calculateCDF(table2D[i],table2D[j]);
							cdfsCalculated++;
						}
					}
				}
				if(cdfsCalculated >0)
					resultCDF = resultCDF / cdfsCalculated ;
				System.out.println("-----> CDF Result = "+resultCDF);
				if (!node.getParents().isEmpty()){
					ProbabilisticNode parentNode = (ProbabilisticNode) node.getParents().get(0);
					cdfHashMap.put(parentNode, resultCDF);
				}
			}
		}
		return cdfHashMap;

	}

	/**
	 * calculate CDF: given two columns, returns the CDF value
	 * 
	 * @param column1
	 * @param column2
	 * @return CDF double value
	 */
	public static double calculateCDF(double[] column1 , double[] column2){
		double result = 0.0;

		for(int i=0; i<column1.length; i++){
			double value1 = 0.0;
			double value2 = 0.0;
			for(int j=0; j<=i; j++){
				value1 += column1[j];
				value2 += column2[j];
			}
			result += Math.abs(value1-value2);
		}
		result = result/(column1.length-1);

		return result;

	}


}
