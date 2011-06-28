package es.upm.dit.gsi.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import es.upm.dit.gsi.ontology.Node;
import es.upm.dit.gsi.ontology.OVariable;
import es.upm.dit.gsi.ontology.SimpleArgRelationship;
/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#SimpleArgRelationship
 *
 * @version generated on Mon Jun 27 12:29:14 CEST 2011
 */
public class DefaultSimpleArgRelationship extends DefaultArgRelationship
         implements SimpleArgRelationship {

    public DefaultSimpleArgRelationship(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultSimpleArgRelationship() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasArgTerm

    public OVariable getHasArgTerm() {
        return (OVariable) getPropertyValueAs(getHasArgTermProperty(), OVariable.class);
    }


    public RDFProperty getHasArgTermProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasArgTerm";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasArgTerm() {
        return getPropertyValueCount(getHasArgTermProperty()) > 0;
    }


    public void setHasArgTerm(OVariable newHasArgTerm) {
        setPropertyValue(getHasArgTermProperty(), newHasArgTerm);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isArgumentOf

    public Node getIsArgumentOf() {
        return (Node) getPropertyValueAs(getIsArgumentOfProperty(), Node.class);
    }


    public RDFProperty getIsArgumentOfProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isArgumentOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsArgumentOf() {
        return getPropertyValueCount(getIsArgumentOfProperty()) > 0;
    }


    public void setIsArgumentOf(Node newIsArgumentOf) {
        setPropertyValue(getIsArgumentOfProperty(), newIsArgumentOf);
    }
}