package test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.stanford.smi.protegex.owl.jena.JenaOWLModel;
import es.upm.dit.gsi.net2prOwl.BayesianTranslatorNet2OWL;
import es.upm.dit.gsi.ontology.MyFactory;
import es.upm.dit.gsi.prOwl2net.UnBBayesTranslatorOWL2Net;

import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.ProbabilisticNetwork;

public class TestBnOwlTranslatorNoRandom {

	
	
	Logger logger = Logger.getLogger(TestBnOwlTranslatorNoRandom.class);

	private static boolean filesCreated = false;
	private static String uriNet = Definitions.uriNet;
	private static String uriOwl = Definitions.uriOwl;
	private static String uriOwlEmpty = Definitions.uriOwlEmpty;
	private static String uriNetFinalFile = Definitions.uriNetFinalFile;
	private static String uriNetFinal = Definitions.uriNetFinal;

	private static ProbabilisticNetwork netBefore;
	private static ProbabilisticNetwork netAfter;
	public static JenaOWLModel owlModel;
	private MyFactory myFactory;
	
	
	
	
	public TestBnOwlTranslatorNoRandom() throws Exception{
		if (!filesCreated) {
			loadFiles();
			filesCreated = true;
		}
	}
	
	public static void loadFiles() throws Exception{

		// Delete previous files
		File owlFile = new File(uriOwl);
		if(owlFile.exists()) {
			owlFile.delete();
		}
		File netAfterFile = new File(uriNetFinalFile);
		if(netAfterFile.exists()) {
			netAfterFile.delete();
		}
		
		// Create New Files
		File netFile = new File(uriNet);
		File owlFileBN = new File(uriOwlEmpty);
		BayesianTranslatorNet2OWL bayesT = new BayesianTranslatorNet2OWL(netFile, owlFileBN);
		bayesT.convertNet2Owl();
		UnBBayesTranslatorOWL2Net bbT = new UnBBayesTranslatorOWL2Net(new File(uriOwl), uriNetFinal);
		bbT.convertOwlToNet();
		
		//LoadNets
		BaseIO io = new NetIO();
		netBefore = (ProbabilisticNetwork) io.load(new File(uriNet));
		netAfter = (ProbabilisticNetwork) io.load(new File(uriNetFinalFile));
	}
	
//	@Test
//	public void testCreateFiles() {
//		File netBeforeFile = new File(uriNet);
//		assertTrue("Net before doesn't exist.", netBeforeFile.exists());
//		File owlEmptyFile = new File(uriOwlEmpty);
//		assertTrue(owlEmptyFile.exists());
//		File owlFile = new File(uriOwl);
//		assertTrue(owlFile.exists());
//		File netAfterFile = new File(uriNetFinalFile);
//		assertTrue(netAfterFile.exists());
//		
//		assertTrue(filesCreated);
//	}

	/**
	 * CheckNameNodesNet
	 */
	@Test
	public void testCheckNameNodesNet() {
		//net before
		int numNodesB = netBefore.getNodes().size();
		ArrayList<Node>  nodesB  = netBefore.getNodes();
		int numNodesA = netAfter.getNodes().size();
		boolean actual = true;
			for(int j = 0; j<numNodesA; j++) {
				Node nodeA = netAfter.getNodes().get(j);
				if (!nodesB.contains(nodeA)){
					actual = false;
					break;
				}
				logger.info("Node net After is: "+ nodeA.getName() + ", and is included");
			}
		assertEquals(true, actual);

	}
	
}
