package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import es.upm.dit.gsi.net2prOwl.BayesianTranslatorNet2OWL;
import es.upm.dit.gsi.ontology.MyFactory;
import es.upm.dit.gsi.prOwl2net.UnBBayesTranslatorOWL2Net;
/**
 * Class that tests whether two networks are alike, these initial and final after the whole process.
 * @author Jeronimo Fco Perez
 *
 */
public class TestBnOwlTranslatorNoRandom {



	Logger logger = Logger.getLogger(TestBnOwlTranslatorNoRandom.class);

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



/**
 * Constructor
 * @throws Exception
 */
	public TestBnOwlTranslatorNoRandom() throws Exception{
		if (!filesCreated) {
			loadFiles();
			filesCreated = true;
		}
	}
/**
 * Carga los ficheros, y hace la traduccion completa NET-OWL-NET
 * @throws Exception
 */
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

	
	/**
	 * Test if the files were created successfully
	 */
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

	/**
	 * Test that the networks have the same nodes, checking that they have the same names.
	 */
	@Test
	public void testCheckNameNodesNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		ArrayList<Node>  nodesB  = netBefore.getNodes();
		int numNodesA = netAfter.getNodes().size();
		boolean actual = true;
		for(int j = 0; j<numNodesA; j++) {
			Node nodeA = netAfter.getNodes().get(j);
			if (!nodesB.contains(nodeA)){
				actual = false;
				break;
			}
			logger.info("Node net After is: "+ nodeA.getName() + ", and is included");
		}
		assertEquals(true, actual);

	}

	/**
	 * Test if the states of each node are the same in both systems
	 */
	@Test
	public void testCheckStatesNodesNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		Set<Node> nodesB = new HashSet<Node>();

		for(int i = 0; i < numNodesB; i++) {
			Node nodeB = netBefore.getNodes().get(i);
			nodesB.add(nodeB);
		}
		//net After
		int numNodesA = netAfter.getNodes().size();
		Set<Node> nodesA = new HashSet<Node>();

		for(int i = 0; i < numNodesA; i++) {
			Node nodeA = netAfter.getNodes().get(i);
			nodesA.add(nodeA);
		}

		///compare states nodes
		boolean actual = true;
		for(Node nodeB : nodesB) {
			for(Node nodeA: nodesA) {

				if(nodeB.getName().equals(nodeA.getName())) {

					ArrayList<String> nodeStatesB = new ArrayList<String>();
					for (int i = 0; i < nodeB.getStatesSize(); i++) {
						nodeStatesB.add(nodeB.getStateAt(i));
					}
					ArrayList<String> nodeStatesA = new ArrayList<String>();
					for (int i = 0; i < nodeA.getStatesSize(); i++) {
						nodeStatesA.add(nodeA.getStateAt(i));
					}

					// compare states
					for (String state: nodeStatesA) {
						if(!nodeStatesB.contains(state)) {

							actual = false;
							break;
						}
					}

				}
			}
		}


		assertEquals(true, actual);
	}

	/**
	 * Test whether the parents of each node are the same in both systems
	 */
	@Test
	public void testCheckParentsNodeNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		Set<Node> nodesB = new HashSet<Node>();

		for(int i = 0; i < numNodesB; i++) {
			Node nodeB = netBefore.getNodeAt(i);
			nodesB.add(nodeB);
		}
		//net After
		int numNodesA = netAfter.getNodes().size();
		Set<Node> nodesA = new HashSet<Node>();

		for(int i = 0; i < numNodesA; i++) {
			Node nodeA = netAfter.getNodes().get(i);
			nodesA.add(nodeA);
		}

		///compare states nodes
		boolean actual = true;
		for(Node nodeB : nodesB) {
			for(Node nodeA: nodesA) {

				if(nodeB.getName().equals(nodeA.getName())) {

					ArrayList<String> nodesParentsB = new ArrayList<String>();
					for (int i = 0; i < nodeB.getParents().size(); i++) {
						Node parentB = nodeB.getParents().get(i);
						nodesParentsB.add(parentB.getName());

					}
					ArrayList<String> nodesParentsA = new ArrayList<String>();
					for (int i = 0; i < nodeA.getParents().size(); i++) {
						Node parentA = nodeA.getParents().get(i);
						nodesParentsA.add(parentA.getName());

					}

					// compare states
					for (String state: nodesParentsA) {
						if(!nodesParentsB.contains(state)) {

							actual = false;
							break;
						}
					}
				}
			}
		}
		assertEquals(true, actual);
	}

	/**
	 * test whether the children of each node are the same in both systems
	 */
	@Test
	public void testCheckChildrenNodeNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		Set<Node> nodesB = new HashSet<Node>();

		for(int i = 0; i < numNodesB; i++) {
			Node nodeB = netBefore.getNodeAt(i);
			nodesB.add(nodeB);
		}
		//net After
		int numNodesA = netAfter.getNodes().size();
		Set<Node> nodesA = new HashSet<Node>();

		for(int i = 0; i < numNodesA; i++) {
			Node nodeA = netAfter.getNodes().get(i);
			nodesA.add(nodeA);
		}

		///compare states nodes
		boolean actual = true;
		for(Node nodeB : nodesB) {
			for(Node nodeA: nodesA) {

				if(nodeB.getName().equals(nodeA.getName())) {

					ArrayList<String> nodesChildrenB = new ArrayList<String>();
					for (int i = 0; i < nodeB.getChildren().size(); i++) {
						Node childB = nodeB.getChildren().get(i);
						nodesChildrenB.add(childB.getName());

					}
					ArrayList<String> nodesChildrenA = new ArrayList<String>();
					for (int i = 0; i < nodeA.getChildren().size(); i++) {
						Node childA = nodeA.getChildren().get(i);
						nodesChildrenA.add(childA.getName());

					}

					// compare states
					for (String state: nodesChildrenA) {
						if(!nodesChildrenB.contains(state)) {

							actual = false;
							break;
						}
					}
				}

			}	
		}
		assertEquals(true, actual);

	}



/**
 * Tests whether the marginal table of each node are the same in both systems
 */
	@Test
	public void testCheckPotentialTableNodeNet() {

		//net before
		int numNodesB = netBefore.getNodes().size();
		Set<Node> nodesB = new HashSet<Node>();

		for(int i = 0; i < numNodesB; i++) {
			Node nodeB = netBefore.getNodeAt(i);
			nodesB.add(nodeB);
		}
		//net After
		int numNodesA = netAfter.getNodes().size();
		Set<Node> nodesA = new HashSet<Node>();

		for(int i = 0; i < numNodesA; i++) {
			Node nodeA = netAfter.getNodes().get(i);
			nodesA.add(nodeA);
		}

		///compare states nodes
		CreateTablesTest createTable = new CreateTablesTest();

		for(Node nodeB : nodesB) {
			for(Node nodeA: nodesA) {
				if(nodeB.getName().equals(nodeA.getName())){
					ProbabilisticNode pNodeB = (ProbabilisticNode) nodeB;
					ProbabilisticNode pNodeA = (ProbabilisticNode) nodeA;

					String[][] table2DpNodeB = null;
					String[] table1DpNodeB = null;
					double[][] value2DpNodeB = null;
					double[] value1DpNodeB = null;

					String[][] table2DpNodeA = null;
					String[] table1DpNodeA = null;
					double[][] value2DpNodeA = null;
					double[] value1DpNodeA = null;

					if(pNodeB.getParents().size() ==  0) {
						table1DpNodeB = createTable.marginalTable1DString(pNodeB);
						value1DpNodeB = createTable.marginalTable1DValue(pNodeB);
					}
					if(pNodeA.getParents().size() == 0) {
						table1DpNodeA = createTable.marginalTable1DString(pNodeA);
						value1DpNodeA = createTable.marginalTable1DValue(pNodeA);
					}

					if(pNodeB.getParents().size() > 0) {
						table2DpNodeB = createTable.marginalTable2DString(pNodeB);
						value2DpNodeB = createTable.marginalTable2DValue(pNodeB);
					}
					if(pNodeA.getParents().size() > 0) {
						table2DpNodeA = createTable.marginalTable2DString(pNodeA);
						value2DpNodeA = createTable.marginalTable2DValue(pNodeA);
					}


					HashMap<String, Double> mapB1D = new HashMap<String, Double>();
					HashMap<String, Double> mapA1D = new HashMap<String, Double>();
					HashMap<String, Double> mapB2D = new HashMap<String, Double>();
					HashMap<String, Double> mapA2D = new HashMap<String, Double>();

					if(table2DpNodeB != null) {		
						mapB2D =  createTable.table2DToHashMap(pNodeB);	
					}
					if(table2DpNodeA != null) {		
						mapA2D =  createTable.table2DToHashMap(pNodeA);	
					}
					if(table1DpNodeB != null) {		
						mapB1D =  createTable.table1DToHashMap(pNodeB);	
					}
					if(table1DpNodeA != null) {		
						mapA1D =  createTable.table1DToHashMap(pNodeA);	
					}

	//2D		
					if(nodeB.getStatesSize() > 0) {
					//prueba si los valores de las marginales en las dos tablas son iguales 
					int numStateParentB = 1;
					for(Node parent: nodeB.getParents()) {
						numStateParentB = numStateParentB * parent.getStatesSize();
					}
					int parentsCelB = numStateParentB;

					int counterB = 0;
					boolean actual = true;
					PotentialTable tableB = pNodeB.getProbabilityFunction();
					PotentialTable tableA = pNodeA.getProbabilityFunction();
					ArrayList<Double> valuesB = new ArrayList<Double>();
					ArrayList<Double> valuesA = new ArrayList<Double>();
					for(int f = 0; f<parentsCelB ; f ++) {
						for(int c = 0; c<nodeB.getStatesSize(); c++) {
							valuesB.add((double) tableB.getValue(counterB));

							counterB +=1;
						}
					}
					int numStateParentA = 1;
					for(Node parent: nodeA.getParents()) {
						numStateParentA = numStateParentA * parent.getStatesSize();
					}
					int parentsCelA = numStateParentA;

					int counterA = 0;
					for(int f = 0; f<parentsCelA ; f ++) {
						for(int c = 0; c<nodeA.getStatesSize(); c++) {

							valuesA.add((double) tableA.getValue(counterA));

							counterA +=1;
						}
					}				
					for(Double valueB : valuesB) {
						if(!valuesA.contains(valueB)) {
							
							actual = false;
						}
					}

					for(Double valueA : valuesA) {
						
						if(!valuesA.contains(valueA)) {
							actual = false;
						}
					}
				//compara que los valores sean iguales en una que en otra.
					assertEquals(true, actual);
					
					Set<String> mapA = mapA2D.keySet();
					Set<String> mapB = mapB2D.keySet();
					boolean actual2 = createTable.areEquaslMarginalString(mapB, mapA);
				//compara que sean iguales las strings
					assertEquals(true, actual2);
					
				//compara que sean iguales las strings y los valores
					boolean actual3 = createTable.areEquaslMarginalValue(mapB2D, mapA2D);
					assertEquals(true, actual3);
					
					}
			//1D
					boolean actual = true;
					if(nodeB.getStatesSize() == 0) {
						
						for(int i = 0; i< table1DpNodeA.length; i++) {
							for(int j = 0; j < table1DpNodeB.length; j++) {
							String nameA = table1DpNodeA[i];
							String nameB = table1DpNodeB[i];
								if(nameA.equals(nameB)){
									if(!mapA1D.get(nameA).equals(mapB1D.get(nameB))) {
										actual = false;
									}
									
								}
							}
						}	
					}
					assertEquals(true, actual);

				}
				
			}
		
		}
	}
	

	/**
	 * Reason with the final network taking some hypotheses and check if the initial 
	 * network were the same hypothesis. Both have the same evidence.
	 */
	@Test
	public void testReason() {
		Definitions def = new Definitions();
		def.refreshAll();
		
		HashMap<String,String> evidences = def.EVIDENCES ;
		String statesStudyBandA = def.TARGETSTATE;
		HashMap<String,Double> hypotheses = def.HYPOTHESES;
		
		//reasonEvidences
		
		//net Before
		JunctionTreeAlgorithm algB = new JunctionTreeAlgorithm();
		algB.setNetwork(netBefore);
		algB.run();
		
		//net After
		JunctionTreeAlgorithm algA = new JunctionTreeAlgorithm();
		algA.setNetwork(netAfter);
		algA.run();

		
		////// net before
		Set<String> evidenceSet = evidences.keySet();

		List<Node> nodeList = netBefore.getNodes();
		for (String ev : evidenceSet) {
			for (Node node : nodeList) {
				String nstring = node.getName();
				if (ev.equals(nstring)) {
					int size = node.getStatesSize();
					String targetEvidenceState = (String) evidences.get(ev);
					boolean foundState = false;
					for (int i = 0; i < size; i++) {
						String state = node.getStateAt(i);
						if (state.equals(targetEvidenceState)) {
							ProbabilisticNode probNode = (ProbabilisticNode) node;
							probNode.addFinding(i);
							foundState = true;
							break;
						}
					}
					if (!foundState) {
						logger.warn("State not found in node: "+ node.getName() + " State: " + targetEvidenceState);
					}
					
				}
				
			}
		}
        logger.info("evidences, already in the network Before, are fixed");
		// propagate evidence
		try {
			netBefore.updateEvidences();
		} catch (Exception exc) {
			
			logger.warn("Has not been possible to upgrade the network Before.");
	
		}
		
		//net after


		List<Node> nodeListA = netAfter.getNodes();
		for (String ev : evidenceSet) {
			for (Node node : nodeListA) {
				String nstring = node.getName();
				if (ev.equals(nstring)) {
					int size = node.getStatesSize();
					String targetEvidenceState = (String) evidences.get(ev);
					boolean foundState = false;
					for (int i = 0; i < size; i++) {
						String state = node.getStateAt(i);
						if (state.equals(targetEvidenceState)) {
							ProbabilisticNode probNode = (ProbabilisticNode) node;
							probNode.addFinding(i);
							foundState = true;
							break;
						}
					}
					if (!foundState) {
						logger.warn("State not found in node: "+ node.getName() + " State: " + targetEvidenceState);
					}
					
				}
				
			}
		}
        logger.info("evidences, already in the network After, are fixed");
		// propagate evidence
		try {
			netAfter.updateEvidences();
		} catch (Exception exc) {
			
			logger.warn("Has not been possible to upgrade the network After.");
	
		}
		
		
		
		//fill Hypotheses
		

		HashMap<String,Double> hypothesesB = hypotheses;
		Set<String> hypothesesSetB = hypothesesB.keySet();
		List<Node> nodeListB = netBefore.getNodes();

		//String targetState = (String) getParameter("hypothesesTargetState").getValue();
		String targetStates = statesStudyBandA;
		
		for (String hypothesis : hypothesesSetB) {
			for (Node node : nodeListB) {
				String nstring = node.getName();
				if (hypothesis.equals(nstring)) {
					for (int i = 0; i < node.getStatesSize(); i++) {
						String nodeStateName = node.getStateAt(i);
			
							if (nodeStateName.equals(targetStates)) {
								hypothesesB.put(hypothesis,
										(double) ((ProbabilisticNode) node)
										.getMarginalAt(i));
							}
						
					}
				}

			}
		}
		logger.info("hypotheses have been loaded.");
//		getLogger().info("hypotheses have been loaded.");
//		IGoal print2 = createGoal("show_hypotheses");
//		dispatchSubgoalAndWait(print2);
		
		//netA

		HashMap<String,Double> hypothesesA = hypotheses;
		Set<String> hypothesesSetA = hypothesesA.keySet();
		List<Node> nodeListAf = netAfter.getNodes();

		//String targetState = (String) getParameter("hypothesesTargetState").getValue();

		
		for (String hypothesis : hypothesesSetA) {
			for (Node node : nodeListAf) {
				String nstring = node.getName();
				if (hypothesis.equals(nstring)) {
					for (int i = 0; i < node.getStatesSize(); i++) {
						String nodeStateName = node.getStateAt(i);
				
							if (nodeStateName.equals(targetStates)) {
								hypothesesA.put(hypothesis,
										(double) ((ProbabilisticNode) node)
										.getMarginalAt(i));
							}
						
					}
				}

			}
		}
		logger.info("hypotheses have been loaded.");
		
		boolean actual = true;
		//are equals?
		for(String hypB : hypothesesSetB) {
			logger.info("hypothesis netB name :" + hypB + " value :" +hypothesesB.get(hypB));
			
			if(!hypothesesA.containsKey((hypB))){
				actual = false;
			}
			
			for(String hypA : hypothesesSetA) {
				if(hypA.equals(hypB)) {
					if(hypothesesA.get(hypA) != hypothesesB.get(hypB)) {
						actual = false;
					}
				}
				logger.info("hypothesis netA name :" + hypA + " value :" +hypothesesA.get(hypA));
			}
			
			
		}
		
		assertEquals(true, actual);

	}
		
		
		
}





