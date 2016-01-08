package dbOperations;

import java.util.ArrayList;

import objects.PcNode;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import awesomeapp.MainActivity;
import awesomeapp.RemoveTerminalFragment;

public class PcNodeOperations {

	
	public void addPcNode(String nodeID, String belongsTo) {
		Log.e("", "///+++ADDING node--> nodeID: "+nodeID + " , belongsTo: "+belongsTo);
	
		/// First, check if PcNode already exists
		ArrayList<String> pcNodes =  getAllPcNodes();
		for (int i=0; i<pcNodes.size(); i++) {
			if (pcNodes.get(i).equalsIgnoreCase(nodeID)) {
				Log.e("", "///+++ PcNODE: " + nodeID + " ALREADY EXISTS!");
				return;
			}
		}
		
		ContentValues values = new ContentValues();
		values.put(DBWrapper.PCNODE_ID, nodeID);
		values.put(DBWrapper.PCNODES_BELONGS_TO, belongsTo);
		long retval = DBOperations.database.insert(DBWrapper.PCNODES, null, values);
		if (retval < 0)
			Log.e("", "///+++PcNODES insertion to database failed!");
		
	}
	
	public void setPcNodeOwner(String nodeID, String belongsTo) {
		//Log.e("", "///+++ Setting for node: "+nodeID + " owner: "+belongsTo);
		
		ContentValues values = new ContentValues();

		ArrayList<String> allNodes = getAllPcNodes();
		if (!allNodes.contains(nodeID)) {
			Log.e("", "///+++ node: " + nodeID + " does not exists!");
			return;
		}
		values.put(DBWrapper.PCNODES_BELONGS_TO, belongsTo);

		DBOperations.database.update(DBWrapper.PCNODES, 
				values, 
				DBWrapper.PCNODE_ID + " = ? ",
				new String[] {nodeID});
	}
	

	
	
	public void deletePcNode(String nodeID){
		//Log.e("", "///+++Try DELETING pcNode: " + nodeID);
		
		long retval = DBOperations.database.delete(DBWrapper.PCNODES, 
				DBWrapper.PCNODE_ID + " = ? ",
				new String[] {nodeID} );
		MainActivity.database.statisticsOP.deleteNodeStatistics(nodeID);
		if (retval == 0)
			Log.e("", "///+++PcNodes deletion failed!");
	}
	

	
	
	public ArrayList<String> getAllPcNodes() {
		ArrayList<String> nodes = new ArrayList<String>();

		Cursor cursor = DBOperations.database.query(
				DBWrapper.PCNODES,
				new String[] {DBWrapper.PCNODE_ID},
				null, null,	null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String tempNode = new String(cursor.getString(0));
			if (nodes.contains(tempNode)) ;
			else nodes.add(tempNode);
			cursor.moveToNext();
		}
		cursor.close();
		return nodes;		
	}
	
	public void deleteAllPcNodes() {
		ArrayList<String> pcNodes = getAllPcNodes();
		for (int i=0; i<pcNodes.size(); i++)
			deletePcNode(pcNodes.get(i));		
	}	
	
	public ArrayList<PcNode> getNodesWithOwners() {
		ArrayList<PcNode> nodes = new ArrayList<PcNode>();

		Cursor cursor = DBOperations.database.query(
				DBWrapper.PCNODES,
				new String[] {DBWrapper.PCNODE_ID, DBWrapper.PCNODES_BELONGS_TO},
				null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
//			String tempNode = new String(cursor.getString(0));

			PcNode tempNode = new PcNode();
			tempNode.setNodeID(cursor.getString(0));
			tempNode.setBelongsTo(cursor.getString(1));
			if (nodes.contains(tempNode)) ;
			else nodes.add(tempNode);
			cursor.moveToNext();
		}
		cursor.close();
		return nodes;	
	}
	
	public ArrayList<String> getAllPcNodeForUser(String user) {
		ArrayList<String> nodes = new ArrayList<String>();

		Cursor cursor = DBOperations.database.query(
				DBWrapper.PCNODES,
				null,
				DBWrapper.PCNODES_BELONGS_TO + " = ?", 
				new String[] {user},	
				null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String tempNode = new String(cursor.getString(0));
			if (nodes.contains(tempNode)) ;
			else nodes.add(tempNode);
			cursor.moveToNext();
		}
		cursor.close();
		return nodes;		
		
	}
	
	/// For Debugging ///
	public void printNodes() {
		ArrayList<String> nodes = getAllPcNodes();
		Log.e("", "///+++++++++++++++++++++++++++++++++++++++++");
		Log.e("", "///+++PRINTING NODES: ");
		for (int i=0; i<nodes.size(); i++)
			Log.e("", "///+++node: " + nodes.get(i));
		
	}
	
	
	/// For Debugging ///
	public void printPcNodesWithOwners() {
		ArrayList<PcNode> nodes = getNodesWithOwners();
		
		for (int i=0; i<nodes.size(); i++) {
			Log.e("", "///+++nodeID: " + nodes.get(i).getNodeID());
			Log.e("", "///+++belongsTo: " + nodes.get(i).getBelongsTo());
		}
		
		
	}

	
}
