package com.ntu.way2fungames.earthdefensebeta.EdUI;

import com.android.angle.AngleSurfaceView;

import android.view.MotionEvent;

public class EdUIObject implements EdUIInterface{
	int left;
	int right;
	int top;
	int bottom;
	boolean lPendingPress = false;
	boolean lButtonDown = false;
	
	public void TouchEvent(MotionEvent nEvent){
		float ex =nEvent.getX(); 
		float ey =nEvent.getY();
		if((ex> left)&(ex<right)&(ey<bottom)&(ey>top)){// event was inside this objects ui area
			if (nEvent.getAction()==MotionEvent.ACTION_DOWN){
				
				lButtonDown = true;
			}
			if (nEvent.getAction()==MotionEvent.ACTION_UP){
				lButtonDown = false;
				lPendingPress= true;
				ButtonPess();
			}
			UpdateGFX();
		}
	}

	@Override
	public void AddToAngle(AngleSurfaceView mGLSurfaceView, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean PendingPress() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void UpdateGFX() {
		// TODO Auto-generated method stub
		
	}

	
	public void UsePendingPress() {
		// TODO Auto-generated method stub
		
	}

	
	public void ButtonPess() {
		// TODO Auto-generated method stub
		
	}
}