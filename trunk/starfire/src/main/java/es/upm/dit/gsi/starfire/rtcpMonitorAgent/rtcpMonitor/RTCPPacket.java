package es.upm.dit.gsi.starfire.rtcpMonitorAgent.rtcpMonitor;

/**
 * <p>
 * RTCPPacket:
 * This class represents a RTCP RR packet. 
 * 
 *  * Constants from http://www.ietf.org/rfc/rfc3550.txt
 * </p>
 * 
 * @version 0.1 (03/2011)
 * @author Jaime de Miguel (GSI-ETSIT-UPM)
 * 
 */

import java.util.logging.Logger;

import jpcap.packet.Packet;


public class RTCPPacket{

    private static Logger logger = Logger.getLogger("rtcpPacket");

	private final int JITTER_OFFSET = 20;
	private final int LOST_OFFSET = 13;
	private final int LOST_PERCENTAGE_OFFSET = 12;
	
	private final int JITTER_NBYTES = 4;
	private final int LOST_NBYTES = 3;
	private final int LOST_PERCENTAGE_NBYTES = 1;
	
	private byte []data;
	
	
	/**
	 * Create a new RTCP RR packet from a jpcap Packet
	 * 
	 * @param p : jpcap Packet
	 */
	public RTCPPacket (Packet p){
		data = new byte[p.data.length];
		data = p.data;

		
	}

/**
 * 
 * 
 * @return the lost percentage value of the RTCP RR Packet as a double
 */
	public double getLOST_PERCENTAGE() {
		//Lost percentage
		int lost = 0;
		for(int i = 0; i<LOST_PERCENTAGE_NBYTES; i++){
		  lost = lost | (data[LOST_PERCENTAGE_OFFSET+i] & 0xFF << (LOST_PERCENTAGE_NBYTES-1-i)*8) ;
		}
		  double lost_percentage = (double) lost/256;
		  logger.info("Instant Lost percentage: "+lost_percentage);
		return lost_percentage;
	}
	
/**
 * 
 * @return the jitter value of the RTCP RR Packet
 */
	
	public long getJITTER() {
		//Jitter
		long jitter = 0;
		for(int i = 0; i<JITTER_NBYTES; i++){
			  jitter = (jitter<<8) + (data[JITTER_OFFSET+i] & 0xFF) ;
			}

		logger.info("Interarrival jitter: "+jitter);
		return jitter;
	}

	
	/**
	 * 
	 * @return the total number of packets lost
	 */
	public long getPACKETS_LOST() {
		//Packets lost
		long packetsLost = 0;
		for(int i = 0; i<LOST_NBYTES; i++){
			  packetsLost = (packetsLost<<8) + (data[LOST_OFFSET+i] & 0xFF) ;
		}
		logger.info("Packets lost: "+packetsLost);
		return packetsLost;
	}
	
	

	
	public byte[] getData() {
		return data;
	}


	public void setData(byte[] data) {
		this.data = data;
	}

}