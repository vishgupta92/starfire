package es.upm.dit.gsi.net2prOwl;

import java.io.File;

/**
 * Class to run to create a. owl from a file. net
 * @author Jeronimo Fco Perez Regidor
 */
public class RunBayesianTranslator {

	/**
         * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String uriOwlEmpty = "./Example/owl/EmptyBN.owl";
		String uriNet = "./Example/net/PruebaLLuviaEstados.net";
		BayesianTranslatorNet2OWL bT = new BayesianTranslatorNet2OWL(new File(uriNet),new File(uriOwlEmpty));

                bT.convertNet2Owl();


		//bT.createStructureOWL();
		//System.out.println("creada la estructura");
		//bT.createTablesOWL();//
		//System.out.println("creadas las tablas");
		//bT.addProbAssingToPROWLTable();
		//bT.saveOwlModel();
		//System.out.println("Guardado en .owl");
		
	}

}
