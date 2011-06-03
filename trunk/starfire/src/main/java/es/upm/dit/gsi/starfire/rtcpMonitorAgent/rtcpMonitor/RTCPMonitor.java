package es.upm.dit.gsi.starfire.rtcpMonitorAgent.rtcpMonitor;

/**
 * <p>
 * RTCPMonitor:
 * This class searches the network interfaces for rtcp packets. Once it finds the port,
 * waits for the packets to arrive. Then, gets the jitter, lost percentage and total lost info 
 * from the packet 
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 */
import java.io.IOException;
import java.util.logging.Logger;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;
import jpcap.packet.UDPPacket;

public class RTCPMonitor {

    private static Logger logger = Logger.getLogger("rtcpMonitor");
	
	private static String interfaceName;
	private static int port = 0;
	private NetworkInterface[] devices ;

	private static final int RTCP_RECEIVER_REPORT = 11001001; //201 in binary to identify the RR RTCP packet
	private static final String RTCP_VERSION = "10"; //The version of the protocol

	private final static int TIMEOUT = 10000;
	private static boolean timeout = false;
	
	
	
/**
 * Searches all the network interfaces for a RTCP RR packet
 * When found, sets the port number and interface name
 * 
 * @return true if a rtcp Stream is found, false otherwise
 */
	public boolean searchForRTCPStream() {
		
		if(devices == null)
			devices = JpcapCaptor.getDeviceList();		//Obtain the list of network interface
		
		if(devices.length == 0) logger.warning("RM: No devices found, make sure the right permissions are granted and everything installed correctly");


		//for each network interface
		for (int i = 0; i < devices.length; i++) {
			
			//print out its name and description
			logger.info("RM: "+i+": "+devices[i].name + "(" + devices[i].description+")");

			try {
				logger.info("RM: Start capture on "+devices[i].name +" device number: "+i);
				JpcapCaptor captor = JpcapCaptor.openDevice(devices[i], 65535, false, 40);
//				logger.info("1");
				captor.setFilter("udp", true);
//				logger.info("2");
				Packet p = captor.getPacket();
//				logger.info("3");
				if (p == null) {
					captor.close();
//					logger.info("4");
					continue;
				}
				for(int j  = 0; j< 10000; j++){
					try{
					if(isRTCPReceiverReportPacket(p)){
//						logger.info("A");
						interfaceName = devices[i].name;
//						logger.info("B");
						UDPPacket udp = (UDPPacket) p;
//						logger.info("C");
						port = udp.dst_port;
						logger.info("RM: RTCP Port = "+port);
						captor.close();
						return true;
					}
					p = captor.getPacket();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				logger.info("5");

				captor.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * Waits for a RTCP RR Packet to arrive in the port and interface specified
	 * If it doesn't receive any (in TIMEOUT time) it sets the timeout value to true and finishes, usually because transmission is finished or interrupted
	 * 
	 * @return the RTCP RR Packet as an instance of jadex.packet.Packet
	 */
	
	public Packet getRTCPPacket(){

		try {
			logger.info("RM: Waiting for a packet on "+interfaceName);
			if(devices == null)
				devices = JpcapCaptor.getDeviceList();		//Obtain the list of network interface
			Packet p = null;
			
			long startTime = System.currentTimeMillis();

			for(NetworkInterface device : devices){

				if(device.name.equals(interfaceName)){

					JpcapCaptor captor = JpcapCaptor.openDevice(device, 65535, false, 20);
					//Set the captor filter to get only the packets that go to the actual rtcp port and are UDP.

					if (port != 0) 
						captor.setFilter("udp and dst port "+port, true);
					else 
						captor.setFilter("udp", true);

					p = captor.getPacket();
					startTime = System.currentTimeMillis();
					while(!isRTCPReceiverReportPacket(p)){
						if (timeout(startTime)) break;
						p = captor.getPacket();
						if (p != null) startTime = System.currentTimeMillis();
					}
					captor.close();
				}
				}
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public NetworkInterface[] getDevices() {
		return devices;
	}


	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		RTCPMonitor.interfaceName = interfaceName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		RTCPMonitor.port = port;
	}

/**
 * Checks if the packet received is an instance of RTCP RR packets (where the QoS info is)
 *
 * 
 * @param p The packet we want to check
 * @return True if is a RTCP RR packet, false otherwise
 */
	public boolean isRTCPReceiverReportPacket(Packet p){

		if (p != null && p.len < 1000 && p.data.length > 0){
			//System.out.println("Total length: "+p.len);
			//System.out.println("data Length: "+p.data.length);
			//System.out.println("header length "+p.header.length);
//			logger.info("AA");
			byte[] packetData = p.data;
//			logger.info("BB");
			
			//Parse the packet to integer as a binary
			int counter = 0;
			int[] packetDataBinary = new int[p.data.length];
//			logger.info("CC");
			for (byte b : packetData){
				//logger.finest(Integer.toBinaryString(b&0xff) + ":");
				packetDataBinary[counter] = Integer.parseInt(Integer.toBinaryString(b&0xff));
//				logger.info("DD");
				counter++;
			}

//			logger.info("A");
			
			//Checks the content of the packet
			if(packetDataBinary.length >= 4){
				//Check if the packet is a receiver report rtcp packet (RFC 3550)
				if(String.valueOf(packetDataBinary[0]).startsWith(RTCP_VERSION) && packetDataBinary[1] == RTCP_RECEIVER_REPORT ){
					logger.info("RM: RTCP RECEIVER REPORT PACKET FOUND!");					
					return true;
				}
			}

//			logger.info("B");
		}
		return false;
	}
	
	/**
	 * If none RTCP RR packet received in TIMEOUT value, returns true
	 * (Checks if the transmission is finished)
	 * 
	 * @param startTime
	 * @return
	 */
	private boolean timeout(long startTime){
		long currentTime = System.currentTimeMillis();
		if(( currentTime - startTime)> TIMEOUT ){
			logger.info("RM: Timeout: Transmission finished or interrupted");
			setTimeout(true);
			return true;
		}
		return false;
		
	}

	public void setTimeout(boolean timeout) {
		RTCPMonitor.timeout = timeout;
	}

	public boolean isTimeout() {
		return timeout;
	}
}
