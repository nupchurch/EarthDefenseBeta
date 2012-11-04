package com.ntu.way2fungames.earthdefensebeta.weapons;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;
//import com.ntu.way2fungames.earthdefensebeta.
import com.ntu.way2fungames.earthdefensebeta.EdBaseEnemy;
import com.ntu.way2fungames.earthdefensebeta.R;
import com.ntu.way2fungames.earthdefensebeta.R.drawable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.*;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.*;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdWeaponEffect;


public class EdRailGun extends EdWeapon implements EdFireable, EdCapacitor, EdUsesEnergy, EdAimingMechanics,EdBuildable,EdStructure,EdThreeLayers{
	final float radsPerDeg = (float) (Math.PI/180f);
	protected AngleSpriteLayout mBulletLayout;
	protected EdProjectiles mBullets;
	protected double mRotRads;
	protected float muzX0;
	protected float muzY0;
	protected AngleSpriteLayout mFlashLayout;
	protected AngleRotatingSprite mFlash;
	protected float CapEnergy;
	protected float muzX1;
	protected float muzY1;
	protected float CapMaxIn;
	protected boolean fireAutoMode;
	protected float maxDistance;
	protected float muzX2;
	protected float muzY2;
	protected float aimSpeed = 6;
	protected float aIAimSpeed = .10f;
	protected int projSpeed =2400;
	protected int[] upgradeLvls = new int[]{1,1,1};
	protected EdExplosion explosions;
	private float capMaxCharge=4;
	//private int mHealth = 50;
	public EdRailGun() {
		super("1234");
		CapMaxIn = (float) (Math.random()*.0125f)+(.1f-.00625f)+0.15f;
		maxDistance = (float) Math.hypot(1920, 1200);
		mHealth= 100;
		mRot = 90;		
	}

	@Override
	public boolean AimAt(float aimX, float aimY,float smod) {
		float toadd;
		AngleVector lpos = this.mSprites[2].mPosition;
		float tRot = (float) angle(aimX,aimY,lpos.getmX(), lpos.getmY());
		mRotRads = (mRot/180f)*Math.PI;
		//this.mSprites[2].mRotation=tRot;
		float dif =Math.abs(tRot-this.mSprites[2].mRotation);
		float cRot= this.mSprites[2].mRotation;
		int dtt= DirToTurn(cRot,tRot);
		
		float maxturn= dtt*(aimSpeed*smod);
		if (maxturn>0){toadd = Math.min(dif,maxturn);}
		else{          toadd = Math.max(-dif,maxturn);}
		
		this.mSprites[2].mRotation=this.mSprites[2].mRotation+toadd;
		
		if (this.mSprites[2].mRotation<0){this.mSprites[2].mRotation=360+this.mSprites[2].mRotation;}
		if (this.mSprites[2].mRotation>360){this.mSprites[2].mRotation=this.mSprites[2].mRotation-360;}
		
		mRot = this.mSprites[2].mRotation;
		return false;
	}

	private int DirToTurn(float curRot, float targetRot) {
		float dif = Math.abs(curRot-targetRot);
		if (curRot> targetRot){
			if (dif < 180){return -1;}
		}
		
		if (curRot < targetRot){
			if (dif > 180){return -1;}
		}
		
		return 1;
	}

	@Override
	public boolean UseEnergy(float uEnergy) {
		if (fireAutoMode==true){
			Fire(null);
		}
		return false;
	}

	@Override
	public int GetUpgradeCost(int idx) {
		
		return (int) (10*Math.pow(upgradeLvls[idx],1.25f));
	}

	@Override
	public int[] GetUpgradeCosts() {
		return new int[]{GetUpgradeCost(0),GetUpgradeCost(1),GetUpgradeCost(2)};
	}

	@Override
	public String[] GetUpgradeButtons() {
		return new String[]{"Structure","Capacitor","Mechanics"};
	}

	@Override
	public EdObject Upgrade(int UpgradeIdx) {
		int cost;
		switch (UpgradeIdx){
			case 0:
				cost = GetUpgradeCost(0);				
				mHealth=(int) (mHealth*1.25f);
				upgradeLvls[0]=upgradeLvls[0]+1;
				return (null);				
			case 1:
				cost = GetUpgradeCost(1);
				CapMaxIn = CapMaxIn*1.25f;
				upgradeLvls[1]=upgradeLvls[1]+1;
				return (null);
			case 2:
				cost = GetUpgradeCost(2);
				aimSpeed=aimSpeed*1.25f;
				upgradeLvls[2]=upgradeLvls[2]+1;
				return (null);
		}
		return null;
		
		
	}

//	@Override
//	public boolean Destruct() {
//		// TODO Auto-generated method stub
//		return false;
//	}

//	@Override
//	public boolean TakeDamage(int nDmg) {
//		mHealth = mHealth -nDmg;
//		if (mHealth>0){return true;}
//		else{Destruct();return false;}
//	}

	@Override
	public float GetTargetValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean BuildAt(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float GetBuildCost() {
		// TODO Auto-generated method stub
		return 0;
		
	}
	@Override	
	public int GetSpriteResId(int whatLayer){
		super.GetSpriteResId(whatLayer);
		
		if (whatLayer==0)     {return SpritesIDs[0];}
		else if (whatLayer==1)     {return Sprite1;}
		else if (whatLayer==2){return R.drawable.ed_gun1;}
		return 0;
		
	}


	@Override
	public boolean Fire(EdObject targetenemy) {
		if (CapEnergy>= CapGetMaxEnergy()){
			CapEnergy=0;
			float uRot = mRot;
			float uMRotRads = (float) mRotRads;
			float sinofit = (float) Math.sin(uMRotRads);
			float cosofit = (float) Math.cos(uMRotRads);
			
			muzX0 = (float) (mX+(cosofit*35));
			muzY0 = (float) (mY-(sinofit*35));
			
			muzX1 = (float) (mX+(cosofit*17));
			muzY1 = (float) (mY-(sinofit*17));
			
			muzX2 = (float) (mX+(cosofit*00));
			muzY2 = (float) (mY-(sinofit*00));
			mBullets.addSome(new float[]{muzX0,muzX1,muzX2}, new float[]{muzY0,muzY1,muzY2}, new float[]{uRot,uRot,uRot}, new float[]{projSpeed,projSpeed,projSpeed});
			//mParts.addone(muzX0, muzY0, uRot, 333);
			//mParts.addone(muzX1, muzY1, uRot, 333);
			//mParts.addone(muzX2, muzY2, uRot, 333);
			
			mFlash.mPosition.set(muzX0+61,muzY0+61);
			
		}
		return false;
	}
	@Override
	public boolean AddToAngle(AngleSurfaceView mGLSurfaceView, int i, int j,float nScale) {
		//this.mSprites[2].mRotation=mRot;
		super.AddToAngle(mGLSurfaceView, i, j,nScale);
		mBulletLayout = new AngleSpriteLayout(mGLSurfaceView, 8,8,com.ntu.way2fungames.earthdefensebeta.R.drawable.bullet,0,0,8,8,3,4);
		mFlashLayout = new AngleSpriteLayout(mGLSurfaceView, 128,128,com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_muzzleflash);
		
		//explosions = new EdExplosion(null, null, mFlashLayout);
		mFlash = new AngleRotatingSprite(160,160,mFlashLayout);
		mFlash.mScale.set(1.25f,1.25f);
		
		//EdMultiDraw mBullets1 = new EdMultiDraw(null, null, mBulletLayout);
		AngleSpriteLayout expLayout = new AngleSpriteLayout(mGLSurfaceView, com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_flashsmall);
		
		mBullets = new EdProjectiles(new float[]{i},new float[]{j},new float[]{0},new float[]{0},mBulletLayout,expLayout);
		//mParts.mScale= new AngleVector(.5f,.5f);
		
		mBullets.AddToAngle(mGLSurfaceView);
		mGLSurfaceView.addObject(mFlash);
		
		
		//mGLSurfaceView.MoveObjectToBottom(mGLSurfaceView.WhatIdx(mParts));
		//mGLSurfaceView.MoveObjectToBottom(mGLSurfaceView.WhatIdx( mSprites[2]));
		//mGLSurfaceView.MoveObjectToBottom(mGLSurfaceView.WhatIdx( mSprites[1]));
		//mGLSurfaceView.MoveObjectToBottom(mGLSurfaceView.WhatIdx( mSprites[0]));
		return true;
	}

	@Override
	public float CapEnergyIn(float nEnergy) {
		mFlash.mPosition.set(-100,-100);
		float maxin  =CapGetMaxEnergyIn();
		float maxremain = CapGetMaxEnergy()-CapEnergy;
		float rv =0;
		if (maxremain<maxin){
			CapEnergy = CapEnergy+maxremain;
			rv=nEnergy- maxremain;
		}else{
			CapEnergy = CapEnergy+maxin;
			rv=nEnergy- maxin;
		}
		
		if (CapEnergy == CapGetMaxEnergy()){UseEnergy(CapEnergy);}
		float pf = CapEnergy/CapGetMaxEnergy();
		mSprites[1].mRed=  ((pf*.75f)+.25f);		
		mSprites[1].mGreen=((pf*.75f)+.25f);
		mSprites[1].mBlue= ((pf*.75f)+.25f);
		

		return rv;
	}

	@Override
	public float CapEnergyOut(float nEnergy) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float CapGetMaxEnergy() {

		return capMaxCharge;
	}

	@Override
	public float CapGetMaxEnergyIn() {

		return CapMaxIn;
	}

	@Override
	public void FireAutoMode(boolean turnOn) {
		fireAutoMode = turnOn;
	}

//	@Override
//	public boolean AIAim(EdEnemy [] enemylist) {
//		float mcv=0;
//		float mx = 0,my = 0;
//		if (enemylist==null){AimAt(0,0);return false;}
//		for (EdEnemy enemy: enemylist){
//			if (enemy != null){
//				if(enemy.GetHealth()>0){				
//				float tv = enemy.GetValue();
//				int tx = enemy.GetX();
//				int ty = enemy.GetY();
//				float td = (float) Math.hypot(mX-tx, mY-ty);
//				float cv =  tv*(1-(td/maxDistance));
//				
//				if (cv>mcv){
//					mx = tx;my=ty;mcv=cv;
//				}
//				}
//			}
//		}
//		AimAt(mx, my);
//		if(mcv !=0){return true;}else{return false;}
//	}
	
	@Override
	public boolean AIAim(EdObject[] AllObjs) {
		float mcv=0;
		float mx = 0,my = 0,tx=0,ty=0,tv=0,td=0,cv=0;
		EdBaseEnemy enemy;
		
		if (AllObjs==null){AimAt(0,0,1);return false;}
		
		for (EdObject o: AllObjs){
			if ((o != null)&(o instanceof EdBaseEnemy)){
				enemy = (EdBaseEnemy)o;
				if(enemy.GetHealth()>0){				
					tv = enemy.GetValue();
					tx = enemy.getX();
					ty = enemy.getY();
					td = (float) Math.hypot(mX-tx, mY-ty);
					cv =  tv*(1-(td/maxDistance));
					
					if (cv>mcv){
						mx = tx;my=ty;mcv=cv;
					}
				}
			}
		}
		AimAt(mx, my,aIAimSpeed);
		if(mcv !=0){return true;}else{return false;}
	}
	
	@Override
	public EdDestructable[] CheckForHit(EdDestructable[] targets) {
		
		return mBullets.CheckForHit(targets);
	}
	@Override
	public boolean IsInMyBox(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int[] GetUpgradeLvls() {
		return upgradeLvls;
	}

	@Override
	public int[] GetUpgradeIcons() {
		return new int[]{R.drawable.ed_st_n_1234,R.drawable.ed_glowcap,R.drawable.ed_gun1};

	}

	@Override
	public void setWeaponEffect(EdWeaponEffect wEff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSprite(AngleSurfaceView mGLSurfaceView,boolean ontop){
		for (int i=0;i<3;i++){
			if (GetSpriteResId(i) != 0){
				if (mSprites !=null){
				if (mSprites[i]!=null){
					mGLSurfaceView.removeObject(mSprites[i]);
					mSpriteLayouts[i]= new AngleSpriteLayout(mGLSurfaceView,GetSpriteResId(i));
					mSprites[i]      = new EdAngleSprite(0, 0, 16, mSpriteLayouts[i]);		
					mSprites[i].mPosition.setmX(mX);mSprites[i].mPosition.setmY(mY);mSprites[i].mZ= i*-1f;
					mGLSurfaceView.addObject(mSprites[i]);
					//mGLSurfaceView.MoveObjectToBottom(mSprites[i]);
					

				}
				
				}	
			}
		}
		mSprites[2].mRotation=mRot;		
	}
	



}
