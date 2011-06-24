package es.upm.dit.gsi.starfire.diagnosisAgent;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import edu.stanford.smi.protegex.owl.model.OWLModel;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.INode;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;

import communeOntology.Diagnosis;
import communeOntology.EnvironmentAction;
import communeOntology.Failure;
import communeOntology.Hypothesis;
import communeOntology.MyFactory;
import communeOntology.Observation;
import communeOntology.TestAction;
import jadex.bdi.runtime.Plan;

public class DiagnosisLoopPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2633687897926958529L;
	private MyFactory myFactory;
	private ProbabilisticNetwork net; //Red bayesiana
	private static final String NET_URI = "net_uri";
	
	public DiagnosisLoopPlan() {
		getLogger().info("DiagnosisLoopPlan: Created: " + this);
	}
	
	@Override
	public void body() {
		
		OWLModel owlModel = (OWLModel) getBeliefbase().getBelief("ontology").getFact();
		myFactory = new MyFactory(owlModel);
		
		//Realiza inferencia con los datos disponibles
			
			//Cargamos la red bayesiana
			loadBayesianNetwork();
		
			//Lanza meta de realizar inferencia y espera a que termine
			makeInferences();
			
			//HashMap rellenado con una query de las hipótesis de este diagnóstico <String id(uri), Float confianza (número)>
			updateHypotheses();
			
			//Selecciona hipótesis con más confianza como hipótesis objetivo, ordenar las hipótesis del hashmap
			Hypothesis hypothesis = selectMoreLikelyHypothesis();
			
		
		//Selecciona acción a ejecutar
		
			//Actualiza influencia de los nodos en la red bayesiana con respecto a la hipótesis objetivo
	
				//Aplica algoritmo "CDF distance". Media de todas las combinaciones de las distribuciones de probabilidad de un nodo.
				//Devuelve un HashMap con <ObservationNode, expectedBenefit>
				HashMap<ProbabilisticNode,Double> expectedBenefits = calculateNodeCDFs(hypothesis);	//ProbalNode de la Hypothesis //Lo hace Jaime
			
				//Actualiza el parámetro de las acciones de test "expectedBenefit" dentro de la belief ontología meterlo en el
				updateExpectedBenefit(expectedBenefits);
				
			//Evalúa todas las condiciones(las reglas con jadex rules) de todas las acciones
			evaluateConditions();	
				
			//Fija una acción objetivo para realizar
			setAnActionGoalToPerform();
			
			getLogger().info("DiagnosisLoopPlan: Plan ends");

	}
	
	private void loadBayesianNetwork() {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream("config/setup.properties"));
			BaseIO io = new NetIO();
			getLogger().info(props.getProperty(NET_URI));
            net = (ProbabilisticNetwork) io.load(new File(props.getProperty(NET_URI)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	//Analiza las evidencias y las actualiza
	private void makeInferences() {
		Set<Observation> observations = myFactory.getAllObservationInstances();
		
		JunctionTreeAlgorithm alg = new JunctionTreeAlgorithm();
		alg.setNetwork(net);
		alg.run();
		
		List<Node> nodeList = net.getNodes();
		
		for (Observation o : observations) {
			String type = o.getObservationType();
			for (Node node : nodeList) {
				String nstring = node.getName();
				if (type.equals(nstring)) {
					int size = node.getStatesSize();
					String targetEvidenceState = (String) o.getObservationValue();
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
						getLogger().warning(
								"State not found in node: " + node.getName()
										+ " State: " + targetEvidenceState);
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
	
	//Actualiza la propiedad confidence de las hipótesis
	private void updateHypotheses() {
		
		Set<Hypothesis> hypotheses = myFactory.getAllHypothesisInstances();
		
			List<Node> nodeList = this.net.getNodes();

			String targetState = (String) getParameter("hypTargetState").getValue();

			for (Hypothesis h : hypotheses) {
				Failure failure = h.getRepresentsPossibleFailure();
				String className = failure.getClass().toString();
				for (Node node : nodeList) {
					String nstring = node.getName();
					if (className.equals(nstring)) {
						for (int i = 0; i < node.getStatesSize(); i++) {
							String nodeStateName = node.getStateAt(i);
							if (nodeStateName.equals(targetState)) {
								h.setHypothesisConfidence((float) ((ProbabilisticNode)node).getMarginalAt(i));
//								hypotheses.put(className,
//										(float) ((ProbabilisticNode) node)
//										.getMarginalAt(i));
							}
						}
					}

				}
			}
						
						
			getLogger().info("hypotheses have been updated.");
			/*IGoal print2 = createGoal("show_hypotheses");
			dispatchSubgoalAndWait(print2);*/

	}
	
	private Hypothesis selectMoreLikelyHypothesis() {
		Hypothesis moreLikelyHypothesis = null;
		float higherConfidence = -1;
		Set<Hypothesis> hypotheses = myFactory.getAllHypothesisInstances();
		for(Hypothesis h: hypotheses) {
			float confidence = h.getHypothesisConfidence();
			if(confidence < higherConfidence) {
				higherConfidence = confidence;
				moreLikelyHypothesis=h;
			}
		}
		return moreLikelyHypothesis;
	}
	
	/**
	 * calculateNodeCDFs: Calculates the node cdf
	 * 
	 * @param node
	 * @return Returns a hashmap with the parents nodes and the correspondent cdf value
	 */
	private HashMap<ProbabilisticNode,Double> calculateNodeCDFs(Hypothesis hypothesis){
		String nodeName = hypothesis.getHasBayesianNode();		
		ProbabilisticNode node = (ProbabilisticNode)net.getNode(nodeName); 
		
		System.out.println("--------------------> Calculate CDF for node: "+node.getName());
		PotentialTable table = node.getProbabilityFunction();
		HashMap<ProbabilisticNode, Double> cdfHashMap = new HashMap<ProbabilisticNode, Double>();
		double resultCDF = 0.0;
		int cdfsCalculated = 0;

		// If there are more than 1 parent:
		if(node.getParentNodes().size() > 1){
			//For each parent node, we get the marginal probabilities table
			List<INode> inodeList = node.getParentNodes();
			Iterator<INode> inodeIterator = inodeList.iterator();
			System.out.println("Number of parent nodes: "+inodeList.size());
			
			while(inodeIterator.hasNext()){
				ProbabilisticNode parentNode = (ProbabilisticNode) inodeIterator.next();
				System.out.println("----> Parent Node: "+parentNode.getName());
				int parentIndex = table.indexOfVariable(parentNode);

				//Create the marginal table:
				double[][] marginalTable = new double[parentNode.getStatesSize()][node.getStatesSize()];
				int[] position = new int[table.getVariablesSize()]; 
				double value=0.0;


				for (int j= 0; j<node.getStatesSize();j++){ // all states of node
					for(int k=0; k<parentNode.getStatesSize(); k++){ //all states of parent node
						for(int l = 0; l< table.tableSize(); l++){ //get all elements of the table and
							position=table.getMultidimensionalCoord(l); 
							
							if(position[0] == j && position[parentIndex] == k){ //checks if corresponds to the one we're looking for
								Iterator<INode> inodeIterator2 = inodeList.iterator();
								double aux = 1;
								while (inodeIterator2.hasNext()){ // get all parents states probabilities
									ProbabilisticNode parentNodeAux = (ProbabilisticNode) inodeIterator2.next();

									if(parentNodeAux != parentNode){

										PotentialTable parentTableAux = parentNodeAux.getProbabilityFunction();
										for(int m = 0; m<parentNodeAux.getStatesSize();m++){
											if(position[table.indexOfVariable(parentNodeAux)] == m){
												aux = aux * parentTableAux.getValue(m); 
											}
											

										}
									}
								}
								
								value += (table.getValue(l) * aux); //table value * each parent state probability ;
							}
						}	
						System.out.println("value = "+value +" position : "+k+" "+j);
						marginalTable[k][j]=value; //Transpose
						value = 0.0;
					}
				}

				//calculateCDF for the marginal table...
				for(int i=0; i<marginalTable.length; i++){
					for(int j=0; j<marginalTable.length; j++){
						if(i !=j){
							resultCDF += calculateCDF(marginalTable[i],marginalTable[j]);
							cdfsCalculated++;
						}
					}
				}

				resultCDF = resultCDF / cdfsCalculated ;
				System.out.println("-----> CDF Result = "+resultCDF);
				cdfsCalculated = 0;
				resultCDF = 0.0;
				cdfHashMap.put(parentNode, resultCDF);
			}
		}else{ //Just one parent
			//Create the 2D table.
			if(node.getParentNodes().size() != 0){ // If it doesn't have a parent, continue
				int numberStatesAdjacentNode = (table.tableSize()/node.getStatesSize());
				double[][] table2D = new double[numberStatesAdjacentNode][node.getStatesSize()];
				int counter = 0;
				for(int j=0; j<numberStatesAdjacentNode; j++){
					for(int k = 0; k<node.getStatesSize();k++){
						table2D[j][k]=table.getValue(counter);
						counter++;
					}
				}

				for(int i=0; i<numberStatesAdjacentNode; i++){
					for(int j=0; j<numberStatesAdjacentNode; j++){
						if(i !=j){
							resultCDF += calculateCDF(table2D[i],table2D[j]);
							cdfsCalculated++;
						}
					}
				}
				if(cdfsCalculated >0)
					resultCDF = resultCDF / cdfsCalculated ;
				System.out.println("-----> CDF Result = "+resultCDF);
				if (!node.getParents().isEmpty()){
					ProbabilisticNode parentNode = (ProbabilisticNode) node.getParents().get(0);
					cdfHashMap.put(parentNode, resultCDF);
				}
			}
		}
		return cdfHashMap;

	}

	/**
	 * calculate CDF: given two columns, returns the CDF value
	 * 
	 * @param column1
	 * @param column2
	 * @return CDF double value
	 */
	private double calculateCDF(double[] column1 , double[] column2){
		double result = 0.0;

		for(int i=0; i<column1.length; i++){
			double value1 = 0.0;
			double value2 = 0.0;
			for(int j=0; j<=i; j++){
				value1 += column1[j];
				value2 += column2[j];
			}
			result += Math.abs(value1-value2);
		}
		result = result/(column1.length-1);

		return result;

	}
	
	private void updateExpectedBenefit(HashMap<ProbabilisticNode,Double> expectedBenefits) {
		//Coger todas las acciones correspondientes a este diagnóstico
		//Quedarme sólo con las TestAction
		//Actualizar los expectedBenefits a partir de sus probabilisticNodes
		
		String diagnosisID = (String)getParameter("diagnosisID").getValue();
		Diagnosis diagnosis = getDiagnosis(diagnosisID);
		@SuppressWarnings("unchecked")
		Set<EnvironmentAction> environmentActions = diagnosis.getHasPossibleActionsToPerform();
		
		Set<ProbabilisticNode> probabilisticNodes = expectedBenefits.keySet();
		
		for(EnvironmentAction ea: environmentActions) {
			if(ea instanceof TestAction) {
				for(ProbabilisticNode pn:probabilisticNodes) {
					if((pn.getName()).equals(((TestAction)ea).getHasBayesianNode())) {						
						((TestAction)ea).setExpectedBenefit((expectedBenefits.get(pn)).floatValue());
					}
				}
			}
		}
		
	}
	
	private Diagnosis getDiagnosis(String diagnosisID) {
		Set<Diagnosis> diagnoses = myFactory.getAllDiagnosisInstances();
		for(Diagnosis d:diagnoses) {
			if((d.getId()).equals(diagnosisID)) {
				return d;
			}
		}
		return null;
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
