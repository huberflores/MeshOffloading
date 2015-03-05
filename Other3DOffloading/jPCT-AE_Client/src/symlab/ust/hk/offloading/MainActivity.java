package symlab.ust.hk.offloading;

import android.app.Activity;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay; 

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import com.threed.jpct.Logger;


public class MainActivity extends Activity {

private static MainActivity master = null;

private GLSurfaceView mGLView;
private MyRenderer renderer = null;

private float xpos = -1;
private float ypos = -1;



protected void onCreate(Bundle savedInstanceState) {

    Logger.log("onCreate");

    if (master != null) {
        copy(master);
    }

    super.onCreate(savedInstanceState);
    mGLView = new GLSurfaceView(getApplication());
   

    mGLView.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
            EGLConfig[] configs = new EGLConfig[1];
            int[] result = new int[1];
            egl.eglChooseConfig(display, attributes, configs, 1, result);
            return configs[0];
        }
    });

    
    renderer = new MyRenderer(getApplicationContext());
    renderer.setActivity(master);
    mGLView.setRenderer(renderer);
    setContentView(mGLView);
}

@Override
protected void onPause() {
    super.onPause();
    mGLView.onPause();
}

@Override
protected void onResume() {
    super.onResume();
    mGLView.onResume();
}

@Override
protected void onStop() {
    super.onStop();
}

private void copy(Object src) {
    try {
        Logger.log("Copying data from master Activity!");
        Field[] fs = src.getClass().getDeclaredFields();
        for (Field f : fs) {
            f.setAccessible(true);
            f.set(this, f.get(src));
        }
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

public boolean onTouchEvent(MotionEvent me) {

    if (me.getAction() == MotionEvent.ACTION_DOWN) {
        xpos = me.getX();
        ypos = me.getY();
        return true;
    }

    if (me.getAction() == MotionEvent.ACTION_UP) {
        xpos = -1;
        ypos = -1;
        Util.touchTurn = 0;
        Util.touchTurnUp = 0;
        return true;
    }

    if (me.getAction() == MotionEvent.ACTION_MOVE) {
        float xd = me.getX() - xpos;
        float yd = me.getY() - ypos;

        xpos = me.getX();
        ypos = me.getY();

        Util.touchTurn = xd / -100f;
        Util.touchTurnUp = yd / -100f;
        return true;
    }

    try {
        Thread.sleep(15);
    } catch (Exception e) {
        // No need for this...
    }

    return super.onTouchEvent(me);
}

protected boolean isFullscreenOpaque() {
    return true;
}


}
