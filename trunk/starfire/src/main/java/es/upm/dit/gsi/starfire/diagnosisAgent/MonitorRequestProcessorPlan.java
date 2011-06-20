package es.upm.dit.gsi.starfire.diagnosisAgent;

import java.util.HashMap;

import jadex.base.fipa.SFipa;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

	public class MonitorRequestProcessorPlan  extends Plan {
			
			private static final long serialVersionUID = -597969299500340110L;

			/**
			 * Create a new plan.
			 */
			public MonitorRequestProcessorPlan() {
				getLogger().info("MRPA: Created: " + this);
				
			}
			
			public void body(){
				getLogger().info("MRPA: Plan begins");
				IMessageEvent req = (IMessageEvent) getReason();
				getLogger().info("MRPA: Rtcp request received: " + req.getParameter(SFipa.CONTENT).getValue());
				String message = (String) req.getParameter(SFipa.CONTENT).getValue();
				
				HashMap<String,String> info = parseMessageToHashMap(message);
				int jitter = Integer.parseInt(info.get("jitter"));
				int packetsLost = Integer.parseInt(info.get("lost"));				
				double lostPercentage = Double.parseDouble(info.get("percentage"));
				
				getLogger().info("MRPA: Jitter = "+jitter+" Packets Lost = "+packetsLost+" Lost Percentage = "+lostPercentage);
				
				getLogger().info("MRPA: Plan ends");
				
			}

			/**
			 * Parses the received message to hash map, to read the values easily
			 * 
			 * @param message
			 * @return Hashmap with the key-value of the message
			 */
			private HashMap<String,String> parseMessageToHashMap(String message){
				String[] messageSplit = message.split("::");
				if (messageSplit.length%2 != 2);
				HashMap<String,String> info = new HashMap<String, String>();
				for (int i = 0; i < messageSplit.length; i+=2){
					info.put(messageSplit[i], messageSplit[i+1]);
				}
				return info;
			}
			
	}

