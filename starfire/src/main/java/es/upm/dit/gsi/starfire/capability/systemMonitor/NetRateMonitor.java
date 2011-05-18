package es.upm.dit.gsi.starfire.capability.systemMonitor;

/**
 * <p>
 * NetRateMonitor:
 * This class provides a monitor for the network interfaces.
 * Jpcap library is required to get the interface rate.
 * Hyperic Sigar library is required to get the percentage of the bandwith used. 
 * </p>
 * 
 * @version 0.3 (04/2011)
 * @author Jaime de Miguel (ETSIT-UPM)
 * 
 */

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Sigar;
//import org.hyperic.sigar.*;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;



public class NetRateMonitor {

	private double[] speed;
	private double[] interfaceUsage;
	private String[] interfaceNames;
	private List<String> interfaceNamesList;
    private static Logger logger = Logger.getLogger("netMonitor");

	
    
	/**
	 * NetRateMonitor
	 * 
	 * Constructor of NetRateMonitor Class.
	 * gets the network interfaces connected in the system
	 * 
	 */
	public NetRateMonitor(){
		interfaceNamesList = initializeInterface();
		speed = new double[interfaceNamesList.size()];
		interfaceUsage = new double[interfaceNamesList.size()];
		interfaceNames = new String[interfaceNamesList.size()];
	}
	



	public void main (String[] args){
		initializeInterface();
	}

	public void updateNetStats() {
		if(!interfaceNamesList.isEmpty()){
		
		try{
		
		//Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		List<Packet> packetList = new ArrayList<Packet>();
		long initialTime = 0;
		long endTime = 0;
		int totalCapture = 0;
		int counter = 0;
		
		for(int j= 0; j<devices.length; j++ ){
		try {
			if(!interfaceNamesList.contains(devices[j].name)){
				continue;
			}
			logger.info("NetMonitor: Start capture on "+devices[j].name);
			JpcapCaptor captor=JpcapCaptor.openDevice(devices[j], 65535, true, 20);
			initialTime = System.currentTimeMillis();
			for(int i = 0; i< 100; i++){
				packetList.add(captor.getPacket());
			}
			endTime = System.currentTimeMillis();
			Iterator<Packet> packetIterator = packetList.iterator();
			while(packetIterator.hasNext()){
				Packet packet = packetIterator.next();
				if (packet != null){
					totalCapture += packet.caplen;
				}
			}
			double bps = (double) totalCapture/(endTime-initialTime)*1000;
			logger.info("NetMonitor:  Bps = "+ bps);
			logger.info("NetMonitor: Interface usage: "+interfaceUsage(devices[j].name,bps));
			speed[counter] = bps;
			interfaceUsage[counter] = interfaceUsage(devices[j].name,bps);
			interfaceNames[counter] = devices[j].name;
			counter++;
			captor.close();
		} catch (IOException e) {
			logger.severe("NetMonitor: IO Exception");
		}
		initialTime = 0;
		endTime = 0;
		totalCapture = 0;
		
		}
		}catch (NoClassDefFoundError e) {
			logger.warning("NetMonitor: JpCaptor couldn't be initialized");
		}
		}
	}


	public List<String> initializeInterface() {
		List<String> interfaceNameList = new ArrayList<String>();

		try{
		//Obtain the list of network interfaces
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		
		if(devices.length == 0){
			  OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
			  if(operatingSystemMXBean.getName().contains("Mac")){
				  logger.warning("NetMonitor: No interfaces found. Execute this on your terminal to grant permission 'sudo chmod 644 /dev/bpf*' ");
				  return interfaceNameList;
			  }
		}
		
		//for each network interface
		for (int i = 0; i < devices.length; i++) {
		  //print out its name and description
		  logger.fine(i+": "+devices[i].name + "(" + devices[i].description+")");

		  //print out its datalink name and description
		  logger.fine(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");

		  //print out its MAC address
		  logger.fine(" MAC address:");
		  String []macAddress = new String[6];
		  int counter = 0;
		  for (byte b : devices[i].mac_address){
			logger.fine(Integer.toHexString(b&0xff) + ":");
		    macAddress[counter] = Integer.toHexString(b&0xff);
		    counter++;
		  }

		  //print out its IP address, subnet mask and broadcast address
		  for (NetworkInterfaceAddress a : devices[i].addresses)
			  logger.fine(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);
		  
		  if (devices[i].addresses.length != 0){
			  for (String s : macAddress){
				if(!s.equals("0")){
					if(!devices[i].name.startsWith("vnic")){
						interfaceNameList.add(devices[i].name);			
						break;
					}
				}  
			}
		}
		  
		}
		}catch (UnsatisfiedLinkError e) {
			logger.warning("NetMonitor: No jpcap found in java library path. Make sure libpcap or winpcap is installed. Check http://netresearch.ics.uci.edu/kfujii/Jpcap/doc/index.html. Continuing without network info");
		}catch (NoClassDefFoundError e) {
			logger.warning("NetMonitor: jpcap couldn't be initialized");
		}
		
		
		  logger.info("NetMonitor: Interfaces connected "+interfaceNameList.size());
		return interfaceNameList;
		
	}
	
	public static double interfaceUsage(String interfaceName, double bps){
		double interfaceUsage = 0;
		try {
			Sigar session = new Sigar();
			NetInterfaceStat netInterfaceStat = session.getNetInterfaceStat(interfaceName);
			if(netInterfaceStat.getSpeed()*100 !=0)
				interfaceUsage = (double) bps/netInterfaceStat.getSpeed()*100;

		} catch (SigarException e) {
			logger.warning("NetMonitor: Can't get the interface usage");
		} catch (NoClassDefFoundError e){
			logger.warning("NetMonitor: Sigar class not found");
		}
		return interfaceUsage;

		
	}
	
	
	public double[] getSpeed() {
		return speed;
	}




	public void setSpeed(double[] speed) {
		this.speed = speed;
	}




	public double[] getInterfaceUsage() {
		return interfaceUsage;
	}




	public void setInterfaceUsage(double[] interfaceUsage) {
		this.interfaceUsage = interfaceUsage;
	}




	public String[] getInterfaceNames() {
		return interfaceNames;
	}




	public void setInterfaceNames(String[] interfaceNames) {
		this.interfaceNames = interfaceNames;
	}

	
}



