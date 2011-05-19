package es.upm.dit.gsi.commune.capability.connectivityTest;


/**
 * <p>
 * ConnectivityTestPlan: Searches for the gateway IP and checks if is connected
 * </p>
 * 
 * @version 0.1 (04/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.Plan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.StringTokenizer;


public class ConnectivityTestPlan extends Plan {
		

		private static final long serialVersionUID = -597969299505340110L;
		

		private int TIMEOUT =100;
		private int pingsToDo = 10;
		
		/**
		 * Create a new plan.
		 */
		public ConnectivityTestPlan() {
			getLogger().info("CTPlan: Created: " + this);
			
		}
		
		public void body(){
			getLogger().info("CTPlan: Plan Begins ");

			String destIp = null;
			
			//We get the gateway IP
			try {
				Process result = Runtime.getRuntime().exec("netstat -rn");

		        BufferedReader output = new BufferedReader(new InputStreamReader(result.getInputStream()));

		        String gateway ="";
		        String firstToken ="";

		        do{
		            String thisLine = output.readLine();
		            StringTokenizer st = new StringTokenizer(thisLine);
		            firstToken = st.nextToken();
		            do{
		            gateway = st.nextToken();
		            }while(gateway.equals("0.0.0.0"));

		        }
		        while(!(firstToken.equals("0.0.0.0") || firstToken.equals("default")));
		        destIp = gateway;
			} catch (Exception e2) {
				IBelief gatewayIP = getBeliefbase().getBelief("gatewayIP");
				destIp = (String) gatewayIP.getFact();
				getLogger().warning("CTPlan: gateway IP not foud, using the one in the beliefs: "+destIp);
			}
		
			byte[] targetAddress = { 0, 0, 0, 0 };
			try {
				targetAddress = InetAddress.getByName(destIp).getAddress();

			} catch (UnknownHostException e1) {

			}
			//Check the connectivity
			try {
				targetAddress = InetAddress.getByName(destIp).getAddress();
				InetAddress address = InetAddress.getByAddress(targetAddress);
				getLogger().info("CTPlan: Destination IP address: " + address);
				boolean isTargetReachable = false;
				long times ; 
				IBelief test = getBeliefbase().getBelief("isConnected");
				test.setFact(false);
				for (int i = 0; i < pingsToDo; i++) {
					long timeBefore = System.currentTimeMillis();
					address.isReachable(TIMEOUT);
					long timeAfter = System.currentTimeMillis();
					times = timeAfter - timeBefore;
					getLogger().fine("Ping Nº " + i+1 + " --> TIME: " + times);
					if (times < TIMEOUT){
						isTargetReachable = true;
						test.setFact(true);
						break;
					}
				}
				
				getLogger().info("CTPlan: Connection test passed: "+ isTargetReachable );
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			

			
			getLogger().info("CTPlan: Plan ends");
			
		}

}