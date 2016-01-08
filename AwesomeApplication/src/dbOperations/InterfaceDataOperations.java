package dbOperations;

import java.util.ArrayList;

import objects.InterfaceData;
import android.database.Cursor;
import android.util.Log;

public class InterfaceDataOperations {

	PcNodeOperations pcNodeOP = new PcNodeOperations();
	
	public ArrayList<String> getNodeInterfaces(String nodeID) {
		ArrayList<String> interfaces = new ArrayList<String>();

		Cursor cursor = DBOperations.database.query(
				DBWrapper.STATISTICS,
				new String[] {DBWrapper.STATISTIC_INTERFACE_NAME},
				DBWrapper.STATISTIC_NODE_ID + " = ? ",
				new String[] {nodeID},
				null, null, null);
		
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			String tempInterface = new String(cursor.getString(0));
			if (interfaces.contains(tempInterface))	;
			else interfaces.add(tempInterface);

			cursor.moveToNext();
		}
		cursor.close();
		return interfaces;		
	}
	
	
	public InterfaceData getInterfaceData(String nodeID, String interfaceName) {
		InterfaceData interfaceData = new InterfaceData();
		Cursor cursor = DBOperations.database.query(
				DBWrapper.STATISTICS,
				new String[] {DBWrapper.STATISTIC_MALICIOUS_PATTERN, DBWrapper.STATISTIC_FREQUENCY},
				DBWrapper.STATISTIC_NODE_ID + " = ? AND " + DBWrapper.STATISTIC_INTERFACE_NAME + " = ? ",
				new String[] {nodeID, interfaceName},
				null, null, null);
		
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			String tempPattern = new String(cursor.getString(0));
			int tempFreq = cursor.getInt(1);
			interfaceData.addPattern(tempPattern);
			interfaceData.addFrequency(tempFreq);
			interfaceData.setBelongsTo(nodeID);
			interfaceData.setInterfaceName(interfaceName);
			
			cursor.moveToNext();
		}
		cursor.close();
		return interfaceData;
	}
	
	/// For Debugging ///
	public void printInterfaceData() {
		
		ArrayList<String> nodes = pcNodeOP.getAllPcNodes();
		Log.e("", "///++++++++++++++++++++++++++++++++++");		
		Log.e("", "///+++PRINTING ALL INTERFACE DATA: ");

		for (int i=0; i<nodes.size(); i++) {
			ArrayList<String> interfaces = getNodeInterfaces(nodes.get(i));
			for (int j=0; j<interfaces.size(); j++) {
				InterfaceData interfaceData = getInterfaceData(nodes.get(i), interfaces.get(j));
				Log.e("", "///++++++++++++++++++++++++++");
				Log.e("", "///+++INTERFACE: "+interfaces.get(j));
				Log.e("", "///+++name: " + interfaceData.getInterfaceName());
				Log.e("", "///+++belongs to: " + interfaceData.getBelongsTo());
				Log.e("", "///+++patterns: " + interfaceData.getPatterns());
				Log.e("", "///+++freqs: " + interfaceData.getFrequencies());
			}
		}
	}

	
}
