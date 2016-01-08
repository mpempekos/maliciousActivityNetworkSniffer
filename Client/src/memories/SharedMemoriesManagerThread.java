package memories;
import java.rmi.RemoteException;

import server.AdderWebServices;
import server.StatisticsEntry;
import connectors.ServerConnector;
import main.MainThread;

//This thread creates MPSM SMPSM ,and prints on console an image of them
public class SharedMemoriesManagerThread extends Thread {
	public void run() 
	{

		Mpsm.getInstance();
		Smpsm.getInstance();
		MainThread.getMemoriesLock().lock();
		try {
			MainThread.setMemoriesReady(true); 
			System.out.println("\nManager Thread (id: "+Thread.currentThread().getId()+") : JUST created MPSM,SMPSM!");
			MainThread.getMemoriesNotReady().signal();
		} finally {
			MainThread.getMemoriesLock().unlock();
		}
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				return;
			}
			//regularly access MPSM and SMPSM to get an image and send it "adder" but at this points just prints them on console 
			//instead of printing should get updates.
			/**** NEW CODE****/
			//should be inside try catch of sleep ?
			if(!((Smpsm.getSmpsmEntryMaliciousIpAddress().isEmpty()) && (Smpsm.getSmpsmMaliciousStringPatterns().isEmpty()))) {
				AdderWebServices adderWebService = ServerConnector.getAdderWebService();
				StatisticsEntry[] SmpsmEntries = new StatisticsEntry[Smpsm.getSmpsmEntryMaliciousIpAddress().size()+Smpsm.getSmpsmMaliciousStringPatterns().size()];
				int i = 0; 
				if(!Smpsm.getSmpsmEntryMaliciousIpAddress().isEmpty()) {
					for(SmpsmEntryMaliciousIpAddress temp : Smpsm.getSmpsmEntryMaliciousIpAddress()) 
						SmpsmEntries[i++] = new StatisticsEntry(MainThread.getUID(),temp.getInterfaceName(),temp.getInterfaceIp(),temp.getMaliciousIp(),temp.getFrequency());
				}
				if(!Smpsm.getSmpsmMaliciousStringPatterns().isEmpty()) {
					for(SmpsmEntryMaliciousStringPattern temp : Smpsm.getSmpsmMaliciousStringPatterns()) 
						SmpsmEntries[i++] = new StatisticsEntry(MainThread.getUID(),temp.getInterfaceName(),temp.getInterfaceIp(),temp.getMaliciousStringPattern(),temp.getFrequency());
				}
				try {
					adderWebService.maliciousPatternsStatisticalReports(MainThread.getUID(),SmpsmEntries);
					Smpsm.clearFrequencies();
					//Smpsm.clearFrequencies();
				} catch (RemoteException e) {
					System.err.println("Serious Problem Connecting to Server");
					System.exit(1);
				}
			}
			/**** UNTILL HERE****/
			Mpsm.printMpsm();
			Smpsm.printSmpsm();	
		}
	}

}

