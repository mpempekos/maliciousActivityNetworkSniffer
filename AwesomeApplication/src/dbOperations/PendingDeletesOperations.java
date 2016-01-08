package dbOperations;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class PendingDeletesOperations {
	
	PcNodeOperations pcNodeOP = new PcNodeOperations();

	public void addPendingDelete(String name) {
		//Log.e("", "///+++ADDING PENDING DELETE--> name: "+name);
		
		boolean nodeExists = false;
		
		/// First, check if pending delete already exists
		ArrayList<String> pending =  getAllPendingDeletes();
		for (int i=0; i<pending.size(); i++) {
			if (pending.get(i).equalsIgnoreCase(name)) {
				Log.e("", "///+++ Pending DELETE: " + name + " ALREADY EXISTS IN QUEUE!");
				return;
			}
		}
		/// Then check if this node does not even exist
		ArrayList<String> pcNodes =  pcNodeOP.getAllPcNodes();
		
		for (int i=0; i<pcNodes.size(); i++)
			if (pcNodes.get(i).equalsIgnoreCase(name))
				nodeExists = true;
		if (!nodeExists) {
			Log.e("", "///+++ Pending DELETE: no such terminal!");
			return;
		}		
		ContentValues values = new ContentValues();
		values.put(DBWrapper.PENDING_DELETES_NODE, name);
		long retval = DBOperations.database.insert(DBWrapper.PENDING_DELETES, null, values);
		if (retval < 0)
			Log.e("", "///+++PENDING DELETE deletion to database failed!");
	}
	
	public void deletePendingDelete(String name){
		//Log.e("", "///+++Try DELETING PENDING DELETE: " + name);
		long retval = DBOperations.database.delete(DBWrapper.PENDING_DELETES, 
				DBWrapper.PENDING_DELETES_NODE+ " = ? ",
				new String[] {name} );
		if (retval == 0)
			Log.e("", "///+++PENDING DELETE deletion failed!");
	}
	
	
	public ArrayList<String> getAllPendingDeletes() {
		ArrayList<String> pending = new ArrayList<String>();
		Cursor cursor = DBOperations.database.query(
				DBWrapper.PENDING_DELETES,
				new String[] {DBWrapper.PENDING_DELETES_NODE},
				null, null,	null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String tempPending = new String(cursor.getString(0));
			if (pending.contains(tempPending)) ;
			else pending.add(tempPending);
			cursor.moveToNext();
		}
		cursor.close();
		return pending;		
	}
	
	public void deleteAllPendingInsertions() {
		ArrayList<String> pending = getAllPendingDeletes();
		for (int i=0; i<pending.size(); i++)
			deletePendingDelete(pending.get(i));		
	}
		
	
	/// For Debugging ///
	public void printPendingInsertion() {
		ArrayList<String> pending = getAllPendingDeletes();
		Log.e("", "///+++++++++++++++++++++++++++++++++++++++++");
		Log.e("", "///+++PRINTING PENDING DELETES: ");
		for (int i=0; i<pending.size(); i++)
			Log.e("", "///+++pending deletion: " + pending.get(i));		
	}

}
