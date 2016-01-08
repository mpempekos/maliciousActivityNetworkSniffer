package jnetpcap.synchronization;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Because jnetpcap library is NOT thread safe, we accomplish 
//safe access from many threads with these locks
public class JnetpcapAccessManager {
	private static final JnetpcapAccessManager instance = new JnetpcapAccessManager();
	private static final Lock libraryUsageLock = new ReentrantLock();
	
	private JnetpcapAccessManager(){}
	public static synchronized JnetpcapAccessManager getInstance() 
	{
		return instance;
	}
	public static synchronized Lock getLibraryLock() 
	{
		return libraryUsageLock;
	}
}
