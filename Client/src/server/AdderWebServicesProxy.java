package server;

public class AdderWebServicesProxy implements server.AdderWebServices {
  private String _endpoint = null;
  private server.AdderWebServices adderWebServices = null;
  
  public AdderWebServicesProxy() {
    _initAdderWebServicesProxy();
  }
  
  public AdderWebServicesProxy(String endpoint) {
    _endpoint = endpoint;
    _initAdderWebServicesProxy();
  }
  
  private void _initAdderWebServicesProxy() {
    try {
      adderWebServices = (new server.AdderWebServicesImplServiceLocator()).getAdderWebServicesImplPort();
      if (adderWebServices != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)adderWebServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)adderWebServices)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (adderWebServices != null)
      ((javax.xml.rpc.Stub)adderWebServices)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public server.AdderWebServices getAdderWebServices() {
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices;
  }
  
  public server.MaliciousPatterns maliciousPatternsRequest(java.lang.String arg0) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.maliciousPatternsRequest(arg0);
  }
  
  public void maliciousPatternsStatisticalReports(java.lang.String arg0, server.StatisticsEntry[] arg1) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    adderWebServices.maliciousPatternsStatisticalReports(arg0, arg1);
  }
  
  public boolean registerAndroid(java.lang.String username, java.lang.String password, server.AvailableNodes nodes) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.registerAndroid(username, password, nodes);
  }
  
  public java.lang.String retrieveMaliciousPatterns(java.lang.String usernamePatterns, java.lang.String passwordPatterns) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.retrieveMaliciousPatterns(usernamePatterns, passwordPatterns);
  }
  
  public void insertMaliciousPatterns(java.lang.String usernameInsert, java.lang.String passwordInsert, java.lang.String maliciousIP, java.lang.String stringPattern) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    adderWebServices.insertMaliciousPatterns(usernameInsert, passwordInsert, maliciousIP, stringPattern);
  }
  
  public boolean unregister(java.lang.String arg0) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.unregister(arg0);
  }
  
  public int login(java.lang.String usernameLogin, java.lang.String passwordLogin) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.login(usernameLogin, passwordLogin);
  }
  
  public boolean logout(java.lang.String usernameLogout, java.lang.String passwordLogout) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.logout(usernameLogout, passwordLogout);
  }
  
  public server.StatisticsEntry[][] retrieveStatistics(java.lang.String usernameStatistics, java.lang.String passwordStatistics) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.retrieveStatistics(usernameStatistics, passwordStatistics);
  }
  
  public boolean register(java.lang.String arg0) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.register(arg0);
  }
  
  public boolean delete(java.lang.String usernameDelete, java.lang.String passwordDelete, java.lang.String nodeDelete) throws java.rmi.RemoteException{
    if (adderWebServices == null)
      _initAdderWebServicesProxy();
    return adderWebServices.delete(usernameDelete, passwordDelete, nodeDelete);
  }
  
  
}