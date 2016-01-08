package dbOperations;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBOperations {
	
	public AndroidClientOperations clientOP = new AndroidClientOperations();
	public StatisticOperations statisticsOP = new StatisticOperations();
	public InterfaceDataOperations interfaceDataOP = new InterfaceDataOperations();
	public PendingDeletesOperations pendingDeletesOP = new PendingDeletesOperations();
	public PendingInsertionsOperations pendingIntertionsOP = new PendingInsertionsOperations();
	public PcNodeOperations pcNodesOP = new PcNodeOperations();
	
	// Database fields
	protected static DBWrapper dbHelper;
	protected static SQLiteDatabase database;

	public DBOperations(Context context) {
		dbHelper = new DBWrapper(context);
	}

	public void openDB() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void closeDB() {
		dbHelper.close();
	}
	
}
