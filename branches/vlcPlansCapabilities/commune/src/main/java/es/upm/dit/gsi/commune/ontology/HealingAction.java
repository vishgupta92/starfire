package es.upm.dit.gsi.commune.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#HealingAction
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public interface HealingAction extends EnvironmentAction {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#repairsFailure

    Collection getRepairsFailure();

    RDFProperty getRepairsFailureProperty();

    boolean hasRepairsFailure();

    Iterator listRepairsFailure();

    void addRepairsFailure(Failure newRepairsFailure);

    void removeRepairsFailure(Failure oldRepairsFailure);

    void setRepairsFailure(Collection newRepairsFailure);
}
