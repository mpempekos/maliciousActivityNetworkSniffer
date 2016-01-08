package memories;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mpsm {
	private static Mpsm instance = null ;
	private static List<String> mpsmMaliciousIpAddresses;				
	private static List<String> mpsmMaliciousStringPatterns;
	private static final Lock lockMpsmIpAddress = new ReentrantLock();
	private static final Lock lockMpsmStringPattern = new ReentrantLock();
	private Mpsm()
	{
		mpsmMaliciousIpAddresses = Collections.synchronizedList(new ArrayList<String>());
		mpsmMaliciousStringPatterns = Collections.synchronizedList(new ArrayList<String>());
	}
	public static synchronized Mpsm getInstance() {
		if(instance == null)
			instance = new Mpsm();
		return instance;
	}
	public static  boolean addMpsmEntryMaliciousIpAddress(String newEntry)
	{
		lockMpsmIpAddress.lock();
		try {
			return mpsmMaliciousIpAddresses.add(newEntry);
		} finally {
			lockMpsmIpAddress.unlock();
		}
	}
	public static boolean addMpsmEntryMaliciousStringPattern(String newEntry)
	{
		lockMpsmStringPattern.lock();
		try {
			return mpsmMaliciousStringPatterns.add(newEntry);
		} finally {
			lockMpsmStringPattern.unlock();
		}
	}
	public static void printMpsm()
	{	
			System.out.println("\t********** M P S M **********");
			System.out.println("\tMalicious IP Addresses : ");
			lockMpsmIpAddress.lock();
			try {
				ListIterator<String> iteratorMaliciousIpList = mpsmMaliciousIpAddresses.listIterator();
				while(iteratorMaliciousIpList.hasNext()) {
					System.out.println("\t\t" + iteratorMaliciousIpList.next() + "\t\t");
				}
			} finally {
				lockMpsmIpAddress.unlock();
			}
			lockMpsmStringPattern.lock();
			try {
				ListIterator<String> iteratorMaliciouStringPatterns = mpsmMaliciousStringPatterns.listIterator();
				System.out.println("\tMalicious String Patterns : ");
				while(iteratorMaliciouStringPatterns.hasNext()) {
					System.out.println("\t\t" + iteratorMaliciouStringPatterns.next() + "\t\t");
				}
			} finally {
				lockMpsmStringPattern.unlock();
			}
			
	}
	public static boolean searchMatchingEntryMaliciousIpAddress(String possibleMatchingEntry)
	{
		lockMpsmIpAddress.lock();
		try {
			return mpsmMaliciousIpAddresses.contains(possibleMatchingEntry);
		} finally {
			lockMpsmIpAddress.unlock();
		}
	}
	public static List<String> searchMatchingEntryMaliciousStringPattern(String payloadStr)
	{
		List<String> matchedMaliciousStringPatterns = new ArrayList<String>(); 
		lockMpsmStringPattern.lock();
		try {
			for (String possibleMaliciousString: mpsmMaliciousStringPatterns ) {
				if((MyPattern.searchMaliciousPatterns(possibleMaliciousString,payloadStr)) > 0) {
					//System.out.println ("\t\t" + possibleMaliciousString + "\t\t");
					System.out.print("'" + possibleMaliciousString + "' <--");
					matchedMaliciousStringPatterns.add(possibleMaliciousString);
				}
			}
			if(matchedMaliciousStringPatterns.isEmpty())
				return null;
			else 
				return matchedMaliciousStringPatterns;
		}
		finally {
			lockMpsmStringPattern.unlock();
		}
	}
}
