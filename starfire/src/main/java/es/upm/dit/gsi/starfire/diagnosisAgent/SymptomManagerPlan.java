package es.upm.dit.gsi.starfire.diagnosisAgent;

import jadex.bdi.runtime.IInternalEvent;
import jadex.bdi.runtime.Plan;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import communeOntology.ConnectivityTestAction;
import communeOntology.Diagnosis;
import communeOntology.EnvironmentAction;
import communeOntology.MyFactory;
import communeOntology.NetworkInterfaceRateTest;
import communeOntology.Observation;
import communeOntology.Symptom;
import communeOntology.UsageCPUTest;
import communeOntology.UsageMemoryTest;

import edu.stanford.smi.protegex.owl.model.OWLModel;

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
		Observation observation = updateOntology(diagnosis,symptom);
		
		//Crear los individuos de todas las acciones
		createActionIndividuals(diagnosis);
		
		//Generar evento interno con la observation
		throwInternalEvent(observation);
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
	
	private Observation updateOntology(Diagnosis diagnosis, Symptom symptom) {
		//Pongo la relación entre el diagnóstico y el síntoma de empezado por
		Set<Symptom> symptoms = new HashSet<Symptom>();
		symptoms.add(symptom);
		diagnosis.setStartedBySymptom(symptoms);
		Set<Diagnosis> diagnoses = new HashSet<Diagnosis>();
		diagnoses.add(diagnosis);
		symptom.setStartsDiagnosis(diagnoses);
		//Debo crear ahora el observation del tipo RTPMonitoringActionObservation y definir sus relaciones con síntoma
		Observation observation = myFactory.createRTPMonitoringActionObservation(null);
		observation.setObservationType("RTPMonitoringAction");
		symptom.setObservationResult(observation);
		return observation;
	}
	
	private void createActionIndividuals(Diagnosis diagnosis) {
		Set<EnvironmentAction> environmentActions = new HashSet<EnvironmentAction>();
		//TODO: Crear dinámicamente un individuo de cada tipo de acción
		//RTPMonitoringAction a1 = myFactory.createRTPMonitoringAction(null);
		//Set<Symptom> symptoms = new HashSet<Symptom>();
		//symptoms.add(symptom);
		//a1.setResultSymptom(symptoms);
		ConnectivityTestAction a2 = myFactory.createConnectivityTestAction(null);
		a2.setHasBayesianNode("ConnectivityTest");
		NetworkInterfaceRateTest a3 = myFactory.createNetworkInterfaceRateTest(null);
		a3.setHasBayesianNode("NetworkInterfaceRateTest");
		UsageCPUTest a4 = myFactory.createUsageCPUTest(null);
		a4.setHasBayesianNode("UsageCPUTest");
		UsageMemoryTest a5 = myFactory.createUsageMemoryTest(null);
		a5.setHasBayesianNode("UsageMemoryTest");
		
		//a1.setBelongsToDiagnosis(diagnosis);
		a2.setBelongsToDiagnosis(diagnosis);
		a3.setBelongsToDiagnosis(diagnosis);
		a4.setBelongsToDiagnosis(diagnosis);
		a5.setBelongsToDiagnosis(diagnosis);
		//environmentActions.add(a1);
		environmentActions.add(a2);
		environmentActions.add(a3);
		environmentActions.add(a4);
		environmentActions.add(a5);
		diagnosis.setHasPossibleActionsToPerform(environmentActions);
	}
	
	private void throwInternalEvent(Observation observation) {
	    IInternalEvent event = createInternalEvent("new_observation");
	    // Setting the content parameter
	    event.getParameter("content").setValue(observation);
	    dispatchInternalEvent(event);
	}
}
