package awesomeapp;

import java.util.List;

import objects.PendingInsertions;
import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class PendingServicesThread extends Thread{
	Service service;
	boolean running = true;
	public PendingServicesThread(Service service) {
		this.service = service;
	}
	@Override
	public void run() {
		while(running) {
			try {
				if(isNetworkAvailable()) {
					List<String> pendingDeletes = MainActivity.database.pendingDeletesOP.getAllPendingDeletes();
					if((pendingDeletes != null) && (!pendingDeletes.isEmpty())) {
						for(String nodeID : pendingDeletes) {
							WebServiceConnector.delete("admin","1234",nodeID);
						}
						MainActivity.database.pendingDeletesOP.deleteAllPendingInsertions();
					}
					List<PendingInsertions> pendingInserts =  MainActivity.database.pendingIntertionsOP.getAllPendingInsertionsWithType();
					if((pendingInserts != null) && (!pendingInserts.isEmpty())) {
						for(PendingInsertions pending : pendingInserts) {
							if(pending.getType() == "ip")
								WebServiceConnector.insertMaliciousPatterns("admin","1234",pending.getName(),null);
							else 
								WebServiceConnector.insertMaliciousPatterns("admin","1234",null,pending.getName());
						}
					}
				}
				try {
					WorkerService.getLock().lock();
					WorkerService.setPendingServicesReady(true);
					WorkerService.getPendingservicesthreadfinished().signal();
				} finally {
					WorkerService.getLock().unlock();
				}
				Thread.sleep(40000);
			} catch (InterruptedException e) {
				running = false;
			}	
		}
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) service.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
