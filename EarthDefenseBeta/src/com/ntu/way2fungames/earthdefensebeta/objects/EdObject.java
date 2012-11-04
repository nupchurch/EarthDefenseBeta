package com.ntu.way2fungames.earthdefensebeta.objects;

import android.util.Log;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;
import com.ntu.way2fungames.earthdefensebeta.EdBaseEnemy;
import com.ntu.way2fungames.earthdefensebeta.EdSpriteShip;
import com.ntu.way2fungames.earthdefensebeta.R;
import com.ntu.way2fungames.earthdefensebeta.R.drawable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdThreeLayers;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdAngleSprite;

public class EdObject implements EdThreeLayers,EdDestructable{
	
	private class MyAnimatedSprite extends AngleRotatingSprite
	{
		private static final float sRotationSpeed = 20;
		private static final float sAlphaSpeed = 0.5f;
		private float mAplhaDir;

		public MyAnimatedSprite(int x, int y, AngleSpriteLayout layout)
		{
			super(x, y, layout);
			mAplhaDir=sAlphaSpeed;
			
		}

		@Override
		public void step(float secondsElapsed)
		{
			//mRotation=super.
			super.step(secondsElapsed);
		}
		
	}
	AngleSurfaceView mAngleSurfaceView;
	protected AngleSpriteLayout   mSpriteLayouts[]= new AngleSpriteLayout[3];
	protected EdAngleSprite mSprites[]= new EdAngleSprite[3];
	protected float mX= 0;
	protected float mY= 0;
	protected float mRot;
	protected int mHealth=0;
	
	protected int[] SpritesIDs= new int[3];

	public EdObject(){}

	public boolean AddToAngle(AngleSurfaceView mGLSurfaceView, int xpos, int ypos,float nscale) {
		mX=xpos;mY= ypos;
		for (int i=0;i<3;i=i+1){
			if (GetSpriteResId(i) != 0){
				mSpriteLayouts[i]= new AngleSpriteLayout(mGLSurfaceView,GetSpriteResId(i));
				mSprites[i]      = new EdAngleSprite(0, 0, 16, mSpriteLayouts[i]);		
				mSprites[i].mPosition.setmX(xpos);mSprites[i].mPosition.setmY(ypos);mSprites[i].mZ= i*-1f;
				mSprites[i].mRotation=mRot;
				mGLSurfaceView.addObject(mSprites[i]);
				}	
		}
		return true;
	}
	
	public double angle(float x1,float y1,float x2,float y2) {
		 
        double dx = x1 - x2;
        double dy = y2 - y1;
        double angle = 0.0d;
 
        if (dx == 0.0) {
            if(dy == 0.0)     angle = 0.0;
            else if(dy > 0.0) angle = Math.PI / 2.0;
            else              angle = (Math.PI * 3.0) / 2.0;
        }
        else if(dy == 0.0) {
            if(dx > 0.0)      angle = 0.0;
            else              angle = Math.PI;
        }
        else {
            if(dx < 0.0)      angle = Math.atan(dy/dx) + Math.PI;
            else if(dy < 0.0) angle = Math.atan(dy/dx) + (2*Math.PI);
            else              angle = Math.atan(dy/dx);
        }
        return (angle * 180) / Math.PI;
    }
//public AngleSprite mAngleSprite;
	
	@Override
	public boolean Destruct() {
		if (mSprites[0]!=null){mSprites[0].mDie=true;}
		if (mSprites[1]!=null){mSprites[1].mDie=true;}
		if (mSprites[2]!=null){mSprites[2].mDie=true;}
		mHealth=0;
		mSprites=null;
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	};
	@Override
	public int GetBottomOfBox() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getDistance(EdObject nobj){
		float nx1 = nobj.getX();
		float nx2 = this.getX();
		float ny1 = nobj.getY();
		float ny2 = this.getY();
		
		return ( (float) Math.hypot(nx1-nx2,ny1-ny2));
	}

	@Override
	public int GetSpriteResId(int whatLayer) {
		
		return SpritesIDs[whatLayer];
	}

	@Override
	public float GetTargetValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	public synchronized  float getX() {
		return mX;
	}

	public synchronized float getY() {
		return mY;
	}

	@Override
	public boolean IsDead() {
		if (mHealth<=0){ return true;}
		else {return false;}
	}

	@Override
	public boolean IsExploding() {
		return false;
	}

	@Override
	public boolean IsGone() {
		return IsDead();
		
	}

	@Override
	public boolean TakeDamage(int nDmg) {
		mHealth = mHealth -nDmg;
		if (mHealth>0){return true;}
		else{Destruct();return false;}
	}

	public void updateSprite(AngleSurfaceView mGLSurfaceView,boolean ontop){
		for (int i=0;i<3;i++){
			if (GetSpriteResId(i) != 0){
				if (mSprites !=null){
				if (mSprites[i]!=null){
					mGLSurfaceView.removeObject(mSprites[i]);
					mSpriteLayouts[i]= new AngleSpriteLayout(mGLSurfaceView,GetSpriteResId(i));
					mSprites[i]      = new EdAngleSprite(0, 0, 16, mSpriteLayouts[i]);		
					mSprites[i].mPosition.setmX(mX);mSprites[i].mPosition.setmY(mY);mSprites[i].mZ= i*-1f;
					mSprites[i].mRotation=mRot;
					mGLSurfaceView.addObject(mSprites[i]);
					//mGLSurfaceView.MoveObjectToBottom(mSprites[i]);
					

				}
				
				}	
			}
		}
		
	}

	@Override
	public boolean IsInMyBox(float x, float y) {
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float[] Intersect(float x1, float y1, float x2, float y2) {
		return null;
	
	}
	
//	public void setmX(float mX) {
//		this.mX = mX;
//	}
	
//	public float getX(){
//		return mAngleSprite.mPosition.mX;
//	}
//	
//	public float getY(){
//		return mAngleSprite.mPosition.mY;
//	}

}
