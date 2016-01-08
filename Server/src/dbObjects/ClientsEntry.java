package dbObjects;

public class ClientsEntry {
	/*Change name to : ClientsEntry.java */
	String nodeID;
	String type;
	String lastReport;
	int numOfMalicious;
	
	public ClientsEntry() {
		nodeID = new String("");
		type = new String("");
		lastReport = new String("");
	}
	
	public String getNodeID() {
		return this.nodeID;
	}
	public String getType() {
		return this.type;
	}
	public String getLastReport() {
		return this.lastReport;
	}
	public int getNumOfMalicious() {
		return this.numOfMalicious;
	}

	
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setLastReport(String lastReport) {
		this.lastReport = lastReport;
	}
	public void setNumOfMalicious(int numOfReport) {
		this.numOfMalicious = numOfReport;
	}
	
}
