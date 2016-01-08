package exchangeableObjects;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

//@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MaliciousPatterns {
	@XmlElement
	private List<String> maliciousStringPatternsList;
	@XmlElement
	private List<String> maliciousIPsList;
public MaliciousPatterns() {
	maliciousStringPatternsList = new ArrayList<String>();
	maliciousIPsList = new ArrayList<String>();
}

public List<String> getMaliciousStringPatternsList() {
	return maliciousStringPatternsList;
}

public List<String> getMaliciousIPsList() {
	return maliciousIPsList;
}
/*
public  void printMaliciousPatterns()
{	
		System.out.println("\tMalicious IP Addresses : ");
		try {
			ListIterator<String> iteratorMaliciousIpList = maliciousIPs.listIterator();
			while(iteratorMaliciousIpList.hasNext()) {
				System.out.println("\t\t" + iteratorMaliciousIpList.next() + "\t\t");
			}
		} finally {
		}
		try {
			ListIterator<String> iteratorMaliciouStringPatterns = maliciousStringPatterns.listIterator();
			System.out.println("\tMalicious String Patterns : ");
			while(iteratorMaliciouStringPatterns.hasNext()) {
				System.out.println("\t\t" + iteratorMaliciouStringPatterns.next() + "\t\t");
			}
		} finally {
		}
		
}
*/
}