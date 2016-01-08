package awesomeapp;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StatisticalReports {
	private List<StatisticsEntry> statisticalReportList ;
	public StatisticalReports() {
		statisticalReportList = new ArrayList<StatisticsEntry>();
	}
	public List<StatisticsEntry> getStatisticalReportEntries() 
	{
		return statisticalReportList;
	}
	public void setStatisticalReportEntries(List<StatisticsEntry> statisticalReportEntries) 
	{
		this.statisticalReportList = statisticalReportEntries;
	}
	public boolean isEmptyOrZero() 
	{
		if(statisticalReportList.isEmpty())
			return true;
		if(statisticalReportList != null) {
			int zeroFrequncies = 0 ;
			for(StatisticsEntry entry : statisticalReportList) {
				if(entry.getFrequency() == 0) 
					zeroFrequncies++;
			}
			if(statisticalReportList.size() == zeroFrequncies)
				return true;
		}
		return false;
	}
	public static void printList(List<StatisticalReports> statistics) {
		// TODO Auto-generated method stub
		for(StatisticalReports temp : statistics) {
			List<StatisticsEntry> entries = temp.getStatisticalReportEntries();
			for(StatisticsEntry tempEntry : entries) {
				System.out.println(tempEntry.toString());
			}
		}
	}
	@Override
	public String toString() {
		int totalMaliciousFrequencies = 0;
		List<String> interfaces = new ArrayList<String>();
		String nodeID = null;
		String sampleMalicious = null;
		String sampleMalicious1 = null;
		for(StatisticsEntry entry: statisticalReportList) {
			if(nodeID == null)
				nodeID = entry.getNodeID();
			totalMaliciousFrequencies += entry.getFrequency();
			if(sampleMalicious == null )
				sampleMalicious = entry.getMaliciousPattern();
			else if((sampleMalicious1 == null) && (!entry.getMaliciousPattern().equals(sampleMalicious)))
				sampleMalicious1 = entry.getMaliciousPattern();
			String interfaceName = entry.getInterfaceName();
			if(!interfaces.contains(interfaceName)){
				interfaces.add(interfaceName);
			}
		}
		StringBuilder builder = new StringBuilder();
		builder.append("User ID : "+nodeID+"\n");
		builder.append("Interfaces : ");
		ListIterator<String> iterator = interfaces.listIterator();
		while(iterator.hasNext()) {
			builder.append(iterator.next());
			if(iterator.hasNext())
				builder.append(",");
		}
		builder.append("\n");
		builder.append("Sample malicious patterns : ");
		if(sampleMalicious != null)
			builder.append(sampleMalicious);
		if(sampleMalicious1 != null) {
			builder.append(",");
			builder.append(sampleMalicious1);
		}
		builder.append("\n");
		builder.append("Total malicious packets : ");
		builder.append(totalMaliciousFrequencies);
		return builder.toString();
	}
	public List<String> getInterfaces() {
		if(statisticalReportList != null) {
			List<String> interfaces = new ArrayList<String>();
			for(StatisticsEntry entry : statisticalReportList){
				if(!interfaces.contains(entry.getInterfaceName()))
					interfaces.add(entry.getInterfaceName());						
			}
			return interfaces;
		}
		return null;
	}
	
}
