package es.upm.dit.gsi.net2prOwl;
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
		String uriOwl = "/home/jero.perez/workspaceCOMMUNE/bayesianTranslator/Example/owl/EmptyBN.owl";
		String uriNet = "/home/jero.perez/workspaceCOMMUNE/bayesianTranslator/Example/net/PruebaLLuviaEstados.net";
		BayesianTranslator bT = new BayesianTranslator(uriNet,uriOwl);

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
