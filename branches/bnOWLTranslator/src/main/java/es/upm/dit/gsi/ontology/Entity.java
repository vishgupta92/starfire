package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#Entity
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface Entity extends OWLIndividual {

    // Property http://www.pr-owl.org/pr-owl.owl#hasType

    MetaEntity getHasType();

    RDFProperty getHasTypeProperty();

    boolean hasHasType();

    void setHasType(MetaEntity newHasType);


    // Property http://www.pr-owl.org/pr-owl.owl#hasUID

    String getHasUID();

    RDFProperty getHasUIDProperty();

    boolean hasHasUID();

    void setHasUID(String newHasUID);


    // Property http://www.pr-owl.org/pr-owl.owl#isArgTermIn

    Set getIsArgTermIn();

    RDFProperty getIsArgTermInProperty();

    boolean hasIsArgTermIn();

    Iterator listIsArgTermIn();

    void addIsArgTermIn(ArgRelationship newIsArgTermIn);

    void removeIsArgTermIn(ArgRelationship oldIsArgTermIn);

    void setIsArgTermIn(Set newIsArgTermIn);


    // Property http://www.pr-owl.org/pr-owl.owl#isConditionantOf

    Set getIsConditionantOf();

    RDFProperty getIsConditionantOfProperty();

    boolean hasIsConditionantOf();

    Iterator listIsConditionantOf();

    void addIsConditionantOf(ProbAssign newIsConditionantOf);

    void removeIsConditionantOf(ProbAssign oldIsConditionantOf);

    void setIsConditionantOf(Set newIsConditionantOf);


    // Property http://www.pr-owl.org/pr-owl.owl#isPossibleValueOf

    Set getIsPossibleValueOf();

    RDFProperty getIsPossibleValueOfProperty();

    boolean hasIsPossibleValueOf();

    Iterator listIsPossibleValueOf();

    void addIsPossibleValueOf(Object newIsPossibleValueOf);

    void removeIsPossibleValueOf(Object oldIsPossibleValueOf);

    void setIsPossibleValueOf(Set newIsPossibleValueOf);
}
