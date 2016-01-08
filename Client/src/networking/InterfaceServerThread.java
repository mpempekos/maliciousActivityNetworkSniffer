package networking;
import java.util.List;

import jnetpcap.synchronization.JnetpcapAccessManager;
import memories.Mpsm;
import memories.Smpsm;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapClosedException;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

//This thread is going to get an interface check passing packets
//keeps statistics for his interface and send them to SMPSM ??

public class InterfaceServerThread extends Thread{
	private String networkIfName;
	private StringBuilder interfaceIp;
	private long numOfPacketsReceived;
	public static final long numOfPacketsLimit = 922337200;
	
	
	public InterfaceServerThread() {
		this.networkIfName = "(unnamed interface)";
		this.interfaceIp = new StringBuilder("(unknown interface IP)");
		this.numOfPacketsReceived = 0;
	}

	public InterfaceServerThread(String networkIfName) {
		this.networkIfName = networkIfName;
		this.interfaceIp = null;
		this.numOfPacketsReceived = 0;
	}
	
	@Override
	public void run() 
	{
		
		try {
			System.out.println("Monitor thread for interface '" + networkIfName + "' was created (thread id: "+Thread.currentThread().getId()+")" );
			StringBuilder errbuf = new StringBuilder(); // For any error msgs

			
				int snaplen = 64 * 1024;
				int flags;
				
				JnetpcapAccessManager.getLibraryLock().lock();
				try {	
					flags = Pcap.MODE_PROMISCUOUS; // capture all packets
				}
				finally {
					JnetpcapAccessManager.getLibraryLock().unlock();
				}
				int timeout = 10 * 500;           // 5 seconds in millis

				
				JnetpcapAccessManager.getLibraryLock().lock();
				Pcap pcap = new Pcap();
				try {	
					pcap = Pcap.openLive(networkIfName, snaplen, flags, timeout, errbuf);

				}
				finally {
					JnetpcapAccessManager.getLibraryLock().unlock();
				}
				
				if (pcap == null) {
					System.err.printf("Error while opening device for capture: "
					    + errbuf.toString());
					return;
				}
				
				PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

					public void nextPacket(PcapPacket packet, String user) {

						JnetpcapAccessManager.getLibraryLock().lock();
						try {	
							/// reset number of received packets 
							/// if we are approaching a very big number
							/// (note: i doubt we will ever reach that number)
							if (numOfPacketsReceived >= numOfPacketsLimit) {
								System.out.println("Reached packet limit... resetting counter...");
								numOfPacketsReceived = 0;
							}
							
							//////////////////////////////////////////////////////////////////
							// We will analyze for malicious packets once every five packets //
							numOfPacketsReceived++;
							if ((numOfPacketsReceived % 5) != 0) {
							}
							else
								return;
						}

						finally {
							JnetpcapAccessManager.getLibraryLock().unlock();
						}

						boolean isPacketOfInterest = false;
						boolean hasPayload = false;
						
						Ip4 myIp = new Ip4();
						Tcp myTcp = new Tcp();
						Udp myUdp = new Udp();
						
						byte[] destIp = new byte[4];
						byte[] sourceIp = new byte[4];
						
						JnetpcapAccessManager.getLibraryLock().lock();
						try {							
							///////////////////////////////////////////////
							/// We only care about TCP and UDP packets! ///
							if (packet.hasHeader(myTcp)) {
								//System.out.println("TCP packet!");
								isPacketOfInterest = true;
							}
							if (packet.hasHeader(myUdp)) {
								//System.out.println("UDP packet!");
								isPacketOfInterest = true;
							}
							if (!isPacketOfInterest) {
								return;
							}
							//getting source/destination IPs
							if (packet.hasHeader(myIp)) {
								destIp = packet.getHeader(myIp).destination();
								sourceIp = packet.getHeader(myIp).source();	
							}
							else {
								return;
							}
							if(interfaceIp == null) 
								interfaceIp = new StringBuilder(networking.NetworkScanner.getInterfaceIpByName(networkIfName));
						}
						finally {
							JnetpcapAccessManager.getLibraryLock().unlock();
						}
	
							Payload payload = new Payload();
							
							//locking every jnetpcap call
							JnetpcapAccessManager.getLibraryLock().lock();
							try {	
								if (packet.hasHeader(payload)) { // Acquire payload and check at the same time
									hasPayload=true;
								}
							}
							finally {
								JnetpcapAccessManager.getLibraryLock().unlock();
							}
							if (hasPayload){
								//uncomment if you want to print the payload
								//System.out.println(payload.toString()); //print payload
							}

							/////////////////////////////////////////////////////////////
							/// searching in shared memory for MALICIOUS IP ADDRESSES ///
							if(Mpsm.searchMatchingEntryMaliciousIpAddress(NetworkScanner.convertIpToString(sourceIp))) {
								System.out.println();
								System.out.println("FOUND MALICIOUS IP ADDRESS (destination IP) :" + NetworkScanner.convertIpToString(sourceIp));
								Smpsm.updateSmpsmMaliciousIp(NetworkScanner.convertIpToString(sourceIp),interfaceIp.toString() ,networkIfName);
							}
							if(Mpsm.searchMatchingEntryMaliciousIpAddress(NetworkScanner.convertIpToString(destIp))) {
								System.out.println();
								System.out.println("MALICIOUS IP ADDRESS (source IP) :" + NetworkScanner.convertIpToString(destIp));
								Smpsm.updateSmpsmMaliciousIp(NetworkScanner.convertIpToString(destIp), interfaceIp.toString() ,networkIfName);
							}			

							//////////////////////////////////////////////////////////
							/// Searching in payload for malicious string patterns ///
							if (hasPayload) {
								List<String> maliciousStringPatternsFound = Mpsm.searchMatchingEntryMaliciousStringPattern(payload.toString());
								if(maliciousStringPatternsFound != null){
									System.out.println("MALICIOUS PAYLOAD at interface: " + interfaceIp.toString());
									Smpsm.updateSmpsmMaliciousStringPatternWrapper(maliciousStringPatternsFound,interfaceIp.toString(),networkIfName);	
								}
							}

					}
				};
			
				try {	
					/// looping forever until interrupted
					pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, "Anaptuksi rocks!");
					pcap.close();
					//in case thread exits before got interrupted.
					Smpsm.removeSmpsmEntryMaliciousIp(networkIfName);
					Smpsm.removeSmpsmEntryMaliciousStringPattern(networkIfName);
					System.out.println("Terminating monitor thread for interface '" + networkIfName + "'" );	
					System.out.println("\t'"+networkIfName+"' SMPSM entries removed!");
					return;
				} catch(PcapClosedException pce) {
					pce.getSuppressed();
				}
				Thread.sleep(1000);
		} catch (InterruptedException e) {
			Smpsm.removeSmpsmEntryMaliciousIp(networkIfName);
			Smpsm.removeSmpsmEntryMaliciousStringPattern(networkIfName);
			System.out.println("Terminating monitor thread for interface '" + networkIfName + "'" );	
			System.out.println("\t'"+networkIfName+"' SMPSM entries removed!");
		} 
			
	}		
}
