package es.upm.dit.gsi.starfire.capability.systemMonitor;

/**
 * <p>
 * CpuMonitor:
 * This class provides a monitor for the cpu, using the hyperic sigar library. 
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (ETSIT-UPM)
 * 
 */

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Logger;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Cpu;


public class CpuMonitor {

    private static Logger logger = Logger.getLogger("cpuMonitor");
	
	public CpuMonitor(){
	}
	
	public void cpuStats() {
		Sigar session = new Sigar();
		
		try {
			
			CpuPerc cpuPerc = session.getCpuPerc();
			System.out.println("Cpu percentage usage combined: "+cpuPerc.getCombined()*100);
			System.out.println("Cpu percentage usage system: "+cpuPerc.getSys()*100);
			System.out.println("Cpu percentage usage user: "+cpuPerc.getUser()*100);
			System.out.println("Free CPU percentage : "+cpuPerc.getIdle()*100);
			
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void getCpuInfo() {
		Sigar session = new Sigar();
		
		try {
			CpuInfo[] cpuInfoArray = session.getCpuInfoList();
			System.out.println("CPU MHz: "+cpuInfoArray[0].getMhz());
			System.out.println("CPU Model "+cpuInfoArray[0].getModel());
			System.out.println("CPU total cores "+ cpuInfoArray[0].getTotalCores());
			
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * getFreeCpuPercentage() :
	 * 	Gets the free global cpu percentage of the system. 
	 * 
	 * @return the value of the free cpu in percentage
	 */
	public double getFreeCpuPercentage(){
		Sigar session = new Sigar();
		double freeCpu = 0;
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
	    simbol.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("##.##",simbol);

		
		try {
			CpuPerc cpuPerc = session.getCpuPerc();
			freeCpu = cpuPerc.getIdle()*100;
			freeCpu = Double.valueOf(df.format(freeCpu)); 
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("CpuMonitor: free cpu percentage: "+ freeCpu);
		return freeCpu;
		
	}
	
}
