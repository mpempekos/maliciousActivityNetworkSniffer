package dbOperations;

import java.util.ArrayList;
import java.util.List;

import objects.Statistic;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import awesomeapp.MainActivity;
import awesomeapp.StatisticalReports;
import awesomeapp.StatisticsEntry;

public class StatisticOperations {

	public void addStatisticIfNotExist(String nodeID, String interfaceName, String interfaceIP, String malPattern, String freq) {

		
		String query = "SELECT * FROM " + DBWrapper.STATISTICS + " WHERE " 
		+ DBWrapper.STATISTIC_NODE_ID + " = ? AND " 
		+ DBWrapper.STATISTIC_INTERFACE_NAME + " = ? AND " 
		+ DBWrapper.STATISTIC_INTERFACE_IP + " = ? AND " 
		+ DBWrapper.STATISTIC_MALICIOUS_PATTERN + " = ? ";
		
		Cursor cursor = DBOperations.database.rawQuery(query, new String[] {nodeID, interfaceName,interfaceIP,malPattern});
		if (cursor == null || !cursor.moveToFirst()) {
			//Log.e("", "///+++Statistic from "+nodeID+" does not exists");
			ContentValues values = new ContentValues();
			values.put(DBWrapper.STATISTIC_NODE_ID, nodeID);
			values.put(DBWrapper.STATISTIC_INTERFACE_NAME, interfaceName);
			values.put(DBWrapper.STATISTIC_INTERFACE_IP, interfaceIP);
			values.put(DBWrapper.STATISTIC_MALICIOUS_PATTERN, malPattern);
			values.put(DBWrapper.STATISTIC_FREQUENCY, freq);
			long retval = DBOperations.database.insert(DBWrapper.STATISTICS, null, values);
			if (retval < 0)
				Log.e("", "///+++Statistic insertion to database failed!");
		}
		else
			;//Log.e("", "///+++statistic does exist!");

		
	}
	
	public void addStatistic(String nodeID, String interfaceName, String interfaceIP, String malPattern, String freq) {
		//Log.e("", "///+++ADDING statistic--> nodeID: "+nodeID);
		ContentValues values = new ContentValues();
		values.put(DBWrapper.STATISTIC_NODE_ID, nodeID);
		values.put(DBWrapper.STATISTIC_INTERFACE_NAME, interfaceName);
		values.put(DBWrapper.STATISTIC_INTERFACE_IP, interfaceIP);
		values.put(DBWrapper.STATISTIC_MALICIOUS_PATTERN, malPattern);
		values.put(DBWrapper.STATISTIC_FREQUENCY, freq);
		long retval = DBOperations.database.insert(DBWrapper.STATISTICS, null, values);
		if (retval < 0)
			Log.e("", "///+++Statistic insertion to database failed!");
	}
	
	public void addAllStatisticsFromNet(List<StatisticalReports> statisticalReports) {
		int i,j;
		//Log.e("", "///+++----------------------ADDING ALL STATISTICS FROM NET-----");
		//deleteAllStatistics();
		if (statisticalReports == null)
			return;
		
		if (!statisticalReports.isEmpty()) {
				for (i=0; i<statisticalReports.size(); i++) {
					//Log.e("", "///+++----------------------ANOTHER CLIENT-----");
					List<StatisticsEntry> clientEntries = statisticalReports.get(i).getStatisticalReportEntries();
					if (!clientEntries.isEmpty()) {
						String currentNodeID = new String(clientEntries.get(0).getNodeID());
						//adding to db nodeID (does not belong to any mobile)
						MainActivity.database.pcNodesOP.addPcNode(currentNodeID, "");
						
						for (j=0; j<clientEntries.size(); j++) {
							//Log.e("", "///----------------report--->");
							StatisticsEntry entry = clientEntries.get(j);
							//Log.e("", "///+++nodeID: "+entry.getNodeID());
							//Log.e("", "///+++nfName: "+entry.getInterfaceName());
							//Log.e("", "///+++ip: "+entry.getInterfaceIP());
							//Log.e("", "///+++pattern: "+entry.getMaliciousPattern());
							//Log.e("", "///+++freq: "+entry.getFrequency());
							MainActivity.database.statisticsOP.addStatisticIfNotExist(entry.getNodeID(), entry.getInterfaceName(), entry.getInterfaceIP(), entry.getMaliciousPattern(), String.valueOf(entry.getFrequency()));
							//MainActivity.database.statisticsOP.addStatistic(entry.getNodeID(), entry.getInterfaceName(), entry.getInterfaceIP(), entry.getMaliciousPattern(), String.valueOf(entry.getFrequency()));
					
						}
					}
				}
		}
		//printAllStatistics();

	}
	

	public void deleteStatistic(Statistic stat){
		//Log.e("", "///+++Try DELETING statistic: " + stat.getNodeID());

		// if i ever want a more specified deletion i will use this ///
		//--------------------------------------------------------///
		//long retval = DBOperations.database.delete(DBWrapper.STATISTICS, DBWrapper.STATISTIC_NODE_ID + " = ? AND " 
		//		+ DBWrapper.STATISTIC_INTERFACE_NAME + " = ? AND "
		//		+ DBWrapper.STATISTIC_MALICIOUS_PATTERN + " = ? ",
		//		new String[] {stat.getNodeID(), stat.getInterfaceName(), stat.getMaliciousPattern()} );
		
		long retval = DBOperations.database.delete(DBWrapper.STATISTICS, DBWrapper.STATISTIC_NODE_ID + " = ? " ,
				new String[] {stat.getNodeID()} );
		//if (retval == 0)
		//	Log.e("", "///+++( deleteStatistic() )Statistic deletion failed!---> "+ stat.getNodeID());
	}
	
	public void deleteNodeStatistics(String nodeID){
		//Log.e("", "///+++Try DELETING statistic: " + stat.getNodeID());

		// if i ever want a more specified deletion i will use this ///
		//--------------------------------------------------------///
		//long retval = DBOperations.database.delete(DBWrapper.STATISTICS, DBWrapper.STATISTIC_NODE_ID + " = ? AND " 
		//		+ DBWrapper.STATISTIC_INTERFACE_NAME + " = ? AND "
		//		+ DBWrapper.STATISTIC_MALICIOUS_PATTERN + " = ? ",
		//		new String[] {stat.getNodeID(), stat.getInterfaceName(), stat.getMaliciousPattern()} );
		
		long retval = DBOperations.database.delete(DBWrapper.STATISTICS, DBWrapper.STATISTIC_NODE_ID + " = ? " ,
				new String[] {nodeID} );
		if (retval == 0)
			Log.e("", "///+++( deleteNodeStatistics() )Statistic deletion failed!---> "+ nodeID);
	}


	public void deleteAllStatistics() {
		//Log.e("", "///+++DELETING ALL statistics: ");
		ArrayList<Statistic> statistics = getAllStatistics();
		for (int i=0; i<statistics.size(); i++)
			deleteStatistic(statistics.get(i));
	}
	
	public ArrayList<Statistic> getAllStatistics() {
		ArrayList<Statistic> stats = new ArrayList<Statistic>();
		Cursor cursor = DBOperations.database.query(DBWrapper.STATISTICS,
				null, null, null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Statistic stat = new Statistic();
			stat.setNodeID(cursor.getString(0));
			stat.setInterfaceName(cursor.getString(1));
			stat.setInterfaceIP(cursor.getString(2));
			stat.setMaliciousPattern(cursor.getString(3));
			stat.setFreq(cursor.getInt(4));
			stats.add(stat);
			cursor.moveToNext();
		}
		cursor.close();
		return stats;
	}
	public StatisticalReports getStatisticsByNodeID(String nodeID) {
		
		StatisticalReports report = new StatisticalReports();
		ArrayList<StatisticsEntry> statsList = new ArrayList<StatisticsEntry>();
		
		Cursor cursor = DBOperations.database.query(DBWrapper.STATISTICS,
				null, 
				DBWrapper.STATISTIC_NODE_ID + " = ? ", 
				new String[] {nodeID}, 
				null, 
				null, 
				null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			StatisticsEntry stat = new StatisticsEntry();
			stat.setNodeID(cursor.getString(0));
			stat.setInterfaceName(cursor.getString(1));
			stat.setInterfaceIP(cursor.getString(2));
			stat.setMaliciousPattern(cursor.getString(3));
			stat.setFrequency(cursor.getInt(4));
			statsList.add(stat);
			
			cursor.moveToNext();
		}
		cursor.close();
		
		report.setStatisticalReportEntries(statsList);
		
		return report;
		
	}
	
	/// For Debugging ///
	public void printAllStatistics() {
	  	  Log.e("", "///+++ALL STATISTICS-----------------------------------");
	  	  ArrayList <Statistic> statistics = new ArrayList<Statistic>();
	  	  statistics= getAllStatistics();
	  	  for (int i=0; i<statistics.size(); i++)
	  		  Log.e("", "///+++statistic--> nodeID: "
		  	  +statistics.get(i).getNodeID() + ", interfaceName: "
		  	  +statistics.get(i).getInterfaceName() + ", ip: "
		  	  +statistics.get(i).getInterfaceIP() + ", mal: "
		  	  +statistics.get(i).getMaliciousPattern() + ", freq: "
		  	  +statistics.get(i).getFreq() );
	}
	
	public List<StatisticalReports> getStatisticsInClientsGroup() {
		List<StatisticalReports> groupedStatistics = new ArrayList<StatisticalReports>();
		ArrayList<String> pcClients = MainActivity.database.pcNodesOP.getAllPcNodes();
		int i=0;
		
		for (i=0; i<pcClients.size(); i++) {
			groupedStatistics.add(getStatisticsByNodeID(pcClients.get(i)));
		}
		return groupedStatistics;
	}
	
	
	
	/// For debugging purposes only! ///
	public List<StatisticalReports> getRandomStats() {
		
	      StatisticsEntry entry1 = new StatisticsEntry();
	      entry1.setNodeID("node111");
	      entry1.setInterfaceName("wlan1");	      
	      entry1.setInterfaceIP("ip111");
	      entry1.setMaliciousPattern("mal111");
	      entry1.setFrequency(123);	      
	      StatisticsEntry entry2 = new StatisticsEntry();
	      entry2.setNodeID("node222");
	      entry2.setInterfaceName("wlan2");
	      entry2.setInterfaceIP("ip2");
	      entry2.setMaliciousPattern("mal111");
	      entry2.setFrequency(123);
	      StatisticsEntry entry3 = new StatisticsEntry();
	      entry3.setNodeID("node223333");
	      entry3.setInterfaceName("wlan3");
	      entry3.setInterfaceIP("ip23");
	      entry3.setMaliciousPattern("mal111323");
	      entry3.setFrequency(13223);
	      StatisticsEntry entry4 = new StatisticsEntry();
	      entry4.setNodeID("node3");
	      entry4.setInterfaceName("wlan4");
	      entry4.setInterfaceIP("ip111");
	      entry4.setMaliciousPattern("mal111");
	      entry4.setFrequency(123);
	      StatisticsEntry entry5 = new StatisticsEntry();
	      entry5.setNodeID("node33");
	      entry5.setInterfaceName("wlan5");
	      entry5.setInterfaceIP("ip111");
	      entry5.setMaliciousPattern("mal111");
	      entry5.setFrequency(123);
	      StatisticsEntry entry6 = new StatisticsEntry();
	      entry6.setNodeID("node333");
	      entry6.setInterfaceName("wlan6");
	      entry6.setInterfaceIP("ip111");
	      entry6.setMaliciousPattern("mal111");
	      entry6.setFrequency(123);
	      
	      List<StatisticsEntry> entriesList1 = new ArrayList<StatisticsEntry>();
	      entriesList1.add(entry1);

	      List<StatisticsEntry> entriesList2 = new ArrayList<StatisticsEntry>();
	      entriesList2.add(entry2);
	      entriesList2.add(entry3);

	      List<StatisticsEntry> entriesList3 = new ArrayList<StatisticsEntry>();
	      entriesList3.add(entry4);
	      entriesList3.add(entry5);
	      entriesList3.add(entry6);
	      
	      StatisticalReports report1 = new StatisticalReports();
	      StatisticalReports report2 = new StatisticalReports();
	      StatisticalReports report3 = new StatisticalReports();

	      report1.setStatisticalReportEntries(entriesList1);
	      report2.setStatisticalReportEntries(entriesList2);
	      report3.setStatisticalReportEntries(entriesList3);
	      
	      List<StatisticalReports> allReports = new ArrayList<StatisticalReports>();

	      allReports.add(report1);
	      allReports.add(report2);
	      allReports.add(report3);
	     
	      return allReports;
	}
		
}
