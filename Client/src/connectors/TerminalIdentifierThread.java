package connectors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Properties;
import java.net.InetAddress;
import java.util.Collections;

import jnetpcap.synchronization.JnetpcapAccessManager;
import main.MainThread;

import org.jnetpcap.packet.format.FormatUtils;

import server.AdderWebServices;

//This thread identifies the laptop/pc device, and sends to "adder" mac address or something

public class TerminalIdentifierThread extends Thread {
	public void run()
	{
		System.out.println("\nTerminal Identifier thread (id: "+Thread.currentThread().getId()+") : ");
		String UID = null;
		File propertiesFile = new File("config.properties");
		if(!propertiesFile.exists()) {
			FileWriter buffer = null;
			try {
				buffer = new FileWriter("config.properties");
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				propertiesFile.createNewFile();
				UID = createUID();
				if(UID != null)
					buffer.write("UID=" + createUID());
				else {
					System.out.println("Problem found retrieving your UID.Please restart the programm");
					System.exit(1);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if(buffer != null)
					buffer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
			/****NEW CODE****/
			AdderWebServices adderWebService = ServerConnector.getAdderWebService();
			System.out.println(adderWebService.register(properties.getProperty("UID")));
			MainThread.setUID(properties.getProperty("UID"));
			/****************/
			//System.out.println("Sending UID to adder : " + properties.getProperty("UID"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	private String createUID() 
	{
		int macAddressHashValue = 0;
		int localMachineNameHashValue;
		String UID = null;
		try {
			InetAddress localMachine;
			localMachine = java.net.InetAddress.getLocalHost();
			localMachineNameHashValue = localMachine.getHostName().hashCode();
			Enumeration<NetworkInterface> netIFs = NetworkInterface.getNetworkInterfaces();
			for(NetworkInterface netIf : Collections.list(netIFs)) {
				if(netIf.getHardwareAddress() != null) {
					JnetpcapAccessManager.getLibraryLock().lock();
					try {
						macAddressHashValue = FormatUtils.mac(netIf.getHardwareAddress()).hashCode();
					} finally {
						JnetpcapAccessManager.getLibraryLock().unlock();
					}
					break;
				}
			}
			UID = new String(new Integer(macAddressHashValue).toString() + new Integer(localMachineNameHashValue)) ;
		} catch (SocketException e) {
			System.err.println("Serious Problem Connecting to Server");
			System.exit(1);
		} catch (UnknownHostException e) {
			System.err.println("Serious Problem Connecting to Server");
			System.exit(1);
		} 
		return UID;
	}
}