package es.upm.dit.gsi.commune.agent.rtcpMonitorAgent;


/**
 * <p>
 * RtcpMonitorPlan: Registers in the DF and starts when a petition by RtcpSubscribePlan is received. 
 * Then searches for a rtcp connection and if a certain threshold is passed, sends a message to all the 
 * subscribed agents.
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.bridge.IComponentIdentifier;

import jpcap.packet.Packet;

import es.upm.dit.gsi.commune.agent.rtcpMonitorAgent.rtcpMonitor.RTCPPacket;
import es.upm.dit.gsi.commune.agent.rtcpMonitorAgent.rtcpMonitor.RTCPMonitor;


public class RtcpMonitorPlan extends Plan {
		

		private static final long serialVersionUID = -597969299500340110L;
		private RTCPMonitor rtcpMonitor;
		private List<IComponentIdentifier> iciList ;
		
		/**
		 * Create a new plan.
		 */
		public RtcpMonitorPlan() {
			getLogger().info("RMPlan: Created: " + this);
			iciList = new ArrayList<IComponentIdentifier>();
			rtcpMonitor = new RTCPMonitor();
			
		}
		
		public void body(){
			getLogger().info("RMPlan: Plan begins");
			
			IBelief monitoringBelief =  getBeliefbase().getBelief("monitoring"); //Change the belief to true
			monitoringBelief.setFact(true);
			
			
			while(!rtcpMonitor.searchForRTCPStream()){
				getLogger().info("RMPlan: RTCP stream not found, trying again in 10000 ms");
				
//				String info = "NOK:RTCP stream not found";
//				msgResp = getEventbase().createMessageEvent("rtcp_inform");
//				Iterator<IComponentIdentifier> iciIterator = iciList.iterator();
//				while(iciIterator.hasNext())
//					msgResp.getParameterSet(SFipa.RECEIVERS).addValue(iciIterator.next());
//				msgResp.getParameter(SFipa.CONTENT).setValue(info);
//				sendMessage(msgResp);		
//				getLogger().info("RA: Rtcp response sent: " + info);
				waitFor(10000);
			}
			
			getLogger().info("RMPlan: RTCP stream found, waiting for RTCP packet to arrive");
			Packet p;
			while(!rtcpMonitor.isTimeout()){ //If a timeout occurs, the plan finishes
				waitFor(100);
				p = rtcpMonitor.getRTCPPacket();
			if(p != null){
				RTCPPacket rtcp = new RTCPPacket(p);
				long jitter = rtcp.getJITTER();
				long packetsLost = rtcp.getPACKETS_LOST();
				double lostPercentage = rtcp.getLOST_PERCENTAGE();
				if(!checkIfRTPTransmissionOK(jitter,lostPercentage)){
					getLogger().info("RMPlan: Transmission not ok, create event");

					String resp = "jitter::"+ jitter + "::lost::" + packetsLost + "::percentage::" + lostPercentage;

					IMessageEvent msgResp = getEventbase().createMessageEvent("rtcp_inform");
					//get the list of rtcp monitor subscribed agents
					IBelief iciBelief = getBeliefbase().getBelief("iciList"); 	//get the list of subsribed agents
					iciList = (List<IComponentIdentifier>) iciBelief.getFact();
					getLogger().info("RMPlan: Rtcp request Ici list: " + iciList.size());

					Iterator<IComponentIdentifier> iciIterator = iciList.iterator();

					while(iciIterator.hasNext())
						msgResp.getParameterSet(SFipa.RECEIVERS).addValue(iciIterator.next());
					msgResp.getParameter(SFipa.CONTENT).setValue(resp);
					sendMessage(msgResp);		
					getLogger().info("RMPlan: Rtcp response sent: " + resp); //send the message to all receivers

					startAtomic(); //Start atomic block
					iciBelief.setFact(new ArrayList<IComponentIdentifier>());	//removes the agents that received the message. If an agent wants more messages, it has to subscribe to monitor agent again
					
					monitoringBelief =  getBeliefbase().getBelief("monitoring");
					monitoringBelief.setFact(false);

					endAtomic();	//end atomic block
					break;
				}
			}
			}
			getLogger().info("RMPlan: Plan ends");
			
		}

		
		/**
		 * 
		 * Checks if the transmission is over a certain threshold specified in the ADF
		 * 
		 * @param jitter
		 * @param lostPercentage
		 * @return
		 */
		private boolean checkIfRTPTransmissionOK(long jitter, double lostPercentage) {
			IBelief jitter_threshold = getBeliefbase().getBelief("jitter_threshold");
			long jitterMax = (Long) jitter_threshold.getFact();
			if(jitter > jitterMax) return false;
			IBelief lost_packets_threshold = getBeliefbase().getBelief("lost_packets_threshold");
			double lostPercentageMax = (Double) lost_packets_threshold.getFact();
			if(lostPercentage > lostPercentageMax) return false;
			
			return true;
		}

}