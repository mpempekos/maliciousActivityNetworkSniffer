package nodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import db.DBConnector;
import exchangeableObjects.MaliciousPatterns;

public class OnlineNodesMemory {
	private static OnlineNodesMemory instance = null;
	private static List<NodeIdentifier> listOnlineNodes = new ArrayList<NodeIdentifier>();
	private static Map<String,Timestamp> maliciousStringPatterns = new HashMap<String, Timestamp>(); 
	private static Map<String,Timestamp> maliciousIPs = new HashMap<String, Timestamp>();
	private static long inactivationTime;
	private static final Lock onlineNodesListLock = new ReentrantLock();
	private static final Lock stringPatternsMapLock = new ReentrantLock();
	private static final Lock maliciousIPsMapLock = new ReentrantLock();
	private OnlineNodesMemory()
	{
		listOnlineNodes = Collections.synchronizedList(listOnlineNodes);
		maliciousStringPatterns = Collections.synchronizedMap(maliciousStringPatterns);
		maliciousIPs = Collections.synchronizedMap(maliciousIPs);
		setInactivationTime();
		fillMaliciousPatternListFromDb();
	}
	private static void setInactivationTime() 
	{
		File propertiesFile = new File("config.properties");
		if(propertiesFile.exists()) {
			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(propertiesFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
			inactivationTime = Long.parseLong(properties.getProperty("INACTIVATIONTIME"));
		} 
		else {
			inactivationTime = 1200000 ;
		}
	}
	public static synchronized OnlineNodesMemory getInstance() 
	{
		if(instance == null)
			instance = new OnlineNodesMemory();
		return instance;
	}
	public static boolean addNode(NodeIdentifier ui)
	{
		System.out.println("Adding node " + ui.getNodeID());
		onlineNodesListLock.lock();
		try {
			for(NodeIdentifier temp : listOnlineNodes) {
				if(temp.getNodeID().equals(ui.getNodeID()))
					return false;
			}		
			return listOnlineNodes.add(ui);  
		} finally {
			printOnlineUsers();
			onlineNodesListLock.unlock();
		}
	}
	public static boolean removeNode(String nodeID) 
	{	
		System.out.println("Removing node " + nodeID);
		onlineNodesListLock.lock();
		try {
			for(NodeIdentifier temp : listOnlineNodes) {
				if(temp.getNodeID().equals(nodeID)) 
					return listOnlineNodes.remove(temp);
			} 
			return false;
		} finally {
			printOnlineUsers();
			onlineNodesListLock.unlock();
		}
	}
	private static Timestamp getNodeRegistrationTime(String uid)
	{
		onlineNodesListLock.lock();
		try {
			for(NodeIdentifier nodeID : listOnlineNodes) {
				if(nodeID.getNodeID().equals(uid))
					return nodeID.getRegistrationTime();
			}
			return null;
		}
		finally {
			onlineNodesListLock.unlock();
		}
	}
	private static Timestamp getNodeLastRequestTime(String uid)
	{
		onlineNodesListLock.lock();
		try {
			for(NodeIdentifier nodeID : listOnlineNodes) {
				if(nodeID.getNodeID().equals(uid))
					return nodeID.getLastRequestTime();
			}
			return null;
		}
		finally {
			onlineNodesListLock.unlock();
		}
	}
	private static NodeIdentifier getNodeIdentifier(String nodeID) 
	{
		onlineNodesListLock.lock();
		try {
			NodeIdentifier tempNode = null;
			for(NodeIdentifier temp : listOnlineNodes) {
				if(temp.getNodeID().equals(nodeID))
					return temp;
			}
			return tempNode;
		} finally {
			onlineNodesListLock.unlock();
		}
	}
	private static boolean isRegisteredNode(String nodeID) 
	{
		onlineNodesListLock.lock();
		try {
			for(NodeIdentifier temp : listOnlineNodes) {
				if(temp.getNodeID().equals(nodeID))
					return true;
			}
			return false;
		} finally {
			onlineNodesListLock.unlock();
		}
	}
	/*debugging use*/
	private static void printOnlineUsers() 
	{
		onlineNodesListLock.lock();
		if(listOnlineNodes.isEmpty())
			System.out.println("Empty list of nodes");
		try {
			for(NodeIdentifier temp : listOnlineNodes)
				System.out.println("[" + temp.getNodeID() + "," + temp.getRegistrationTime() + "]");
		} finally {
			onlineNodesListLock.unlock();
		}
	}
	public static ArrayList<String> getRegisteredNodes()
	{
		onlineNodesListLock.lock();
		try {
			ArrayList<String> onlineNodeIDsList = new ArrayList<String>();
			for(NodeIdentifier nodeID : listOnlineNodes)
				onlineNodeIDsList.add(nodeID.getNodeID());
			return onlineNodeIDsList;
		} finally {
			onlineNodesListLock.unlock();
		}
	}
	public static void setNodeValidRequestTime(String uid) {
		Date currentDate = new Date();
		Timestamp currentTime = new Timestamp(currentDate.getTime());
		getNodeIdentifier(uid).setLastValidStatisticalReportTime(currentTime);
	}
	public static void checkAndRemoveInactiveNodes()
	{
		System.out.println("checking for inactive nodes");
		Date currentDate = new Date();
		Timestamp currentTime = new Timestamp(currentDate.getTime());
		onlineNodesListLock.lock();
		try {  
			if(!listOnlineNodes.isEmpty()) {
				ListIterator<NodeIdentifier> iterator = listOnlineNodes.listIterator();
				while(iterator.hasNext()) {
					Timestamp statisticsTimeSent = iterator.next().getLastValidStatisticalReportTime();
					if(statisticsTimeSent != null) {
						if((currentTime.getTime()) - (statisticsTimeSent.getTime()) > inactivationTime)
							iterator.remove();
					}
					else
						iterator.remove();
				}	
			}
		} finally {
			printOnlineUsers();
			onlineNodesListLock.unlock();
		}
	}
	/*if needed to fill memory from db */
	public static void fillMaliciousPatternListFromDb() 
	{
		Date currentDate = new Date();
		Timestamp currentTime = new Timestamp(currentDate.getTime());
		MaliciousPatterns maliciousPatterns = DBConnector.getAllMaliciousPatternsFromDb();
		stringPatternsMapLock.lock();
		try {
			for(String entry : maliciousPatterns.getMaliciousIPsList()) {
				maliciousIPs.put(entry, currentTime);
			}
		} finally {
			stringPatternsMapLock.unlock();
		}
		maliciousIPsMapLock.lock();
		try {
			for(String entry : maliciousPatterns.getMaliciousStringPatternsList()) {
				maliciousStringPatterns.put(entry, currentTime);
			}
		} finally {
			printMaliciousPatterns();
			maliciousIPsMapLock.unlock();
		}
	}
	public static void addMaliciousStringPattern(String maliciousStringPattern) 
	{	
		Date currentDate = new Date();
		Timestamp currentTime = new Timestamp(currentDate.getTime());
		stringPatternsMapLock.lock();
		try {
			maliciousStringPatterns.put(maliciousStringPattern,currentTime); 
		} finally {
			stringPatternsMapLock.unlock();
		}
	}
	public static void addMaliciousIp(String maliciousIP) {
		Date currentDate = new Date();
		Timestamp currentTime = new Timestamp(currentDate.getTime()); 
		maliciousIPsMapLock.lock();
		try {
			maliciousIPs.put(maliciousIP, currentTime);
		} finally {
			maliciousIPsMapLock.unlock();
		}
	}
	public static MaliciousPatterns requestMaliciousPatterns(String nodeID) 
	{
		if(isRegisteredNode(nodeID)) {
			Date currentDate = new Date();
			Timestamp currentTime = new Timestamp(currentDate.getTime()); 
			if(!getNodeIdentifier(nodeID).isUpdatedAtLeastOnce()) {
				getNodeIdentifier(nodeID).setUpdatedAtLeastOnce(true);
				getNodeIdentifier(nodeID).setLastRequestTime(currentTime);
				return getAllMaliciousPatterns();
			}
			else {
				MaliciousPatterns maliciousPatterns = new MaliciousPatterns();
				Timestamp nodeTimestamp = getNodeLastRequestTime(nodeID);
				maliciousIPsMapLock.lock();
				try {
					for(Map.Entry<String,Timestamp> entry : maliciousIPs.entrySet()) {
						if(nodeTimestamp.before(entry.getValue()))
							maliciousPatterns.getMaliciousIPsList().add(entry.getKey());
					}
				} finally {
					maliciousIPsMapLock.unlock();
				}
				stringPatternsMapLock.lock();
				try {
					for(Map.Entry<String,Timestamp> entry : maliciousStringPatterns.entrySet()) {
						if(nodeTimestamp.before(entry.getValue()))
							maliciousPatterns.getMaliciousStringPatternsList().add(entry.getKey());
					}
					return maliciousPatterns;
				} finally {
					getNodeIdentifier(nodeID).setLastRequestTime(currentTime);
					stringPatternsMapLock.unlock();
				}
			}
		}
		else {
			return null;
		}
	}
	private static MaliciousPatterns getAllMaliciousPatterns()
	{
		MaliciousPatterns maliciousPatterns = new MaliciousPatterns();
		maliciousIPsMapLock.lock();
		try {
			for(Map.Entry<String,Timestamp> entry : maliciousIPs.entrySet())
				maliciousPatterns.getMaliciousIPsList().add(entry.getKey());
		} finally {
			maliciousIPsMapLock.unlock();
		}
		stringPatternsMapLock.lock();
		try {
			for(Map.Entry<String,Timestamp> entry : maliciousStringPatterns.entrySet()) 
				maliciousPatterns.getMaliciousStringPatternsList().add(entry.getKey());
		} finally {
			stringPatternsMapLock.unlock();
		}
		return maliciousPatterns;
	}
	public static void printMaliciousPatterns() 
	{
		maliciousIPsMapLock.lock();
		try {
			for(Map.Entry<String,Timestamp> entry : maliciousIPs.entrySet()) 
				System.out.println(entry.getKey() + " " + entry.getValue());
		} finally {
			maliciousIPsMapLock.unlock();
		}
		stringPatternsMapLock.lock();
		try {
			for(Map.Entry<String,Timestamp> entry : maliciousStringPatterns.entrySet()) 
				System.out.println(entry.getKey() + " " + entry.getValue());
		} finally {
			stringPatternsMapLock.unlock();
		}
	}
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}	
}
