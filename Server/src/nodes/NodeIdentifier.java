package nodes;
import java.sql.Timestamp;

public class NodeIdentifier {
	private String nodeID;
	private Timestamp registrationTime;
	private Timestamp lastRequestTime;
	private Timestamp lastValidStatisticalReportTime;
	private boolean updatedAtLeastOnce;
	public NodeIdentifier()
	{
		this.updatedAtLeastOnce = false;
	}
	public NodeIdentifier(String nodeID,Timestamp registraTimestamp)
	{
		this.nodeID = nodeID;
		this.registrationTime = registraTimestamp;
		this.updatedAtLeastOnce = false;
		this.lastValidStatisticalReportTime = null;
		this.lastRequestTime = null;
	}
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public Timestamp getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Timestamp registrationTime) {
		this.registrationTime = registrationTime;
	}
	public Timestamp getLastRequestTime() {
		return lastRequestTime;
	}
	public void setLastRequestTime(Timestamp lastRequestTime) {
		this.lastRequestTime = lastRequestTime;
	}
	public Timestamp getLastValidStatisticalReportTime() {
		return this.lastValidStatisticalReportTime;
	}
	public void setLastValidStatisticalReportTime(Timestamp time) {
		this.lastValidStatisticalReportTime = time;
	}
	public boolean equals(NodeIdentifier ni){
		return this.nodeID.equals(ni.getNodeID());
	}
	public boolean isUpdatedAtLeastOnce() {
		return updatedAtLeastOnce;
	}
	public void setUpdatedAtLeastOnce(boolean updatedAtLeastOnce) {
		this.updatedAtLeastOnce = updatedAtLeastOnce;
	}
}
