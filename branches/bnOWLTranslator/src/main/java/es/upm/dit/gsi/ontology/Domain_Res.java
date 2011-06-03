package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#Domain_Res
 *
 * @version generated on Fri Apr 01 15:09:42 CEST 2011
 */
public interface Domain_Res extends Resident {

    // Property http://www.pr-owl.org/pr-owl.owl#hasContextInstance

    Set getHasContextInstance();

    RDFProperty getHasContextInstanceProperty();

    boolean hasHasContextInstance();

    Iterator listHasContextInstance();

    void addHasContextInstance(Context newHasContextInstance);

    void removeHasContextInstance(Context oldHasContextInstance);

    void setHasContextInstance(Set newHasContextInstance);


    // Property http://www.pr-owl.org/pr-owl.owl#isParentOf

    Set getIsParentOf();

    RDFProperty getIsParentOfProperty();

    boolean hasIsParentOf();

    Iterator listIsParentOf();

    void addIsParentOf(Domain_Res newIsParentOf);

    void removeIsParentOf(Domain_Res oldIsParentOf);

    void setIsParentOf(Set newIsParentOf);


    // Property http://www.pr-owl.org/pr-owl.owl#isResidentNodeIn

    Set getIsResidentNodeIn();

    RDFProperty getIsResidentNodeInProperty();

    boolean hasIsResidentNodeIn();

    Iterator listIsResidentNodeIn();

    void addIsResidentNodeIn(Domain_MFrag newIsResidentNodeIn);

    void removeIsResidentNodeIn(Domain_MFrag oldIsResidentNodeIn);

    void setIsResidentNodeIn(Set newIsResidentNodeIn);
}