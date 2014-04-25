package symlab.ust.hk.database.manager;

import android.database.sqlite.SQLiteDatabase;


public class EventDescriptor {

	//Database schema
	//Table
	public static final String TABLE_TIMESTAMP = "timestamp";
	//Attributes
	public static final String COLUMN_EVENT_ID = "_id";
	public static final String COLUMN_EVENT_NAME = "event_name";
	public static final String COLUMN_EVENT_TIME = "event_time";
	
	//Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TIMESTAMP 
			+ "("
			+ COLUMN_EVENT_ID + " integer primary key autoincrement, "
			+ COLUMN_EVENT_NAME + " text not null, "
			+ COLUMN_EVENT_TIME + " real not null "
			+");";
	
	 //Database creation
	 public static void onCreate(SQLiteDatabase database) {
		    database.execSQL(DATABASE_CREATE);
	 }

	 
	 public static void onUpgrade(SQLiteDatabase database, int oldVersion,
		      int newVersion) {
		    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMESTAMP);
		    onCreate(database);
	 }
	
}
