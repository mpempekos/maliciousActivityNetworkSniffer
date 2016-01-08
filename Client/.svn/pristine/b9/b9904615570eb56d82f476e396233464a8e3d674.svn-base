package memories;

abstract class SmpsmEntry {
	private String interfaceName;
	private String interfaceIP;	
	private int frequency;
	SmpsmEntry (String interfaceName,String interfaceIP) {
		this.interfaceName = interfaceName;
		this.interfaceIP = interfaceIP;
		this.frequency = 1;
	}
	public String getInterfaceName()
	{
		return interfaceName;
	}
	public String getInterfaceIp()
	{
		return interfaceIP;
	}
	public void clearFrequency() {
		this.frequency = 0; 
	}
	public void setFrequency()
	{
		this.frequency++;
	}
	public int getFrequency() 
	{
		return this.frequency; 
	}
	public String toString() 
	{
		return "interfaceName = " + this.interfaceName + ", interfaceIp = " + this.interfaceIP + ","
				+ " , frequency = " + this.frequency;
	}
	public synchronized void printEntries() 
	{
		System.out.println("interfaceName = " + this.interfaceName + ", interfaceIp = " + this.interfaceIP + ","
				+ " , frequency = " + this.frequency + "\t\t");
	}
}

