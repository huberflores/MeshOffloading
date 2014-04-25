package com.blubee.ObjLoader;

import java.lang.reflect.Method; 
import edu.ut.mobile.network.CloudRemotable;
import java.util.Vector;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ObjReader extends CloudRemotable  {

	
	public StringBuilder localload(String filename)
	{
		long start = System.currentTimeMillis();

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
			System.out.println("total time "+(end-start)+" ms");
			is.close();
			
		} catch (IOException e) { e.printStackTrace();	}
	
		return model;
	}


	public StringBuilder load(String filename)
	{
	Method toExecute; 
	Class<?>[] paramTypes = {String.class}; 
	Object[] paramValues = {filename}; 
	StringBuilder result = null;
	try{
		toExecute = this.getClass().getDeclaredMethod("localload", paramTypes);
		Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
		if(results != null){
			result = (StringBuilder)results.get(0);
			copyState(results.get(1));
		}else{
			result = localload(filename);
		}
	}  catch (SecurityException se){
	} catch (NoSuchMethodException ns){
	}catch (Throwable th){
	}
	
	return result;
	}

void copyState(Object state){
	ObjReader localstate = (ObjReader) state;
}
}
