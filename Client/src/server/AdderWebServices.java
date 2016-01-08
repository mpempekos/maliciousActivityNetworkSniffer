/**
 * AdderWebServices.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package server;

public interface AdderWebServices extends java.rmi.Remote {
    public server.MaliciousPatterns maliciousPatternsRequest(java.lang.String arg0) throws java.rmi.RemoteException;
    public void maliciousPatternsStatisticalReports(java.lang.String arg0, server.StatisticsEntry[] arg1) throws java.rmi.RemoteException;
    public boolean registerAndroid(java.lang.String username, java.lang.String password, server.AvailableNodes nodes) throws java.rmi.RemoteException;
    public java.lang.String retrieveMaliciousPatterns(java.lang.String usernamePatterns, java.lang.String passwordPatterns) throws java.rmi.RemoteException;
    public void insertMaliciousPatterns(java.lang.String usernameInsert, java.lang.String passwordInsert, java.lang.String maliciousIP, java.lang.String stringPattern) throws java.rmi.RemoteException;
    public boolean unregister(java.lang.String arg0) throws java.rmi.RemoteException;
    public int login(java.lang.String usernameLogin, java.lang.String passwordLogin) throws java.rmi.RemoteException;
    public boolean logout(java.lang.String usernameLogout, java.lang.String passwordLogout) throws java.rmi.RemoteException;
    public server.StatisticsEntry[][] retrieveStatistics(java.lang.String usernameStatistics, java.lang.String passwordStatistics) throws java.rmi.RemoteException;
    public boolean register(java.lang.String arg0) throws java.rmi.RemoteException;
    public boolean delete(java.lang.String usernameDelete, java.lang.String passwordDelete, java.lang.String nodeDelete) throws java.rmi.RemoteException;
}
