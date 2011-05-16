package es.upm.dit.gsi.commune.diagnosisAgent;

import javaOntology.Diagnosis;
import javaOntology.Symptom;
import jadex.bdi.runtime.Plan;

public class SymptomManagerPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8174299593349854500L;

	@Override
	public void body() {
		// TODO Auto-generated method stub
		
		//Plan sensible a síntomas
		//En el de sintomas creo un nuevo diagnostico
		//Extraigo observaciones
		
		//Obtengo el síntoma
		Symptom symptom = getSymptom();
			
		//Creo el diagnóstico comenzado por este síntoma
		Diagnosis diagnosis = createDiagnosis(symptom);
		
		//Actualiza la ontología con el nuevo diagnóstico y el síntoma recibido
		updateOntology(diagnosis,symptom);

		//Lanza DiagnosisLoopPlan
		throwDiagnosisLoopPlan();
	}

	private Symptom getSymptom() {
		// TODO Auto-generated method stub
		return null;
	}

	private Diagnosis createDiagnosis(Symptom symptom) {
		// TODO Auto-generated method stub
		
		//Creo el diagnóstico comenzado por el síntoma
			//Diagnosis diagnosis = new Diagnosis(...);
		//Pongo la relación entre el diagnóstico y el síntoma de empezado por
		
		return null;
	}
	
	private void updateOntology(Diagnosis diagnosis, Symptom symptom) {
		// TODO Auto-generated method stub
		
	}
	
	private void throwDiagnosisLoopPlan() {
		// TODO Auto-generated method stub
		
	}
}
