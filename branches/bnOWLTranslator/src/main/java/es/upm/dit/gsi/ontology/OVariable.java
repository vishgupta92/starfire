package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#OVariable
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface OVariable extends OWLIndividual {

    // Property http://www.pr-owl.org/pr-owl.owl#isArgTermIn

    Set getIsArgTermIn();

    RDFProperty getIsArgTermInProperty();

    boolean hasIsArgTermIn();

    Iterator listIsArgTermIn();

    void addIsArgTermIn(ArgRelationship newIsArgTermIn);

    void removeIsArgTermIn(ArgRelationship oldIsArgTermIn);

    void setIsArgTermIn(Set newIsArgTermIn);


    // Property http://www.pr-owl.org/pr-owl.owl#isOVariableIn

    MFrag getIsOVariableIn();

    RDFProperty getIsOVariableInProperty();

    boolean hasIsOVariableIn();

    void setIsOVariableIn(MFrag newIsOVariableIn);


    // Property http://www.pr-owl.org/pr-owl.owl#isRepBySkolem

    Set getIsRepBySkolem();

    RDFProperty getIsRepBySkolemProperty();

    boolean hasIsRepBySkolem();

    Iterator listIsRepBySkolem();

    void addIsRepBySkolem(Exemplar newIsRepBySkolem);

    void removeIsRepBySkolem(Exemplar oldIsRepBySkolem);

    void setIsRepBySkolem(Set newIsRepBySkolem);


    // Property http://www.pr-owl.org/pr-owl.owl#isSubsBy

    MetaEntity getIsSubsBy();

    RDFProperty getIsSubsByProperty();

    boolean hasIsSubsBy();

    void setIsSubsBy(MetaEntity newIsSubsBy);


    // Property http://www.pr-owl.org/pr-owl.owl#representsOVar

    Exemplar getRepresentsOVar();

    RDFProperty getRepresentsOVarProperty();

    boolean hasRepresentsOVar();

    void setRepresentsOVar(Exemplar newRepresentsOVar);
}
