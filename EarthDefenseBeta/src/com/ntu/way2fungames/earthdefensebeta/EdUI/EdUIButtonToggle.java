package com.ntu.way2fungames.earthdefensebeta.EdUI;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;

	
	public class EdUIButtonToggle extends EdUIObject implements EdUIInterface{
		int mX;
		int mY;
		int rOffRes;
		int rOnRes;
		int rTouchRes;
		int bState;
		
		AngleSpriteLayout offLayout;
		AngleSpriteLayout onLayout;
		AngleSpriteLayout touchLayout;
		
		AngleSprite offSprite;
		AngleSprite onSprite;
		AngleSprite touchSprite;
		
		public EdUIButtonToggle(int nROff, int nROn, int nRTouch){
			rOffRes= nROff;rOnRes= nROn;rTouchRes= nRTouch;
		}
		
		
		public void AddToAngle(AngleSurfaceView mGLSurfaceView, int x, int y) {
			mX=x;mY=y;AngleVector vScale = new AngleVector();
			
			
			offLayout = new AngleSpriteLayout(mGLSurfaceView,rOffRes);
			onLayout = new AngleSpriteLayout(mGLSurfaceView,rOnRes);
			touchLayout = new AngleSpriteLayout(mGLSurfaceView,rTouchRes);
			
			
			offSprite=   new AngleSprite(offLayout);
			onSprite=    new AngleSprite(onLayout);
			touchSprite= new AngleSprite(touchLayout);
			
			offSprite.mPosition.setmX(mX);
			offSprite.mPosition.setmY(mY);
			onSprite.mPosition.setmX(mX);
			onSprite.mPosition.setmY(mY);
			touchSprite.mPosition.setmX(mX);
			touchSprite.mPosition.setmY(mY);
			
			float scale = (64f/64f);
			vScale.set(scale,scale);
			offSprite.mScale  = vScale;
			onSprite.mScale   = vScale;
			touchSprite.mScale= vScale;
			
			top = mY-(offSprite.roLayout.roHeight/2);
			bottom = mY+(offSprite.roLayout.roHeight/2);
			right = mX+(offSprite.roLayout.roWidth/2);
			left=   mX-(offSprite.roLayout.roWidth/2);
			
			mGLSurfaceView.addObject(offSprite);
			mGLSurfaceView.addObject(onSprite);
			mGLSurfaceView.addObject(touchSprite);
			touchSprite.mAlpha=0;
			onSprite.mAlpha=0;
		}

		@Override
		public boolean PendingPress() {
			return lPendingPress;
			
		}


		@Override
		public void UsePendingPress() {
			lPendingPress=false;
		}


		@Override
		public void UpdateGFX() {
			if (lButtonDown == true){
				touchSprite.mAlpha=1;
			}else{
				touchSprite.mAlpha=0;
			}
			
			if (bState==0){
				onSprite.mAlpha=0;
				offSprite.mAlpha=1;
			}else if (bState==2){
				onSprite.mAlpha=1;
				offSprite.mAlpha=0;
			}
			
		}
		
		@Override
		public void ButtonPess(){
			if      (bState == 0){bState=2;}
			else if (bState == 2){bState=0;}
		}
		
		public Boolean getstate(){
			if (bState ==2){return true;}else{return false;}
		}
	}


