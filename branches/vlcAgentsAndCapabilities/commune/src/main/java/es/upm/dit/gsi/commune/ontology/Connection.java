package es.upm.dit.gsi.commune.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Connection
 *
 * @version generated on Tue Apr 05 14:50:26 CEST 2011
 */
public interface Connection extends OWLIndividual {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#connectsSystem

    Collection getConnectsSystem();

    RDFProperty getConnectsSystemProperty();

    boolean hasConnectsSystem();

    Iterator listConnectsSystem();

    void addConnectsSystem(Device newConnectsSystem);

    void removeConnectsSystem(Device oldConnectsSystem);

    void setConnectsSystem(Collection newConnectsSystem);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#isPartOfNetwork

    Collection getIsPartOfNetwork();

    RDFProperty getIsPartOfNetworkProperty();

    boolean hasIsPartOfNetwork();

    Iterator listIsPartOfNetwork();

    void addIsPartOfNetwork(Network newIsPartOfNetwork);

    void removeIsPartOfNetwork(Network oldIsPartOfNetwork);

    void setIsPartOfNetwork(Collection newIsPartOfNetwork);
}