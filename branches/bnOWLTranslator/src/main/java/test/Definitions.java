package test;

import java.util.HashMap;


public class Definitions {

	//Net diagnosis5Test
	public static final String uriNet ="./Example/net/diagnosis5Test.net";
	public static final String uriOwlEmpty ="./Example/owl/EmptyBN.owl";
	public static final String uriOwl = "./OWL/Generated/diagnosis5Test.owl";
	public static String uriNetFinal = "./NET/Generated";
	public static String uriNetFinalFile = "./NET/Generated/diagnosis5Test.net";
	
//	//Net  PruebaLLuvia
//	public static final String uriNet ="./Example/net/PruebaLLuvia.net";
//	public static final String uriOwlEmpty ="./Example/owl/EmptyBN.owl";
//	public static final String uriOwl = "./OWL/Generated/PruebaLLuvia.owl";
//	public static String uriNetFinal = "./NET/Generated";
//	public static String uriNetFinalFile = "./NET/Generated/PruebaLLuvia.net";

//	//Net PadreHijo
//	public static final String uriNet ="./Example/net/PadreHijo.net";
//	public static final String uriOwlEmpty ="./Example/owl/EmptyBN.owl";
//	public static final String uriOwl = "./OWL/Generated/PadreHijo.owl";
//	public static String uriNetFinal = "./NET/Generated";
//	public static String uriNetFinalFile = "./NET/Generated/PadreHijo.net";
	
//	//Net Agua
//	public static final String uriNet ="./Example/net/Agua.net";
//	public static final String uriOwlEmpty ="./Example/owl/EmptyBN.owl";
//	public static final String uriOwl = "./OWL/Generated/Agua.owl";
//	public static String uriNetFinal = "./NET/Generated";
//	public static String uriNetFinalFile = "./NET/Generated/Agua.net";
	
//	//Net RedHambre
//	public static final String uriNet ="./Example/net/RedHambre.net";
//	public static final String uriOwlEmpty ="./Example/owl/EmptyBN.owl";
//	public static final String uriOwl = "./OWL/Generated/RedHambre.owl";
//	public static String uriNetFinal = "./NET/Generated";
//	public static String uriNetFinalFile = "./NET/Generated/RedHambre.net";
	
	public  HashMap<String, String> EVIDENCES = new HashMap<String, String>();
	public  static String TARGETSTATE = "YES";
	public  HashMap<String, Double> HYPOTHESES = new HashMap<String, Double>();

	
	/**
	 * Constructor
	 */
	public Definitions() {}
	
	/**
	 * method responsible for adding evidence and hypothesis.
	 */
	public void refreshAll() {
		
//		//Diagnosis5
		HYPOTHESES.put("ServerFailure", null);
		HYPOTHESES.put("ConnectionClientToRouterFailure", null);
		
		EVIDENCES.put("DetectedJitter", "High");
		EVIDENCES.put("ConnectionTest", "Passed");
		EVIDENCES.put("PacketLossPercentage", "Low");
		EVIDENCES.put("ConnectionClientToRouter", "OK");
	
//		//Padre hijo
//		HYPOTHESES.put("Padre", null);
//		EVIDENCES.put("Hijo", "TRAVIESO");
	
		
//		//Agua
//		HYPOTHESES.put("Humano", null);
//		HYPOTHESES.put("Animal", null);
//		HYPOTHESES.put("Planta", null);
//		EVIDENCES.put("Agua", "Caliente");
		
//		//RedHambre
//		HYPOTHESES.put("Humanos", null);
//		HYPOTHESES.put("Agua", null);
//		EVIDENCES.put("Herbivoros", "SI");
//		EVIDENCES.put("Plantas", "NO");
//		EVIDENCES.put("Carnivoro", "SI");
//		EVIDENCES.put("Humanos", "Sediento");
//		
		
//		// PruebaLluviaEstados
//		EVIDENCES.put("Cesped", "YES");
//		EVIDENCES.put("Aspersor", "NO");
//		HYPOTHESES.put("Lluvia", null);
//		
	}
	
	


}
