package server;

import gui.ActionInsertListener;
import gui.ActionPanel;
import gui.ReportTerminalOperations;
import gui.StatisticsPanel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import nodes.NodeIdentifier;
import nodes.OnlineNodesMemory;
import db.DBConnector;
import dbObjects.StatisticsEntry;
import exchangeableObjects.AvailableNodes;
import exchangeableObjects.MaliciousPatterns;
import exchangeableObjects.StatisticalReports;

@WebService(endpointInterface="server.AdderWebServices")
@SOAPBinding(style=Style.DOCUMENT)
public class AdderWebServicesImpl implements AdderWebServices {
	@Override
	@WebMethod(operationName = "register")
	public boolean register(String nodeID) 
	{
		Date currentDate = new Date();
		Timestamp timestamp = new Timestamp(currentDate.getTime());
		NodeIdentifier ui = new NodeIdentifier(nodeID,timestamp);
		DBConnector.clientDbRegistration(ui);
		/*CODE FOR GUI*/
		/* setText() supposed to be thread safe (oracle documentation)*/
		gui.ReportTerminalOperations.addReport("> Node " + nodeID + " just registered !");    	
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);
		
		return OnlineNodesMemory.addNode(ui);
	}
	@Override
	@WebMethod
	@WebResult(name="MaliciousPatterns")
	public MaliciousPatterns maliciousPatternsRequest(String nodeID) 
	{

		/*
		/*CODE FOR GUI*/
		/* setText() supposed to be thread safe (oracle documentation)
		gui.ReportTerminalOperations.addReport(">Node " + nodeID + " requested Malicious Patterns");    	
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);
		*/
		MaliciousPatterns maliciousPatterns = null;
		List<String> onlineNodes = OnlineNodesMemory.getRegisteredNodes();
		if(onlineNodes.contains(nodeID))
			maliciousPatterns = OnlineNodesMemory.requestMaliciousPatterns(nodeID);
		// TODO Auto-generated method stub
		//System.out.println(maliciousPatterns.getMaliciousIPsList().);
		//System.out.println(maliciousPatterns.getMaliciousStringPatternsList().toString());

		return maliciousPatterns;
	}
	@Override
	@WebMethod
	public void maliciousPatternsStatisticalReports(String nodeID,StatisticalReports statisticalReport) 
	{	
		/*
		/*CODE FOR GUI*/
		/* setText() supposed to be thread safe (oracle documentation)
		gui.ReportTerminalOperations.addReport(">Node " + nodeID + " sent a Statistical Report ");    	
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);
		*/
		List<String> onlineNodes = OnlineNodesMemory.getRegisteredNodes();
		if(onlineNodes.contains(nodeID)) {
			if(!statisticalReport.isEmptyOrZero()) 
				OnlineNodesMemory.setNodeValidRequestTime(nodeID);
		
			List<StatisticsEntry> statistics = statisticalReport.getStatisticalReportEntries();
			for(StatisticsEntry crawl : statistics) {
				DBConnector.updateStatistics(crawl,nodeID);
			}
		}
	}
	@Override
	@WebMethod
	public boolean unregister(String nodeID) 
	{
		/*CODE FOR GUI*/
		/* setText() supposed to be thread safe (oracle documentation)*/
		gui.ReportTerminalOperations.addReport("> Node " + nodeID + " just unregistered !");    	
		String newReport = new String(StatisticsPanel.textArea.getText() + ReportTerminalOperations.getReport());
		StatisticsPanel.textArea.setText(newReport);
		ActionPanel.textArea.setText(newReport);
		
		return OnlineNodesMemory.removeNode(nodeID);
	}
	/*********************************************************/
	/*Following services for Android smartphones and tablets */
	/*********************************************************/
	@Override
	public boolean registerAndroid(String username, String password,AvailableNodes nodes) 
	{	
		System.out.println(nodes.getNodes().size());
		ListIterator<String> listIterator = nodes.getNodes().listIterator();
		while(listIterator.hasNext())
			System.out.println(listIterator.next());
		System.out.println(username);
		System.out.println(password);
		return DBConnector.register(username,password,nodes);
	}
	@Override
	public List<StatisticalReports> retrieveStatistics(String username,String password) {
		System.out.println("retrieveStatistics");
		System.out.println(username);
		System.out.println(password);
		return DBConnector.retrieveStatistics(username,password);
	
	}
	@Override
	public String retrieveMaliciousPatterns(String username, String password) {
		MaliciousPatterns patterns =  DBConnector.getAllMaliciousPatternsFromDb();
		if(patterns == null)
			return null;
		List<String> ips = patterns.getMaliciousIPsList();
		List<String> strings = patterns.getMaliciousStringPatternsList();
		ListIterator<String> listIteratorIPs = ips.listIterator();
		ListIterator<String> listIteratorStrings = strings.listIterator();
		StringBuilder builder = new StringBuilder();
		while(listIteratorIPs.hasNext()) {
			builder.append(listIteratorIPs.next()).append("+");
		}	
		while(listIteratorStrings.hasNext()) {
			builder.append(listIteratorStrings.next()).append("+");
		}
		return builder.toString();
	}
	@Override
	public void insertMaliciousPatterns(String username, String password,String maliciousIP, String stringPattern) {
		System.out.println(username);
		System.out.println(password);
		System.out.println(maliciousIP);
		System.out.println(stringPattern);
		if(maliciousIP.isEmpty()) {
			DBConnector.insertMaliciousPattern(stringPattern,"STRINGPATTERN");
			OnlineNodesMemory.addMaliciousStringPattern(stringPattern);
		}
		else {
			DBConnector.insertMaliciousPattern(maliciousIP,"IP");
			OnlineNodesMemory.addMaliciousIp(maliciousIP);
		}
		ActionInsertListener.addFromWebService();
	}
	@Override
	public int login(String username, String password) {
		System.out.println("login");
		System.out.println(username);
		System.out.println(password);
		return DBConnector.login(username,password);
	}
	@Override
	public boolean logout(String username,String password) {
		System.out.println("logout");
		if(DBConnector.login(username,password) > 0)
			return true;
		return false;
	}
	@Override
	public boolean delete(String username,String password,String nodeID) {
		if(OnlineNodesMemory.getRegisteredNodes().contains(nodeID))
			OnlineNodesMemory.removeNode(nodeID);
		if(DBConnector.login(username,password) > 0) {
			DBConnector.deleteNode(nodeID);
			return true;
		}
		return false;
	}


}
