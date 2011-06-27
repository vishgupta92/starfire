package es.upm.dit.gsi.prOwl2net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.logging.Logger;

import tools.CreateTables;
import unbbayes.io.NetIO;
import unbbayes.prs.Edge;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.prs.exception.InvalidParentException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.RDFSLiteral;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLIndividual;
import edu.stanford.smi.protegex.owl.model.impl.DefaultRDFIndividual;
import es.upm.dit.gsi.ontology.CategoricalRVState;
import es.upm.dit.gsi.ontology.Domain_MFrag;
import es.upm.dit.gsi.ontology.Domain_Res;
import es.upm.dit.gsi.ontology.MyFactory;
import es.upm.dit.gsi.ontology.PR_OWLTable;
import es.upm.dit.gsi.ontology.ProbAssign;
import es.upm.dit.gsi.ontology.Resident;
import es.upm.dit.gsi.ontology.impl.DefaultCategoricalRVState;
import es.upm.dit.gsi.ontology.impl.DefaultResident;


/**
 * Class that seeks to convert a file. owl in a file. net
 * @author Jeronimo Fco Perez Regidor
 */
public class UnBBayesTranslatorOWL2Net {

	private Logger logger = Logger.getLogger(UnBBayesTranslatorOWL2Net.class.toString());
//	private File owlFile;
	private String netDirectoryUri;
	private MyFactory myFactory;
	public static JenaOWLModel OWL_MODEL;
	private ProbabilisticNetwork net;
	private String nameNet;

	/**
	 * Builder
	 *
	 * @param uriOwl where is the file. owl turn
	 * @param nameNet Name the network will
	 */
	public UnBBayesTranslatorOWL2Net(File owlFile, String netDirectoryUri) {

		// create Owl
//		this.owlFile = owlFile;
		this.netDirectoryUri = netDirectoryUri;
		try {
			FileReader owlFileReader = new FileReader(owlFile);
			OWL_MODEL = ProtegeOWL.createJenaOWLModelFromReader(owlFileReader);
			//OWL_MODEL.getNamespaceManager().setDefaultNamespace("http://www.gsi.upm.es/Commune.owl#");

		} catch (Exception oe) {
			oe.printStackTrace();

		}

		// create myFactory.
		this.myFactory = new MyFactory(UnBBayesTranslatorOWL2Net.OWL_MODEL);

		//create Net   
//cambiado
		Collection<Domain_MFrag> mFragSet =   myFactory.getAllDomain_MFragInstances();
		if(mFragSet.size() == 1) {
			for(Domain_MFrag mFrag : mFragSet) {
				String nameMfrag = mFrag.getLocalName();
				String[] mFragCut = nameMfrag.split("_");
				this.nameNet = mFragCut[0];
			}
		}else {
			this.nameNet = "unnamedNetwork";
		}



		this.net = new ProbabilisticNetwork(this.nameNet);

	}
	
	
	/**
         * Create the network structure in the. net, ie, nodes,
         * parents and children, states, and finally marginal probability tables
	 */
	private void createStructureNet() {

		//Add nodes to .net
//cambiado
		Set<Domain_Res> nodesOwl= (Set<Domain_Res>) myFactory.getAllDomain_ResInstances();
		
		ArrayList<ProbabilisticNode> nodeListNet = new ArrayList<ProbabilisticNode>();
		for(Domain_Res res: nodesOwl) {
			String nameRes = res.getLocalName();

			ProbabilisticNode nodeNet = new ProbabilisticNode();
			nodeNet.setName(nameRes);
			nodeListNet.add(nodeNet);
			logger.info("add node " +nameRes + " to Net." );
			

		}

		//add states
		for(Domain_Res res: nodesOwl) {
			String nameRes = res.getLocalName();
			Set<DefaultRDFIndividual> states = res.getHasPossibleValues();
			
			for(DefaultRDFIndividual state : states) {
				
								String stateS = state.getLocalName();
								for(ProbabilisticNode node :nodeListNet) {
				
									if(node.getName().equals(nameRes)) {
					
										node.appendState(stateS);
									}
				
								}
							}
						}
	
//// Cambiado 
//			//Collection states = res.getHasPossibleValues();
//			//Set states =  res.getHasPossibleValues();
//			Set<DefaultCategoricalRVState> states = res.getHasPossibleValues();
//			
//
//			for(DefaultCategoricalRVState state : states) {
//
//				String stateS = state.getLocalName();
//				for(ProbabilisticNode node :nodeListNet) {
//
//					if(node.getName().equals(nameRes)) {
//
//						node.appendState(stateS);
//					}
//
//				}
//			}
//		}

		//add nodes to net
		for(ProbabilisticNode node: nodeListNet) {
			net.addNode(node);
		}

		//add parents
		for(Domain_Res res: nodesOwl) {
			@SuppressWarnings("unchecked")
			Set<DefaultOWLIndividual> parents =  (Set<DefaultOWLIndividual>)res.getHasParent();

			ProbabilisticNode nodeNet = (ProbabilisticNode) net.getNode(res.getLocalName());
			for(DefaultOWLIndividual parent: parents) {
				String nameParent = parent.getLocalName();
				ProbabilisticNode parentNodeNet = (ProbabilisticNode) net.getNode(nameParent);
				Edge auxEdge = new Edge(parentNodeNet, nodeNet);
				try {
					net.addEdge(auxEdge);
				} catch (InvalidParentException e) {
					e.printStackTrace();
				}
			}			
		}

		//add tables
		addTablesToNet();
		logger.info("The potential tables are add in NET");	
	}


	/**
	 * Add tables to the nodes of the network
	 */
	private void addTablesToNet() {

		CreateTables createTables = new CreateTables();
		Set<ProbAssign> probAssing = (Set<ProbAssign>) myFactory.getAllProbAssignInstances();
		Set<PR_OWLTable> tables = (Set<PR_OWLTable>) myFactory.getAllPR_OWLTableInstances();
		for(PR_OWLTable table : tables) {
//			Set<DefaultResident> residents = (Set<DefaultResident>)  table.getIsProbDistOf();
			DefaultResident res = (DefaultResident) table.getIsProbDistOf();
//			for(DefaultResident res : residents) {
			String nameNode = res.getLocalName();

			ProbabilisticNode nodeNet = (ProbabilisticNode) net.getNode(nameNode);
			String[][] table2D = null;
			String[] table1D = null;
			double[][] value2D = null;
			double[] value1D = null;

			if(nodeNet.getParents().size() > 0) {
				table2D = createTables.marginalTable2DString(nodeNet);
				value2D = createTables.marginalTable2DValue(nodeNet);

			}
			if(nodeNet.getParents().size() == 0) {
				table1D = createTables.marginalTable1DString(nodeNet);
				value1D = createTables.marginalTable1DValue(nodeNet);
			}

			if(table2D != null) {

				ArrayList<unbbayes.prs.Node> parents = nodeNet.getParents();
				int numStateParent = 1;
				for(unbbayes.prs.Node parent: parents) {
					numStateParent = numStateParent * parent.getStatesSize();
				}
				int parentsCel = numStateParent;

				for(int f = 0; f<parentsCel ; f ++) {
					for(int c = 0; c<nodeNet.getStatesSize(); c++) {
						String name = table2D[c][f];
						//add marginals
						String[] nameA = name.split("-");
						for(ProbAssign prob :probAssing ) {

							String probS = prob.getLocalName();
							String[] probSA = probS.split("-");
							boolean equals = compareArray(nameA, probSA) ;

							if(equals == true) {

								RDFSLiteral literal = prob.getHasStateProb();
								double probState = literal.getDouble();
								value2D[c][f] = probState; 
								logger.info("Node name: "+ nameNode + ",states name: " + probS + " ,value marginal: " + probState);
								break;
							}
						}


					}
				}
			}
			


			if(table1D != null) {

				for(int i = 0; i< table1D.length; i++) {
					String nameS = table1D[i];
					String[] nameA = nameS.split("-");
					for(ProbAssign prob :probAssing ) {//recorre todas
						String probS = prob.getLocalName();
						String[] probSA = probS.split("-");
						boolean equals = compareArray(nameA, probSA) ;

						if(equals == true) {

							RDFSLiteral literal = prob.getHasStateProb();
							double probState = literal.getDouble();
							value1D[i] = probState;
							logger.info("Node name: "+ nameNode + ",states name: " + probS + " ,value marginal: " + probState);
							break;
						}

					}

				}

			}


			//add the tables to net
			if(value1D != null) {
				
				PotentialTable tableNet = nodeNet.getProbabilityFunction();
				tableNet.addVariable(nodeNet);
				for(int i = 0; i< value1D.length; i++) {
					double value = value1D[i];
					float valueF = (float) value;
					tableNet.addValueAt(i, valueF);
				}
			}

			if(value2D != null) {
				ArrayList<unbbayes.prs.Node> parents = nodeNet.getParents();
				int numStateParent = 1;
				for(unbbayes.prs.Node parent: parents) {
					numStateParent = numStateParent * parent.getStatesSize();
				}
				int parentsCel = numStateParent ;
				int counter = 0;
				PotentialTable tableNet = nodeNet.getProbabilityFunction();
				tableNet.addVariable(nodeNet);
				for(int f = 0; f<parentsCel ; f ++) {
					for(int c = 0; c<nodeNet.getStatesSize(); c++) {
						double value = value2D[c][f];
						float valueF = (float) value;
						tableNet.addValueAt(counter, valueF);
						counter += 1;

					}
				}

			}
			}

			}
//		}

	

	/**
	 * Compares if two string arrays are equal
	 * @param array1
	 * @param array2
	 * @return true, if the arrays are equals.
	 */
	private boolean compareArray(String[]array1, String[]array2) {

		int array1size = array1.length;
		int array2size = array2.length;

		if(array1size == array2size) {
			int counter = 0;
			for(int i = 0 ; i< array1size; i ++) {
				String s1 = array1[i];
				for(int j = 0; j<array2size; j ++) {
					String s2 = array2[j];
					if(s1.equals(s2)) {
						counter += 1;
						break;
					}
				}
			}
			if (counter == array2size) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}

	}
	
	/**
	 * Save the network to be created from a file .owl
	 */
	private void save() {
		NetIO io = new NetIO();
                String name = this.nameNet + ".net";
		try {

			if(net == null) {
				System.out.println("the network is null");
			}
			
			io.save(new File(netDirectoryUri + "/" + name), this.net);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	/**
	 * Convert a method to get .owl to a .net step correctly
	 */
	public void convertOwlToNet(){
		createStructureNet();
		System.out.println("The network structure has been created");
		save();
		System.out.println("The file has been created and saved successfully");

	}
	
}

