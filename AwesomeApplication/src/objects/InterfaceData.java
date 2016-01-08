package objects;

import java.util.ArrayList;

public class InterfaceData {

	private String interfaceName;
	private String belongsTo;
	private ArrayList<String> patterns = new ArrayList<String>();
	private ArrayList<Integer> frequencies = new ArrayList<Integer>();

	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String name) {
		this.interfaceName = name;
	}
	public String getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	public void addPattern(String pattern) {
		this.patterns.add(pattern);
		
	}
	public ArrayList<String> getPatterns() {
		return patterns;
	}	

	public void addFrequency(int freq) {
		this.frequencies.add(freq);
	}

	public ArrayList<Integer> getFrequencies() {
		return frequencies;
	}
	
}
