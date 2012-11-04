package com.ntu.way2fungames.earthdefensebeta;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdAngleSprite;

public class EdSpriteShip extends EdAngleSprite{
	int moveType=0;
	int maxX = 0;
	float speedY;
	float mPPS=0;
	float mHeading=0;
	private float maxHeadingChange=(float) (50);//5 degree per second
	private float aX=0;
	private float aY=0;


	
	public EdSpriteShip(int x, int y, float nHeading, float speed, AngleSpriteLayout layout) {
			super(x, y, layout);
			mRotation = mHeading = nHeading;
			mPPS = speed;
			float r = (float) ((mRotation /180f)* Math.PI);
			aY = (float)-(Math.sin(r)*mPPS);
			aX = (float) (Math.cos(r)*mPPS);
	}
	
	public EdSpriteShip(int x,int y, int nmaxX,int nspeedY,float speed,AngleSpriteLayout layout){
		super(x,y,layout);
		moveType=1;
		maxX = nmaxX;
		mPPS = speed;
		speedY=nspeedY;
		mRotation=270;
		
	}
	
	public void ChangeSpeed(int nPPS){
		mPPS=nPPS;
		float r = (float) ((mRotation /180f)* Math.PI);
		aY = (float)-(Math.sin(r)*nPPS);
		aX = (float) (Math.cos(r)*nPPS);
		speedY = nPPS/(10);
	}
	
	public void ChangeSpeed(int nPPS,float ySpeed2){
		mPPS=nPPS;		
		speedY = Math.max(ySpeed2,1);
	}

	@Override
	public synchronized void  step(float secondsElapsed){
		super.step(secondsElapsed);
		if (moveType==0){
			if (mHeading != mRotation){UpdateHeading(secondsElapsed);}
			if (mScale.getmX()<1){
				mPosition.setmX(mPosition.getmX() +(aX*secondsElapsed*((mScale.getmX()/2)+.5f))); 
				mPosition.setmY(mPosition.getmY() +(aY*secondsElapsed*((mScale.getmY()/2)+.5f)));
			}else{
				mPosition.setmX(mPosition.getmX() +(aX*secondsElapsed)); 
				mPosition.setmY(mPosition.getmY() +(aY*secondsElapsed));
			}
		}else if (moveType==1){
			mPosition.setmX(mPosition.getmX()+mPPS); 
			mPosition.setmY(mPosition.getmY()+((speedY*30)*secondsElapsed));
			if (mPosition.getmX()>maxX){mPPS=-mPPS;}
			if (mPosition.getmX()<0){mPPS=-mPPS;}
		}
	}
	private void UpdateHeading(float secondsElapsed){
		float dif = mHeading-mRotation;
		float mhcts= maxHeadingChange*secondsElapsed;
		if (Math.abs(dif) > (mhcts)){
			if(mHeading>mRotation){mRotation=mRotation+mhcts;}
			if(mHeading<mRotation){mRotation=mRotation-mhcts;}
		}else{
			mRotation=mRotation+dif;
		}
		float r = (float) ((mHeading /180f)* Math.PI);
		aY = (float)-(Math.sin(r)*mPPS);
		aX = (float) (Math.cos(r)*mPPS);
		
	}
	public synchronized float getX(){
		return mPosition.getmX();
	}
	public synchronized float getY(){
		return mPosition.getmY();
	}

//	@Override
//	public void draw(GL10 gl){
//		if (exploding>0){
//			for (int part=0;part<4;part=part+1){
//				for (int v=0;v<2;v=v+1){
//					mPartsLoc[part][v]=mPartsLoc[part][v]*1.05f;
//					mAlpha=exploding/30f;
//				}
//			}
//			exploding= exploding-1;
//		}
//		if (exploding==1){isDead=true;exploding=0;return;}
//		if ((isDead==true)&(exploding==0)){return;}
//		super.draw(gl);
//	}
	
}
