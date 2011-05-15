package es.upm.dit.gsi.commune.ruleEngine;

import java.io.File;
import java.io.FileReader;

import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.swrl.SWRLRuleEngine;
import edu.stanford.smi.protegex.owl.swrl.bridge.SWRLRuleEngineFactory;
import edu.stanford.smi.protegex.owl.swrl.bridge.SWRLRuleEngineFactory.TargetSWRLRuleEngineCreator;
import edu.stanford.smi.protegex.owl.swrl.bridge.drools.DroolsNames;
import edu.stanford.smi.protegex.owl.swrl.bridge.drools.DroolsSWRLRuleEngineCreator;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.JessNames;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.JessSWRLRuleEngine;
import edu.stanford.smi.protegex.owl.swrl.bridge.jess.JessSWRLRuleEngineCreator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
//			String input = "/home/jlgonzalez/workspaceAgenteDiagnostico/RuleEngineTest/plugins/edu.stanford.smi.protegex.owl/P2PDiagnosis2.owl";
//			File input = new File("http://starfire.googlecode.com/svn/trunk/starfire/plugins/edu.stanford.smi.protegex.owl/P2PDiagnosis.owl");
//			FileReader owlfile = new FileReader(input);
//			OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromReader(owlfile);
			
			OWLModel owlModel = ProtegeOWL.createJenaOWLModelFromURI("http://starfire.googlecode.com/svn/trunk/starfire/plugins/edu.stanford.smi.protegex.owl/Diagnosis.owl");
//			SWRLRuleEngineFactory.registerRuleEngine(JessNames.PluginName, new JessSWRLRuleEngineCreator());
			SWRLRuleEngine ruleEngine = SWRLRuleEngineFactory.create(JessNames.PluginName, owlModel);
			
//			SWRLRuleEngineFactory.registerRuleEngine(DroolsNames.PluginName, new DroolsSWRLRuleEngineCreator());
//			SWRLRuleEngine ruleEngine = SWRLRuleEngineFactory.create(DroolsNames.PluginName, owlModel);
			
			ruleEngine.reset();
			ruleEngine.importSWRLRulesAndOWLKnowledge();
			ruleEngine.run();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};  // Create using normal Protege-OWL mechanisms.	

	}

}
