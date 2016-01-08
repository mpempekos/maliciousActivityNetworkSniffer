package dbOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBWrapper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Android.db";
	private static final int DATABASE_VERSION = 71;

	public static final String CLIENTS = "AndroidClients";
	public static final String CLIENT_IS_LOGGED_IN = "nodeID";	
	public static final String CLIENT_USERNAME = "username";
	public static final String CLIENT_PASSWORD = "password";
	
	public static final String STATISTICS = "Statistics";
	public static final String STATISTIC_NODE_ID = "nodeID";
	public static final String STATISTIC_INTERFACE_NAME = "interfaceName";
	public static final String STATISTIC_INTERFACE_IP = "interfaceIP";
	public static final String STATISTIC_MALICIOUS_PATTERN = "maliciousPattern";
	public static final String STATISTIC_FREQUENCY = "frequency";

	public static final String PCNODES = "PcNodes";
	public static final String PCNODE_ID = "nodeID";
	public static final String PCNODES_BELONGS_TO = "belongsTo";

	public static final String PENDING_DELETES = "PendingDeletes";
	public static final String PENDING_DELETES_ID = "id";
	public static final String PENDING_DELETES_NODE = "node";

	public static final String PENDING_INSERTIONS = "PendingInsertions";
	public static final String PENDING_INSERTIONS_ID = "id";
	public static final String PENDING_INSERTIONS_TYPE = "type";
	public static final String PENDING_INSERTIONS_NAME = "name";

	
	private static final String CREATE_TABLE_CLIENTS = "CREATE TABLE "
			+ CLIENTS + "(" 
			+ CLIENT_IS_LOGGED_IN + " TEXT, "
			+ CLIENT_USERNAME + " TEXT PRIMARY KEY,  "
			+ CLIENT_PASSWORD + " TEXT NOT NULL " + ");";
	
	private static final String CREATE_TABLE_STATISTICS = "CREATE TABLE "
			+ STATISTICS + "(" 
			+ STATISTIC_NODE_ID + " TEXT,"
			+ STATISTIC_INTERFACE_NAME + " TEXT,"
			+ STATISTIC_INTERFACE_IP + " TEXT,"
			+ STATISTIC_MALICIOUS_PATTERN + " TEXT,"
			+ STATISTIC_FREQUENCY + " INT " + ")";
	
	private static final String CREATE_TABLE_PCNODES = "CREATE TABLE "
			+ PCNODES + "("
			+ PCNODE_ID + " TEXT PRIMARY KEY,"
			+ PCNODES_BELONGS_TO + " TEXT " + " );";
	

	private static final String CREATE_TABLE_PENDING_INSERTIONS = "CREATE TABLE "
			+ PENDING_INSERTIONS + "(" 
			+ PENDING_INSERTIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ PENDING_INSERTIONS_TYPE + " TEXT, " 
			+ PENDING_INSERTIONS_NAME + " TEXT" + " )";
			
	private static final String CREATE_TABLE_PENDING_DELETES = "CREATE TABLE "
			+ PENDING_DELETES + "(" 
			+ PENDING_DELETES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ PENDING_DELETES_NODE + " TEXT " + ")";
			
	
	
	public DBWrapper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		Log.e("", "///+++Creating database");
		
        db.execSQL(CREATE_TABLE_CLIENTS);
        db.execSQL(CREATE_TABLE_STATISTICS);
        db.execSQL(CREATE_TABLE_PCNODES);
        db.execSQL(CREATE_TABLE_PENDING_INSERTIONS);
        db.execSQL(CREATE_TABLE_PENDING_DELETES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e("", "///+++Database on Upgrade");
		
        db.execSQL("DROP TABLE IF EXISTS " + CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + STATISTICS);
        db.execSQL("DROP TABLE IF EXISTS " + PCNODES);
        db.execSQL("DROP TABLE IF EXISTS " + PENDING_DELETES);
        db.execSQL("DROP TABLE IF EXISTS " + PENDING_INSERTIONS);

		onCreate(db);		
	}
	
}