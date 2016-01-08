package memories;

class SmpsmEntryMaliciousStringPattern extends SmpsmEntry {
	private String maliciousStringPattern;		
 	SmpsmEntryMaliciousStringPattern(String interfaceName,String interfaceIP,String maliciousStringPattern)
	{
 		super(interfaceName, interfaceIP);
 		this.maliciousStringPattern = maliciousStringPattern;
	}
 	public String getMaliciousStringPattern()
	{
		return maliciousStringPattern;
	}
 	@Override
	public String toString() 
 	{
			return	super.toString() + " maliciousStringPattern = " + this.maliciousStringPattern;
 	}
}
