package com.blubee.ObjLoader;


import symlab.ust.hk.database.manager.DatabaseHandler;

import com.blubee.ObjLoader.R;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;

public class MainActivity extends Activity {
	
	//private Logger Log = Logger.getLogger(MainActivity.class.getName()); 
	
	GLSurfaceView mView;
	MyRenderer rend;
	
	public static long lineTime = System.currentTimeMillis();
	
	private final float TOUCH_SCALE_FACTOR = 360.0f / 10000;
	private float mPreviousX; 
    private float mPreviousY;
    private float x;
    private float y;
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        //may create null expection
        DatabaseHandler dataEvent = DatabaseHandler.getInstance();
        dataEvent.setContext(this);
     
        dataEvent.getInstance().getDatabaseManager().saveData("Application started", 0.0f);
        
        mView = new GLSurfaceView(this); 
        mView.setEGLContextClientVersion(2);
        rend = new MyRenderer(this);
        mView.setRenderer(rend);

           
        setContentView(mView);
        
        mView.setFocusableInTouchMode(true);
    }

    
    
    @Override
	protected void onResume() {
		super.onResume();
		mView.onResume();

	}
    


	@Override
	protected void onPause() {
		super.onPause();
		mView.onPause();

	}



	@Override
	protected void onDestroy() {
		super.onDestroy();

	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		
		
		if (me.getAction()== MotionEvent.ACTION_UP){
			mPreviousX = Util.rotationalX;
			mPreviousY = Util.rotationalY;
			
			return true;
		}

	    
	    if (me.getAction() == MotionEvent.ACTION_MOVE) {
	        x = me.getX();
	        y = me.getY();
	        
	        float xd = x - mPreviousX;
            float yd = y - mPreviousY;


	        Util.touchTurn = xd / -100f;
	        Util.touchTurnUp = yd / -100f;
	        Util.rotationalX = (xd)*TOUCH_SCALE_FACTOR;
	        Util.rotationalY = (yd)*TOUCH_SCALE_FACTOR;
	        
	        Util.accumulatedAngle = Util.accumulatedAngle + Util.rotationalX;
	        
	        if (Util.accumulatedAngle>360){
	        	Util.accumulatedAngle = 0f;
	        	Util.times++;
	        }
	        
	        //Log.info("ANGLE: " + Util.accumulatedAngle);
	       
	        	        
	        return true;
	    }
	   

	    return false;
	}

}
