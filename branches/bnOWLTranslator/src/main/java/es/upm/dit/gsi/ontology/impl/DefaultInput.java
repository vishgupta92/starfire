package es.upm.dit.gsi.ontology.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import es.upm.dit.gsi.ontology.Input;
import es.upm.dit.gsi.ontology.MFrag;
import es.upm.dit.gsi.ontology.Resident;
import es.upm.dit.gsi.ontology.SimpleArgRelationship;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#Input
 *
 * @version generated on Mon Jun 27 12:29:14 CEST 2011
 */
public class DefaultInput extends DefaultNode
         implements Input {

    public DefaultInput(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultInput() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasArgument

    public Set getHasArgument() {
        return new HashSet(getPropertyValuesAs(getHasArgumentProperty(), SimpleArgRelationship.class));
    }


    public RDFProperty getHasArgumentProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasArgument";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasArgument() {
        return getPropertyValueCount(getHasArgumentProperty()) > 0;
    }


    public Iterator listHasArgument() {
        return listPropertyValuesAs(getHasArgumentProperty(), SimpleArgRelationship.class);
    }


    public void addHasArgument(SimpleArgRelationship newHasArgument) {
        addPropertyValue(getHasArgumentProperty(), newHasArgument);
    }


    public void removeHasArgument(SimpleArgRelationship oldHasArgument) {
        removePropertyValue(getHasArgumentProperty(), oldHasArgument);
    }


    public void setHasArgument(Set newHasArgument) {
        setPropertyValues(getHasArgumentProperty(), newHasArgument);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasInnerTerm

    public Set getHasInnerTerm() {
        return new HashSet(getPropertyValuesAs(getHasInnerTermProperty(), Input.class));
    }


    public RDFProperty getHasInnerTermProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasInnerTerm";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasInnerTerm() {
        return getPropertyValueCount(getHasInnerTermProperty()) > 0;
    }


    public Iterator listHasInnerTerm() {
        return listPropertyValuesAs(getHasInnerTermProperty(), Input.class);
    }


    public void addHasInnerTerm(Input newHasInnerTerm) {
        addPropertyValue(getHasInnerTermProperty(), newHasInnerTerm);
    }


    public void removeHasInnerTerm(Input oldHasInnerTerm) {
        removePropertyValue(getHasInnerTermProperty(), oldHasInnerTerm);
    }


    public void setHasInnerTerm(Set newHasInnerTerm) {
        setPropertyValues(getHasInnerTermProperty(), newHasInnerTerm);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isInputInstanceOf

    public Object getIsInputInstanceOf() {
        return (Object) getPropertyValue(getIsInputInstanceOfProperty());
    }


    public RDFProperty getIsInputInstanceOfProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isInputInstanceOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsInputInstanceOf() {
        return getPropertyValueCount(getIsInputInstanceOfProperty()) > 0;
    }


    public void setIsInputInstanceOf(Object newIsInputInstanceOf) {
        setPropertyValue(getIsInputInstanceOfProperty(), newIsInputInstanceOf);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isInputNodeIn

    public Set getIsInputNodeIn() {
        return new HashSet(getPropertyValuesAs(getIsInputNodeInProperty(), MFrag.class));
    }


    public RDFProperty getIsInputNodeInProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isInputNodeIn";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsInputNodeIn() {
        return getPropertyValueCount(getIsInputNodeInProperty()) > 0;
    }


    public Iterator listIsInputNodeIn() {
        return listPropertyValuesAs(getIsInputNodeInProperty(), MFrag.class);
    }


    public void addIsInputNodeIn(MFrag newIsInputNodeIn) {
        addPropertyValue(getIsInputNodeInProperty(), newIsInputNodeIn);
    }


    public void removeIsInputNodeIn(MFrag oldIsInputNodeIn) {
        removePropertyValue(getIsInputNodeInProperty(), oldIsInputNodeIn);
    }


    public void setIsInputNodeIn(Set newIsInputNodeIn) {
        setPropertyValues(getIsInputNodeInProperty(), newIsInputNodeIn);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isParentOf

    public Set getIsParentOf() {
        return new HashSet(getPropertyValuesAs(getIsParentOfProperty(), Resident.class));
    }


    public RDFProperty getIsParentOfProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isParentOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsParentOf() {
        return getPropertyValueCount(getIsParentOfProperty()) > 0;
    }


    public Iterator listIsParentOf() {
        return listPropertyValuesAs(getIsParentOfProperty(), Resident.class);
    }


    public void addIsParentOf(Resident newIsParentOf) {
        addPropertyValue(getIsParentOfProperty(), newIsParentOf);
    }


    public void removeIsParentOf(Resident oldIsParentOf) {
        removePropertyValue(getIsParentOfProperty(), oldIsParentOf);
    }


    public void setIsParentOf(Set newIsParentOf) {
        setPropertyValues(getIsParentOfProperty(), newIsParentOf);
    }
}