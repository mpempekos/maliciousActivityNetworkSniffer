package memories;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Smpsm {
	private static Smpsm instance = null;
	private static ArrayList<SmpsmEntryMaliciousIpAddress> smpsmMaliciousIpAddresses;
	private static ArrayList<SmpsmEntryMaliciousStringPattern> smpsmMaliciousStringPatterns;
	
	private static Lock lockSmpsmIpAddress = new ReentrantLock();
	private static Lock lockSmpsmStringPattern = new ReentrantLock();
	private Smpsm()
	{
		smpsmMaliciousIpAddresses = new ArrayList<SmpsmEntryMaliciousIpAddress>();
		smpsmMaliciousStringPatterns = new ArrayList<SmpsmEntryMaliciousStringPattern>();
	}
	public static synchronized Smpsm getInstance() {
		if(instance == null)
			instance = new Smpsm();
		return instance;
	}
	public static ArrayList<SmpsmEntryMaliciousIpAddress> getSmpsmEntryMaliciousIpAddress() {
		return smpsmMaliciousIpAddresses;
	}
	public static ArrayList<SmpsmEntryMaliciousStringPattern> getSmpsmMaliciousStringPatterns() {
		return smpsmMaliciousStringPatterns;
	}
	private static boolean addSmpsmEntryMaliciousIpAddress(SmpsmEntryMaliciousIpAddress newEntry)
	{
		lockSmpsmIpAddress.lock();
		try {
			return smpsmMaliciousIpAddresses.add(newEntry);
		} finally {
			lockSmpsmIpAddress.unlock();
		}
		
	}
	private static boolean addSmpsmEntryMaliciousStringPattern(SmpsmEntryMaliciousStringPattern newEntry)
	{
		lockSmpsmStringPattern.lock();
		try {
			return smpsmMaliciousStringPatterns.add(newEntry);
		} finally {
			lockSmpsmStringPattern.unlock();
		}	
	}
	public static void printSmpsm()			
	{
			System.out.println("\t********** S M P S M **********");
			lockSmpsmIpAddress.lock();
			try {
				ListIterator<SmpsmEntryMaliciousIpAddress> iteratorMaliciousIpAddresses = smpsmMaliciousIpAddresses.listIterator();
				System.out.println("\tMalicious IP Addresses : ");
				while(iteratorMaliciousIpAddresses.hasNext()) {
					System.out.println("\t\t" + iteratorMaliciousIpAddresses.next() + "\t\t");
				}
			} finally {
				lockSmpsmIpAddress.unlock();
			}
			lockSmpsmStringPattern.lock();
			try {
			
				ListIterator<SmpsmEntryMaliciousStringPattern> iteratorMaliciouStringPatterns = smpsmMaliciousStringPatterns.listIterator();
				System.out.println("\tMalicious String Patterns : ");
				while(iteratorMaliciouStringPatterns.hasNext()) {
					System.out.println("\t\t" + iteratorMaliciouStringPatterns.next() + "\t\t");
				}
			} finally {
				lockSmpsmStringPattern.unlock();
			}
	}
	public static void updateSmpsmMaliciousIp(String maliciousIp,String interfaceIp,String interfaceName) 
	{
		boolean found = false;
		lockSmpsmIpAddress.lock();
		try {
			for (SmpsmEntryMaliciousIpAddress smpsmCrawl : smpsmMaliciousIpAddresses) {
				if (smpsmCrawl.getMaliciousIp().equals(maliciousIp) && smpsmCrawl.getInterfaceIp().equals(interfaceIp) && smpsmCrawl.getInterfaceName().equals(interfaceName))
				{
						smpsmCrawl.setFrequency();
						found = true;
						break;
				}
			}
			if(!found)
					Smpsm.addSmpsmEntryMaliciousIpAddress(new SmpsmEntryMaliciousIpAddress(interfaceName,interfaceIp,maliciousIp));
		} finally {
			lockSmpsmIpAddress.unlock();
		}
	}
	public static void updateSmpsmMaliciousStringPatternWrapper(List<String> maliciousStringPatternsFound,String interfaceIp,String interfaceName) 
	{
		for (String pattern : maliciousStringPatternsFound) {
			Smpsm.updateSmpsmMaliciousStringPattern(pattern,interfaceIp,interfaceName);
		}
	}
	private static void updateSmpsmMaliciousStringPattern(String maliciousStringPattern,String interfaceIp,String interfaceName) 
	{
		boolean found = false;
		lockSmpsmStringPattern.lock();
		try {
			for (SmpsmEntryMaliciousStringPattern smpsmCrawl : smpsmMaliciousStringPatterns) {
				if(smpsmCrawl.getMaliciousStringPattern().equals(maliciousStringPattern) && smpsmCrawl.getInterfaceIp().equals(interfaceIp) && smpsmCrawl.getInterfaceName().equals(interfaceName))
				{
					smpsmCrawl.setFrequency();
					found = true;
					break;
				}
			}
			if (!found)
				Smpsm.addSmpsmEntryMaliciousStringPattern(new SmpsmEntryMaliciousStringPattern(interfaceName,interfaceIp,maliciousStringPattern));
		} finally {
			lockSmpsmStringPattern.unlock();
		}
	}
	public static void removeSmpsmEntryMaliciousIp(String interfaceName) 
	{
		lockSmpsmIpAddress.lock();
		try {
			ListIterator<SmpsmEntryMaliciousIpAddress> iteratorMaliciousIpAddress = smpsmMaliciousIpAddresses.listIterator();
			while(iteratorMaliciousIpAddress .hasNext()) {
				if(iteratorMaliciousIpAddress .next().getInterfaceName().equals(interfaceName)) {					
					iteratorMaliciousIpAddress.remove();
				}
			}
		} finally {
			lockSmpsmIpAddress.unlock();
		}
	}
	public static void removeSmpsmEntryMaliciousStringPattern(String interfaceName) 
	{
		lockSmpsmStringPattern.lock();
		try {
			ListIterator<SmpsmEntryMaliciousStringPattern> iteratorMaliciousStringPattern = smpsmMaliciousStringPatterns.listIterator();
			while(iteratorMaliciousStringPattern.hasNext()) {
				if(iteratorMaliciousStringPattern.next().getInterfaceName().equals(interfaceName)) {					
					iteratorMaliciousStringPattern.remove();	
				}
			}
		} finally {
			lockSmpsmStringPattern.unlock();
		}
	}
	/**** NEW CODE ****/
	public static void clearFrequencies() 
	{
		lockSmpsmStringPattern.lock();
		try {
			ListIterator<SmpsmEntryMaliciousStringPattern> iteratorMaliciousStringPattern = smpsmMaliciousStringPatterns.listIterator();
			while(iteratorMaliciousStringPattern.hasNext())
				iteratorMaliciousStringPattern.next().clearFrequency();
		} finally {
			lockSmpsmStringPattern.unlock();
		}
		lockSmpsmIpAddress.lock();
		try {
			ListIterator<SmpsmEntryMaliciousIpAddress> iteratorMaliciousIpAddress = smpsmMaliciousIpAddresses.listIterator();
			while(iteratorMaliciousIpAddress .hasNext()) 
				iteratorMaliciousIpAddress.next().clearFrequency();
		} finally {
			lockSmpsmIpAddress.unlock();
		}
	}
	/*****************/
}
