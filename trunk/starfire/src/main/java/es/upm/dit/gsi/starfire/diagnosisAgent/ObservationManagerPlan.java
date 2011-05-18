package es.upm.dit.gsi.starfire.diagnosisAgent;

import java.util.Set;

import communeOntology.Diagnosis;
import communeOntology.MyFactory;
import communeOntology.Observation;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;

public class ObservationManagerPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1757589675344064937L;
	private MyFactory myFactory;

	@Override
	public void body() {
		
		OWLModel owlModel = (OWLModel) getBeliefbase().getBelief("ontology").getFact();
		myFactory = new MyFactory(owlModel);
		
		//Plan sensible a observaciones resultado de acciones de test realizadas
		//Extraigo observaciones
		
		//Obtengo la observación 
		Observation observation = (Observation)getParameter("observation").getValue();
		
		//Actualiza la ontología con la observación recibida
		updateOntology(observation);
		
		String diagnosisID = (String)getParameter("diagnosisID").getValue();
		Diagnosis diagnosis = getDiagnosis(diagnosisID);

		//Lanza DiagnosisLoopPlan
		//poner el id del diagnóstico en el parámetro
		throwDiagnosisLoopPlan(diagnosis);
	}

	private void updateOntology(Observation observation) {
		// TODO Auto-generated method stub
		//enganchar donde sea: antes o después
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
	
	private void throwDiagnosisLoopPlan(Diagnosis diagnosis) {
		IGoal goal = createGoal("make_diagnosis_loop_goal");
		goal.getParameter("diagnosisID").setValue(diagnosis.getId());
		getLogger().info("Starting a diagnosis loop"); 
		dispatchSubgoalAndWait(goal);
	}
}
