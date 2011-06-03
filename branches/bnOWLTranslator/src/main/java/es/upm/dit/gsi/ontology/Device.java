package es.upm.dit.gsi.ontology;

import edu.stanford.smi.protegex.owl.model.*;

import java.util.*;

/**
 * Generated by Protege-OWL  (http://protege.stanford.edu/plugins/owl).
 * Source OWL Class: http://www.owl-ontologies.com/Diagnosis.owl#Device
 *
 * @version generated on Fri Apr 01 15:09:41 CEST 2011
 */
public interface Device extends System, HardwareSystem {

    // Property http://www.owl-ontologies.com/Diagnosis.owl#belongsToNetwork

    Set getBelongsToNetwork();

    RDFProperty getBelongsToNetworkProperty();

    boolean hasBelongsToNetwork();

    Iterator listBelongsToNetwork();

    void addBelongsToNetwork(Network newBelongsToNetwork);

    void removeBelongsToNetwork(Network oldBelongsToNetwork);

    void setBelongsToNetwork(Set newBelongsToNetwork);


    // Property http://www.owl-ontologies.com/Diagnosis.owl#isConnectedBy

    Set getIsConnectedBy();

    RDFProperty getIsConnectedByProperty();

    boolean hasIsConnectedBy();

    Iterator listIsConnectedBy();

    void addIsConnectedBy(Connection newIsConnectedBy);

    void removeIsConnectedBy(Connection oldIsConnectedBy);

    void setIsConnectedBy(Set newIsConnectedBy);
}