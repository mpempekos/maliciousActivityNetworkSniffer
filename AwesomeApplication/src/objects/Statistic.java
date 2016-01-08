package objects;

public class Statistic {

	private String nodeID;
	private String interfaceName;
	private String interfaceIP;
	private String maliciousPattern;
	private int freq;
	
	public int getFreq() {
		return freq;
	}
	public String getInterfaceIP() {
		return interfaceIP;
	}
	public String getMaliciousPattern() {
		return maliciousPattern;
	}
	public String getNodeID() {
		return nodeID;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	
	
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public void setInterfaceIP(String interfaceIP) {
		this.interfaceIP = interfaceIP;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public void setMaliciousPattern(String maliciousPattern) {
		this.maliciousPattern = maliciousPattern;
	}
	
}
