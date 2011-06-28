package es.upm.dit.gsi.ontology.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import es.upm.dit.gsi.ontology.PR_OWLTable;
import es.upm.dit.gsi.ontology.ProbAssign;
import es.upm.dit.gsi.ontology.Resident;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.pr-owl.org/pr-owl.owl#PR-OWLTable
 *
 * @version generated on Mon Jun 27 12:29:14 CEST 2011
 */
public class DefaultPR_OWLTable extends DefaultProbDist
         implements PR_OWLTable {

    public DefaultPR_OWLTable(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultPR_OWLTable() {
    }



    // Property http://www.pr-owl.org/pr-owl.owl#hasProbAssign

    public Set getHasProbAssign() {
        return new HashSet(getPropertyValuesAs(getHasProbAssignProperty(), ProbAssign.class));
    }


    public RDFProperty getHasProbAssignProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#hasProbAssign";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasProbAssign() {
        return getPropertyValueCount(getHasProbAssignProperty()) > 0;
    }


    public Iterator listHasProbAssign() {
        return listPropertyValuesAs(getHasProbAssignProperty(), ProbAssign.class);
    }


    public void addHasProbAssign(ProbAssign newHasProbAssign) {
        addPropertyValue(getHasProbAssignProperty(), newHasProbAssign);
    }


    public void removeHasProbAssign(ProbAssign oldHasProbAssign) {
        removePropertyValue(getHasProbAssignProperty(), oldHasProbAssign);
    }


    public void setHasProbAssign(Set newHasProbAssign) {
        setPropertyValues(getHasProbAssignProperty(), newHasProbAssign);
    }



    // Property http://www.pr-owl.org/pr-owl.owl#isProbDistOf

   
	public DefaultResident getIsProbDistOf() {
        return ((DefaultResident) getPropertyValueAs(getIsProbDistOfProperty(), Resident.class));
    }


    public RDFProperty getIsProbDistOfProperty() {
        final String uri = "http://www.pr-owl.org/pr-owl.owl#isProbDistOf";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasIsProbDistOf() {
        return getPropertyValueCount(getIsProbDistOfProperty()) > 0;
    }


    public void setIsProbDistOf(Resident newIsProbDistOf) {
        setPropertyValue(getIsProbDistOfProperty(), newIsProbDistOf);
    }
}