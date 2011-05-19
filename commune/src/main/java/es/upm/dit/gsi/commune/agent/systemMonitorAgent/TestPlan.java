package es.upm.dit.gsi.commune.agent.systemMonitorAgent;


import java.util.ArrayList;

import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

public class TestPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2081121919681597871L;

	/**
	 * Create a new plan.
	 */
	public TestPlan() {
		getLogger().info("MonitorAgent: Created: " + this);
	}

	/**
	 * The plan body.
	 */
	public void body() {
		
		IGoal ask_info = createGoal("monitor.monitorize");
		dispatchSubgoalAndWait(ask_info);				
		

		double freeCpu = (Double) getBeliefbase().getBelief("monitor.freeCpu").getFact();			
		double freeMemory = (Double) getBeliefbase().getBelief("monitor.freeMemory").getFact();
		double maxNetRate = (Double) getBeliefbase().getBelief("monitor.maxNetRate").getFact();
		double[] netRate = (double[]) getBeliefbase().getBelief("monitor.netRate").getFact();
		

		String resp = "FreeCpu: " + freeCpu+  "; FreeMemory: "  + freeMemory + "; MaxNetRate: "  + maxNetRate ;//+ "; NetRate: " +netRate.toString();
				
		getLogger().info("MonitorTest: " + resp);
	}
	
}
