package es.upm.dit.gsi.ontology.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import es.upm.dit.gsi.ontology.CategoricalRVState;
import es.upm.dit.gsi.ontology.MetaEntity;
import es.upm.dit.gsi.ontology.Resident;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#CategoricalRVState
 *
 * @version generated on Mon Jun 27 12:29:14 CEST 2011
 */
public class DefaultCategoricalRVState extends DefaultEntity
         implements CategoricalRVState {

    public DefaultCategoricalRVState(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultCategoricalRVState() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasType

    public MetaEntity getHasType() {
        return (MetaEntity) getPropertyValueAs(getHasTypeProperty(), MetaEntity.class);
    }


    public RDFProperty getHasTypeProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasType";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasType() {
        return getPropertyValueCount(getHasTypeProperty()) > 0;
    }


    public void setHasType(MetaEntity newHasType) {
        setPropertyValue(getHasTypeProperty(), newHasType);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isGloballyExclusive

    public Set getIsGloballyExclusive() {
        return new HashSet(getPropertyValuesAs(getIsGloballyExclusiveProperty(), Resident.class));
    }


    public RDFProperty getIsGloballyExclusiveProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isGloballyExclusive";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsGloballyExclusive() {
        return getPropertyValueCount(getIsGloballyExclusiveProperty()) > 0;
    }


    public Iterator listIsGloballyExclusive() {
        return listPropertyValuesAs(getIsGloballyExclusiveProperty(), Resident.class);
    }


    public void addIsGloballyExclusive(Resident newIsGloballyExclusive) {
        addPropertyValue(getIsGloballyExclusiveProperty(), newIsGloballyExclusive);
    }


    public void removeIsGloballyExclusive(Resident oldIsGloballyExclusive) {
        removePropertyValue(getIsGloballyExclusiveProperty(), oldIsGloballyExclusive);
    }


    public void setIsGloballyExclusive(Set newIsGloballyExclusive) {
        setPropertyValues(getIsGloballyExclusiveProperty(), newIsGloballyExclusive);
    }
}