package es.upm.dit.gsi.commune.capability.systemMonitor;


/**
 * <p>
 * MonitorPlan:
 * This class provides the plan of the Jadex monitor agent.
 * Jpcap and Hyperic Sigar libraries are required to get some system info. 
 * </p>
 * 
 * @version 0.3 (04/2011)
 * @author Jaime de Miguel (ETSIT-UPM)
 * 
 */

import jadex.bdi.runtime.IBelief;
import jadex.bdi.runtime.Plan;
import jadex.bridge.IComponentIdentifier;


import es.upm.dit.gsi.commune.capability.systemMonitor.CpuMonitor;
import es.upm.dit.gsi.commune.capability.systemMonitor.MemoryMonitor;
import es.upm.dit.gsi.commune.capability.systemMonitor.NetRateMonitor;
import es.upm.dit.gsi.commune.capability.systemMonitor.SystemMonitor;


public class MonitorPlan extends Plan {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -597969269500340110L;

	/**
	 * Create a new plan.
	 */
	public MonitorPlan() {
		getLogger().info("MA: Created: " + this);
	}
	
	public void body(){
		getLogger().info("MA: Plan begins");
		CpuMonitor cpuMonitor = new CpuMonitor();
		MemoryMonitor memoryMonitor = new MemoryMonitor();
		NetRateMonitor netMonitor = new NetRateMonitor();
				
		IBelief cpu = getBeliefbase().getBelief("freeCpu");
		cpu.setFact(cpuMonitor.getFreeCpuPercentage());
		
		memoryMonitor.updateMemoryUsage();
		IBelief memory = getBeliefbase().getBelief("freeMemory");
		memory.setFact(memoryMonitor.getFreeMemoryPercentage());
		
		netMonitor.updateNetStats();
		IBelief net = getBeliefbase().getBelief("netRate");
		double[] speed = netMonitor.getSpeed();
		net.setFact(speed);	
		IBelief maxNetRate = getBeliefbase().getBelief("maxNetRate");
		maxNetRate.setFact(maxValue(speed)); //Get the maximum of all the interfaces 

		
		getLogger().info("MA: Plan ends");
		
	}

	/**
	 * Gets the maximum value of an array of doubles
	 * 
	 * @param array of doubles
	 * @return
	 */
	private double maxValue(double[] array){
		if (array.length > 0){
			double max = array[0];
			for(double value : array){
				if (value > max) max = value;
			}
			return max;

		}else return -1; //If the array is empty, return -1
		
	}
}
