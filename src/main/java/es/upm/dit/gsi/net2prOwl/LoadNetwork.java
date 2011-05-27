package es.upm.dit.gsi.net2prOwl;

import java.io.File;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.bn.ProbabilisticNetwork;
/**
 * A class that allows you to upload a file. net
 * @author Jeronimo Perez Regidor
 */
public class LoadNetwork {

	private ProbabilisticNetwork net;

	/**
	 * Network load
	 * 
	 * @return net , is a ProbalisticNetwork
	 */
	public ProbabilisticNetwork load(String uri) {
		ProbabilisticNetwork net = null;

		try {
			BaseIO io = new NetIO();
			net = (ProbabilisticNetwork) io.load(new File(uri));

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		//getLogger().info("The network is loaded.");
		return net;
	}
	
	
}
