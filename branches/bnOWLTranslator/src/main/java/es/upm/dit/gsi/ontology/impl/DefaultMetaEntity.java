package es.upm.dit.gsi.ontology.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import es.upm.dit.gsi.ontology.Entity;
import es.upm.dit.gsi.ontology.MetaEntity;
import es.upm.dit.gsi.ontology.OVariable;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#MetaEntity
 *
 * @version generated on Mon Jun 27 12:29:14 CEST 2011
 */
public class DefaultMetaEntity extends DefaultEntity
         implements MetaEntity {

    public DefaultMetaEntity(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultMetaEntity() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isTypeOf

    public Set getIsTypeOf() {
        return new HashSet(getPropertyValuesAs(getIsTypeOfProperty(), Entity.class));
    }


    public RDFProperty getIsTypeOfProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isTypeOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsTypeOf() {
        return getPropertyValueCount(getIsTypeOfProperty()) > 0;
    }


    public Iterator listIsTypeOf() {
        return listPropertyValuesAs(getIsTypeOfProperty(), Entity.class);
    }


    public void addIsTypeOf(Entity newIsTypeOf) {
        addPropertyValue(getIsTypeOfProperty(), newIsTypeOf);
    }


    public void removeIsTypeOf(Entity oldIsTypeOf) {
        removePropertyValue(getIsTypeOfProperty(), oldIsTypeOf);
    }


    public void setIsTypeOf(Set newIsTypeOf) {
        setPropertyValues(getIsTypeOfProperty(), newIsTypeOf);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#subsOVar

    public Set getSubsOVar() {
        return new HashSet(getPropertyValuesAs(getSubsOVarProperty(), OVariable.class));
    }


    public RDFProperty getSubsOVarProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#subsOVar";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasSubsOVar() {
        return getPropertyValueCount(getSubsOVarProperty()) > 0;
    }


    public Iterator listSubsOVar() {
        return listPropertyValuesAs(getSubsOVarProperty(), OVariable.class);
    }


    public void addSubsOVar(OVariable newSubsOVar) {
        addPropertyValue(getSubsOVarProperty(), newSubsOVar);
    }


    public void removeSubsOVar(OVariable oldSubsOVar) {
        removePropertyValue(getSubsOVarProperty(), oldSubsOVar);
    }


    public void setSubsOVar(Set newSubsOVar) {
        setPropertyValues(getSubsOVarProperty(), newSubsOVar);
    }
}