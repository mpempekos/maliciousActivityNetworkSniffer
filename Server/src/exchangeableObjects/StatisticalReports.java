package exchangeableObjects;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import dbObjects.StatisticsEntry;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatisticalReports {
	@XmlElement
	private List<StatisticsEntry> statisticalReportList ;
	public StatisticalReports() {
		statisticalReportList = new ArrayList<StatisticsEntry>();
	}
	public List<StatisticsEntry> getStatisticalReportEntries() 
	{
		return statisticalReportList;
	}
	public void setStatisticalReportEntries(List<StatisticsEntry> statisticalReportEntries) 
	{
		this.statisticalReportList = statisticalReportEntries;
	}
	public boolean isEmptyOrZero() 
	{
		if(statisticalReportList.isEmpty())
			return true;
		if(statisticalReportList != null) {
			int zeroFrequncies = 0 ;
			for(StatisticsEntry entry : statisticalReportList) {
				if(entry.getFrequency() == 0) 
					zeroFrequncies++;
			}
			if(statisticalReportList.size() == zeroFrequncies)
				return true;
		}
		return false;
	}
	
}
