package server;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import exchangeableObjects.AvailableNodes;
import exchangeableObjects.MaliciousPatterns;
import exchangeableObjects.StatisticalReports;

@WebService
@SOAPBinding(style=Style.DOCUMENT)
public interface AdderWebServices {
	@WebMethod
	public boolean register(String nodeID);
	@WebMethod
	@WebResult(name="MaliciousPatterns")
	public MaliciousPatterns maliciousPatternsRequest(String nodeID);
	@WebMethod
	public void maliciousPatternsStatisticalReports(String nodeID,StatisticalReports m);
	@WebMethod
	public boolean unregister(String nodeID);
	//following services for Android smartphones/tablets
	@WebMethod
	public boolean registerAndroid(@WebParam(name="username")String username,@WebParam(name="password")String password,
			@WebParam(name="nodes")AvailableNodes nodes);
	@WebMethod
	public List<StatisticalReports> retrieveStatistics(@WebParam(name="usernameStatistics")String username,@WebParam(name="passwordStatistics")String password);
	@WebMethod
	public String retrieveMaliciousPatterns(@WebParam(name="usernameRetrieve")String username,@WebParam(name="passwordRetrieve")String password);
	@WebMethod
	public void insertMaliciousPatterns(@WebParam(name="usernameInsert")String username,@WebParam(name="passwordInsert")String password,@WebParam(name="maliciousIP")String maliciousIP,@WebParam(name="stringPattern")String stringPattern);
	@WebMethod
	public int login(@WebParam(name="usernameLogin")String username,@WebParam(name="passwordLogin")String password);
	@WebMethod
	public boolean logout(@WebParam(name="usernameLogout")String username,@WebParam(name="passwordLogout")String password);
	@WebMethod
	public boolean delete(@WebParam(name="usernameDelete")String username,@WebParam(name="passwordDelete")String password,@WebParam(name="nodeDelete")String nodeID);

}
