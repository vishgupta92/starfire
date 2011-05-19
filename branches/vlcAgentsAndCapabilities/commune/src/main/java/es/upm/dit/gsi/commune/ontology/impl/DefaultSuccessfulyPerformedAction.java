package es.upm.dit.gsi.commune.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import es.upm.dit.gsi.commune.ontology.SuccessfulyPerformedAction;
import es.upm.dit.gsi.commune.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#SuccessfulyPerformedAction
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public class DefaultSuccessfulyPerformedAction extends DefaultPerformedAction
         implements SuccessfulyPerformedAction {

    public DefaultSuccessfulyPerformedAction(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultSuccessfulyPerformedAction() {
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#successfullyPerformed

    public boolean getSuccessfullyPerformed() {
        return getPropertyValueLiteral(getSuccessfullyPerformedProperty()).getBoolean();
    }


    public RDFProperty getSuccessfullyPerformedProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#successfullyPerformed";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasSuccessfullyPerformed() {
        return getPropertyValueCount(getSuccessfullyPerformedProperty()) > 0;
    }


    public void setSuccessfullyPerformed(boolean newSuccessfullyPerformed) {
        setPropertyValue(getSuccessfullyPerformedProperty(), new java.lang.Boolean(newSuccessfullyPerformed));
    }
}