package es.upm.dit.gsi.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import java.util.*;

import es.upm.dit.gsi.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#ProbDist
 *
 * @version generated on Fri Apr 01 15:09:42 CEST 2011
 */
public class DefaultProbDist extends AbstractCodeGeneratorIndividual
         implements ProbDist {

    public DefaultProbDist(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultProbDist() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isDefault

    public boolean getIsDefault() {
        return getPropertyValueLiteral(getIsDefaultProperty()).getBoolean();
    }


    public RDFProperty getIsDefaultProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isDefault";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsDefault() {
        return getPropertyValueCount(getIsDefaultProperty()) > 0;
    }


    public void setIsDefault(boolean newIsDefault) {
        setPropertyValue(getIsDefaultProperty(), new java.lang.Boolean(newIsDefault));
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isProbDistOf

    public Resident getIsProbDistOf() {
        return (Resident) new HashSet(getPropertyValuesAs(getIsProbDistOfProperty(), Resident.class));
    }


    public RDFProperty getIsProbDistOfProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isProbDistOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsProbDistOf() {
        return getPropertyValueCount(getIsProbDistOfProperty()) > 0;
    }


    public Iterator listIsProbDistOf() {
        return listPropertyValuesAs(getIsProbDistOfProperty(), Resident.class);
    }


    public void addIsProbDistOf(Resident newIsProbDistOf) {
        addPropertyValue(getIsProbDistOfProperty(), newIsProbDistOf);
    }


    public void removeIsProbDistOf(Resident oldIsProbDistOf) {
        removePropertyValue(getIsProbDistOfProperty(), oldIsProbDistOf);
    }


    public void setIsProbDistOf(Set newIsProbDistOf) {
        setPropertyValues(getIsProbDistOfProperty(), newIsProbDistOf);
    }
}