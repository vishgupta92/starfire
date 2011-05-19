package es.upm.dit.gsi.commune.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Actor
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public interface Actor extends OWLIndividual {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#canPerform

    Collection getCanPerform();

    RDFProperty getCanPerformProperty();

    boolean hasCanPerform();

    Iterator listCanPerform();

    void addCanPerform(Action newCanPerform);

    void removeCanPerform(Action oldCanPerform);

    void setCanPerform(Collection newCanPerform);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasPerformed

    Collection getHasPerformed();

    RDFProperty getHasPerformedProperty();

    boolean hasHasPerformed();

    Iterator listHasPerformed();

    void addHasPerformed(PerformedAction newHasPerformed);

    void removeHasPerformed(PerformedAction oldHasPerformed);

    void setHasPerformed(Collection newHasPerformed);
}
