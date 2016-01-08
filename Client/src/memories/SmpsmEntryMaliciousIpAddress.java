package memories;

class SmpsmEntryMaliciousIpAddress extends SmpsmEntry { 
	private String maliciousIP;	
    SmpsmEntryMaliciousIpAddress(String interfaceName,String interfaceIP,String maliciousIP)
	{
		super(interfaceName, interfaceIP);
		this.maliciousIP = maliciousIP;
	}
	public String getMaliciousIp()
	{
		return maliciousIP;
	}
	@Override
	public String toString() 
	{
			return super.toString() + " maliciousIP = " + this.maliciousIP  ;
	}
}
