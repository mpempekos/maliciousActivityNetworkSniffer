package dbOperations;

import java.util.ArrayList;
import java.util.List;

import objects.PendingInsertions;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class PendingInsertionsOperations {

	public void addPendingInsertion(String type, String name) {
		//Log.e("", "///+++ADDING PENDING INSERTION--> type: "+type + " , name: "+name);
		
		
		/// First, check if already exists
		ArrayList<String> pending =  getAllPendingInsertions();
		for (int i=0; i<pending.size(); i++)
			if (pending.get(i).equalsIgnoreCase(name)) {
				Log.e("", "///+++ Pending Insertion: " + name + " ALREADY EXISTS!");
				return;
			}
		
		ContentValues values = new ContentValues();
		values.put(DBWrapper.PENDING_INSERTIONS_TYPE, type);
		values.put(DBWrapper.PENDING_INSERTIONS_NAME, name);
		long retval = DBOperations.database.insert(DBWrapper.PENDING_INSERTIONS, null, values);
		if (retval < 0)
			Log.e("", "///+++PENDING Insertions to database failed!");
		
	}
	
	public void deletePendingInsertion(String name){
		//Log.e("", "///+++Try DELETING PENDING INSERTION: " + name);
		long retval = DBOperations.database.delete(DBWrapper.PENDING_INSERTIONS, 
				DBWrapper.PENDING_INSERTIONS_NAME + " = ? ",
				new String[] {name} );
		if (retval == 0)
			Log.e("", "///+++PENDING INSERTION deletion failed!");
	}
	
	public List<PendingInsertions> getAllPendingInsertionsWithType() {
		List<PendingInsertions> pending = new ArrayList<PendingInsertions>();

		Cursor cursor = DBOperations.database.query(
				DBWrapper.PENDING_INSERTIONS,
				new String[] {DBWrapper.PENDING_INSERTIONS_NAME, DBWrapper.PENDING_INSERTIONS_TYPE},
				null, null,	null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			PendingInsertions tempInsertion = new PendingInsertions();
			String tempPending = new String(cursor.getString(0));
			String tempPendingType = new String(cursor.getString(1));

			tempInsertion.setName(tempPending);
			tempInsertion.setType(tempPendingType);
			
			pending.add(tempInsertion);
			cursor.moveToNext();
		}
		cursor.close();
		return pending;		
	}
	
	public ArrayList<String> getAllPendingInsertions() {
		ArrayList<String> pending = new ArrayList<String>();

		Cursor cursor = DBOperations.database.query(
				DBWrapper.PENDING_INSERTIONS,
				new String[] {DBWrapper.PENDING_INSERTIONS_NAME},
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
		ArrayList<String> pending = getAllPendingInsertions();
		
		for (int i=0; i<pending.size(); i++)
			deletePendingInsertion(pending.get(i));		
	}
	
	
	/// For Debugging ///
	public void printPendingInsertion() {
		ArrayList<String> pending = getAllPendingInsertions();
		Log.e("", "///+++++++++++++++++++++++++++++++++");
		Log.e("", "///+++PRINTING PENDING INSERTIONS: ");
		for (int i=0; i<pending.size(); i++)
			Log.e("", "///+++pending insertion: " + pending.get(i));
		
	}
		
}
