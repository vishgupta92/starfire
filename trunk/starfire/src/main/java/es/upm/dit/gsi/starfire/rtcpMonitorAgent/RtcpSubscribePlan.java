package es.upm.dit.gsi.starfire.rtcpMonitorAgent;


/**
 * <p>
 * RtcpMonitorPlan
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

import java.util.ArrayList;
import java.util.List;

import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.GoalFailureException;
import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.bridge.IComponentIdentifier;

public class RtcpSubscribePlan extends Plan {
		

		private static final long serialVersionUID = -597969299500340110L;
		private List<IComponentIdentifier> iciList ;
		private boolean startMonitoring;
		
		/**
		 * Create a new plan.
		 */
		public RtcpSubscribePlan() {
			getLogger().info("RSubscribePlan: Created: " + this);
			IBelief listBelief = getBeliefbase().getBelief("iciList");
			if(listBelief.getFact() == null) 
				iciList = new ArrayList<IComponentIdentifier>();
			else iciList = (List<IComponentIdentifier>) listBelief.getFact();
			startMonitoring=false;
		}
		
		public void body(){
			getLogger().info("RSubscribePlan: Plan begins");
			startAtomic();
			IMessageEvent req = (IMessageEvent) getReason();
			IComponentIdentifier ici = (IComponentIdentifier) req.getParameter(SFipa.SENDER).getValue();
			iciList.add(ici); //Add the agent to the list of agents subscribed to this service


			IBelief list = getBeliefbase().getBelief("iciList");
			list.setFact(iciList);
			
			getLogger().info("RSubscribePlan: Rtcp request received: " + req);
			getLogger().info("----------- iciList: "+iciList.size());
			IMessageEvent msgResp = getEventbase().createReply(req,"rtcp_ack");
			msgResp.getParameter(SFipa.CONTENT).setValue("rtcp inform petition received");
			sendMessage(msgResp);	
			
			IBelief monitoring = getBeliefbase().getBelief("monitoring");
			boolean isMonitoring = (Boolean) monitoring.getFact();
			endAtomic();
			waitFor(100);
			if(!isMonitoring && !startMonitoring){
				getLogger().info("RSubscribePlan: Not monitoring, starting the plan");
				startMonitoring = true;
			IGoal goal = createGoal("keepMonitoring");
			try
			{
			  this.dispatchTopLevelGoal(goal);
			  getLogger().info("RSubscribePlan: GOAL: "+goal);
			  startMonitoring = false;
			}
			catch(GoalFailureException e)
			{
				e.printStackTrace();
			  getLogger().info("Goal exception ");
			};
			}
			getLogger().info("RSubscribePlan: Plan ends");
			
			
			
		}
}