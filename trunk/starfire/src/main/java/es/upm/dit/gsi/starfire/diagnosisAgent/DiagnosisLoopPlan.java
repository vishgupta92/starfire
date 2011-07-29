package es.upm.dit.gsi.starfire.diagnosisAgent;

import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import org.drools.command.runtime.rule.GetFactHandleCommand;


import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.INode;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.PotentialTable;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;

import communeOntology.Action;
import communeOntology.Condition;
import communeOntology.Diagnosis;
import communeOntology.EnvironmentAction;
import communeOntology.Failure;
import communeOntology.Hypothesis;
import communeOntology.MyFactory;
import communeOntology.Observation;
import communeOntology.Symptom;
import communeOntology.TestAction;

import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.swrl.SWRLRuleEngine;
import edu.stanford.smi.protegex.owl.swrl.bridge.SWRLRuleEngineFactory;
import edu.stanford.smi.protegex.owl.swrl.bridge.drools.DroolsNames;
import edu.stanford.smi.protegex.owl.swrl.bridge.drools.DroolsSWRLRuleEngineCreator;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.JessNames;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.JessSWRLRuleEngineCreator;
import edu.stanford.smi.protegex.owl.swrl.exceptions.SWRLRuleEngineException;
import org.drools.rule.builder.dialect.java.JavaDialectConfiguration;
import org.drools.rule.builder.dialect.clips.*;

public class DiagnosisLoopPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2633687897926958529L;
	private MyFactory myFactory;
	private ProbabilisticNetwork net; //Red bayesiana
	private static final String NET_URI = "net_uri";
	private double cpu;
	private double memory;
	
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
		
			//Extrae los nodos de observacion de la red bayesiana
			extractObservationNodes();
			selectValueForObservation();
			//Lanza meta de realizar inferencia y espera a que termine
			makeInferences();
			
			//Extrae los nodos hipotesis de la red bayesiana
			extractHypothesisNode();
					
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
	//Carga la red bayesiana
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
	//Inicializa la carga de cpu y memoria
	private void setFactMemoryAndCpu(){
		if(cpu == 0.0 && memory == 0.0){
			cpu = 100.0;
			memory = 100.0;
		}
	}
	
	//Extrae los nodos de observacion
	private void extractObservationNodes(){
		String diagnosisID = (String)getParameter("diagnosisID").getValue();
		List<Node> nodeList = net.getNodes();
		for(Node n : nodeList){
			if(!n.hasState("TRUE") && !n.hasState("NO")){
				Observation obs = myFactory.createObservation(n.getName() + diagnosisID);
				obs.setObservationType(n.getName());
			}
		}		
	}
	//Selecciona el estado correspondiente al nodo de observacion
	private void selectValueForObservation(){
		Set<Symptom> symptom = myFactory.getAllSymptomInstances();
		Set<Observation> observations = myFactory.getAllObservationInstances();
		String packetLoss = "PacketLossPercentage";
		String detectJitter = "DetectedJitter";
		String cpuUsage = "ServerCpuUsage";
		String memoryUsage = "ServerMemoryUsage";
		for(Symptom sym : symptom){
			Observation o = sym.getObservationResult();
		    String oValue = o.getObservationValue();
		    StringTokenizer st = new StringTokenizer(oValue, "::");
		    String [] dates = new String[st.countTokens()];
		    for(int i = 0; st.hasMoreTokens(); i++){
		    	dates[i] = st.nextToken();
		    }
		    float jitter = Float.parseFloat(dates[1]);
		    float percentage = Float.parseFloat(dates[5]);
		    cpu = (Double) getBeliefbase().getBelief("freeCpu").getFact();
		    memory = (Double) getBeliefbase().getBelief("freeMemory").getFact();
		    setFactMemoryAndCpu();
		    getLogger().info("CPU LIBRE: " + cpu);
		    getLogger().info("MEMORY LIBRE: " + memory);
		    getLogger().info("PERCENTAGE " + percentage);
		    for(Observation obs : observations){	    	
		    	String obsType = obs.getObservationType();
			    if(percentage == 0 && jitter < 100){
			    	if(obsType.equals(packetLoss)){
			    		obs.setObservationValue("None");
			    	}
				}else if(0<=percentage && percentage <=0.05 && jitter <= 100){
					if(obsType.equals(packetLoss)){
			    		obs.setObservationValue("Low");
					}
			    }else if(0.05<percentage && percentage <0.1 && jitter <= 100){
			    	if(obsType.equals(packetLoss)){
			    		obs.setObservationValue("Medium");
			    	}
			    }else if(percentage >= 0.10 && jitter <= 100 && memory > 10 && cpu > 15){
			    	if(obsType.equals(packetLoss)){
			    		obs.setObservationValue("High");
			    	}
			    }else if(percentage <= 0.025 && jitter > 25 && jitter <= 100){
			    	if(obsType.equals(detectJitter)){
			    		obs.setObservationValue("Low");
			    	}
			    }else if(percentage <= 0.025 && jitter > 100 && jitter < 200){	
			    	if(obsType.equals(detectJitter)){
			    		obs.setObservationValue("Medium");
			    	}
			    }else if(percentage <= 0.025 && jitter >= 200 && memory > 10 && cpu > 15){	
			    	if(obsType.equals(detectJitter)){
			    		obs.setObservationValue("High");
			    	}
			    }else if(memory < 10){
			    	if(obsType.equals(memoryUsage)){
			    		obs.setObservationValue("HighUsage");
			    	}
			    }else if(cpu < 15){
			    	if(obsType.equals(cpuUsage)){
			    		obs.setObservationValue("HighUsage");
			    	}
			    }
		    }
		}
	}
	
	//Analiza las evidencias y las actualiza
	private void makeInferences() {
		Set<Observation> observations = myFactory.getAllObservationInstances();
		List<Node> nodeList = net.getNodes();
		JunctionTreeAlgorithm alg = new JunctionTreeAlgorithm();
		alg.setNetwork(net);
		alg.run();
		for (Observation o : observations) {
			for (Node node : nodeList) {
				String nstring = node.getName();
				String type = o.getObservationType();
				if (type.equals(nstring)){
					int size = node.getStatesSize();
					String targetEvidenceState = (String) o.getObservationValue();
					getLogger().info("OBSERVACION Y VALOR EN INFERENCES: " + o.getObservationValue());
					boolean foundState = false;
					for (int i = 0; i < size; i++) {
						String state = node.getStateAt(i);
						if (state.equals(targetEvidenceState)){
							getLogger().info("EVIDENCIA " + targetEvidenceState);
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
	
	
//	Extrae los nodos hipotesis de la red bayesiana
	private void extractHypothesisNode(){
			String diagnosisID = (String)getParameter("diagnosisID").getValue();
			List<Node> nodes = net.getNodes();
			Set<Hypothesis> hypothesis = myFactory.getAllHypothesisInstances();
			getLogger().info(hypothesis.toString());
			if(hypothesis.isEmpty()){
			for(Node n : nodes){
				if(n.hasState("YES") && n.hasState("NO")){
					getLogger().info("NODOS HIPOTESIS: " + n.getName());
					Hypothesis hyp = myFactory.createHypothesis(n.getName() + diagnosisID);
					getLogger().info("HIPOTESIS: " + hyp.getName());
					hyp.setHasBayesianNode(n.getName());
			    	Failure fail = myFactory.createFailure(n.getName());
			    	hyp.setRepresentsPossibleFailure(fail);
			    	//fail.addCanBeRepairedWith(myFactory.createHealingAction("KillProcces " + n.getName()));
				}
			}
			}
	}

	//Actualiza la propiedad confidence de las hipótesis
	private void updateHypotheses() {
		
		Set<Hypothesis> hypotheses = myFactory.getAllHypothesisInstances();
		//getLogger().info("DAME LAS HIPOTESIS: " + myFactory.getAllHypothesisInstances().toString());
			List<Node> nodeList = this.net.getNodes();
			String targetState = (String) getParameter("hypothesesTargetState").getValue();
			getLogger().info("TARGETSTATE: " + targetState);
			for (Hypothesis h : hypotheses) {
				Failure failure = h.getRepresentsPossibleFailure();
				String className = failure.getName();
				getLogger().info("HIPOTESIS: " + h.getName() + " REPRESENTA FALLO: " + className);
				for (Node node : nodeList) {
					String nstring = node.getName();
					if (className.equals(nstring)) {
						for (int i = 0; i < node.getStatesSize(); i++) {
							String nodeStateName = node.getStateAt(i);
							if (nodeStateName.equals(targetState)) {  							    
								h.setHypothesisConfidence((float) ((ProbabilisticNode)node).getMarginalAt(i));
                                getLogger().info("NUEVA PROBABILIDAD: " + h.getHypothesisConfidence() + "DEL NODO: " + h.getName());
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
	//Selecciona la hipotesis mas probable
	private Hypothesis selectMoreLikelyHypothesis() {
		Hypothesis moreLikelyHypothesis = null;
		float higherConfidence = -1;
		Set<Hypothesis> hypotheses = myFactory.getAllHypothesisInstances();
		for(Hypothesis h: hypotheses) {
			float confidence = h.getHypothesisConfidence();
			getLogger().info("POSIBILIDAD EN SELECT: " + h.getHypothesisConfidence() + " DE LA HIPOTESIS " + h.getName());
			if(confidence > higherConfidence) {
				higherConfidence = confidence;
				moreLikelyHypothesis=h;
			}
		}
		getLogger().info("HIPOTESIS ESCOGIDA " + moreLikelyHypothesis.getName());
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
		if(node.getChildNodes().size() > 1){
			//For each parent node, we get the marginal probabilities table
			List<INode> inodeList = node.getChildNodes();
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
			if(node.getChildNodes().size() != 0){ // If it doesn't have a parent, continue
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
				if (!node.getChildren().isEmpty()){					
					ProbabilisticNode parentNode = (ProbabilisticNode) node.getChildren().get(0);
					cdfHashMap.put(parentNode, resultCDF);
					System.out.println("-----> CDF Result = " + resultCDF);
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
	/**
	 * update Expected Benefit: set the expected benefit for each test action
	 * 
	 * @param expectedBenefits
	 */
	private void updateExpectedBenefit(HashMap<ProbabilisticNode,Double> expectedBenefits) {		
		String diagnosisID = (String)getParameter("diagnosisID").getValue();
		Diagnosis diagnosis = getDiagnosis(diagnosisID);
		@SuppressWarnings("unchecked")
		Set<Action> ac = diagnosis.getHasPossibleActionsToPerform();
		getLogger().info("ACCIONES EN UPDATE: " + ac.toString());
		Set<ProbabilisticNode> probabilisticNodes = expectedBenefits.keySet();
		
		for(Action test : ac){
			if(test instanceof TestAction){
				getLogger().info(test.getName() + " ES UNA TESTACTION");
				for(ProbabilisticNode pn:probabilisticNodes){
					getLogger().info("NOMBRE PN: " + pn.getName());
					if((pn.getName()).equals(((TestAction)test).getHasBayesianNode())) {						
						((TestAction)test).setExpectedBenefit((expectedBenefits.get(pn)).floatValue());
						getLogger().info("ES LA ACCION: " + test.getName());
						getLogger().info("VALOR: " + ((TestAction) test).getExpectedBenefit());
					}else{
						((TestAction)test).setExpectedBenefit((float) 0.0);
					}
				}
			}
		}
		
	}
	
	/**
	 * getDiagnosis: get the Diagnosis ID
	 * @param diagnosisID
	 * @return the Diagnosis ID
	 */
	private Diagnosis getDiagnosis(String diagnosisID) {
		Set<Diagnosis> diagnoses = myFactory.getAllDiagnosisInstances();
		for(Diagnosis d:diagnoses) {
			if((d.getId()).equals(diagnosisID)) {
				return d;
			}
		}
		return null;
	}
	/**
	 * EvaluateConditions: execute the rule engine
	 */
	private void evaluateConditions(){
		try {	
			OWLModel owlModel = (OWLModel) getBeliefbase().getBelief("ontology").getFact();	
			SWRLRuleEngineFactory.registerRuleEngine(JessNames.PluginName, new JessSWRLRuleEngineCreator());
			SWRLRuleEngine ruleEngine = SWRLRuleEngineFactory.create(JessNames.PluginName, owlModel);
			ruleEngine.reset();
			ruleEngine.importSWRLRulesAndOWLKnowledge();
			ruleEngine.run();
			ruleEngine.writeInferredKnowledge2OWL();
			//ruleEngine.infer();
		} catch (SWRLRuleEngineException e) {
			e.printStackTrace();
		}  // Create using normal Protege-OWL mechanisms.	
		
	}
	
	/**
	 * setAnActionGoalToPerform: Select the action to perform, first it takes HealingActions, 
	 * if there weren't HealingActions it takes TestActions
	 */
	private void setAnActionGoalToPerform() {
		float expectedBenefit = 0;
		String diagnosisID = (String)getParameter("diagnosisID").getValue();
		Hypothesis hypothesis = selectMoreLikelyHypothesis();
		Failure fail = hypothesis.getRepresentsPossibleFailure();
		@SuppressWarnings("unchecked")
		Set<Action> action = fail.getCanBeRepairedWith();
		Diagnosis diagnosis = getDiagnosis(diagnosisID);
		@SuppressWarnings("unchecked")
		Set<Action> t = diagnosis.getHasPossibleActionsToPerform();
			if(action != null){
				for(Action act : action){
					System.out.println("Accion ejecutada " + act.getName());
					return;
				}
			}
		
		getLogger().info("NO HAY HEALING ACTIONS REALIZAMOS TESTACTIONS");
		Set<TestAction> testActions = myFactory.getAllTestActionInstances();
		getLogger().info(testActions.toString());
		//Select the Highest expected benefit
		for(Action test : t){
			if(test instanceof TestAction){
				getLogger().info("ACCION: " + test.getName());
				float eb = ((TestAction) test).getExpectedBenefit();
				if(eb >= expectedBenefit){
					expectedBenefit = eb;
				}
			}
			
		}
		//Select the test action with the highest expected benefit
		for(Action perform : t){		
			if(((TestAction) perform).getExpectedBenefit() == expectedBenefit){
				System.out.println("Intentamos la accion " + perform.getName());
				if(!perform.hasAvailable()){
					perform.setAvailable(false);
				}else{
					perform.setAvailable(true);
				}
				if(!perform.getAvailable()){					
					System.out.println("Accion no conseguida");
					IGoal monitorize = createGoal("monitorize");
					dispatchSubgoalAndWait(monitorize);				
				    cpu = (Double) getBeliefbase().getBelief("freeCpu").getFact();
				    memory = (Double) getBeliefbase().getBelief("freeMemory").getFact();
				    getLogger().info("CPU " + cpu + " MEMORIA " + memory);
				    IGoal goal = createGoal("make_diagnosis_loop_goal");
					goal.getParameter("diagnosisID").setValue(diagnosis.getId());
					getLogger().info("Starting a diagnosis loop"); 
					dispatchSubgoalAndWait(goal);
				}else{
					System.out.println("ACCION " + perform.getName() + " REALIZADA SATISFACTORIAMENTE");
				}
				
				return;
			}
			getLogger().info("ACCION " + perform.getName() + " NO VALIDA REALIZAMOS LA SIGUIENTE");
		}							
	}
	
	
}
