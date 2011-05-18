package es.upm.dit.gsi.starfire.diagnosisAgent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import edu.stanford.smi.protegex.owl.model.OWLModel;
import communeOntology.Diagnosis;
import communeOntology.EnvironmentAction;
import communeOntology.MyFactory;
import communeOntology.Symptom;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

public class SymptomManagerPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8174299593349854500L;
	private MyFactory myFactory;

	@Override
	public void body() {
		
		OWLModel owlModel = (OWLModel) getBeliefbase().getBelief("ontology").getFact();
		myFactory = new MyFactory(owlModel);
		
		//Plan sensible a síntomas
		//En el de sintomas creo un nuevo diagnostico
		//Extraigo observaciones
		
		//Obtengo el síntoma
		Symptom symptom = (Symptom)getParameter("symptom").getValue();
			
		//Creo el diagnóstico comenzado por este síntoma
		Diagnosis diagnosis = createDiagnosis(symptom);
				
		//Actualiza la ontología con el nuevo diagnóstico y el síntoma recibido
		updateOntology(diagnosis,symptom);
		
		//Crear los individuos de todas las acciones
		createActionIndividuals(diagnosis);

//		//Lanza DiagnosisLoopPlan
//		//poner el id del diagnóstico en el parámetro
//		throwDiagnosisLoopPlan(diagnosis);
		
		//Generar evento interno con la observation
	}

	private Diagnosis createDiagnosis(Symptom symptom) {				
		//Creo el diagnóstico comenzado por el síntoma
		Diagnosis diagnosis = myFactory.createDiagnosis(null);
		
		//poner identificador al diagnóstico con timestamp, nombre del agente, nombre de la máquina
		try {
			diagnosis.setId((new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())).toString()
					+":"+getComponentName()+":"+InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return diagnosis;
	}
	
	private void updateOntology(Diagnosis diagnosis, Symptom symptom) {
		//Pongo la relación entre el diagnóstico y el síntoma de empezado por
		Set<Symptom> symptoms = new HashSet<Symptom>();
		symptoms.add(symptom);
		diagnosis.setStartedBySymptom(symptoms);
		Set<Diagnosis> diagnoses = new HashSet<Diagnosis>();
		diagnoses.add(diagnosis);
		symptom.setStartsDiagnosis(diagnoses);
		//Debo crear ahora el observation del tipo RTPMonitoringActionObservation y definir sus relaciones con síntoma
		
		//Debe retornar una observation para lanzare el evento interno
	}
	
	private void createActionIndividuals(Diagnosis diagnosis) {
		Set<EnvironmentAction> environmentActions = new HashSet<EnvironmentAction>();
		//TODO: Crear dinámicamente un individuo de cada tipo de acción
		EnvironmentAction ea1 = myFactory.createRTPMonitoringAction(null);
		EnvironmentAction ea2 = myFactory.createConnectivityTestAction(null);
		EnvironmentAction ea3 = myFactory.createNetworkInterfaceRateTest(null);
		EnvironmentAction ea4 = myFactory.createUsageCPUTest(null);
		EnvironmentAction ea5 = myFactory.createUsageMemoryTest(null);
		ea1.setBelongsToDiagnosis(diagnosis);
		ea2.setBelongsToDiagnosis(diagnosis);
		ea3.setBelongsToDiagnosis(diagnosis);
		ea4.setBelongsToDiagnosis(diagnosis);
		ea5.setBelongsToDiagnosis(diagnosis);
		environmentActions.add(ea1);
		environmentActions.add(ea2);
		environmentActions.add(ea3);
		environmentActions.add(ea4);
		environmentActions.add(ea5);
		diagnosis.setHasPossibleActionsToPerform(environmentActions);
	}
	
	private void throwDiagnosisLoopPlan(Diagnosis diagnosis) {
		IGoal goal = createGoal("make_diagnosis_loop_goal");
		goal.getParameter("diagnosisID").setValue(diagnosis.getId());
		getLogger().info("Starting a diagnosis loop"); 
		dispatchSubgoalAndWait(goal);
	}
}
