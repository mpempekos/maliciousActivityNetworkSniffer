package awesomeapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class WebServiceConnector {
	private static final String NAMESPACE = "http://server/";
	private static final String URL = "http://" + new PropertyFile().getServerIP() + ":9999/Adder";
	private WebServiceConnector() {}
	public static boolean register(String username,String password,AvailableNodes nodes) {
		final String METHOD_NAME = "registerAndroid";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo property = new PropertyInfo();
		property.setName("username");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);

		PropertyInfo property1 = new PropertyInfo();
		property1.setName("password");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
		
		PropertyInfo property2 = new PropertyInfo();
		property2.setName("nodes");
		property2.setType(AvailableNodes.class);
		property2.setValue(nodes);
		request.addProperty(property2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            boolean result =  Boolean.parseBoolean(response.getProperty(0).toString());
            System.out.println(response.getProperty(0).toString());
            //Log.d("result",response.getProperty(0).toString());
            return result;
        } catch(Exception e)
        {
            e.printStackTrace();
        }
		return false;
	}
	public static int login(String username,String password) {
		final String METHOD_NAME = "login";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		PropertyInfo property = new PropertyInfo();
		property.setName("usernameLogin");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);
		
		PropertyInfo property1 = new PropertyInfo();
		property1.setName("passwordLogin");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            int result =  Integer.parseInt(response.getProperty(0).toString());
            System.out.println(response.getProperty(0).toString());
            //Log.d("result",response.getProperty(0).toString());
            return result;
        } catch(Exception e)
        {
            e.printStackTrace();
        }
		return 0;
	}
	public static boolean logout(String username,String password) {
		final String METHOD_NAME = "logout";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo property = new PropertyInfo();
		property.setName("usernameLogout");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);
		
		PropertyInfo property1 = new PropertyInfo();
		property1.setName("passwordLogout");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
	
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            boolean result =  Boolean.parseBoolean(response.getProperty(0).toString());
            System.out.println(response.getProperty(0).toString());
            //Log.d("result",response.getProperty(0).toString());
            return result;
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
	}
	public static String retrieveMaliciousPatterns(String username,String password) {
		final String METHOD_NAME = "retrieveMaliciousPatterns";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo property = new PropertyInfo();
		property.setName("usernameRetrieve");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);
		
		PropertyInfo property1 = new PropertyInfo();
		property1.setName("passwordRetrieve");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
	
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            String result = response.getProperty(0).toString();
            System.out.println(result);
            //Log.d("result",response.getProperty(0).toString());
            return result;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	public static boolean delete(String username,String password,String nodeID) {
		final String METHOD_NAME = "delete";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo property = new PropertyInfo();
		property.setName("usernameDelete");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);
		
		PropertyInfo property1 = new PropertyInfo();
		property1.setName("passwordDelete");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
		
		PropertyInfo property2 = new PropertyInfo();
		property2.setName("nodeDelete");
		property2.setType(String.class);
		property2.setValue(nodeID);
		request.addProperty(property2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            boolean result =  Boolean.parseBoolean(response.getProperty(0).toString());
            System.out.println(response.getProperty(0).toString());
            //Log.d("result",response.getProperty(0).toString());
            return result;
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return false;
	}
	public static void insertMaliciousPatterns(String username, String password,String maliciousIP, String stringPattern) {
		final String METHOD_NAME = "insertMaliciousPatterns";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo property = new PropertyInfo();
		property.setName("usernameInsert");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);
		
		PropertyInfo property1 = new PropertyInfo();
		property1.setName("passwordInsert");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
		
		PropertyInfo property2 = new PropertyInfo();
		property2.setName("maliciousIP");
		property2.setType(String.class);
		property2.setValue(maliciousIP);
		request.addProperty(property2);
		
		PropertyInfo property3 = new PropertyInfo();
		property3.setName("stringPattern");
		property3.setType(String.class);
		property3.setValue(stringPattern);
		request.addProperty(property3);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            //Log.d("result",response.getProperty(0).toString());
        } catch(Exception e)
        {
            e.printStackTrace();
        }
	}
	public static List<StatisticalReports> retrieveStatistics(String username,String password) {
		final String METHOD_NAME = "retrieveStatistics";
		final String SOAP_ACTION =  "\"" +NAMESPACE + METHOD_NAME+ "\""  ;
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		PropertyInfo property = new PropertyInfo();
		property.setName("usernameStatistics");
		property.setType(String.class);
		property.setValue(username);
		request.addProperty(property);
		
		PropertyInfo property1 = new PropertyInfo();
		property1.setName("passwordStatistics");
		property1.setType(String.class);
		property1.setValue(password);
		request.addProperty(property1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        HttpTransportSE HttpTransport = new HttpTransportSE(URL);
        try {
        	HttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject)envelope.bodyIn;
            return getStatisticalReports(response);
        } catch(Exception e) {
            e.printStackTrace();
        }
		return null;

	}
	private static List<StatisticalReports> getStatisticalReports(SoapObject response) {
		List<StatisticalReports> reportsList = new ArrayList<StatisticalReports>();
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject responseChild = (SoapObject) response.getProperty(i);
                StatisticalReports report = new StatisticalReports();
                List<StatisticsEntry> statisticsEntries = new ArrayList<StatisticsEntry>();
                if (responseChild.hasProperty("statisticalReportList")) {
                	for (int j = 0; j < responseChild.getPropertyCount(); j++) {
                		SoapObject responseChildNested = (SoapObject) responseChild.getProperty(j);
                		StatisticsEntry entry = new StatisticsEntry();
                		if(responseChildNested.hasProperty("nodeID")) 
                			entry.setNodeID(responseChildNested.getPropertyAsString("nodeID"));
                		if(responseChildNested.hasProperty("interfaceName"))
                			entry.setInterfaceName(responseChildNested.getPropertyAsString("interfaceName"));
                		if(responseChildNested.hasProperty("interfaceIP")) 
                			entry.setInterfaceIP(responseChildNested.getPropertyAsString("interfaceIP"));               		
						if(responseChildNested.hasProperty("maliciousPattern")) 
                			entry.setMaliciousPattern(responseChildNested.getPropertyAsString("maliciousPattern"));				
						if(responseChildNested.hasProperty("frequency")) 
							entry.setFrequency(Integer.parseInt(responseChildNested.getProperty("frequency").toString()));             		
						statisticsEntries.add(entry);
                	}
                	report.setStatisticalReportEntries(statisticsEntries);
                }
                reportsList.add(report);
            }
        return reportsList;
    }
}
