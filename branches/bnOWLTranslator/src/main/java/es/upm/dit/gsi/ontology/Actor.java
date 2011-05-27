package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Actor
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface Actor extends OWLIndividual {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#canPerform

    Set getCanPerform();

    RDFProperty getCanPerformProperty();

    boolean hasCanPerform();

    Iterator listCanPerform();

    void addCanPerform(Action newCanPerform);

    void removeCanPerform(Action oldCanPerform);

    void setCanPerform(Set newCanPerform);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasPerformed

    Set getHasPerformed();

    RDFProperty getHasPerformedProperty();

    boolean hasHasPerformed();

    Iterator listHasPerformed();

    void addHasPerformed(PerformedAction newHasPerformed);

    void removeHasPerformed(PerformedAction oldHasPerformed);

    void setHasPerformed(Set newHasPerformed);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#id

    String getId();

    RDFProperty getIdProperty();

    boolean hasId();

    void setId(String newId);
}
