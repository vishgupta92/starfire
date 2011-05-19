package es.upm.dit.gsi.commune.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import es.upm.dit.gsi.commune.ontology.DeviceFailure;
import es.upm.dit.gsi.commune.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/P2PDiagnosis.owl#DeviceFailure
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public class DefaultDeviceFailure extends DefaultFailure
         implements DeviceFailure {

    public DefaultDeviceFailure(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultDeviceFailure() {
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasLocation

    public Device getHasLocation() {
        return (Device) getPropertyValueAs(getHasLocationProperty(), Device.class);
    }


    public RDFProperty getHasLocationProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#hasLocation";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasLocation() {
        return getPropertyValueCount(getHasLocationProperty()) > 0;
    }


    public void setHasLocation(Device newHasLocation) {
        setPropertyValue(getHasLocationProperty(), newHasLocation);
    }
}