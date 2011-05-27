package tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


import unbbayes.prs.Node;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNode;

public class CreateTables {

	


	public CreateTables() {

	}

	/**
	 * Make a Marginal Table if the node have parents
	 * @param nodeNet
	 * @return Table 2D of values
	 */
	public double[][] marginalTable2DValue(ProbabilisticNode probNodeNet){
	
		ProbabilisticNode node = probNodeNet;
		PotentialTable table = node.getProbabilityFunction();
		double[][] marginalTable = null;
		
		if(node.getParentNodes().size() >= 1){
			double numNodes = node.getParents().size();
			//estados de los padres multiplicados
			ArrayList<Node> parents = node.getParents();
			int numStateParent = 1;
			for(Node parent: parents) {
				numStateParent = numStateParent * parent.getStatesSize();
			}
			int parentsCel = numStateParent;
		//	int parentsCel  = (int)Math.pow(2, numNodes); 

			double[][] table2D = new double[node.getStatesSize()][parentsCel];
		
			int counter = 0;
			for(int j=0; j<parentsCel; j++){
				for(int k = 0; k<node.getStatesSize();k++){
					table2D[k][j]=table.getValue(counter);
					
					counter++;
				}
			}

			marginalTable = table2D;
	
		}else {//If the parent is One.
			if(node.getParentNodes().size() != 0){ // If it doesn't have a parent, continue
				String nombreNodo = node.getName();
				int numberStatesAdjacentNode = (table.tableSize()/node.getStatesSize());
				double[][] table2D = new double[node.getStatesSize()][numberStatesAdjacentNode];
				int counter = 0;
				for(int j=0; j < numberStatesAdjacentNode; j++){
					for(int k = 0; k < node.getStatesSize();k++){
						
						table2D[k][j]=table.getValue(counter);
						counter++;
					}
				}

				marginalTable = table2D;

			}
		}

		return marginalTable;

	}


/**
 *  Create a table of marginal values of node.
 * @param probNodeNet
 * @return table of values 1D
 */
	public double[] marginalTable1DValue(ProbabilisticNode probNodeNet) {

		
		ProbabilisticNode node = probNodeNet;

		PotentialTable table = node.getProbabilityFunction();

		int counter = 0;
		double[] table1D = new double[node.getStatesSize()];
		for(int k = 0; k<node.getStatesSize();k++){
			table1D[k]=table.getValue(counter);
			counter++;
		}
		return table1D;
	}

	/**
	 * 
	 * @param counter
	 * @param array
	 * @return true if increment has be done. 
	 * false if we rise the end of the array (We can't increment)
	 */
	private boolean incrementCounter(int[] counter, ArrayList<ArrayList<String>> array) {
		counter[counter.length-1]++;
		for(int i = counter.length-1;i>=0;i--) {
			if(array.get(i).size()<=counter[i]) {
				counter[i]=0;
				if(i!=0) {
					counter[i-1]++;
				}else {
					for(int j=0;j<counter.length;j++) {
						counter[j]=0;
					}
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Make a String of name parents Table if the node have parents
	 * @param nodeNet
	 * @return Table 2D of String
	 */
	public String[][] marginalTable2DString(ProbabilisticNode probNodeNet){
		
		ProbabilisticNode node = probNodeNet;
		PotentialTable table = node.getProbabilityFunction();
		
		String[][] marginalTable = null;
		ArrayList<Node> parents =  node.getParents();

		//create table.
		if(node.getParentNodes().size() >= 1){// parents more than one.
			double numNodes = node.getParents().size();
			//estados de los padres multiplicados
		
			int numStateParent = 1;
			for(Node parent: parents) {
				numStateParent = numStateParent * parent.getStatesSize();
			}
			int parentsCel = numStateParent;
//			double numNodes = node.getParents().size();
//			int parentsCel  = (int)Math.pow(2, numNodes); 

			String[][] table2D = new String[node.getStatesSize()][parentsCel];

			for(int f = 0; f<parentsCel ; f ++) {
				for(int c = 0; c<node.getStatesSize(); c++) {
					String state = node.getStateAt(c);
					String stateInTable = node.getName() + "_" + state + "-"; 
					table2D[c][f] =  stateInTable;

				}
			}
			marginalTable = table2D;

			
			//add the values of name string to table

			ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();//array de array
			ArrayList<String> statesNode = new ArrayList<String>();

			//add to array, the arrayList of the states of nodes.
			for(int i = 0; i< node.getStatesSize(); i++) {
				statesNode.add(node.getStateAt(i));
			}
			array.add(statesNode);//add in first position the states of important node.

			for(Node parent : parents) {
				ArrayList<String> statesParent = new ArrayList<String>();
				for(int i = 0; i< parent.getStatesSize(); i++) {
					statesParent.add(parent.getStateAt(i));
				}
				array.add(statesParent);
			}// add the states of parents of principal node.


			ArrayList<String> dataString = new ArrayList<String>();
			int[] counter = new int[array.size()];

			//string states initialy.
			String dataIni = "";
			for(int i = 0; i<array.size() ; i++) {
				ArrayList<String>statesNodes = array.get(i);
				String state = statesNodes.get(0);
				if(i == 0) {
					dataIni +=  node.getName() + "_" + state + "-";
				}else {
					if(i != array.size()-1) {dataIni += parents.get(i-1).getName() + "_" + state + "-";}
					else { dataIni += parents.get(i-1).getName() + "_" + state;}

				}

			}
			dataString.add(dataIni);

			boolean stateCounter = incrementCounter(counter, array);

			while(stateCounter == true) {
				String data = "";
				for(int i = 0; i<array.size() ; i++) {
					ArrayList<String>statesNodes = array.get(i);
					int numberState = counter[i];
					String state = statesNodes.get(numberState);
					if(i == 0) {
						data +=  node.getName() + "_" + state + "-";
					}else {
						if(i != array.size()-1) {data += parents.get(i-1).getName() + "_" + state + "-";}
						else {data += parents.get(i-1).getName() + "_" + state ;}

					}

				}
				stateCounter = incrementCounter(counter, array);

				dataString.add(data);


			}

			//Add the data String to table.
			int counterData = 0;
			for(int k = 0; k<node.getStatesSize();k++){
				for(int j=0; j<parentsCel; j++){

					table2D[k][j] = dataString.get(counterData);
					counterData++;
				}
			}
			marginalTable = table2D;

		}else {//If the parent is One.
			if(node.getParentNodes().size() != 0){ // If it doesn't have a parent, continue
				int numberStatesAdjacentNode = (table.tableSize()/node.getStatesSize());
				String[][] table2D = new String[numberStatesAdjacentNode][node.getStatesSize()];
				int counter = 0;
				for(int f = 0; f<node.getStatesSize();f++){
					String state = node.getStateAt(f);
					String stateInTable = node.getName() + "_" + state + "-"; 
					for(int c=0; c<numberStatesAdjacentNode; c++){
						table2D[c][f]=stateInTable;
						counter++;
					}
				}
				marginalTable = table2D;

			}
		}


		return marginalTable;


	}

/**
 * Create a Table 1D, with the string of the StateNode
 * @param probNodeNet
 * @return table_String_1D
 */
	public String[] marginalTable1DString(ProbabilisticNode probNodeNet) {

		//ProbabilisticNode node = (ProbabilisticNode) nodeNet;
		ProbabilisticNode node = probNodeNet;

		PotentialTable table = node.getProbabilityFunction();

		int counter = 0;
		String[] table1D = new String[node.getStatesSize()];
		for(int k = 0; k<node.getStatesSize();k++){
			table1D[k]= node.getName() + "_" + node.getStateAt(k);
			counter++;
		}
		return table1D;
	}


/**
 * Create hashMap with String and value of the marginal table.
 * The node must have parents
 * @param probNodeNet
 * @return HashMap
 */
	public HashMap<String, Double> table2DToHashMap(ProbabilisticNode probNodeNet){

		HashMap<String,Double> data = new HashMap<String, Double>();
		int numberParents = probNodeNet.getParentNodes().size();
		int numberStates = probNodeNet.getStatesSize();
		double[][] tableValue = marginalTable2DValue(probNodeNet);
		String[][] tableString = marginalTable2DString(probNodeNet);
		
		double numNodes = numberParents;
		//estados de los padres multiplicados
		ArrayList<Node> parents = probNodeNet.getParents();
		int numStateParent = 1;
		for(Node parent: parents) {
			numStateParent = numStateParent * parent.getStatesSize();
		}
		int parentsCel = numStateParent;
//		double numNodes = numberParents;
//		int parentsCel  = (int)Math.pow(2, numNodes); 
		for(int i = 0; i< parentsCel; i++) {
			for(int j = 0; j<numberStates; j++) {
				data.put(tableString[j][i], tableValue[j][i]);

			}
		}
		
		
		return data;
	}

	/**
	 * Create hashMap with String and value of one node.
	 * @param probNodeNet
	 * @return HashMap
	 */
	public HashMap<String, Double> table1DToHashMap(ProbabilisticNode probNodeNet){
		
		HashMap<String,Double> data = new HashMap<String, Double>();
		String[] tableString = marginalTable1DString(probNodeNet);
		double[] tableValue = marginalTable1DValue(probNodeNet);
		
		for(int k = 0; k<tableString.length;k++){
			data.put(tableString[k], tableValue[k]);

		}
		
		return data;
		
		
		
		
	}


}
