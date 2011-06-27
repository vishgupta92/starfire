package es.upm.dit.gsi.ontology;
import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#ObjectEntity
 *
 * @version generated on Mon Jun 27 12:29:14 CEST 2011
 */
public interface ObjectEntity extends Entity {

    // Property http://www.pr-owl.org/pr-owl.owl#subsOVar

    Set getSubsOVar();

    RDFProperty getSubsOVarProperty();

    boolean hasSubsOVar();

    Iterator listSubsOVar();

    void addSubsOVar(OVariable newSubsOVar);

    void removeSubsOVar(OVariable oldSubsOVar);

    void setSubsOVar(Set newSubsOVar);
}
