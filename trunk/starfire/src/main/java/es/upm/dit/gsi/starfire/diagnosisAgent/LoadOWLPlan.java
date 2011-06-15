package es.upm.dit.gsi.starfire.diagnosisAgent;

import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
//import java.util.Set;
//
//import communeOntology.Agent;
//import communeOntology.MyFactory;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;

public class LoadOWLPlan extends Plan {

	private static final long serialVersionUID = 1L;
	private static final String OWL_URI = "owl_uri";
	private static final String AGENT_ID = "agent_ID";

	public LoadOWLPlan() {
		getLogger().info("LoadOWLPlan: Created: " + this);
	}

	public void body() {
		getLogger().info("LoadOWLPlan: Plan begins");

		setUp();

		getLogger().info("LoadOWLPlan: Setup done");

		loadOWLModel();

		getLogger().info("LoadOWLPlan: Owl model loaded");

//		boolean isAgentInModel = checkID();

//		if (isAgentInModel) {
			//Lanzar meta para crear sesión RTP
			IGoal goal = createGoal("launch_vlc");
			getLogger().info("Starting a VlcLauncherPlan"); 
			dispatchSubgoalAndWait(goal);
//		} else {
//			//Lanza excepción
//			getLogger().info("LoadOWLPlan: Agent not found in the model");
//		}
	}

	private void setUp() {
		// create an instance of properties class
		Properties props = new Properties();

		try { // try retrieve data from file
			props.load(new FileInputStream("config/setup.properties"));

			getLogger().info("LoadOWLPlan: Owl uri: " + props.getProperty(OWL_URI));

			getBeliefbase().getBelief("owl_uri").setFact(props.getProperty(OWL_URI));

			getLogger().info("LoadOWLPlan: Agent ID: " + props.getProperty(AGENT_ID));

			getBeliefbase().getBelief("agent_ID").setFact(props.getProperty(AGENT_ID));
		} catch (IOException e) { // catch exception in case properties file does not exist
			e.printStackTrace();
		}
	}

	private void loadOWLModel() {
		try {
			String owlUri = (String) getBeliefbase().getBelief("owl_uri").getFact();

			JenaOWLModel ontology = ProtegeOWL.createJenaOWLModelFromURI(owlUri);

			getBeliefbase().getBelief("ontology").setFact(ontology);
		} catch (OntologyLoadException e) {
			e.printStackTrace();
		}
	}

//	private boolean checkID() {
//		JenaOWLModel ontology = (JenaOWLModel) getBeliefbase().getBelief("ontology").getFact();
//		MyFactory myFactory = new MyFactory(ontology);
//		Set<Agent> agents = myFactory.getAllAgentInstances();
//		for (Agent a : agents) {
//			if ((a.getId()).equals((String) getBeliefbase().getBelief("agent_ID").getFact())) {
//				return true;
//			}
//		}
//		return false;
//	}
}