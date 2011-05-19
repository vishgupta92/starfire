package es.upm.dit.gsi.commune.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/P2PDiagnosis.owl#DetectedJitter
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public interface DetectedJitter extends Symptom {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#possibleResults

    Collection getPossibleResults();

    RDFProperty getPossibleResultsProperty();

    boolean hasPossibleResults();

    Iterator listPossibleResults();

    void addPossibleResults(String newPossibleResults);

    void removePossibleResults(String oldPossibleResults);

    void setPossibleResults(Collection newPossibleResults);
}