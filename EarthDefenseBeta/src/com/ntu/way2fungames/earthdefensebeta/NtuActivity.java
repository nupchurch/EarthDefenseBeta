package com.ntu.way2fungames.earthdefensebeta;

import com.android.angle.AngleActivity;
import com.android.angle.AngleSurfaceView;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class NtuActivity extends AngleActivity{
	public EdAngleSurfaceView mGLSurfaceView; // The main GL View
	
	@Override
	public void onCreate(Bundle sis){
		super.onCreate(sis);
			try
			{
				super.mGLSurfaceView.stop();
				super.mGLSurfaceView=null;
				Thread.sleep(100);
//				
//				mGLSurfaceView = new EdAngleSurfaceView(this,nEdObjects);
//				mGLSurfaceView.setAwake(true);
//				mGLSurfaceView.start();
//				
//				Thread.sleep(100);
//				super.mGLSurfaceView= (AngleSurfaceView)this.mGLSurfaceView;
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
				
		
		Log.v("way2fungames","onCreate");
	}
	public void CreateEdGlSurfaceView(EdObject[] nEdObjects){
		mGLSurfaceView = new EdAngleSurfaceView(this,nEdObjects);
		mGLSurfaceView.setAwake(true);
		mGLSurfaceView.start();
		try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		super.mGLSurfaceView= (AngleSurfaceView)this.mGLSurfaceView;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		Log.v("way2fungames","onStart");
	}
	
	@Override
	public void onResume(){
		super.onResume();
		Log.v("way2fungames","onResume");
	}

	@Override
	public void onPause(){
		super.onPause();
		Log.v("way2fungames","onPause");
	}

	@Override
	public void onStop(){
		super.onStop();
		Log.v("way2fungames","onStop");
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.v("way2fungames","onDestroy");
	}


}
