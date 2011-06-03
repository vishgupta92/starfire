package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Failure
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface Failure extends OWLIndividual {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#canBeRepairedWith

    Set getCanBeRepairedWith();

    RDFProperty getCanBeRepairedWithProperty();

    boolean hasCanBeRepairedWith();

    Iterator listCanBeRepairedWith();

    void addCanBeRepairedWith(HealingAction newCanBeRepairedWith);

    void removeCanBeRepairedWith(HealingAction oldCanBeRepairedWith);

    void setCanBeRepairedWith(Set newCanBeRepairedWith);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasLocation

    System getHasLocation();

    RDFProperty getHasLocationProperty();

    boolean hasHasLocation();

    void setHasLocation(System newHasLocation);
}