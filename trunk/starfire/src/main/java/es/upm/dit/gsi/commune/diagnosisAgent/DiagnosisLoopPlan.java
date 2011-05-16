package es.upm.dit.gsi.commune.diagnosisAgent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import edu.stanford.smi.protegex.owl.model.OWLModel;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;

import javaOntology.Hypothesis;
import javaOntology.MyFactory;
import javaOntology.ObservationNode;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

public class DiagnosisLoopPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2633687897926958529L;
	private MyFactory myfactory;
	private ProbabilisticNetwork net; //Red bayesiana
	@Override
	public void body() {
		
		OWLModel owlModel = (OWLModel) getBeliefbase().getBelief("ontology").getFact();
		myfactory = new MyFactory(owlModel);
		
		//Realiza inferencia con los datos disponibles
			
			//Lanza meta de realizar inferencia y espera a que termine
			throwMakeInferencesGoal();
			
			//HashMap rellenado con una query de las hipótesis de este diagnóstico <String id(uri), Float confianza (número)>
			HashMap<String,Float> confidences = getHypothesis();
			
			//Selecciona hipótesis con más confianza como hipótesis objetivo, ordenar las hipótesis del hashmap
			Hypothesis hypothesis = selectMoreLikelyHypothesis(confidences);
			
		
		//Selecciona acción a ejecutar
		
			//Actualiza influencia de los nodos en la red bayesiana con respecto a la hipótesis objetivo
	
				//Aplica algoritmo "CDF distance". Media de todas las combinaciones de las distribuciones de probabilidad de un nodo.
				//Devuelve un HashMap con <ObservationNode, expectedBenefit>
				HashMap<ObservationNode,Float> expectedBenefits = applyCDFAlgorithm(hypothesis);	//Lo hace Jaime
			
				//Actualiza el parámetro de las acciones de test "expectedBenefit" dentro de la belief ontología meterlo en el
				updateExpectedBenefit(expectedBenefits);
				
			//Evalúa todas las condiciones(las reglas con jadex rules) de todas las acciones
			evaluateConditions();	
				
			//Fija una acción objetivo para realizar
			setAnActionGoalToPerform();
	}
	//Analiza las evidencias y las actualiza
	private void throwMakeInferencesGoal() {
		HashMap evidences = (HashMap) getBeliefbase().getBelief("evidences")
		.getFact();
		JunctionTreeAlgorithm alg = new JunctionTreeAlgorithm();
		alg.setNetwork(net);
		alg.run();

		Set<String> evidenceSet = evidences.keySet();

		List<Node> nodeList = net.getNodes();
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
						getLogger().warning("State not found in node: "+ node.getName() + " State: " + targetEvidenceState);
					}
					
				}
				
			}
		}
        getLogger().info("evidences, already in the network, are fixed");
		// propagate evidence
		try {
			this.net.updateEvidences();
		} catch (Exception exc) {
			
			Logger.getLogger("updateEvidences").warning(
					"Has not been possible to upgrade the network.");
		}

		/*IGoal print1 = createGoal("show_evidences");
		dispatchSubgoalAndWait(print1);*/	
	}
	
	//Crea un HashMap con las hipotesis de la red y su valor(probabilidad)
	private HashMap<String, Float> getHypothesis() {
		
		HashMap hypotheses = (HashMap) getBeliefbase().getBelief("hypotheses")
		.getFact();
			Set<String> hypothesesSet = hypotheses.keySet();
			List<Node> nodeList = this.net.getNodes();

			String targetState = (String) getParameter("hypTargetState").getValue();

			for (String hypothesis : hypothesesSet) {
				for (Node node : nodeList) {
					String nstring = node.getName();
					if (hypothesis.equals(nstring)) {
						for (int i = 0; i < node.getStatesSize(); i++) {
							String nodeStateName = node.getStateAt(i);
							if (nodeStateName.equals(targetState)) {
								hypotheses.put(hypothesis,
										(float) ((ProbabilisticNode) node)
										.getMarginalAt(i));
							}
						}
					}

				}
			}
			getLogger().info("hypotheses have been loaded.");
			/*IGoal print2 = createGoal("show_hypotheses");
			dispatchSubgoalAndWait(print2);*/

		return hypotheses;
	}
	
	private Hypothesis selectMoreLikelyHypothesis(HashMap<String,Float> confidences) {
		Hypothesis hypothesis = null;
		Float higherConfidence = new Float(-1);
		for(String uri: (Set<String>)confidences.keySet()) {
			Float confidence = confidences.get(uri);
			if(confidence < higherConfidence) {
				higherConfidence = confidence;
				hypothesis=myfactory.getHypothesis(uri);
			}
		}
		return null;
	}
	
	private HashMap<ObservationNode, Float> applyCDFAlgorithm(Hypothesis hypothesis) {
		// TODO Auto-generated method stub
		
		//Consulta los nodos adyacentes de la hipótesis objetivo	
		//ArrayList<ObservationNodes> adjacentNodes = consultAdjacentNodes(hypothesis);
		
		return null;
	}
	
	private void updateExpectedBenefit(HashMap<ObservationNode, Float> expectedBenefits) {
		// TODO Auto-generated method stub
		
	}
	
	private void evaluateConditions() {
		// TODO Auto-generated method stub
		
	}
	
	private void setAnActionGoalToPerform() {
		// TODO Auto-generated method stub
		//1) Ve las acciones disponibles
		//2) De las acciones disponibles selecciona la de mayor expected benefit
		
		//Acción de recuperación(las acciones de recuperación tienen preferencia)
		
		//Acción de test. Selecciona acción disponible con mayor influencia
	}
}
