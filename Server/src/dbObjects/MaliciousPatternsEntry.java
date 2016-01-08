package dbObjects;
/****************************/
/* @tochange name to : MaciliousPatternEntry.java */
/***************************/
public class MaliciousPatternsEntry {
	int patternID;
	String pattern;
	String time;
	public MaliciousPatternsEntry() {
		pattern = new String();
		time = new String();
	}
	public String getMaliciousPattern() {
		return pattern;
	}
	public int getMaliciousPatternID() {
		return patternID;
	}
	public String getMaliciousTime() {
		return time;
	}
	public void setMaliciousPattern(String pattern) {
		this.pattern = pattern;
	}
	public void setMaliciousPatternID(int patternID) {
		this.patternID = patternID;
	}
	public void setMaliciousTime(String time) {
		this.time = time;
	}
}
