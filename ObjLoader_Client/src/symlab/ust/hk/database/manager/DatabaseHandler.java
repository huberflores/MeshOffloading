package symlab.ust.hk.database.manager;

import android.content.Context;
import android.net.Uri;

public class DatabaseHandler {

	public static DatabaseHandler instance;
	
	private Uri dbUri; 
	private static DatabaseManager dManager = null;
	
	private Context context;

	private DatabaseHandler(){ 

	}

	public static synchronized DatabaseHandler getInstance(){
		if (instance==null){
			instance = new DatabaseHandler();
			return instance;
		}

		return instance;
	}
	
	public void setContext(Context context){
		this.context = context;
		dManager = new DatabaseManager(context);
	    dManager.setDbUri(dbUri);
	}
	
	public static DatabaseManager getDatabaseManager(){
		return dManager;
	}


	
	
	
}
