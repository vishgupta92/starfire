package es.upm.dit.gsi.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import java.util.*;

import es.upm.dit.gsi.ontology.Resident;
import es.upm.dit.gsi.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#Resident
 *
 * @version generated on Fri Apr 01 15:09:42 CEST 2011
 */
public class DefaultResident extends DefaultNode
         implements Resident {

    public DefaultResident(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultResident() {
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
        return new HashSet(getPropertyValuesAs(getHasInnerTermProperty(), Resident.class));
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
        return listPropertyValuesAs(getHasInnerTermProperty(), Resident.class);
    }


    public void addHasInnerTerm(Resident newHasInnerTerm) {
        addPropertyValue(getHasInnerTermProperty(), newHasInnerTerm);
    }


    public void removeHasInnerTerm(Resident oldHasInnerTerm) {
        removePropertyValue(getHasInnerTermProperty(), oldHasInnerTerm);
    }


    public void setHasInnerTerm(Set newHasInnerTerm) {
        setPropertyValues(getHasInnerTermProperty(), newHasInnerTerm);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasInputInstance

    public Set getHasInputInstance() {
        return new HashSet(getPropertyValuesAs(getHasInputInstanceProperty(), Input.class));
    }


    public RDFProperty getHasInputInstanceProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasInputInstance";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasInputInstance() {
        return getPropertyValueCount(getHasInputInstanceProperty()) > 0;
    }


    public Iterator listHasInputInstance() {
        return listPropertyValuesAs(getHasInputInstanceProperty(), Input.class);
    }


    public void addHasInputInstance(Input newHasInputInstance) {
        addPropertyValue(getHasInputInstanceProperty(), newHasInputInstance);
    }


    public void removeHasInputInstance(Input oldHasInputInstance) {
        removePropertyValue(getHasInputInstanceProperty(), oldHasInputInstance);
    }


    public void setHasInputInstance(Set newHasInputInstance) {
        setPropertyValues(getHasInputInstanceProperty(), newHasInputInstance);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasParent

    public Set getHasParent() {
        return new HashSet(getPropertyValues(getHasParentProperty()));
    }


    public RDFProperty getHasParentProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasParent";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasParent() {
        return getPropertyValueCount(getHasParentProperty()) > 0;
    }


    public Iterator listHasParent() {
        return listPropertyValues(getHasParentProperty());
    }


    public void addHasParent(Object newHasParent) {
        addPropertyValue(getHasParentProperty(), newHasParent);
    }


    public void removeHasParent(Object oldHasParent) {
        removePropertyValue(getHasParentProperty(), oldHasParent);
    }


    public void setHasParent(Set newHasParent) {
        setPropertyValues(getHasParentProperty(), newHasParent);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasPossibleValues

    public Set getHasPossibleValues() {
        return new HashSet(getPropertyValuesAs(getHasPossibleValuesProperty(), Entity.class));
    }


    public RDFProperty getHasPossibleValuesProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasPossibleValues";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasPossibleValues() {
        return getPropertyValueCount(getHasPossibleValuesProperty()) > 0;
    }


    public Iterator listHasPossibleValues() {
        return listPropertyValuesAs(getHasPossibleValuesProperty(), Entity.class);
    }


    public void addHasPossibleValues(Entity newHasPossibleValues) {
        addPropertyValue(getHasPossibleValuesProperty(), newHasPossibleValues);
    }


    public void removeHasPossibleValues(Entity oldHasPossibleValues) {
        removePropertyValue(getHasPossibleValuesProperty(), oldHasPossibleValues);
    }


    public void setHasPossibleValues(Set newHasPossibleValues) {
        setPropertyValues(getHasPossibleValuesProperty(), newHasPossibleValues);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasProbDist

    public Set getHasProbDist() {
        return new HashSet(getPropertyValuesAs(getHasProbDistProperty(), ProbDist.class));
    }


    public RDFProperty getHasProbDistProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasProbDist";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasProbDist() {
        return getPropertyValueCount(getHasProbDistProperty()) > 0;
    }


    public Iterator listHasProbDist() {
        return listPropertyValuesAs(getHasProbDistProperty(), ProbDist.class);
    }


    public void addHasProbDist(ProbDist newHasProbDist) {
        addPropertyValue(getHasProbDistProperty(), newHasProbDist);
    }


    public void removeHasProbDist(ProbDist oldHasProbDist) {
        removePropertyValue(getHasProbDistProperty(), oldHasProbDist);
    }


    public void setHasProbDist(Set newHasProbDist) {
        setPropertyValues(getHasProbDistProperty(), newHasProbDist);
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



    // Property http://www.pr-owl.org/pr-owl.owl#isResidentNodeIn

    public Set getIsResidentNodeIn() {
        return new HashSet(getPropertyValuesAs(getIsResidentNodeInProperty(), MFrag.class));
    }


    public RDFProperty getIsResidentNodeInProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isResidentNodeIn";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsResidentNodeIn() {
        return getPropertyValueCount(getIsResidentNodeInProperty()) > 0;
    }


    public Iterator listIsResidentNodeIn() {
        return listPropertyValuesAs(getIsResidentNodeInProperty(), MFrag.class);
    }


    public void addIsResidentNodeIn(MFrag newIsResidentNodeIn) {
        addPropertyValue(getIsResidentNodeInProperty(), newIsResidentNodeIn);
    }


    public void removeIsResidentNodeIn(MFrag oldIsResidentNodeIn) {
        removePropertyValue(getIsResidentNodeInProperty(), oldIsResidentNodeIn);
    }


    public void setIsResidentNodeIn(Set newIsResidentNodeIn) {
        setPropertyValues(getIsResidentNodeInProperty(), newIsResidentNodeIn);
    }
}