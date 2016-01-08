package networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class NetworkScanner{
	
	////////////////////////////////////////////
	/// get all network interfaces in a List ///
	public /*synchronized*/ static List<PcapIf>  getNetworkInterfaces() {	
		try {
			
			List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
			List<PcapIf> realDevs = new ArrayList<PcapIf>(); // Will be filled with devs that has MAC 
			StringBuilder errbuf = new StringBuilder();     // For any error msgs  
	
			
			int r = Pcap.findAllDevs(alldevs, errbuf);  
			if (r == Pcap.NOT_OK || alldevs.isEmpty()) {  
			  System.err.printf("Can't read list of devices, error is %s", errbuf.toString());  
			  return null;
			}
			
				// we only want the interfaces with mac address
			  	for (final PcapIf curDev : alldevs) {  
			  		final byte[] mac = curDev.getHardwareAddress();  
				      if (mac == null) {  
				    	  continue; // Interface doesn't have a hardware address  
				      }
				      if (curDev.getName().equals("lo"))
				    	  continue;

				      realDevs.add(curDev);
			  	}  
			
			
			return realDevs;
		} catch (IOException e) {
			return null;
		}
	}

	
	
	
	
	////////////////////////////////////////
	/// get a list with interfaces names ///
	
	public /*synchronized*/ static List<String> getNetworkInterfacesNames(){

		try {
			
			List<PcapIf> alldevs = new ArrayList<PcapIf>(); // Will be filled with NICs  
			//List<String> alldevsNames = new ArrayList<String>();
			List<String> realDevsNames = new ArrayList<String>();
			StringBuilder errbuf = new StringBuilder();     // For any error msgs  
	
			int r = Pcap.findAllDevs(alldevs, errbuf);  
			if (r == Pcap.NOT_OK || alldevs.isEmpty()) {  
			  System.err.printf("Can't read list of devices, error is %s", errbuf.toString());  
			  return null;
			}
			
			// we only want the interfaces with mac address
		  	for (final PcapIf curDev : alldevs) {  
			      final byte[] mac = curDev.getHardwareAddress();  
			      if (mac == null) {  
			    	  continue; // Interface doesn't have a hardware address  
			      }  
			      if (curDev.getName().equals("lo"))
			    	  continue;

			      
			      
			      realDevsNames.add(curDev.getName());
			    	  //System.out.println(curDev.getName() + " mac (strings) = " + macToString(mac));
			    }  
		
			/*
			for (i=0; i<alldevs.size(); i++) {
				alldevsNames.add(alldevs.get(i).getName());		
			}
			*/
			
			
			//return alldevsNames;
			return realDevsNames;
		}
		catch (IOException e) {
			return null;
		}
	}
	

	
	///////////////////////////////////////////////
	/// converts IP address (byte[4]) to String ///
	public static String convertIpToString(byte[] myIp) {
		int i = 0;
        String ipAddress = "";
        
        if (myIp == null) {
        	return "unknown IP address";
        }
        for (byte ip : myIp)
        {
            ipAddress += (ip & 0xFF);
            if (i++ < 3)
            {
                ipAddress += ".";
            }
        }
        return ipAddress;
    }		
	

	/////////////////////////////////////////////
	/// converts byte[] mac address to String ///
	public static String macToString(final byte[] mac){
		
	    final StringBuilder buf = new StringBuilder();  
	    
	    for (byte b : mac) {  
	      if (buf.length() != 0) {  
	        buf.append(':');  
	      }  
	      if (b >= 0 && b < 16) {  
	        buf.append('0');  
	      }  
	      buf.append(Integer.toHexString((b < 0) ? b + 256 : b).toUpperCase());  
	    }  
	  
	    return buf.toString();  
	  }  		
	

	
	
	/////////////////////////////////////////////////////////////////////////////
	/// returns the interface IP by interface name //////////////////////////////
	
	public /*synchronized*/ static StringBuilder getInterfaceIpByName(String netInterfaceName) {
		int i;
		List<PcapIf> alldevs = new ArrayList<PcapIf>();
		PcapIf netInterface = new PcapIf();
		
		alldevs = getNetworkInterfaces();
		for (i=0; i<alldevs.size(); i++) {
			if (alldevs.get(i).getName().equals(netInterfaceName)) {
				netInterface = alldevs.get(i);
				break;
			}
		}
		String str;

		if (!netInterface.getAddresses().isEmpty())
			if (netInterface.getAddresses().size() > 1)
				str = netInterface.getAddresses().get(1).getAddr().toString();
			else
				return new StringBuilder("unknown IP interface");
		else
			return new StringBuilder("unknown IP interface");

		StringBuilder str2 = new StringBuilder(); //str2 is the interface name
		boolean inetShowedUp = false;
		for (i=0; i<str.length(); i++) {
			if ((str.charAt(i) == ':') || (str.charAt(i) == ']')) {
				inetShowedUp = true;
				continue;
			}
			if (inetShowedUp) {
				str2.append(str.charAt(i));
			}
		}
		return str2;
	}
	

	
	
	//////////////////////////////////////////////////////
	/// returns #of times word is in payloadStr //////////

	public static int searchMaliciousPayload(String word, String payloadStr){

		int index = payloadStr.indexOf(word);
		int count = 0;
		while (index != -1) {
		    count++;
		    payloadStr = payloadStr.substring(index + 1);
		    index = payloadStr.indexOf(word);
		}
		return count;
		   
	}
	
	
	
}