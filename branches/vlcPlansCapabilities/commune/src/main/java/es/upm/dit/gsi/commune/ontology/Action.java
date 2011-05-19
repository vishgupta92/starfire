package es.upm.dit.gsi.commune.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Action
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public interface Action extends OWLIndividual {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#available

    boolean getAvailable();

    RDFProperty getAvailableProperty();

    boolean hasAvailable();

    void setAvailable(boolean newAvailable);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#canBePerformedBy

    Collection getCanBePerformedBy();

    RDFProperty getCanBePerformedByProperty();

    boolean hasCanBePerformedBy();

    Iterator listCanBePerformedBy();

    void addCanBePerformedBy(Actor newCanBePerformedBy);

    void removeCanBePerformedBy(Actor oldCanBePerformedBy);

    void setCanBePerformedBy(Collection newCanBePerformedBy);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasCondition

    Collection getHasCondition();

    RDFProperty getHasConditionProperty();

    boolean hasHasCondition();

    Iterator listHasCondition();

    void addHasCondition(Condition newHasCondition);

    void removeHasCondition(Condition oldHasCondition);

    void setHasCondition(Collection newHasCondition);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasPostcondition

    Collection getHasPostcondition();

    RDFProperty getHasPostconditionProperty();

    boolean hasHasPostcondition();

    Iterator listHasPostcondition();

    void addHasPostcondition(Object newHasPostcondition);

    void removeHasPostcondition(Object oldHasPostcondition);

    void setHasPostcondition(Collection newHasPostcondition);

    // Property http://www.owl-ontologies.com/Diagnosis.owl#performed

    boolean getPerformed();

    RDFProperty getPerformedProperty();

    boolean hasPerformed();

    void setPerformed(boolean newPerformed);
}
