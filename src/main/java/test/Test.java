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
import es.upm.dit.gsi.net2prOwl.BayesianTranslator;
import es.upm.dit.gsi.ontology.Domain_Res;
import es.upm.dit.gsi.ontology.MyFactory;
import es.upm.dit.gsi.ontology.PR_OWLTable;
import es.upm.dit.gsi.prOwl2net.UnBBayesTranslator;
import junit.framework.TestCase;

public class Test extends TestCase {

	
	private UnBBayesTranslator bbT;
	private BayesianTranslator bayesT;
	private ProbabilisticNetwork netBefore;
	private ProbabilisticNetwork netAfter;
	public static JenaOWLModel owlModel;
	private MyFactory myFactory;
	String uriNet;
	String uriOwl;
	String uriOwlEmpty;
	String uriNetAfter;
	String nameNet;

	public Test(){
		uriNet = "";
		uriOwl = "";
		uriOwlEmpty = "";
		nameNet = "";
		bayesT = new BayesianTranslator(uriNet, uriOwlEmpty);
		bbT = new UnBBayesTranslator(uriOwl, nameNet);

		
		String aux = Definitions.uriNet;
		
		//Generation of files
		bayesT.convertNet2Owl();
		bbT.convertOwlToNet();

	}
	public void createNetAndOwl() {
		//  NetBefore
		netBefore = null;

		try {
			BaseIO io = new NetIO();
			netBefore = (ProbabilisticNetwork) io.load(new File(uriNet));

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		//owl
		try {
			FileReader owlFile = new FileReader(uriOwl);
			owlModel = ProtegeOWL.createJenaOWLModelFromReader(owlFile);
			owlModel.getNamespaceManager().setDefaultNamespace(
			"http://www.gsi.upm.es/Commune.owl#");

		} catch (Exception oe) {
			oe.printStackTrace();

		}
		this.myFactory = new MyFactory(this.owlModel);

		// NetAfter
		netAfter = null;

		try {
			BaseIO io = new NetIO();
			netAfter = (ProbabilisticNetwork) io.load(new File(uriNetAfter));

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * Check if the names of the nodes that 
	 * are in the redBayesiana are the same that exist in the ontology
	 */
	public void checkNodesOwlNet() {
		ArrayList<Node> nodesNet = netBefore.getNodes();
		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();

		Set<String> nodesNetName = new HashSet<String>();
		Set<String> nodesOwlName = new HashSet<String>();
		for(Node node : nodesNet) {
			String nameNode = node.getName();
			nodesNetName.add(nameNode);
		}
		for(Domain_Res res : nodesOwl) {
			String nameNode = res.getLocalName();
			nodesOwlName.add(nameNode);
		}

		assertEquals(nodesOwlName, nodesNetName);

	}

	/**
	 * Checks the status of each
	 *  node are the same in the Bayesian network and the ontology
	 */
	public void nodeStatesEqual() {
		ArrayList<Node> nodesNet = netBefore.getNodes();
		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
		boolean equals = true;
		for(Node node : nodesNet) {
			Set<String> statesNodeNet = new HashSet<String>();
			for(int i = 0; i<node.getStatesSize(); i++){
				statesNodeNet.add(node.getStateAt(i));
			}

			for(Domain_Res res : nodesOwl) {
				//a�adir el set con los estados que tiene uno
				Set<String> statesNodeOwl = new HashSet<String>();
				//`poner el nombre de cada estado en el set anterior

				if(node.getName().equals(res.getLocalName())) {
					if(!statesNodeOwl.equals(statesNodeNet)) {
						equals = false;
					}
					break;
				}


			}

		}
		assertEquals(equals, true);

	}

	/**
	 * Compare if each node has the same parents in Bayesian Network and Owl
	 */
	public void checkParentsNode() {
		ArrayList<Node> nodesNet = netBefore.getNodes();
		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
		boolean expected = true;
		boolean now = true;
		for(Node node : nodesNet) {
			ProbabilisticNode pNode = (ProbabilisticNode)node;
			ArrayList<Node> parentsNodeNet = pNode.getParents(); 
			Set<String> parentsNodeNetName = new HashSet<String>();
			for(int i = 0; i<parentsNodeNet.size(); i++){
				String nameParent = parentsNodeNet.get(i).getName();
				parentsNodeNetName.add(nameParent);					
			}

			for(Domain_Res res : nodesOwl) {
				//a�adir el set con los padres que tiene uno
				Set<String> parentsNodeOwlName = new HashSet<String>();
				//`poner el nombre de cada estado en el set anterior

				for (String parentOwlName : parentsNodeOwlName) {
					boolean found = false;
					for (String parentNetName : parentsNodeNetName) {
						if (parentOwlName.equals(parentOwlName)) {
							found = true;
						}
					}
					
				}
				
				if(node.getName().equals(res.getLocalName())) {
					if(!parentsNodeOwlName.equals(parentsNodeNetName)) {
						now = false;
					}
					break;
				}

			}

		}
		assertEquals(expected, now);


	}

	/**
	 * Compare marginal tables of each node and net owl
	 */
	public void compareTables() {
		ArrayList<Node> nodesNet = netBefore.getNodes();
		Set<Domain_Res> nodesOwl = myFactory.getAllDomain_ResInstances();
		Set<PR_OWLTable> tablesOwl = myFactory.getAllPR_OWLTableInstances();
		boolean expected = true;
		boolean now = true;
		for(Node node : nodesNet) {
			ProbabilisticNode pNode = (ProbabilisticNode)node;
			PotentialTable tableNode = pNode.getProbabilityFunction();
			Set<Double> marginalsNodeNet = new HashSet<Double>(); 
			for(int i= 0;i<tableNode.getVariablesSize();i++){
				marginalsNodeNet.add((double) tableNode.getValue(i));	
			}

			for(Domain_Res res : nodesOwl){


				if(node.getName().equals(res.getLocalName())) {
					Set<Double> marginalsNodeOwl = new HashSet<Double>();

					for(PR_OWLTable table : tablesOwl) {
						Domain_Res resTable = (Domain_Res) table.getIsProbDistOf();
						if(resTable.equals(res)) {
							//cogemos cada una de las assign y cogemos su valor
							table.getHasProbAssign();
							//las a�adimos al marginalsNodeOwl
//							if(!parentsNodeOwlName.equals(parentsNodeNetName)) {
//								now = false;
//							}

							break;
						}
					}


				}

			}

		}



		assertEquals(expected, now);
	}



}




