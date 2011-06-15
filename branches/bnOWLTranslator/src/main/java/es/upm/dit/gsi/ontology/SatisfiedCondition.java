package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#SatisfiedCondition
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface SatisfiedCondition extends Condition {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#satisfied

    boolean getSatisfied();

    RDFProperty getSatisfiedProperty();

    boolean hasSatisfied();

    void setSatisfied(boolean newSatisfied);
}