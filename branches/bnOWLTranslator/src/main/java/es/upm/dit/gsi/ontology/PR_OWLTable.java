package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#PR-OWLTable
 *
 * @version generated on Fri Apr 01 15:09:42 CEST 2011
 */
public interface PR_OWLTable extends ProbDist {

    // Property http://www.pr-owl.org/pr-owl.owl#hasProbAssign

    Set getHasProbAssign();

    RDFProperty getHasProbAssignProperty();

    boolean hasHasProbAssign();

    Iterator listHasProbAssign();

    void addHasProbAssign(ProbAssign newHasProbAssign);

    void removeHasProbAssign(ProbAssign oldHasProbAssign);

    void setHasProbAssign(Set newHasProbAssign);


    // Property http://www.pr-owl.org/pr-owl.owl#isProbDistOf

    Resident getIsProbDistOf();

    RDFProperty getIsProbDistOfProperty();

    boolean hasIsProbDistOf();

    void setIsProbDistOf(Resident newIsProbDistOf);
}
