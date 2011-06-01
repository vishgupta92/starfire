package test;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

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

public class TestBnOwlTranslator extends TestCase {
	Logger logger = Logger.getLogger(TestBnOwlTranslator.class);

	private static boolean filesCreated = false;
	private static String uriNet = Definitions.uriNet;
	private static String uriOwl = Definitions.uriOwl;
	private static String uriOwlEmpty = Definitions.uriOwlEmpty;
	private static String uriNetFinalFile = Definitions.uriNetFinalFile;
	private static String uriNetFinal = Definitions.uriNetFinal;

	private static ProbabilisticNetwork netBefore;
	private static ProbabilisticNetwork netAfter;
	public static JenaOWLModel owlModel;
	private MyFactory myFactory;

	public static void loadFiles() throws Exception{

		// Delete previous files
		File owlFile = new File(uriOwl);
		if(owlFile.exists()) {
			owlFile.delete();
		}
		File netAfterFile = new File(uriNetFinalFile);
		if(netAfterFile.exists()) {
			netAfterFile.delete();
		}
		
		// Create New Files
		BayesianTranslatorNet2OWL bayesT = new BayesianTranslatorNet2OWL(uriNet, uriOwlEmpty);
		bayesT.convertNet2Owl();
		UnBBayesTranslatorOWL2Net bbT = new UnBBayesTranslatorOWL2Net(new File(uriOwl), uriNetFinal);
		bbT.convertOwlToNet();
		
		//LoadNets
		BaseIO io = new NetIO();
		netBefore = (ProbabilisticNetwork) io.load(new File(uriNet));
		netAfter = (ProbabilisticNetwork) io.load(new File(uriNetFinalFile));
	}

	public TestBnOwlTranslator() throws Exception{
		if (!filesCreated) {
			loadFiles();
			filesCreated = true;
		}
	}

	@Test
	public void testCreateFiles() {
		File netBeforeFile = new File(uriNet);
		assertTrue("Net before doesn't exist.", netBeforeFile.exists());
		File owlEmptyFile = new File(uriOwlEmpty);
		assertTrue(owlEmptyFile.exists());
		File owlFile = new File(uriOwl);
		assertTrue(owlFile.exists());
		File netAfterFile = new File(uriNetFinalFile);
		assertTrue(netAfterFile.exists());
		
		assertTrue(filesCreated);
	}
	
	@Test
	public void testLoadNets() {
		assertNotNull(netBefore);
		assertNotNull(netAfter);
	}

	@Test
	public void testCheckNumNodesNet() {

		//net before
		int numNodesB = netBefore.getNodes().size();
		logger.info("number nodes of net before " + numNodesB);

		//net after
		int numNodesA = netAfter.getNodes().size();
		logger.info("number nodes of net after " + numNodesA);

		assertEquals(numNodesB, numNodesA);

	}

	@Test
	public void testCheckNameNodesNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		int random = new Double(Math.random() * numNodesB).intValue();
		
		int numberOfNode = random;
		if(random == numNodesB) {
		numberOfNode = random - 1;
		}
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		logger.info("name node net before "+ nodeB.getName());

		// net after
		Node nodeA = netAfter.getNode(nodeB.getName());
		logger.info("name node net after "+ nodeA.getName());

		assertEquals(nodeA, nodeB);

	}

	@Test
	public void testCheckStatesNodesNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		int random = new Double(Math.random() * numNodesB).intValue();
		
		int numberOfNode = random;
		if(random == numNodesB) {
		numberOfNode = random - 1;
		}
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		logger.info("name node net before "+ nodeB.getName());
		ArrayList<String> nodeStatesB = new ArrayList<String>();
		for (int i = 0; i < nodeB.getStatesSize(); i++) {
			nodeStatesB.add(nodeB.getStateAt(i));
		}
		logger.info("name node net before "+ nodeB.getName() + " and the states are: " + nodeStatesB);
		boolean contain = true;
		//net After
		Node nodeA = netAfter.getNode(nodeB.getName());
		for (int j = 0; j < nodeA.getStatesSize(); j++) {
			String stateA = nodeA.getStateAt(j);

			if (!nodeStatesB.contains(stateA)) {
				contain = false;
			}
		}
		if(contain) {
			logger.info("name node net after "+ nodeA.getName() +" and the states are: " + nodeStatesB );
		}
		assertEquals(true, contain);
	}

	@Test
	public void testCheckParentsNodeNet() {

		//net before
		int numNodesB = netBefore.getNodes().size();
		int random = new Double(Math.random() * numNodesB).intValue();
		
		int numberOfNode = random;
		if(random == numNodesB) {
		numberOfNode = random - 1;
		}
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		
		ArrayList<Node> nodeParentsB = nodeB.getParents();
		logger.info("name node net before "+ nodeB.getName() + " and the parents are: " + nodeParentsB);
		boolean contain = true;
		//net After
		Node nodeA = netAfter.getNode(nodeB.getName());
		ArrayList<Node> nodeParentsA = nodeA.getParents();
		for (int j = 0; j < nodeParentsA.size(); j++) {
			Node parentA = nodeParentsA.get(j);

			if (!nodeParentsB.contains(parentA)) {
				contain = false;
			}
		}
		if(contain) {
			logger.info("name node net after "+ nodeA.getName() + " and the parents are: " + nodeParentsB);
		}
		assertEquals(true, contain);
	}

	@Test
	public void testCheckChildrenNodeNet() {

		//net before
		int numNodesB = netBefore.getNodes().size();
		int random = new Double(Math.random() * numNodesB).intValue();
	
		int numberOfNode = random;
		if(random == numNodesB) {
		numberOfNode = random - 1;
		}
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		ArrayList<Node> nodeChildrenB = nodeB.getChildren();

		boolean contain = true;
		//net After

		Node nodeA = netAfter.getNode(nodeB.getName());
		ArrayList<Node> nodeChildrenA = nodeA.getChildren();
		logger.info("name node net before "+ nodeB.getName() + " and the children are: " + nodeChildrenB);
		for (int j = 0; j < nodeChildrenA.size(); j++) {
			Node childA = nodeChildrenA.get(j);

			if (!nodeChildrenB.contains(childA)) {
				contain = false;
			}
		}
		if(contain) {
			logger.info("name node net after "+ nodeA.getName() + " and the children are: " + nodeChildrenB);
		}
		assertEquals(true, contain);
	}

	@Test
	public void testCheckPotentialTableNodeNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		int random = new Double(Math.random() * numNodesB).intValue();
		
		int numberOfNode = random;
		if(random == numNodesB) {
		numberOfNode = random - 1;
		}
		Node nodeB = netBefore.getNodes().get(numberOfNode);
		ProbabilisticNode pNodeB = (ProbabilisticNode) nodeB;
		PotentialTable tableB = pNodeB.getProbabilityFunction();
		double variablesSizeB = tableB.getVariablesSize();//mirar esta parte.
		logger.info("name table net before " + nodeB.getName() + "_Table, and the variables size is :" + variablesSizeB);
		Node nodeA = netAfter.getNode(nodeB.getName());
		ProbabilisticNode pNodeA = (ProbabilisticNode) nodeA;
		PotentialTable tableA = pNodeA.getProbabilityFunction();
		double variablesSizeA = tableA.getVariablesSize(); //mirar esta parte
		logger.info("name table net after " + nodeA.getName() + "_Table, and the variables size is :"+ variablesSizeA);
		assertEquals(variablesSizeB,variablesSizeA);


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
