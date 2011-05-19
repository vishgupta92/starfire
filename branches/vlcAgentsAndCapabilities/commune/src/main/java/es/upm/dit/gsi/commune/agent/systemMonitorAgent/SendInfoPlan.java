package es.upm.dit.gsi.commune.agent.systemMonitorAgent;


import java.util.ArrayList;
import java.util.List;

import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

public class SendInfoPlan extends Plan {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2081121919681597871L;

	/**
	 * Create a new plan.
	 */
	public SendInfoPlan() {
		getLogger().info("MonitorAgent: Created: " + this);
	}

	/**
	 * The plan body.
	 */
	public void body() {
		
		// Get the time request message
		IMessageEvent req = (IMessageEvent) getReason();
		getLogger().info("MonitorAgent: Monitor request received: " + req);
				
		IGoal ask_info = createGoal("monitor.monitorize");
		dispatchSubgoalAndWait(ask_info);
						
		Double freeCpu = (Double) getBeliefbase().getBelief("monitor.freeCpu").getFact();
		Double freeMemory = (Double) getBeliefbase().getBelief("monitor.freeMemory").getFact();
		
		List<Double> resp = new ArrayList<Double>();
		resp.add(freeCpu);
		resp.add(freeMemory);
		
//		ArrayList netRate = (ArrayList) getBeliefbase().getBelief("monitor.freeMemory").getFact();
		
		
		IMessageEvent msgResp = getEventbase().createReply(req,"response");
		msgResp.getParameter(SFipa.CONTENT).setValue(resp);
		sendMessage(msgResp);		
		getLogger().info("MonitorAgent: Monitor response sent: " + resp);
	}
	
}
