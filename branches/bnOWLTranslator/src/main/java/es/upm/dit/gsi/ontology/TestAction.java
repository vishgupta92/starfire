package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#TestAction
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface TestAction extends EnvironmentAction {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#expectedBenefit

    float getExpectedBenefit();

    RDFProperty getExpectedBenefitProperty();

    boolean hasExpectedBenefit();

    void setExpectedBenefit(float newExpectedBenefit);


    // Property http://www.owl-ontologies.com/BayesianDiagnosis.owl#hasBayesianObservation

    Set getHasBayesianObservation();

    RDFProperty getHasBayesianObservationProperty();

    boolean hasHasBayesianObservation();

    Iterator listHasBayesianObservation();

    void addHasBayesianObservation(ObservationNode newHasBayesianObservation);

    void removeHasBayesianObservation(ObservationNode oldHasBayesianObservation);

    void setHasBayesianObservation(Set newHasBayesianObservation);
}