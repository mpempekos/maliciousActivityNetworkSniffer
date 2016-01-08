package networking;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jnetpcap.synchronization.JnetpcapAccessManager;

import org.jnetpcap.PcapIf;

//This thread recognize all network interfaces an creates 
//the proper InterfaceServerThread threads.

public class InterfacesObserverThread extends Thread{	
	private Map<String,Thread> aliveThread;
	public void run() 
	{
		System.out.println("\nObserver Thread (id: " +Thread.currentThread().getId() + ")");
		try {
			int i;	
			List<PcapIf> upIfs = new ArrayList<PcapIf>(NetworkScanner.getNetworkInterfaces());
			List<String> upIfsNames = new ArrayList<String>();
			upIfsNames = NetworkScanner.getNetworkInterfacesNames();
			List<String> currentIfsNames = new ArrayList<String>();
		
			//all threads instances live in this Map paired with an interface name
			Map<String,Thread> threadTable=new HashMap<String,Thread>();
				
			//first we create a thread for every net interfaces (which is up)
			for (i=0; i<upIfsNames.size(); i++) {
				threadTable.put(upIfsNames.get(i) , new InterfaceServerThread(upIfs.get(i).getName()));
				threadTable.get(upIfsNames.get(i)).start();
			}

			while (true) {
				
				//lock every time we use the NOT thread safe jnetpcap library
				JnetpcapAccessManager.getLibraryLock().lock();
				try {	
					// capture in every loop current interfaces
					currentIfsNames = NetworkScanner.getNetworkInterfacesNames();
				}
				finally {
					JnetpcapAccessManager.getLibraryLock().unlock();
				}

				
				//InterFaces to monitor from now on
				List<String> ifsToAdd = new ArrayList<String>(currentIfsNames);
				
				//InterFaces to remove from monitoring
				List<String> ifsToRemove = new ArrayList<String>();

				// Interfaces we will add to monitor = NEW(current) - OLD(up)
				ifsToAdd.removeAll(upIfsNames);

				// Calculating Interfaces to stop monitoring
				for (i=0; i<upIfsNames.size(); i++) {
					if (!(currentIfsNames.contains(upIfsNames.get(i)))) {
						ifsToRemove.add(upIfsNames.get(i));
					}				
				}				
				
				// create a new thread for every new interface
				for (i=0; i<ifsToAdd.size(); i++) {
					//Thread.sleep(1000);
					InterfaceServerThread interfaceServerThread = new InterfaceServerThread(ifsToAdd.get(i));
					threadTable.put(ifsToAdd.get(i) , interfaceServerThread );
					interfaceServerThread.start();					
				}

				// interrupt all threads whose net interface is now down
				for (i=0; i<ifsToRemove.size(); i++) {
					Thread tempThread = threadTable.get(ifsToRemove.get(i));
					if(tempThread.isAlive())
						tempThread.interrupt();
					//remove thread instance from Map
					threadTable.remove(ifsToRemove.get(i));
				}
				aliveThread = threadTable;
				// updating new 'UPed' interfaces
				upIfsNames = new ArrayList<String>(currentIfsNames);	

				//uncomment below to print 'UPed' interfaces (debugging purposes)
				System.out.println(upIfsNames);
				
				// checking for changes every second
				Thread.sleep(1000);				
			}
			
		} catch (InterruptedException e) {
			for (Map.Entry<String,Thread> entry : aliveThread.entrySet()) {
				if(entry.getValue().isAlive()) {
					System.out.println("Interrupting thread with id: " + entry.getValue().getId());
					entry.getValue().interrupt();
				}
			}
			return;
		}
	}
}
