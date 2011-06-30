package test;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import es.upm.dit.gsi.net2prOwl.BayesianTranslatorNet2OWL;
import es.upm.dit.gsi.prOwl2net.UnBBayesTranslatorOWL2Net;

public class TestBnOwlTranslatorRandom extends TestCase {
	Logger logger = Logger.getLogger(TestBnOwlTranslatorRandom.class);

	private static boolean filesCreated = false;
	private static String uriNet = Definitions.uriNet;
	private static String uriOwl = Definitions.uriOwl;
	private static String uriOwlEmpty = Definitions.uriOwlEmpty;
	private static String uriNetFinalFile = Definitions.uriNetFinalFile;
	private static String uriNetFinal = Definitions.uriNetFinal;

	private static ProbabilisticNetwork netBefore;
	private static ProbabilisticNetwork netAfter;
	public static JenaOWLModel owlModel;
	

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
		File netFile = new File(uriNet);
		File owlFileBN = new File(uriOwlEmpty);
		BayesianTranslatorNet2OWL bayesT = new BayesianTranslatorNet2OWL(netFile, owlFileBN);
		bayesT.convertNet2Owl();
		UnBBayesTranslatorOWL2Net bbT = new UnBBayesTranslatorOWL2Net(new File(uriOwl), uriNetFinal);
		bbT.convertOwlToNet();
		
		//LoadNets
		BaseIO io = new NetIO();
		netBefore = (ProbabilisticNetwork) io.load(new File(uriNet));
		netAfter = (ProbabilisticNetwork) io.load(new File(uriNetFinalFile));
	}

	public TestBnOwlTranslatorRandom() throws Exception{
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
		
		//try if the size of tables are same
		assertEquals(tableB.tableSize(), tableA.tableSize());
	}
	
		

}
