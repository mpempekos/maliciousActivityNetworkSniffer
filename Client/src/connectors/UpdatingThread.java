package connectors;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ListIterator;
import main.MainThread;
import memories.Mpsm;
import server.AdderWebServices;

//This thread request updates from "adder"
//also adds the updates to MPSM.

public class UpdatingThread extends Thread {
	private List<String> listMaliciousIPsPatterns;
	private String pathToMaliciousFile = null;
	public UpdatingThread(){}
	public UpdatingThread(String pathToMaliciousFile){
		this.pathToMaliciousFile = new String(pathToMaliciousFile);
	}
	public void run()
	{
		//readMaliciousPatternsFromFile();
		update();
		MainThread.getMpsmFilledLock().lock();
		try {
			MainThread.setMpsmFilled(true);
			System.out.println("\nUpdating thread (id: "+Thread.currentThread().getId()+") : just filled MPSM! ready to begin!");
		} finally {
			MainThread.getMpsmNotEmpty().signal();
			MainThread.getMpsmFilledLock().unlock();
		}
		while(true) {
			try {
				Thread.sleep(8000);
				update();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}	
	}
	private boolean addMaliciousIpAddressesToMpsm(String maliciousIpAddress)
	{
		return Mpsm.addMpsmEntryMaliciousIpAddress(maliciousIpAddress) ; 
	}
	private boolean addMaliciousStringPatternToMpsm(String maliciousStringPattern)
	{
		return Mpsm.addMpsmEntryMaliciousStringPattern(maliciousStringPattern);
	}
	/****************** NEW CODE ******************/
	private void update() 
	{
		AdderWebServices adderWebService = ServerConnector.getAdderWebService();
		server.MaliciousPatterns maliciousPatterns;
		try {
			if((maliciousPatterns = adderWebService.maliciousPatternsRequest(MainThread.getUID())) == null ) {
				System.err.println("This node maybe is deleted");
				System.exit(1);
				return;
			}
			String[] maliciousStringPatterns = maliciousPatterns.getMaliciousStringPatternsList();
			if(maliciousStringPatterns != null ) {
				for(String entry : maliciousStringPatterns)
					Mpsm.addMpsmEntryMaliciousStringPattern(entry);
			}
			String[] maliciousIpAddresses = maliciousPatterns.getMaliciousIPsList();
			if(maliciousIpAddresses != null) {
				for(String entry : maliciousIpAddresses)
					Mpsm.addMpsmEntryMaliciousIpAddress(entry);
			}
		} catch (RemoteException e) {
			System.err.println("Serious Problem Connecting to Server");
			System.exit(1);
		}
	}
	private void readMaliciousPatternsFromFile() {
		Path pathToMaliciouIPsAndPatternFile;
		if(pathToMaliciousFile == null) {
			pathToMaliciouIPsAndPatternFile = Paths.get("MaliciousIPsPatterns");
		}
		else {
			pathToMaliciouIPsAndPatternFile = Paths.get(pathToMaliciousFile);
		}
		try {
			listMaliciousIPsPatterns = Files.readAllLines(pathToMaliciouIPsAndPatternFile,StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ListIterator<String> fileLinesIterator =  listMaliciousIPsPatterns.listIterator();
		while(fileLinesIterator.hasNext()) {
			if(fileLinesIterator.next().equals("Malicious Ips")) {
				while((!fileLinesIterator.next().isEmpty())) {
					if(!addMaliciousIpAddressesToMpsm(fileLinesIterator.previous()))
						System.out.println("Problem detected adding " + fileLinesIterator.next() + "to MPSM");
					fileLinesIterator.next();			
				}
				break;
			}	
		}
		while(fileLinesIterator.hasNext()) {
			if(!fileLinesIterator.previous().equals("Malicious string patterns")) {
				fileLinesIterator.next();
				fileLinesIterator.next();
				while(fileLinesIterator.hasNext()) { 
					if(!addMaliciousStringPatternToMpsm(fileLinesIterator.next()))
						System.out.println("Problem detected adding " + fileLinesIterator.next() + "to MPSM");
				}
			}
		}
	}
	/***********************************************/
}
