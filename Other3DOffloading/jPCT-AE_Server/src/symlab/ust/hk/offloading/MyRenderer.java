package symlab.ust.hk.offloading;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Logger;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;

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
	
    MyRenderer(Context context) {
        this.mContext = context;
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

            //OBJECT
            try {
        	   	obj1 = Loader.loadModel("" + thingName1 + ".3ds", thingScale);
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
                obj2 = Loader.loadModel("" + thingName2 + ".3ds", thingScale);
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
                obj3 = Loader.loadModel("" + thingName3 + ".3ds", thingScale);
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
    
}
