package es.upm.dit.gsi.net2prOwl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.input.SAXHandler;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import tools.CreateTables;
import unbbayes.prs.Node;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.RDFSDatatype;
import edu.stanford.smi.protegex.owl.model.RDFSLiteral;
import es.upm.dit.gsi.ontology.CategoricalRVState;
import es.upm.dit.gsi.ontology.CondRelationship;
import es.upm.dit.gsi.ontology.Domain_Res;
import es.upm.dit.gsi.ontology.MyFactory;
import es.upm.dit.gsi.ontology.PR_OWLTable;
import es.upm.dit.gsi.ontology.ProbAssign;
import es.upm.dit.gsi.ontology.impl.DefaultCategoricalRVState;
/**
 * This class tries to convert a. net to a file. owl
 * @author Jeronimo Fco Perez Regidor
 */
public class BayesianTranslatorNet2OWL {
	Logger logger = Logger.getLogger(BayesianTranslatorNet2OWL.class.toString());

	private LoadNetwork load = new LoadNetwork();
	private ProbabilisticNetwork net;
	private MyFactory myFactory;
	private File uriNet;
	private File uriOwl;
	public static JenaOWLModel owlModel;


        /**
         * Builder
         * @param uriNet where the file to convert
         * @param uriOwl where is the file. owl base / empty
         */
	public BayesianTranslatorNet2OWL(File netFile, File owlEmptyFile) {
		// load net
		this.uriNet = netFile;
		loadNet();

		// create Owl
		this.uriOwl = owlEmptyFile;
		try {
			FileReader owlFile = new FileReader(this.uriOwl);
			owlModel = ProtegeOWL.createJenaOWLModelFromReader(owlFile);
			owlModel.getNamespaceManager().setDefaultNamespace(
					"http://www.gsi.upm.es/Commune.owl#");

		} catch (Exception oe) {
			oe.printStackTrace();

		}

		// create myFacrtory.
		this.myFactory = new MyFactory(this.owlModel);

	}

	/**
	 * load network
	 */
	private void loadNet() {
		net = this.load.load(this.uriNet);

	}

	/**
	 * Create the structure in OWL, ie, nodes,
         * states, parents and children, tables of these nodes, M_Frag.
	 */
	private void createStructureOWL() {
		ArrayList<Node> nodesNet = net.getNodes();

		// String nameMfrag = net.getName() + "_Mfrag";

		String nameMfrag = net.getId() + "_Mfrag";

		// myFactory.createMFrag(nameMfrag);
		myFactory.createDomain_MFrag(nameMfrag);

		for (int i = 0; i < nodesNet.size(); i++) {
			String nameNode = nodesNet.get(i).getName();
			logger.info("Name: " + nameNode);
			// Domain_Res domain_Res = myFactory.getDomain_Res(nameNode);
			Domain_Res domain_Res = myFactory.createDomain_Res(nameNode);
		}
		for (int i = 0; i < nodesNet.size(); i++) {
			// add to owl the parents of node.
			ArrayList<Node> parentsNode = nodesNet.get(i).getParents();
			String nameNode = nodesNet.get(i).getName();
			Domain_Res domain_Res = myFactory.getDomain_Res(nameNode);
			for (int j = 0; j < parentsNode.size(); j++) {

				Domain_Res domain_Res_Parent = myFactory
						.getDomain_Res(parentsNode.get(j).getName());
				domain_Res.addHasParent(domain_Res_Parent);

			}

			// add to owl the states of node.(solo añado no a los nodos
			int stateSize = nodesNet.get(i).getStatesSize();
			for (int k = 0; k < stateSize; k++) {
				String nameState = nodesNet.get(i).getStateAt(k);
				
				
					
				
				// pruebo si no esta añadido en categorical states
				CategoricalRVState categorical = myFactory
						.getCategoricalRVState(nameState);
				
				if (categorical == null) {
					logger.info("the state is null");
					myFactory.createCategoricalRVState(nameState);		

				}

				domain_Res.addHasPossibleValues(myFactory
						.getCategoricalRVState(nameState));
			}

			// add to owl the PR_table node
			String nameTable = nameNode + "_Table";
			myFactory.createPR_OWLTable(nameTable);

			// add Mfrag to domainRes
			domain_Res.addIsNodeFrom(myFactory.getDomain_MFrag(nameMfrag));

			domain_Res
					.addIsResidentNodeIn(myFactory.getDomain_MFrag(nameMfrag));

		}

	}

	/**
	 * Create tables of the Domain resident nodes
	 */
	private void createTablesOWL() {
		CreateTables createTables = new CreateTables();

		ArrayList<Node> nodesNet = net.getNodes();

		for (int i = 0; i < nodesNet.size(); i++) {

			Node nodeNet = nodesNet.get(i);
			ProbabilisticNode probNodeNet = (ProbabilisticNode) nodeNet;

			if (nodeNet.getParents().size() == 0) {// dont have parents

				String[] table1DS = createTables
						.marginalTable1DString(probNodeNet);
				HashMap<String, Double> table1DMap = createTables
						.table1DToHashMap(probNodeNet);

				// create ProbAssing
				for (int t = 0; t < table1DS.length; t++) {
					myFactory.createProbAssign(table1DS[t]);
				}
//cambiado
				Collection<ProbAssign> allProbAssing =  myFactory
						.getAllProbAssignInstances();
				for (ProbAssign prob : allProbAssing) {
					for (int t = 0; t < table1DS.length; t++) {

						if (prob.getPrefixedName().equals(table1DS[t])) {
							double value = table1DMap.get(prob
									.getPrefixedName());// get value of key
							RDFSDatatype dateType = owlModel
									.getRDFSDatatypeByName("xsd:decimal");
							RDFSLiteral literal = owlModel.createRDFSLiteral(
									new Double(value).toString(), dateType);

							prob.setHasStateProb(literal);// add the marginal to
															// .owl

							// add the node State
							String[] brokenChain = prob.getPrefixedName()
									.split("_");
							String nameState = brokenChain[1];
							CategoricalRVState hasStateName = myFactory
									.getCategoricalRVState(nameState);
							prob.setHasStateName(hasStateName);

							// Dont have conditionants because dont have parents

						}
					}
				}

			}

			if (nodeNet.getParents().size() > 0) {// have parents

				String[][] table2DString = createTables
						.marginalTable2DString(probNodeNet);
				HashMap<String, Double> hashMapTable2D = createTables
						.table2DToHashMap(probNodeNet);

				// create probAssing
				// double numNodes = nodeNet.getParents().size();
				// int parentsCel = (int) Math.pow(2, numNodes);
				ArrayList<Node> parents = probNodeNet.getParents();
				int numStateParent = 1;
				for (Node parent : parents) {
					numStateParent = numStateParent * parent.getStatesSize();
				}
				int parentsCel = numStateParent;

				for (int w = 0; w < parentsCel; w++) {
					for (int t = 0; t < probNodeNet.getStatesSize(); t++) {

						String nameCode = table2DString[t][w];
						myFactory.createProbAssign(nameCode);

					}
				}
//cambiado de set
				Collection<ProbAssign> allProbAssing =  myFactory
						.getAllProbAssignInstances();
				for (ProbAssign prob : allProbAssing) {
					for (int w = 0; w < parentsCel; w++) {
						for (int t = 0; t < probNodeNet.getStatesSize(); t++) {

							if (prob.getPrefixedName().equals(
									table2DString[t][w])) {
								double value = hashMapTable2D.get(prob
										.getPrefixedName());// get value of key
								// RDFSLiteral literal = new DefaultRDFSLiteral(
								// OWL_MODEL, new Double(value).toString());
								//
								RDFSDatatype dateType = owlModel
										.getRDFSDatatypeByName("xsd:decimal");
								RDFSLiteral literal = owlModel
										.createRDFSLiteral(
												new Double(value).toString(),
												dateType);

								prob.setHasStateProb(literal);// add the
																// marginal to
																// .owl

								// add the node State
								String[] brokenChain = prob.getPrefixedName()
										.split("-");
								String name = brokenChain[0];
								String[] brokenName = name.split("_");
								String nameState = brokenName[1];
								CategoricalRVState hasStateName = myFactory
										.getCategoricalRVState(nameState);
								prob.setHasStateName(hasStateName);

								// create conditionant

								for (int part = 1; part < brokenChain.length; part++) {

									String nameParent = brokenChain[part];
									String newNameParent = "cond_" + nameParent;
									CondRelationship condition = myFactory
											.getCondRelationship(newNameParent);
									if (condition == null) {
										myFactory
												.createCondRelationship(newNameParent);
									}

									// Add conditionant to probAssing
									CondRelationship hasConditionant = myFactory
											.getCondRelationship(newNameParent);
									// broken chain of nameParent
									String[] parentPlusState = nameParent
											.split("_");

									hasConditionant.setHasParentName(myFactory
											.getDomain_Res(parentPlusState[0]));
									hasConditionant
											.setHasParentState(myFactory
													.getCategoricalRVState(parentPlusState[1]));
									prob.addHasConditionant(hasConditionant);

								}

							}
						}
					}
				}

			}// close if
		}
	}

	/**
	 * Add ProbAssing to PR-OWLTable and to add this table to Resident
	 */
	private void addProbAssingToPROWLTable() {
		//cambiado
		Collection<ProbAssign> allProbAssign =  myFactory.getAllProbAssignInstances();

		for (ProbAssign prob : allProbAssign) {

			String name = prob.getPrefixedName();
			String[] nameNodesAndStates = name.split("-");
			String[] nameNodeAndState = nameNodesAndStates[0].split("_");
			String nameNode = nameNodeAndState[0];
			String nameTable = nameNode + "_Table";
			PR_OWLTable table = myFactory.getPR_OWLTable(nameTable);
			table.addHasProbAssign(prob);

		}
//cambiado de set
		Collection<PR_OWLTable> tables =  myFactory.getAllPR_OWLTableInstances();
		for (PR_OWLTable table : tables) {
			String nameTable = table.getPrefixedName();
			String[] brokenNameTable = nameTable.split("_");
			String nameNode = brokenNameTable[0];

			table.addIsProbDistOf(myFactory.getDomain_Res(nameNode));
		}

	}

	/**
	 * Save the owl model
	 */
	private void saveOwlModel() {
		try {
			String name = net.getId() + ".owl";
	
	
			URI saveUri = (new File(name).toURI());
	
			owlModel.save(saveUri);
					
	
			String uri = "http://www.gsi.dit.upm.es/Commune";

			SAXBuilder builder = new SAXBuilder();
			SAXHandler handler = new SAXHandler(builder.getFactory());
			builder.setDTDHandler(handler);
			Document doc = (Document) builder.build(new File(name));
			Element root = (Element) doc.getRootElement();

			Attribute a = new Attribute("base", uri);
			a.setNamespace(Namespace.XML_NAMESPACE);
			root.setAttribute(a);

			List<Element> children = root.getChildren();
			for (Element child : children) {
				logger.info("child name: " + child.getName());
				if (child.getName().equals("Ontology")) {
					List<Attribute> atlist = child.getAttributes();
					for (Attribute at : atlist) {
						logger.info("at name: " + at.getName() + " : "
								+ at.getValue());
						if (at.getName().equals("about")) {
							Attribute at2 = new Attribute("about", uri+"#");
							at2.setNamespace(root.getNamespace("rdf"));
							child.removeAttribute(a);
							child.setAttribute(at2);
							break;
						}
					}
					break;
				}
			}

			XMLOutputter outputter = new XMLOutputter();
			Writer w = new FileWriter(new File(new File(name).toURI()));
			outputter.output(doc, w);
			w.flush();

		

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	




        /**
         * Public method that it does is convert a. net to. owl
         */
        public void convertNet2Owl(){
                createStructureOWL();
		System.out.println("created the structure");
		createTablesOWL();//
		System.out.println("created tables");
		addProbAssingToPROWLTable();
        System.out.println("added the probAssing");
		saveOwlModel();
		
		System.out.println("the file. owl has been created and updated");
        }

     
}
