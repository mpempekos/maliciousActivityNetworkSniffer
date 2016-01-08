package awesomeapp;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class WebServiceThread extends Thread{
	Service service;
	boolean running = true;
	public WebServiceThread(Service service) {
		this.service = service;
	}
	@Override
	public void run() {
		while(running) {
			try {
				try {
					WorkerService.getLock().lock();
					while(!WorkerService.isPendingServicesReady())
						WorkerService.getPendingservicesthreadfinished().await();
					WorkerService.getPendingservicesthreadfinished().signal();
				} finally {
					WorkerService.getLock().unlock();		
				}
				if(isNetworkAvailable()) {
					List<StatisticalReports> reports = WebServiceConnector.retrieveStatistics(MainActivity.username,MainActivity.password);
					if(reports != null) {
						if((!reports.isEmpty())) 
							MainActivity.database.statisticsOP.addAllStatisticsFromNet(reports);
					}
				}
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				running = false;
			}
		}
		Log.i("///","Thread Successfully ended");
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) service.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
