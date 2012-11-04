package com.ntu.way2fungames.earthdefensebeta.weapons;

import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdEnemy;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;

public class EdTriGun extends EdRailGun{
	
	
	public EdTriGun(){
		projSpeed= 500;
		aimSpeed = 10;
		aIAimSpeed =.05f;
	}
	
	@Override
	public boolean Fire(EdObject nearestTarget) {		
		if (CapEnergy>= CapGetMaxEnergy()){
			float rot1 = (float) (mRotRads-(radsPerDeg*4));
			float rot2 = (float) (mRotRads);
			float rot3 = (float) (mRotRads+(radsPerDeg*4));
			
			CapEnergy=0;
			
			float sinofit = (float) Math.sin(rot1);
			float cosofit = (float) Math.cos(rot1);
			muzX0 = (float) (mX+(cosofit*35));
			muzY0 = (float) (mY-(sinofit*35));
			
			sinofit = (float) Math.sin(rot2);
			cosofit = (float) Math.cos(rot2);
			muzX1 = (float) (mX+(cosofit*35));
			muzY1 = (float) (mY-(sinofit*35));

			sinofit = (float) Math.sin(rot3);
			cosofit = (float) Math.cos(rot3);
			muzX2 = (float) (mX+(cosofit*35));
			muzY2 = (float) (mY-(sinofit*35));

			
			mBullets.addSome(new float[]{muzX0,muzX1,muzX2}, new float[]{muzY0,muzY1,muzY2}, new float[]{(mRot-(.25f*upgradeLvls[2])),mRot,(mRot+(.25f*upgradeLvls[2]))}, new float[]{projSpeed,projSpeed,projSpeed});
			mBullets.addSome(new float[]{muzX0,muzX1,muzX2}, new float[]{muzY0,muzY1,muzY2}, new float[]{(mRot-(.5f*upgradeLvls[2])),mRot,(mRot+(.5f*upgradeLvls[2]))}, new float[]{projSpeed,projSpeed,projSpeed});
			
			//mParts.addone(muzX0, muzY0, uRot, 333);
			//mParts.addone(muzX1, muzY1, uRot, 333);
			//mParts.addone(muzX2, muzY2, uRot, 333);
			
			mFlash.mPosition.set(muzX0+61,muzY0+61);
			
		}
		return false;
	}
	@Override
	public boolean AddToAngle(AngleSurfaceView mGLSurfaceView, int i, int j,float nScale) {
		super.AddToAngle(mGLSurfaceView, i, j, nScale);
		mBullets.mRed=.50f;
		mBullets.mGreen=.75f;
		mBullets.mScale=new AngleVector(1.45f,1.45f);
		return true;
	
	}
	@Override
	public int GetUpgradeCost(int idx) {
		
		return (int) (10*Math.pow(upgradeLvls[idx],1.50f));
	}
}
