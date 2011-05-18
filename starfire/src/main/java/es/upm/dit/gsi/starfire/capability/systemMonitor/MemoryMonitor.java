package es.upm.dit.gsi.starfire.capability.systemMonitor;

/**
 * <p>
 * MemoryMonitor:
 * This class provides a monitor for the system memory. No external libraries needed. 
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (ETSIT-UPM)
 * 
 */

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Logger;

public class MemoryMonitor {
	
	private long freeMemory;
	private long totalMemory;
	private double freeMemoryPercentage;
    private static Logger logger = Logger.getLogger("memoryMonitor");

	
	
	public MemoryMonitor(){
		setFreeMemory(0);
		setTotalMemory(0);
		setFreeMemoryPercentage(0);
	}
	

	public void setFreeMemory(long i) {
		freeMemory = i;
	}

	public void setTotalMemory(long i) {
		totalMemory = i;
	}
	

	public void updateMemoryUsage() {
		  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		  logger.info("MemoryMonitor: update memory usage");
		  
		  for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
		    method.setAccessible(true);
		    if (method.getName().startsWith("get") 
		        && Modifier.isPublic(method.getModifiers())) {
		            Object value;
		        try {
		            value = method.invoke(operatingSystemMXBean);
		        } catch (Exception e) {
		            value = e;
		        }
		        //System.out.println(method.getName() + " = " + value);
		        if(method.getName().toString().equals("getFreePhysicalMemorySize")){
		        	setFreeMemory(Long.parseLong(value.toString()));
		        }
		        if(method.getName().toString().equals("getTotalPhysicalMemorySize")){
		        	setTotalMemory(Long.parseLong(value.toString()));
		        }
		    } 
		  } 


			//System.out.println("free memory: "+getFreeMemory());
			//System.out.println("total memory: "+getTotalMemory());
		  DecimalFormatSymbols simbol = new DecimalFormatSymbols();
		  simbol.setDecimalSeparator('.');
		  freeMemoryPercentage = (double) (getFreeMemory())/(getTotalMemory())*100;
			DecimalFormat df = new DecimalFormat("##.##",simbol);
			freeMemoryPercentage = Double.valueOf(df.format(freeMemoryPercentage));
			
		  logger.info("Free memory percentage: "+freeMemoryPercentage);

	}


	public long getTotalMemory() {
		return totalMemory;
	}


	public long getFreeMemory() {
		return freeMemory;
	}
	
	
	public double getFreeMemoryPercentage() {
		return freeMemoryPercentage;
	}


	public void setFreeMemoryPercentage(double freeMemoryPercentage) {
		this.freeMemoryPercentage = freeMemoryPercentage;
	}
	

}
