package es.upm.dit.gsi.ontology.impl;

import edu.stanford.smi.protege.model.FrameID;
import edu.stanford.smi.protegex.owl.model.*;
import edu.stanford.smi.protegex.owl.model.impl.*;
import edu.stanford.smi.protegex.owl.javacode.AbstractCodeGeneratorIndividual;
import java.util.*;

import es.upm.dit.gsi.ontology.Diagnosis;
import es.upm.dit.gsi.ontology.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Diagnosis
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public class DefaultDiagnosis extends AbstractCodeGeneratorIndividual
         implements Diagnosis {

    public DefaultDiagnosis(OWLModel owlModel, FrameID id) {
        super(owlModel, id);
    }


    public DefaultDiagnosis() {
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#hasHypothesis

    public Set getHasHypothesis() {
        return new HashSet(getPropertyValuesAs(getHasHypothesisProperty(), Hypothesis.class));
    }


    public RDFProperty getHasHypothesisProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#hasHypothesis";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasHasHypothesis() {
        return getPropertyValueCount(getHasHypothesisProperty()) > 0;
    }


    public Iterator listHasHypothesis() {
        return listPropertyValuesAs(getHasHypothesisProperty(), Hypothesis.class);
    }


    public void addHasHypothesis(Hypothesis newHasHypothesis) {
        addPropertyValue(getHasHypothesisProperty(), newHasHypothesis);
    }


    public void removeHasHypothesis(Hypothesis oldHasHypothesis) {
        removePropertyValue(getHasHypothesisProperty(), oldHasHypothesis);
    }


    public void setHasHypothesis(Set newHasHypothesis) {
        setPropertyValues(getHasHypothesisProperty(), newHasHypothesis);
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#id

    public String getId() {
        return (String) getPropertyValue(getIdProperty());
    }


    public RDFProperty getIdProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#id";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasId() {
        return getPropertyValueCount(getIdProperty()) > 0;
    }


    public void setId(String newId) {
        setPropertyValue(getIdProperty(), newId);
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#performedAction

    public Set getPerformedAction() {
        return new HashSet(getPropertyValuesAs(getPerformedActionProperty(), PerformedAction.class));
    }


    public RDFProperty getPerformedActionProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#performedAction";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformedAction() {
        return getPropertyValueCount(getPerformedActionProperty()) > 0;
    }


    public Iterator listPerformedAction() {
        return listPropertyValuesAs(getPerformedActionProperty(), PerformedAction.class);
    }


    public void addPerformedAction(PerformedAction newPerformedAction) {
        addPropertyValue(getPerformedActionProperty(), newPerformedAction);
    }


    public void removePerformedAction(PerformedAction oldPerformedAction) {
        removePropertyValue(getPerformedActionProperty(), oldPerformedAction);
    }


    public void setPerformedAction(Set newPerformedAction) {
        setPropertyValues(getPerformedActionProperty(), newPerformedAction);
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#performedHealingAction

    public Set getPerformedHealingAction() {
        return new HashSet(getPropertyValues(getPerformedHealingActionProperty()));
    }


    public RDFProperty getPerformedHealingActionProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#performedHealingAction";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformedHealingAction() {
        return getPropertyValueCount(getPerformedHealingActionProperty()) > 0;
    }


    public Iterator listPerformedHealingAction() {
        return listPropertyValues(getPerformedHealingActionProperty());
    }


    public void addPerformedHealingAction(Object newPerformedHealingAction) {
        addPropertyValue(getPerformedHealingActionProperty(), newPerformedHealingAction);
    }


    public void removePerformedHealingAction(Object oldPerformedHealingAction) {
        removePropertyValue(getPerformedHealingActionProperty(), oldPerformedHealingAction);
    }


    public void setPerformedHealingAction(Set newPerformedHealingAction) {
        setPropertyValues(getPerformedHealingActionProperty(), newPerformedHealingAction);
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#performedTestAction

    public Set getPerformedTestAction() {
        return new HashSet(getPropertyValues(getPerformedTestActionProperty()));
    }


    public RDFProperty getPerformedTestActionProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#performedTestAction";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasPerformedTestAction() {
        return getPropertyValueCount(getPerformedTestActionProperty()) > 0;
    }


    public Iterator listPerformedTestAction() {
        return listPropertyValues(getPerformedTestActionProperty());
    }


    public void addPerformedTestAction(Object newPerformedTestAction) {
        addPropertyValue(getPerformedTestActionProperty(), newPerformedTestAction);
    }


    public void removePerformedTestAction(Object oldPerformedTestAction) {
        removePropertyValue(getPerformedTestActionProperty(), oldPerformedTestAction);
    }


    public void setPerformedTestAction(Set newPerformedTestAction) {
        setPropertyValues(getPerformedTestActionProperty(), newPerformedTestAction);
    }



    // Property http://www.owl-ontologies.com/Diagnosis.owl#startedBySymptom

    public Set getStartedBySymptom() {
        return new HashSet(getPropertyValuesAs(getStartedBySymptomProperty(), Symptom.class));
    }


    public RDFProperty getStartedBySymptomProperty() {
        final String uri = "http://www.owl-ontologies.com/Diagnosis.owl#startedBySymptom";
        final String name = getOWLModel().getResourceNameForURI(uri);
        return getOWLModel().getRDFProperty(name);
    }


    public boolean hasStartedBySymptom() {
        return getPropertyValueCount(getStartedBySymptomProperty()) > 0;
    }


    public Iterator listStartedBySymptom() {
        return listPropertyValuesAs(getStartedBySymptomProperty(), Symptom.class);
    }


    public void addStartedBySymptom(Symptom newStartedBySymptom) {
        addPropertyValue(getStartedBySymptomProperty(), newStartedBySymptom);
    }


    public void removeStartedBySymptom(Symptom oldStartedBySymptom) {
        removePropertyValue(getStartedBySymptomProperty(), oldStartedBySymptom);
    }


    public void setStartedBySymptom(Set newStartedBySymptom) {
        setPropertyValues(getStartedBySymptomProperty(), newStartedBySymptom);
    }
}
