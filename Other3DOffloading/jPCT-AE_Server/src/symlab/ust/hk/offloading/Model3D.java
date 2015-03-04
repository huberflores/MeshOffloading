package symlab.ust.hk.offloading;

import java.lang.reflect.Method; 
import edu.ut.mobile.network.CloudRemotable;
import java.util.Vector;


import java.io.IOException;
import java.io.InputStream;

import com.threed.jpct.Loader;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

public class Model3D extends CloudRemotable  {
	
	
	public Object3D localloadModel(String filename, float scale) throws IOException {
        
		String file = "res/raw/" + filename;
		
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(file);
        
        System.out.println("The code is executed");
     		   
         Object3D[] model = Loader.load3DS(stream, scale);
         Object3D o3d = new Object3D(0);
         Object3D temp = null;
         for (int i = 0; i < model.length; i++) {
             temp = model[i];
             temp.setCenter(SimpleVector.ORIGIN);
             temp.rotateX((float)( -.5*Math.PI));
             temp.rotateMesh();
             temp.setRotationMatrix(new Matrix());
             o3d = Object3D.mergeObjects(o3d, temp);
             o3d.build();
         }
         return o3d;
     }
    


	public Object3D loadModel(String filename, float scale) throws IOException {
	Method toExecute; 
	Class<?>[] paramTypes = {String.class, float.class}; 
	Object[] paramValues = {filename,scale}; 
	Object3D result = null;
	try{
		toExecute = this.getClass().getDeclaredMethod("localloadModel", paramTypes);
		Vector results = getCloudController().execute(toExecute,paramValues,this,this.getClass());
		if(results != null){
			result = (Object3D)results.get(0);
			copyState(results.get(1));
		}else{
			result = localloadModel(filename,scale);
		}
	}  catch (SecurityException se){
	} catch (NoSuchMethodException ns){
	}catch (Throwable th){
	}
	
	return result;
	}

void copyState(Object state){
	Model3D localstate = (Model3D) state;
}
}
