package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#Domain_MFrag
 *
 * @version generated on Fri Apr 01 15:09:42 CEST 2011
 */
public interface Domain_MFrag extends MFrag {

    // Property http://www.pr-owl.org/pr-owl.owl#hasContextNode

    Set getHasContextNode();

    RDFProperty getHasContextNodeProperty();

    boolean hasHasContextNode();

    Iterator listHasContextNode();

    void addHasContextNode(Context newHasContextNode);

    void removeHasContextNode(Context oldHasContextNode);

    void setHasContextNode(Set newHasContextNode);
}