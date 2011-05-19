package es.upm.dit.gsi.commune.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import es.upm.dit.gsi.commune.ontology.RequiredMinimumConfidenceCondition;
import es.upm.dit.gsi.commune.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/P2PDiagnosis.owl#RequiredMinimumConfidenceCondition
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public class DefaultRequiredMinimumConfidenceCondition extends DefaultCondition
         implements RequiredMinimumConfidenceCondition {

    public DefaultRequiredMinimumConfidenceCondition(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultRequiredMinimumConfidenceCondition() {
    }



    // Property http://www.owl-ontologies.com/P2PDiagnosis.owl#threshold

    public float getThreshold() {
        return getPropertyValueLiteral(getThresholdProperty()).getFloat();
    }


    public RDFProperty getThresholdProperty() {
        final String uri = "http://www.owl-ontologies.com/P2PDiagnosis.owl#threshold";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasThreshold() {
        return getPropertyValueCount(getThresholdProperty()) > 0;
    }


    public void setThreshold(float newThreshold) {
        setPropertyValue(getThresholdProperty(), new java.lang.Float(newThreshold));
    }
}