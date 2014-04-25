package com.blubee.ObjLoader;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;


public class MyRenderer implements GLSurfaceView.Renderer  {
	
	
	ObjDescriptor myObj;
	
	int sProgram, vShader, fShader, posHandle, modMatHandle, viewMatHandle, projMatHandle, textureHandle, texCoordHandle, texId;
	float[] modMat, viewMat, projMat;
	
	
	
	Context context;
	
	
	public MyRenderer(Context context)
	{
		this.context = context;
		modMat = new float[16];
		viewMat = new float[16];
		projMat = new float[16];
		
		myObj = new ObjDescriptor();
		
		myObj.setObjectRoot("lego");
		myObj.initializeObjBuffer();
		
		
	}
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 3.0f;
        final float centerX = 0.0f;
        final float centerY = 0.0f;
        final float centerZ = 0.0f;
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;
        
        Matrix.setLookAtM(viewMat, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
        
		vShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		GLES20.glShaderSource(vShader, vCode);
		GLES20.glCompileShader(vShader);

		fShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		GLES20.glShaderSource(fShader, fCode);
		GLES20.glCompileShader(fShader);

		sProgram = GLES20.glCreateProgram();
		GLES20.glAttachShader(sProgram, vShader);
		GLES20.glAttachShader(sProgram, fShader);
		GLES20.glLinkProgram(sProgram);
		

		posHandle = GLES20.glGetAttribLocation(sProgram, "aPos");
		texCoordHandle = GLES20.glGetAttribLocation(sProgram, "aTexPos");
		
		modMatHandle = GLES20.glGetUniformLocation(sProgram, "uModMat");
		viewMatHandle = GLES20.glGetUniformLocation(sProgram, "uViewMat");
		projMatHandle = GLES20.glGetUniformLocation(sProgram, "uProjMat");
		textureHandle = GLES20.glGetUniformLocation(sProgram, "texture");
		
		Matrix.setIdentityM(modMat, 0);
		Matrix.translateM(modMat, 0, 0.0f, -2.0f, -2.0f);
	

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		GLES20.glViewport(0, 0, width, height);
		final float ratio = (float)width/height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 100.0f;
		
		Matrix.frustumM(projMat, 0, left, right, bottom, top, near, far);
		GLES20.glUseProgram(sProgram);
				
		GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
	}

	
	public void onDrawFrame(GL10 gl) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT); 
		 
		
		if (Util.touchTurn != 0) { 
	        Matrix.rotateM(modMat, 0, Util.rotationalX, 0.0f, -2.0f, 0.0f);
	        
	        if ((Util.accumulatedAngle>115)&& (Util.part2==false)){
	        	ObjDescriptor.counter++; 
	        	myObj.addObjSegment();
	        
	        	Util.part2 = true;
	        }
	         
	        if ((Util.accumulatedAngle>300)&& (Util.part3==false)){ 
	        	ObjDescriptor.counter++; 
	        	myObj.addObjSegment();
	        
	        	Util.part3 = true;
	        }
	        
	        
	        
        	Util.touchTurn = 0;
        }
		
		for (int i =0; i<ObjDescriptor.segments.size(); i++){
		
		GLES20.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 0, ObjDescriptor.segments.get(i).getVertexBuffer());
        GLES20.glEnableVertexAttribArray(posHandle);
        
        GLES20.glUniformMatrix4fv(modMatHandle, 1, false, modMat, 0);
        GLES20.glUniformMatrix4fv(viewMatHandle, 1, false, viewMat, 0);
        GLES20.glUniformMatrix4fv(projMatHandle, 1, false, projMat, 0);
	
        GLES20.glUniform1i(textureHandle, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, ObjDescriptor.segments.get(i).getFaces()*3, GLES20.GL_UNSIGNED_SHORT, ObjDescriptor.segments.get(i).getIndexBuffer());
		
		}
        
	}
	

		
	private final String vCode = 
			  "uniform mat4 uModMat;            \n"
			+ "uniform mat4 uViewMat;           \n"
			+ "uniform mat4 uProjMat;           \n"
			+ "attribute vec4 aPos;             \n"
			+ "attribute vec4 aCol;             \n"
			+ "attribute vec2 aTexPos;          \n"
			+ "varying vec2 vTexPos;            \n"
			+ "varying vec4 vCol;               \n"
			+ "void main(){                     \n"
			+ " vCol = aCol;                    \n"
			+ " mat4 mv = uViewMat * uModMat;   \n"
			+ " mat4 mvp = uProjMat * mv;       \n"
			//+ " gl_Position =  aPos;          \n"
			+ " vTexPos = aTexPos;              \n"
			+ " gl_Position = mvp * aPos;       \n"
			+ " }                               \n";

	private final String fCode = 
			  "precision mediump float;        \n"
			+ "uniform sampler2D texture;      \n"
			+ "varying vec4 vCol;              \n"
			+ "varying vec2 vTexPos;           \n"
			+ "void main(){                    \n"
			+ " gl_FragColor = vec4(0.0, 0.0, 0.0, 0.0);       \n"
			//+ " gl_FragColor = texture2D(texture, vTexPos);  \n"
			+ " }                              \n";


	
	
	
	
	
}
