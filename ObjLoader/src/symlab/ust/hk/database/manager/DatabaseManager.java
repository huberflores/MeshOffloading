package symlab.ust.hk.database.manager;

import symlab.ust.hk.offloading.ar.manager.MyEventContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

public class DatabaseManager {
		
	private Uri dbUri;
	private Context dContext;
	
	
	public DatabaseManager(Context c){
		this.dContext = c;
	}
	
	public void saveData(String action, double timestamp){
		ContentValues values = new ContentValues();
		values.put(EventDescriptor.COLUMN_EVENT_NAME, action);
		values.put(EventDescriptor.COLUMN_EVENT_TIME, timestamp);
		 	
		dbUri = dContext.getContentResolver().insert(MyEventContentProvider.CONTENT_URI, values);
		
	 }
	
	public void setDbUri(Uri cursor){
		this.dbUri = cursor;
	}


}
