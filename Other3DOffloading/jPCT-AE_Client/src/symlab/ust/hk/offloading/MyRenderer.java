/*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* Please send inquiries to huber AT ut DOT ee
*/

package symlab.ust.hk.offloading;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import symlab.ust.hk.database.DatabaseCommons;
import symlab.ust.hk.database.DatabaseHandler;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Logger;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.os.Environment;


import android.util.Log;

/**
 * author Huber Flores
 */

public class MyRenderer implements GLSurfaceView.Renderer{

	private static MainActivity master = null;
	
	private long time = System.currentTimeMillis();
	
	private FrameBuffer fb = null;
	private World world = null;
	private RGBColor back = new RGBColor(50, 50, 100);

	private Object3D obj1 = null;
	private Object3D obj2 = null;
	private Object3D obj3 = null;
	
	private Context mContext;
	
	private Model3D Loader;
	
	private int fps = 0;

	private Light sun = null;

	AssetManager assMan;
	InputStream is;

	private String thingName1 = "woman";
	private String thingName2 = "chair";
	private String thingName3 = "lamp";
	private int thingScale = 1;//end
	
	//database
	 Context baseContext;
     DatabaseHandler dataEvent;
	
    MyRenderer(Context context) {
        this.mContext = context;
        dataEvent = DatabaseHandler.getInstance();
        dataEvent.setContext(context);
        baseContext = context;
       
    }
    
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        if (fb != null) { 
            fb.dispose();
        }
        fb = new FrameBuffer(gl, w, h);
        
        Loader= new Model3D();

        if (master == null) {

            world = new World();
            world.setAmbientLight(20, 20, 20);

            sun = new Light(world);
            sun.setIntensity(250, 250, 250);

            //OBJECT 1
            try {
            	double startTime1 = System.currentTimeMillis();
        	   	obj1 = Loader.localloadModel("" + thingName1 + ".3ds", thingScale);
        	   	double endTime1 = System.currentTimeMillis() - startTime1;
        	   	
        	   	Log.i("Object 1 loading time", endTime1+"");
        	   	dataEvent.getInstance().getDatabaseManager().saveData("Object 1 loading time", 0, endTime1, 0, 0);
        	   	
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            obj1.build();
            world.addObject(obj1);

            Camera cam = world.getCamera();
            cam.moveCamera(Camera.CAMERA_MOVEOUT, 50);
            cam.lookAt(obj1.getTransformedCenter());
 
            SimpleVector sv = new SimpleVector();
            sv.set(obj1.getTransformedCenter());
            sv.y -= 100;
            sv.z -= 100;
            sun.setPosition(sv);
            MemoryHelper.compact();
            
            
            //OBJECT2
            try {
            	double startTime2 = System.currentTimeMillis();
            	obj2 = Loader.loadModel("" + thingName2 + ".3ds", thingScale);
        	   	double endTime2 = System.currentTimeMillis() - startTime2;
        	   	
        	   	Log.i("Object 2 loading time", endTime2+"");
        	   	   
        	   	dataEvent.getInstance().getDatabaseManager().saveData("Object 2 loading time", 0, endTime2, 0, 0);
            	
                
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            obj2.translate(-6, 28, 0); 
            obj2.rotateY(-.6f);
            world.addObject(obj2);
            
          
 
            //OBJECT3
            try {
                
                double startTime3 = System.currentTimeMillis();
                obj3 = Loader.loadModel("" + thingName3 + ".3ds", thingScale);
        	   	double endTime3 = System.currentTimeMillis() - startTime3;
        	   	
        	   	Log.i("Object 3 loading time", endTime3+"");            	
        	   	
        	   	dataEvent.getInstance().getDatabaseManager().saveData("Object 3 loading time", 0, endTime3, 0, 0);
                

        	   	boolean manualExtraction=true; 
        	   	if (manualExtraction==true){
        	   		extractDatabaseFile(new DatabaseCommons());
        	   	}
        	   	
        	   	
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
            obj3.translate(-15, -20, 0);      
            obj3.rotateY(-.6f);
            world.addObject(obj3);
            
            

            /*if (master == null) {
                Logger.log("Saving master Activity!");
                master = MainActivity.this;
            }*/
        }
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	
    }

    public void onDrawFrame(GL10 gl) {
  
        if (Util.touchTurn != 0) {
        	obj1.rotateY(Util.touchTurn);
        	Util.touchTurn = 0;
        }

        if (Util.touchTurnUp != 0) {
        	obj1.rotateX(Util.touchTurnUp);
        	Util.touchTurnUp = 0;
        }

        fb.clear(back);
        world.renderScene(fb);
        world.draw(fb);
        fb.display();

        if (System.currentTimeMillis() - time >= 1000) {
            Logger.log(fps + "fps");
            fps = 0;
            time = System.currentTimeMillis();
        }
        fps++;
    }

    

    public void setActivity(MainActivity m){
    	this.master = m;
    }
    
    
    public void extractDatabaseFile(DatabaseCommons db){
    	try {
    	db.copyDatabaseFile();
    	} catch (IOException e) {
    	e.printStackTrace();
    	}
    }
    
    private File getDir() {
        File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        return new File(sdDir, "database");
    }

    
}
