package es.upm.dit.gsi.prOwl2net;

import java.io.File;

/**
 * Execution class that allows to create a file .net from a file .owl
 * @author Administrador
 */
public class RunUnBBayesTranslator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String uriOwl = "/home/jero.perez/workspaceCOMMUNE/bayesianTranslator/PruebaLLuviaEstados.owl";
//		String uriOwl = "PruebaLLuviaEstados.owl";
		String uriOwl = "./OWL/diagnosis5Test.owl";
		String uriNet = "./NET/Generated";

                UnBBayesTranslatorOWL2Net aux = new UnBBayesTranslatorOWL2Net(new File(uriOwl), uriNet);
		aux.convertOwlToNet();



	    //aux.createStructureNet();
	    // aux.save();

	}

}
