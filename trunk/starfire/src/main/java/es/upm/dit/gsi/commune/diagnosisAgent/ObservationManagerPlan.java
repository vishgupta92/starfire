package es.upm.dit.gsi.commune.diagnosisAgent;

import javaOntology.Observation;
import jadex.bdi.runtime.Plan;

public class ObservationManagerPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1757589675344064937L;

	@Override
	public void body() {
		// TODO Auto-generated method stub
		
		//Plan sensible a observaciones resultado de acciones de test realizadas
		//Extraigo observaciones
		
		//Obtengo la obsservación 
		Observation observation = getObservation();
		
		//Actualiza la ontología con el síntoma recibido
		updateOntology(observation);

		//Lanza DiagnosisLoopPlan
		throwDiagnosisLoopPlan();
	}

	private Observation getObservation() {
		// TODO Auto-generated method stub
		return null;
	}

	private void updateOntology(Observation observation) {
		// TODO Auto-generated method stub
		
	}
	
	private void throwDiagnosisLoopPlan() {
		// TODO Auto-generated method stub
		
	}
}
