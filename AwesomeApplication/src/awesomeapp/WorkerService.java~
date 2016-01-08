package awesomeapp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WorkerService extends Service {
	WebServiceThread statisticsThread;
	PendingServicesThread pendingSevicesThread;
	private static final Lock lock = new ReentrantLock();
	private static final Condition pendingServicesThreadFinished =  lock.newCondition();
	private static volatile boolean pendingServicesReady = false ;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		statisticsThread = new WebServiceThread(this);
		statisticsThread.start();
		pendingSevicesThread = new PendingServicesThread(this);
		pendingSevicesThread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		statisticsThread.interrupt();
		pendingSevicesThread.interrupt();
		super.onDestroy();
	}
	public static boolean isPendingServicesReady() {
		return pendingServicesReady;
	}
	public static void setPendingServicesReady(boolean pendingServicesReady) {
		WorkerService.pendingServicesReady = pendingServicesReady;
	}
	public static Lock getLock() {
		return lock;
	}
	public static Condition getPendingservicesthreadfinished() {
		return pendingServicesThreadFinished;
	}
	
}
