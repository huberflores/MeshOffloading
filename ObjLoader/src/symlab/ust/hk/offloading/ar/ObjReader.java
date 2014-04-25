package symlab.ust.hk.offloading.ar;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import symlab.ust.hk.database.manager.DatabaseHandler;
 
public class ObjReader {  
	//DatabaseHandler dataEvent = DatabaseHandler.getInstance();
	
	//private Logger Log = Logger.getLogger(MainActivity.class.getName()); 
 
	public StringBuilder load(String filename)   
	{
		long start = System.currentTimeMillis(); 
		//dataEvent.getInstance().getDatabaseManager().saveData("Starting to load mesh", System.currentTimeMillis() - MainActivity.lineTime);
 
		String file = "res/raw/" + filename; 
		
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(file); 
        
		StringBuilder model = new StringBuilder("");
		String line;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
			line = reader.readLine();
			while(line != null)
			{ 
				model.append(line);
				model.append("\n");
				line = reader.readLine();
			}
			long end = System.currentTimeMillis();
			//System.out.println("total time "+(end-start)+" ms");
			//Log.warning("total time "+(end-start)+" ms");
			//dataEvent.getInstance().getDatabaseManager().saveData("Finishing to load mesh", System.currentTimeMillis() - MainActivity.lineTime);
			is.close();
			
		} catch (IOException e) { e.printStackTrace();	}
	
		return model;
	}

}
