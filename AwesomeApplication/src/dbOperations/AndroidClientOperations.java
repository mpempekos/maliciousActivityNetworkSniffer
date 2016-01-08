package dbOperations;

import java.util.ArrayList;

import objects.AndroidClient;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import awesomeapp.PropertyFile;

public class AndroidClientOperations {

	
	public void addAndroidClient(AndroidClient client) {
//		Log.e("", "///+++adding client: "
//			+client.getNodeID() + " ,username: "
//			+client.getUsername()+", password: "
//			+client.getPassword());
			
		/// First, check if username already exists
		ArrayList<AndroidClient> clients =  getAllAndroidClients();
		for (int i=0; i<clients.size(); i++)
			if (clients.get(i).getUsername().equalsIgnoreCase(client.getUsername())) {
				Log.e("", "///+++ USERNAME: " + client.getUsername() + " ALREADY EXISTS!");
				return;
			}
		ContentValues values = new ContentValues();
		values.put(DBWrapper.CLIENT_IS_LOGGED_IN, "no");
		values.put(DBWrapper.CLIENT_USERNAME, client.getUsername());
		values.put(DBWrapper.CLIENT_PASSWORD, client.getPassword());
		long retval = DBOperations.database.insert(DBWrapper.CLIENTS, null, values);
		if (retval < 0)
			Log.e("", "///+++Insert client to database failed!");
	}
	
	/// setting client logged in
	/// if wrong username or password is given FALSE will be returned
	public void setClientLoggedIn(String username, String password) {
		
		setClientsLoggedOut();
		
		ContentValues values = new ContentValues();
		values.put(DBWrapper.CLIENT_IS_LOGGED_IN, "yes");

		DBOperations.database.update(DBWrapper.CLIENTS, 
				values, 
				DBWrapper.CLIENT_USERNAME + " = ? AND "
				+ DBWrapper.CLIENT_PASSWORD + " = ? ",
				new String[] {username, password});
	}
	


	
	/// sets all clients logged out! ///
	public void setClientsLoggedOut() {
		ContentValues values = new ContentValues();
		values.put(DBWrapper.CLIENT_IS_LOGGED_IN, "no");

		DBOperations.database.update(DBWrapper.CLIENTS, 
				values, null, null);
	}
	
	
	public int getLoggedInLevel() {
		int level = 0;
		//level equals with:
			// 0 if none logged in
			// 1 if simple user is logged in
			// 2 if administrator is logged in
		Cursor cursor = DBOperations.database.query(DBWrapper.CLIENTS,
				new String[] { DBWrapper.CLIENT_IS_LOGGED_IN , DBWrapper.CLIENT_USERNAME, DBWrapper.CLIENT_PASSWORD }, 
				DBWrapper.CLIENT_IS_LOGGED_IN + " = ? ", 
				new String[] {"yes"}, 
				null, null, null);

		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {	
			if (cursor.getString(1).equalsIgnoreCase("admin"))
				level = 2; //administration level
			else
				level = 1; //simple user level
		}
		cursor.close();
		return level;
	}
	
	
	
	
	
	public AndroidClient getLoggedInClient() {
		AndroidClient loggedInClient = new AndroidClient();
		
		Cursor cursor = DBOperations.database.query(DBWrapper.CLIENTS,
				new String[] { DBWrapper.CLIENT_IS_LOGGED_IN , DBWrapper.CLIENT_USERNAME, DBWrapper.CLIENT_PASSWORD }, 
				DBWrapper.CLIENT_IS_LOGGED_IN + " = ? ", 
				new String[] {"yes"}, 
				null, null, null);

		cursor.moveToFirst();
		
		
		if (!cursor.isAfterLast()) {
			loggedInClient.setIsLoggedIn(cursor.getString(0));
			loggedInClient.setUsername(cursor.getString(1));
			loggedInClient.setPassword(cursor.getString(2));
			return loggedInClient;
		}
		cursor.close();

		return loggedInClient;
		
	}
	

	
	public void addAdmin() {
		/// gets admin username and password from property file ///
    	PropertyFile prop = new PropertyFile();

		String un = new String(prop.getAdminUsername());
		String pw = new String(prop.getAdminPassword());
		
		AndroidClient client = new AndroidClient(un, pw);		
		addAndroidClient(client);
		setClientLoggedIn(un, pw);
		
	}
	
	
	
	
	
	public void deleteAndroidClient(String clientUsername) {	
		Log.e("", "///+++DELETING client: " + clientUsername);
		long retval = DBOperations.database.delete(DBWrapper.CLIENTS, " ( " 
				+  DBWrapper.CLIENT_USERNAME + " = ? )", new String[] {clientUsername});
		if (retval == 0)
			Log.e("", "///+++Client deletion failed!");
	}

	public ArrayList<AndroidClient> getAllAndroidClients() {
		ArrayList<AndroidClient> clients = new ArrayList<AndroidClient>();
		Cursor cursor = DBOperations.database.query(DBWrapper.CLIENTS,
				new String[] { DBWrapper.CLIENT_IS_LOGGED_IN , DBWrapper.CLIENT_USERNAME, DBWrapper.CLIENT_PASSWORD }, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			AndroidClient client = new AndroidClient();
			client.setIsLoggedIn(cursor.getString(0));
			client.setUsername(cursor.getString(1));
			client.setPassword(cursor.getString(2));
			clients.add(client);
			cursor.moveToNext();
		}
		cursor.close();
		return clients;
	}

	
	
	public void deleteAllAndroidClients() {
		Log.e("", "///+++DELETING ALL ANDROID CLIENTS----------");
		
		ArrayList<AndroidClient> users = getAllAndroidClients();

		for (int i=0; i<users.size(); i++) {
			deleteAndroidClient(users.get(i).getUsername());
			
		}
		
	}
	
	
	
	/// For Debugging ///
	public void printAllClients() {
		Log.e("", "///+++-----------------------------------");
		Log.e("", "///+++PRINTING ALL CLIENTS---------------");

		ArrayList <AndroidClient> clients = new ArrayList<AndroidClient>();
  	  	clients = getAllAndroidClients();
	    for (int i=0; i<clients.size(); i++)
	    	Log.e("", "///+++logged in: "+clients.get(i).getIsLoggedIn() + " ,username: "+clients.get(i).getUsername()+", password: "+clients.get(i).getPassword());
	}
	
}
