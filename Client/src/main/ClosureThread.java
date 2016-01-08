package main;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import server.AdderWebServices;
import connectors.ServerConnector;
public class ClosureThread extends Thread{
	private final List<Thread> theadIdsAboutToKill;
	public ClosureThread(List<Thread> theadIdsAboutToKill) {
		this.theadIdsAboutToKill = theadIdsAboutToKill;
	}
	//////////////////////////////////////////////////////////////
	/// Interrupting all threads when program is about to exit ///

	public void run(){
		ListIterator<Thread> iterator = theadIdsAboutToKill.listIterator();
		System.out.println("Closure thread : ");
		while(iterator.hasNext()) {
			if(iterator.next().isAlive()) {
				System.out.println("Interrupting thread with id: " + iterator.previous().getId());
				iterator.next().interrupt();
			}
		}
		AdderWebServices service = ServerConnector.getAdderWebService();
		try {
			System.out.println("Unregistering : " + service.unregister(MainThread.getUID()));
		} catch (RemoteException e) {
			System.err.println("Serious Problem Connecting to Server");
			System.exit(1);
		}
	}
}
