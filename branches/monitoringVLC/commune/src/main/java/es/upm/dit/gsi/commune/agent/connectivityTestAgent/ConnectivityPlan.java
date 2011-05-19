package es.upm.dit.gsi.commune.agent.connectivityTestAgent;


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
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;


public class ConnectivityPlan extends Plan {
		

		private static final long serialVersionUID = -597969299505340110L;
		
		
		/**
		 * Create a new plan.
		 */
		public ConnectivityPlan() {
			getLogger().info("ConnectivityPlan: Created: " + this);
			
		}
		
		public void body(){
			getLogger().info("ConnectivityPlan: Plan Begins ");

			IGoal ask_info = createGoal("connectivityCapability.checkConnection");
			dispatchSubgoalAndWait(ask_info);				
			

			boolean testPassed = (Boolean) getBeliefbase().getBelief("connectivityCapability.isConnected").getFact();			

			String resp = "TestPassed: " + testPassed;
					
			getLogger().info("ConnectivityTest: " + resp);
			
		}

}