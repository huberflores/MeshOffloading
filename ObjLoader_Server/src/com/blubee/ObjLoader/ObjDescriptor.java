package com.blubee.ObjLoader;


import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;


public class ObjDescriptor {

	int fn = 0;
	int bb = 0;
	ObjLoader loader;
		
	
	float[] verts;
	
	short[] ind;
	FloatBuffer vertsBuff;
	ShortBuffer indicesBuff;
	
	private String rootSegment = "";
	
	int numFaces;
	
	private ObjSegments obj;
	
	public static ArrayList<ObjSegments> segments;
	
	public static int counter;
		
	
	public void initializeObjBuffer(){
		if (rootSegment.length()>0){
			counter = 1;
			segments = new ArrayList<ObjSegments>();
	
			addObjSegment();		
			
		}
	}
	
	
    
    public void setObjectRoot(String root){
    	this.rootSegment = root;
    }
    
    
    public void addObjSegment(){
    	loader = new ObjLoader();
		loader.load(rootSegment + counter + ".obj");
		
		vertsBuff = loader.vertsBuffer;
		indicesBuff = loader.indicesBuffer;
		numFaces = loader.numFaces;
		
		obj = new ObjSegments();
		obj.setObjectProperties(loader.numFaces, loader.vertsBuffer, loader.indicesBuffer, rootSegment +".obj");
		segments.add(obj);
		
    }
	
    
    
    class ObjSegments{
    	
    	int numFaces;
    	FloatBuffer vertsBuff;
    	ShortBuffer indicesBuff;
    	String fileSegment;
    	
    	
    	public void setObjectProperties(int f, FloatBuffer vb, ShortBuffer ib, String fl){
    		this.numFaces = f;
    		this.vertsBuff = vb;
    		this.indicesBuff = ib;
    		this.fileSegment = fl;
   
    	}
    	
    	public FloatBuffer getVertexBuffer(){
    		return this.vertsBuff;
    	}
    	
    	public ShortBuffer getIndexBuffer(){
    		return this.indicesBuff;
    	}
    	
    	public int getFaces(){
    		return this.numFaces;
    	}
 	
    	
    }
	
   
	
}
