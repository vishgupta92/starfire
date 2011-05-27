package es.upm.dit.gsi.prOwl2net;
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
		String uriOwl = "/home/jero.perez/workspaceCOMMUNE/bayesianTranslator/PruebaLLuviaEstados.owl";
		String nameNet = "NetworkName";
                UnBBayesTranslator aux = new UnBBayesTranslator(uriOwl);
		aux.convertOwlToNet();



	    //aux.createStructureNet();
	    // aux.save();

	}

}
