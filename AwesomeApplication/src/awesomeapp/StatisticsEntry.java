package awesomeapp;

public class StatisticsEntry {
	
	private String nodeID;
	private String interfaceName;
	private String interfaceIP;	
	private String maliciousPattern;
	private int frequency;
	
	public StatisticsEntry() {}
	public StatisticsEntry (String interfaceName,String interfaceIP,String maliciousPattern,int frequency) {
		this.interfaceName = interfaceName;
		this.interfaceIP = interfaceIP;
		this.maliciousPattern = maliciousPattern;
		this.frequency = frequency;
	}
	public String getInterfaceName()
	{
		return interfaceName;
	}
	public String getMaliciousPattern() 
	{
		return maliciousPattern;
	}
	public void setMaliciousPattern(String maliciousPattern) 
	{
		this.maliciousPattern = maliciousPattern;
	}
	public String getInterfaceIp()
	{
		return interfaceIP;
	}
	public void setFrequency()
	{
		this.frequency++;
	}	
	public void setFrequency(int freq)
	{
		this.frequency = freq;
	}
	public int getFrequency()
	{
		return this.frequency;
	}

	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public void setInterfaceIP(String interfaceIP) {
		this.interfaceIP = interfaceIP;
	}
	public String getInterfaceIP() {
		return interfaceIP;
	}
	public String toString() 
	{
		return "NodeID="+nodeID + "interfaceName = " + this.interfaceName + ", interfaceIp = " + this.interfaceIP + "," + "maliciousPattern=" +this.maliciousPattern
				+ " , frequency = " + this.frequency ;
	}
	
}

