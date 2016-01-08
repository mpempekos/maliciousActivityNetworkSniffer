package connectors;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import main.ClosureThread;
import main.MainThread;
import server.AdderWebServices;
import server.AdderWebServicesImplServiceLocator;

public class ServerConnector {
	public static String createURL() 
	{
		String url = MainThread.getServerURL();
		if(url != null) {
			return url;
		} 
		else {
			String portNumber = null;
			String ipAddress = null;
			String implemantationName = null;
			File propertiesFile = new File("netConfig.properties");
			Properties properties = new Properties();
			FileInputStream inPutStream = null;
			try {
				inPutStream = new FileInputStream(propertiesFile);	
				properties.load(inPutStream);
				portNumber = properties.getProperty("PORT");
				ipAddress = properties.getProperty("IP");
				implemantationName = properties.getProperty("IMPLEMANTATION");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if(inPutStream != null)
						inPutStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String serverURL = "http://" + ipAddress + ":" + portNumber + "/" + implemantationName + "?wsdl";
			MainThread.setServerURL(serverURL);
			return serverURL;
		}
	}
	public static AdderWebServices getAdderWebService()
	{
		AdderWebServicesImplServiceLocator service;
		AdderWebServices server = null;
	    try {
	    	service = new AdderWebServicesImplServiceLocator(createURL(),new QName("http://server/","AdderWebServicesImplService"));
	    	server = service.getAdderWebServicesImplPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("Serious Problem Connecting to Server");
			System.err.println("Ending application");
			System.exit(1);
		}
	    return server;
	}
}

