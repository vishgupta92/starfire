package es.upm.dit.gsi.starfire.capability.systemMonitor;

/**
 * <p>
 * SystemMonitor:
 * This class gives info about the system . 
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (ETSIT-UPM)
 * 
 */

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

//import org.hyperic.sigar.*;

import es.upm.dit.gsi.starfire.capability.systemMonitor.CpuMonitor;
import es.upm.dit.gsi.starfire.capability.systemMonitor.MemoryMonitor;
import es.upm.dit.gsi.starfire.capability.systemMonitor.NetRateMonitor;


public class SystemMonitor {
	private String systemName;
	private String systemArchitechture;
	private String systemVersion;

public SystemMonitor() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		systemName = operatingSystemMXBean.getName();
		systemArchitechture = operatingSystemMXBean.getArch();
		systemVersion = operatingSystemMXBean.getVersion();
	}


public String getSystemName() {
		return systemName;
	}

	public String getSystemArchitechture() {
		return systemArchitechture;
	}

	public String getSystemVersion() {
		return systemVersion;
	}


public void printSystemInfo(){
	  System.out.println("System name: "+getSystemName());
	  System.out.println("System version: "+getSystemVersion());
	  System.out.println("System architechture: "+getSystemArchitechture());
}


public static void main(String[] args){
	SystemMonitor sm = new SystemMonitor();
	sm.printSystemInfo();
	MemoryMonitor mm = new MemoryMonitor();
	mm.updateMemoryUsage();
	System.out.println(mm.getFreeMemoryPercentage());
	NetRateMonitor net = new NetRateMonitor();
	net.updateNetStats();
	CpuMonitor cpu = new CpuMonitor();
	cpu.getCpuInfo();
	cpu.cpuStats();
	
}

	
}
