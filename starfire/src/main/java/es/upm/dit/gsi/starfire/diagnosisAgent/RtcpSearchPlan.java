package es.upm.dit.gsi.starfire.diagnosisAgent;

import jadex.base.fipa.IDF;
import jadex.base.fipa.IDFComponentDescription;
import jadex.base.fipa.IDFServiceDescription;
import jadex.bdi.runtime.GoalFailureException;
import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;
import jadex.bridge.IComponentIdentifier;
import jadex.commons.service.SServiceProvider;

	public class RtcpSearchPlan  extends Plan {
			

			private static final long serialVersionUID = -597969299500340110L;
			private static final long actReqTimeout = (long)60000;					// A minute

			protected IComponentIdentifier a;


			/**
			 * Create a new plan.
			 */
			public RtcpSearchPlan() {
				getLogger().info("RSearchPlan: Created: " + this);
				
			}
			
			public void body(){
				getLogger().info("RSearchPlan: Plan begins");
				// The agent looks for the other agent's identifier
				this.a = this.getIComponentIdentifier("rtcp_monitor");

				getLogger().info("RSearchPlan: The rtcp monitor has been found: " + a);

				// The response
				String response = null;
				
				try
				{
					// This method will create a rp_initiate goal, this goal will trigger a plan.
					// In this plan a request will be sent and the time server's response will
					// be received. There is a timeout of 60*1000 mills, in other words 1 minute.
					getLogger().info("RSearchPlan: The request is going to be sent");
					response=(String)sendRequestAndGetResponse("RTCP", this.a, actReqTimeout);
					getLogger().info("RSearchPlan: The request has been sent and the response has arrived");
					getLogger().info("RSearchPlan: The rtcp server's response was: "+response);

				}
				catch(GoalFailureException gfe)
				{
					getLogger().info("RSearchPlan: Failure in the fipa-request protocol "+gfe);
				}
				getLogger().info("RSearchPlan: Plan ends");
				
			}

			
			/**
			 *  Get the agent's identifier of a certain type of agent.
			 *  @param type the type of service the agent searched offers.
			 *  @param timeout the maximum time it will be looking for the service. Zero or negative for infinite time.
			 *  @param periodBetweenRequests the time it will wait between consecutive attempts.
			 *  @return the identifiers of the first agent who offers the service.
			 */
			protected IComponentIdentifier getIComponentIdentifier(String type, long timeout, long periodBetweenRequests) {
				
				// The agent's identifier
				IComponentIdentifier a = null;
				
				// The current time
				long referenceTime = System.currentTimeMillis();
				
				// Search an agent of type type.
				while(a==null && (System.currentTimeMillis()<(referenceTime+timeout) || timeout<=0))
				{
					IDF	dfservice = (IDF)SServiceProvider.getService(getScope().getServiceProvider(), IDF.class).get(this);
					
					// Create a service description to search for.
					IDFServiceDescription sd = dfservice.createDFServiceDescription(null, type, null);
					IDFComponentDescription ad = dfservice.createDFComponentDescription(null, sd);

					// Use a subgoal to search for a translation agent
					IGoal ft = createGoal("df_search");
		            ft.getParameter("description").setValue(ad);

					dispatchSubgoalAndWait(ft);
		            //Object result = ft.getResult();
		            IDFComponentDescription[] result = (IDFComponentDescription[])ft.getParameterSet("result").getValues();

					if(result.length>0)
					{
						a = result[0].getName();
					}
					else
					{
						//if(result instanceof Exception)
						//	((Exception)result).printStackTrace();
						//System.out.println("No translation agent found.");
						waitFor(periodBetweenRequests);
					}
				}
				
				// Return the agent's identifier
				return a;
			}
			
			/**
			 *  Get the agent's identifier of a certain type of agent.
			 *  @param type the type of service the agent searched offers.
			 *  @return the identifiers of the first agent who offers the service.
			 */
			protected IComponentIdentifier getIComponentIdentifier(String type) {
				
				return getIComponentIdentifier(type, 0, 1000);
			}
			
			/**
			 * Active the Goal to initiate a request protocol communication.
			 * This goal will trigger a plan which will send a request and will wait for a response.
			 * @param request the Object to send
			 * @param receiver the agent's identifier of the receiver agent
			 * @param timeout the timeout in milliseconds we will wait for answer
			 * @throws the exception which will be send if timeout pass with no answer 
			 */
			protected Object sendRequestAndGetResponse(String request,IComponentIdentifier receiver,Long timeout) throws GoalFailureException {
				// This method uses the Request.capability.xml in jadex.bdi.planlib.protocols.request
				IGoal goal = createGoal("rp_initiate");
				goal.getParameter("action").setValue(request);
				goal.getParameter("receiver").setValue(receiver);
				goal.getParameter("timeout").setValue(timeout);
				getLogger().info("Sending a request and waiting for the response"); 
				dispatchSubgoalAndWait(goal);
				getLogger().info("Response received"); 
				Object response = goal.getParameter("result").getValue();
				return response;
			}
			
	}

