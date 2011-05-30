package test;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import es.upm.dit.gsi.net2prOwl.BayesianTranslatorNet2OWL;
import es.upm.dit.gsi.ontology.Domain_Res;
import es.upm.dit.gsi.ontology.MyFactory;
import es.upm.dit.gsi.ontology.PR_OWLTable;
import es.upm.dit.gsi.prOwl2net.UnBBayesTranslatorOWL2Net;
import junit.framework.TestCase;

public class Test extends TestCase {

	
	private UnBBayesTranslatorOWL2Net bbT;
	private BayesianTranslatorNet2OWL bayesT;
	private ProbabilisticNetwork netBefore;
	private ProbabilisticNetwork netAfter;
	public static JenaOWLModel owlModel;
	private MyFactory myFactory;
	String uriNet;
	String uriOwl;
	String uriOwlEmpty;
	String uriNetFinal;

	public void createFiles(){
		uriNet = Definitions.uriNet;;
		uriOwl = Definitions.uriOwl;
		uriOwlEmpty = Definitions.uriOwlEmpty;
		uriNetFinal = Definitions.uriNetFinal;
		
		bayesT = new BayesianTranslatorNet2OWL(uriNet, uriOwlEmpty);
		bbT = new UnBBayesTranslatorOWL2Net(new File(uriOwl), uriNetFinal);		
		//Generation of files
		boolean actual = false;
		try{
		bayesT.convertNet2Owl();
		bbT.convertOwlToNet();
		actual = true;
		}catch(Exception e){
			System.out.println("Error: " + e);
			actual = false;
		}
		
		assertEquals(true, actual);

	}
	
	
	public void loadNetBefore() {
		netBefore = null;
		boolean loaded = false;
		try {
			BaseIO io = new NetIO();
			netBefore = (ProbabilisticNetwork) io.load(new File(uriNet));
			loaded = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			loaded = false;
		}
		
		assertEquals(true, loaded);

	}
	public void loadNetAfter(){
		
		netAfter = null;
		boolean loaded = false;
		try {
			BaseIO io = new NetIO();
			netAfter = (ProbabilisticNetwork) io.load(new File(uriNetFinal));
			loaded = true;

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
			loaded = false;
		}
		assertEquals(true, loaded);
	}
	
	
	public void checkNumNodesNet(){
		
		//net before
		int numNodesB = netBefore.getNodes().size();
		
		//net after
		int numNodesA = netAfter.getNodes().size();
		
		assertEquals(numNodesB, numNodesA);

	}
	
	public void checkNameNodesNet(){
		//net before
		int numNodesB = netBefore.getNodes().size();	
		int random = new Double(Math.random() * numNodesB).intValue();	
		System.out.println("numero nodos :"+ numNodesB +", numero random :" + random );
		int numberOfNode = random - 1;
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		
		// net after
		Node nodeA = netAfter.getNode(nodeB.getName());
		
		assertEquals(nodeA, nodeB);

	}
	
	public void checkStatesNodesNet(){
		//net before
		int numNodesB = netBefore.getNodes().size();	
		int random = new Double(Math.random() * numNodesB).intValue();	
		System.out.println("numero nodos :"+ numNodesB +", numero random :" + random );
		int numberOfNode = random - 1;
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		ArrayList<String> nodeStatesB = new ArrayList<String>();
		for(int i = 0; i<nodeB.getStatesSize(); i++){
			nodeStatesB.add(nodeB.getStateAt(i));
		}
		
		boolean contain = true;
		//net After
		Node nodeA = netAfter.getNode(nodeB.getName());
		for(int j = 0; j < nodeA.getStatesSize(); j++){
			String stateA = nodeA.getStateAt(j);
			
			if(!nodeStatesB.contains(stateA)){
				contain = false;
			}
		}
		
		assertEquals(true, contain);		
	}
	
	
	public void checkParentsNodeNet(){
		
		//net before
		int numNodesB = netBefore.getNodes().size();	
		int random = new Double(Math.random() * numNodesB).intValue();	
		System.out.println("numero nodos :"+ numNodesB +", numero random :" + random );
		int numberOfNode = random - 1;
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		ArrayList<Node> nodeParentsB = nodeB.getParents();
		
		
		boolean contain = true;
		//net After
		Node nodeA = netAfter.getNode(nodeB.getName());
		ArrayList<Node> nodeParentsA = nodeA.getParents();
		for(int j = 0; j < nodeParentsA.size(); j++){
			 Node parentA = nodeParentsA.get(j);
			
			if(!nodeParentsB.contains(parentA)){
				contain = false;
			}
		}
		
		assertEquals(true, contain);		
	}
		
	
	
public void checkChildrenNodeNet(){
	
	//net before
	int numNodesB = netBefore.getNodes().size();	
	int random = new Double(Math.random() * numNodesB).intValue();	
	System.out.println("numero nodos :"+ numNodesB +", numero random :" + random );
	int numberOfNode = random - 1;
	Node nodeB = netBefore.getNodes().get(numberOfNode);
	ArrayList<Node> nodeChildrenB = nodeB.getChildren();
	
	
	boolean contain = true;
	//net After
	
	Node nodeA = netAfter.getNode(nodeB.getName());
	ArrayList<Node> nodeChildrenA = nodeA.getChildren();
	for(int j = 0; j < nodeChildrenA.size(); j++){
		 Node childA = nodeChildrenA.get(j);
		
		if(!nodeChildrenB.contains(childA)){
			contain = false;
		}
	}
	
	assertEquals(true, contain);		
}
	


public void checkPotentialTableNodeNet(){
	//net before
	int numNodesB = netBefore.getNodes().size();	
	int random = new Double(Math.random() * numNodesB).intValue();	
	System.out.println("numero nodos :"+ numNodesB +", numero random :" + random );
	int numberOfNode = random - 1;
	Node nodeB = netBefore.getNodes().get(numberOfNode);
	ProbabilisticNode pNodeB = (ProbabilisticNode) nodeB;
	PotentialTable tableB = pNodeB.getProbabilityFunction();
	double valueB = tableB.getValue(0);//mirar esta parte.
	
	//net After 
	
	Node nodeA = netAfter.getNode(nodeB.getName());
	ProbabilisticNode pNodeA = (ProbabilisticNode) nodeA;
	PotentialTable tableA = pNodeA.getProbabilityFunction();
	double valueA = tableA.getValue(0); //mirar esta parte
	
	
	assertEquals(valueB, valueA);
	

	
}
	
	
	
//
//	/**
//	 * Check if the names of the nodes that 
//	 * are in the redBayesiana are the same that exist in the ontology
//	 */
//	public void checkNodesOwlNet() {
//		ArrayList<Node> nodesNet = netBefore.getNodes();
//		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
//
//		Set<String> nodesNetName = new HashSet<String>();
//		Set<String> nodesOwlName = new HashSet<String>();
//		for(Node node : nodesNet) {
//			String nameNode = node.getName();
//			nodesNetName.add(nameNode);
//		}
//		for(Domain_Res res : nodesOwl) {
//			String nameNode = res.getLocalName();
//			nodesOwlName.add(nameNode);
//		}
//
//		assertEquals(nodesOwlName, nodesNetName);
//
//	}
//
//	/**
//	 * Checks the status of each
//	 *  node are the same in the Bayesian network and the ontology
//	 */
//	public void nodeStatesEqual() {
//		ArrayList<Node> nodesNet = netBefore.getNodes();
//		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
//		boolean equals = true;
//		for(Node node : nodesNet) {
//			Set<String> statesNodeNet = new HashSet<String>();
//			for(int i = 0; i<node.getStatesSize(); i++){
//				statesNodeNet.add(node.getStateAt(i));
//			}
//
//			for(Domain_Res res : nodesOwl) {
//				//a�adir el set con los estados que tiene uno
//				Set<String> statesNodeOwl = new HashSet<String>();
//				//`poner el nombre de cada estado en el set anterior
//
//				if(node.getName().equals(res.getLocalName())) {
//					if(!statesNodeOwl.equals(statesNodeNet)) {
//						equals = false;
//					}
//					break;
//				}
//
//
//			}
//
//		}
//		assertEquals(equals, true);
//
//	}
//
//	/**
//	 * Compare if each node has the same parents in Bayesian Network and Owl
//	 */
//	public void checkParentsNode() {
//		ArrayList<Node> nodesNet = netBefore.getNodes();
//		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
//		boolean expected = true;
//		boolean now = true;
//		for(Node node : nodesNet) {
//			ProbabilisticNode pNode = (ProbabilisticNode)node;
//			ArrayList<Node> parentsNodeNet = pNode.getParents(); 
//			Set<String> parentsNodeNetName = new HashSet<String>();
//			for(int i = 0; i<parentsNodeNet.size(); i++){
//				String nameParent = parentsNodeNet.get(i).getName();
//				parentsNodeNetName.add(nameParent);					
//			}
//
//			for(Domain_Res res : nodesOwl) {
//				//a�adir el set con los padres que tiene uno
//				Set<String> parentsNodeOwlName = new HashSet<String>();
//				//`poner el nombre de cada estado en el set anterior
//
//				for (String parentOwlName : parentsNodeOwlName) {
//					boolean found = false;
//					for (String parentNetName : parentsNodeNetName) {
//						if (parentOwlName.equals(parentOwlName)) {
//							found = true;
//						}
//					}
//					
//				}
//				
//				if(node.getName().equals(res.getLocalName())) {
//					if(!parentsNodeOwlName.equals(parentsNodeNetName)) {
//						now = false;
//					}
//					break;
//				}
//
//			}
//
//		}
//		assertEquals(expected, now);
//
//
//	}
//
//	/**
//	 * Compare marginal tables of each node and net owl
//	 */
//	public void compareTables() {
//		ArrayList<Node> nodesNet = netBefore.getNodes();
//		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
//		Set<PR_OWLTable> tablesOwl = myFactory.getAllPR_OWLTableInstances();
//		boolean expected = true;
//		boolean now = true;
//		for(Node node : nodesNet) {
//			ProbabilisticNode pNode = (ProbabilisticNode)node;
//			PotentialTable tableNode = pNode.getProbabilityFunction();
//			Set<Double> marginalsNodeNet = new HashSet<Double>(); 
//			for(int i= 0;i<tableNode.getVariablesSize();i++){
//				marginalsNodeNet.add((double) tableNode.getValue(i));	
//			}
//
//			for(Domain_Res res : nodesOwl){
//
//
//				if(node.getName().equals(res.getLocalName())) {
//					Set<Double> marginalsNodeOwl = new HashSet<Double>();
//
//					for(PR_OWLTable table : tablesOwl) {
//						Domain_Res resTable = (Domain_Res) table.getIsProbDistOf();
//						if(resTable.equals(res)) {
//							//cogemos cada una de las assign y cogemos su valor
//							table.getHasProbAssign();
//							//las a�adimos al marginalsNodeOwl
////							if(!parentsNodeOwlName.equals(parentsNodeNetName)) {
////								now = false;
////							}
//
//							break;
//						}
//					}
//
//
//				}
//
//			}
//
//		}
//
//
//
//		assertEquals(expected, now);
//	}



}




