package es.upm.dit.gsi.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import java.util.*;

import es.upm.dit.gsi.ontology.OVariable;
import es.upm.dit.gsi.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#OVariable
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public class DefaultOVariable extends AbstractCodeGeneratorIndividual
         implements OVariable {

    public DefaultOVariable(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultOVariable() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isArgTermIn

    public Set getIsArgTermIn() {
        return new HashSet(getPropertyValuesAs(getIsArgTermInProperty(), ArgRelationship.class));
    }


    public RDFProperty getIsArgTermInProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isArgTermIn";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsArgTermIn() {
        return getPropertyValueCount(getIsArgTermInProperty()) > 0;
    }


    public Iterator listIsArgTermIn() {
        return listPropertyValuesAs(getIsArgTermInProperty(), ArgRelationship.class);
    }


    public void addIsArgTermIn(ArgRelationship newIsArgTermIn) {
        addPropertyValue(getIsArgTermInProperty(), newIsArgTermIn);
    }


    public void removeIsArgTermIn(ArgRelationship oldIsArgTermIn) {
        removePropertyValue(getIsArgTermInProperty(), oldIsArgTermIn);
    }


    public void setIsArgTermIn(Set newIsArgTermIn) {
        setPropertyValues(getIsArgTermInProperty(), newIsArgTermIn);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isOVariableIn

    public MFrag getIsOVariableIn() {
        return (MFrag) getPropertyValueAs(getIsOVariableInProperty(), MFrag.class);
    }


    public RDFProperty getIsOVariableInProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isOVariableIn";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsOVariableIn() {
        return getPropertyValueCount(getIsOVariableInProperty()) > 0;
    }


    public void setIsOVariableIn(MFrag newIsOVariableIn) {
        setPropertyValue(getIsOVariableInProperty(), newIsOVariableIn);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isRepBySkolem

    public Set getIsRepBySkolem() {
        return new HashSet(getPropertyValuesAs(getIsRepBySkolemProperty(), Exemplar.class));
    }


    public RDFProperty getIsRepBySkolemProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isRepBySkolem";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsRepBySkolem() {
        return getPropertyValueCount(getIsRepBySkolemProperty()) > 0;
    }


    public Iterator listIsRepBySkolem() {
        return listPropertyValuesAs(getIsRepBySkolemProperty(), Exemplar.class);
    }


    public void addIsRepBySkolem(Exemplar newIsRepBySkolem) {
        addPropertyValue(getIsRepBySkolemProperty(), newIsRepBySkolem);
    }


    public void removeIsRepBySkolem(Exemplar oldIsRepBySkolem) {
        removePropertyValue(getIsRepBySkolemProperty(), oldIsRepBySkolem);
    }


    public void setIsRepBySkolem(Set newIsRepBySkolem) {
        setPropertyValues(getIsRepBySkolemProperty(), newIsRepBySkolem);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isSubsBy

    public MetaEntity getIsSubsBy() {
        return (MetaEntity) getPropertyValueAs(getIsSubsByProperty(), MetaEntity.class);
    }


    public RDFProperty getIsSubsByProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isSubsBy";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsSubsBy() {
        return getPropertyValueCount(getIsSubsByProperty()) > 0;
    }


    public void setIsSubsBy(MetaEntity newIsSubsBy) {
        setPropertyValue(getIsSubsByProperty(), newIsSubsBy);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#representsOVar

    public Exemplar getRepresentsOVar() {
        return (Exemplar) getPropertyValueAs(getRepresentsOVarProperty(), Exemplar.class);
    }


    public RDFProperty getRepresentsOVarProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#representsOVar";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasRepresentsOVar() {
        return getPropertyValueCount(getRepresentsOVarProperty()) > 0;
    }


    public void setRepresentsOVar(Exemplar newRepresentsOVar) {
        setPropertyValue(getRepresentsOVarProperty(), newRepresentsOVar);
    }
}