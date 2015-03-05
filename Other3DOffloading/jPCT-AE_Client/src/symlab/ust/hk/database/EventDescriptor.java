/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package symlab.ust.hk.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Huber Flores
 *
 */


public class EventDescriptor {

	//Database schema
	//Table
	public static final String TABLE_TIMESTAMP = "timestamp";
	//Attributes
	public static final String COLUMN_EVENT_ID = "_id";
	public static final String COLUMN_EVENT_NAME = "event_name";
	public static final String COLUMN_START_TIME = "start_time";
	public static final String COLUMN_END_TIME = "end_time";
	public static final String COLUMN_ATTRIBUTE1 = "battery_level";
	public static final String COLUMN_ATTRIBUTE2 = "cpu_load_all";
	
	//Database creation SQL statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TIMESTAMP 
			+ "("
			+ COLUMN_EVENT_ID + " integer primary key autoincrement, "
			+ COLUMN_EVENT_NAME + " text not null, "
			+ COLUMN_START_TIME + " real not null, "
			+ COLUMN_END_TIME + " real not null, "
			+ COLUMN_ATTRIBUTE1 + " real not null, "
			+ COLUMN_ATTRIBUTE2 + " real not null "
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
