package com.ntu.way2fungames.earthdefensebeta.weapons;

import android.util.Log;
import android.view.MotionEvent;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;
import com.ntu.way2fungames.earthdefensebeta.R;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdCapacitor;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdFireable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdMotionEvent;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdUpgradeable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdUsesEnergy;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdExplosion;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdMultiDraw;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdProjectiles;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdSimpleParticalSystem;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdBeam;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdWeaponEffect;

public class EdRadiationFlinger extends EdWeapon implements EdMotionEvent, EdUsesEnergy,EdCapacitor,EdUpgradeable,EdFireable{
	public EdRadiationFlinger(String structureConnections) {
		super(structureConnections);
		// TODO Auto-generated constructor stub
	}
	final float radsPerDeg = (float) (Math.PI/180f);
	protected AngleSpriteLayout mRadiationLayout;
	protected EdSimpleParticalSystem mParts;
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
	protected int projSpeed =1200;
	protected int[] upgradeLvls = new int[]{1,1,1};
	protected EdExplosion explosions;
	protected int mHealth=50;
	private AngleSpriteLayout mBeamLayout;
	private EdBeam mBeam;
	private boolean beamOn;
	private int beamCount;
	private float FireVectorX;
	private float FireVectorY;
	private float d1=1000;
	private float eY2;
	private float eY1;
	private float eX1;
	private float eX2;
	private float mDmg = 1.5f;
	
	private float d2;
	private int flingState=0;
	@Override
	public float CapEnergyIn(float nEnergy) {

		float maxin     = CapGetMaxEnergyIn();
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
		//mSprites[1].mRed=  ((pf*.75f)+.25f);		
		//mSprites[1].mGreen=((pf*.75f)+.25f);
		mSprites[1].mAlpha= ((pf*.75f)+.25f);
		 
		for (float i = (float) (Math.random()*36f);i<360;i=i+36){
			mParts.addone(mX,mY,i,3.0f*pf,1-(pf/2),0);		
		}
		if (beamOn==true){
			if (beamCount<=0){
				//mBeam.ChangeBeam(-50, -50, -50,-50);
				//mBeam.mAlpha=0;
				beamOn=false;
				mBeam.TurnOff();
				}
			beamCount= beamCount-1;
			//mBeam.mAlpha=beamCount/10f;
			}
		return rv;
	}
	@Override
	public boolean Fire(EdObject nearestTarget) {
		if (flingState!=3){return false;}
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
			
			
			//mParts.addSome(new float[]{muzX0,muzX1,muzX2}, new float[]{muzY0,muzY1,muzY2}, new float[]{uRot,uRot,uRot}, new float[]{projSpeed,projSpeed,projSpeed});
			//mParts.addone(muzX0, muzY0, uRot, 333);
			//mParts.addone(muzX1, muzY1, uRot, 333);
			//mParts.addone(muzX2, muzY2, uRot, 333);
			
			//mFlash.mPosition.set(muzX0+61,muzY0+61);
			mBeam.ChangeBeam((int)mX+00, (int)mY+00,(int)(mX-(FireVectorX*825f)) ,(int)(mY+(FireVectorY*825f)));
			beamOn = true;
			//mBeam.mAlpha=1;
			mBeam.TurnOn();
			beamCount =(int) (mDmg*mDmg*mDmg);
		}
		return false;
}
	@Override	
	public int GetSpriteResId(int whatLayer){
		
		if      (whatLayer==0){return SpritesIDs[0];}
		else if (whatLayer==1){return R.drawable.ed_glowcap ;}
		return 0;

		
	}
	@Override
	public boolean AddToAngle(AngleSurfaceView mGLSurfaceView, int i, int j,float nScale) {
		super.AddToAngle(mGLSurfaceView, i, j, nScale);
		mRadiationLayout = new AngleSpriteLayout(mGLSurfaceView,com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_flashsmall2);
		mFlashLayout = new AngleSpriteLayout(mGLSurfaceView, 128,128,com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_muzzleflash);
		mBeamLayout =  new AngleSpriteLayout(mGLSurfaceView,R.drawable.ed_flashsmall2);
		//explosions = new EdExplosion(null, null, mFlashLayout);
		mFlash = new AngleRotatingSprite(160,160,mFlashLayout);		
		mFlash.mPosition.set(mX+190,mY+190);
		mFlash.mScale.set(4.00f,4.00f);
		mFlash.mAlpha = .75f;
		
		//EdMultiDraw mBullets1 = new EdMultiDraw(null, null, mRadiationLayout);
		AngleSpriteLayout expLayout = new AngleSpriteLayout(mGLSurfaceView, com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_flashsmall);
		
		mBeam = new EdBeam(-50,-50,-50,-50,mBeamLayout);
		
		mParts = new EdSimpleParticalSystem(mRadiationLayout);
		
		mParts.mScale= new AngleVector(1,1);
		mParts.mAlpha=.75f;
		//mParts.mScale= new AngleVector(.5f,.5f);
		
		//mParts.AddToAngle(mGLSurfaceView);
		mGLSurfaceView.addObject(mParts);
		mGLSurfaceView.addObject(mFlash);
		mGLSurfaceView.addObject(mBeam);
		mHealth= 50;
		return true;
	}
	
	@Override
	public boolean IsDead() {
		if (mHealth<=0){ return true;}
		else {return false;}
	}

	@Override
	public boolean UseEnergy(float uEnergy) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public EdObject Upgrade(int UpgradeIdx) {
		if (UpgradeIdx==1){
			mDmg=mDmg*1.1f;
			upgradeLvls[1]=upgradeLvls[1]+1;
		}
		return null;
	}
	@Override
	public float CapEnergyOut(float nEnergy) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float CapGetMaxEnergy() {
		// TODO Auto-generated method stub
		return 100;
	}
	@Override
	public float CapGetMaxEnergyIn() {
		// TODO Auto-generated method stub
		return (upgradeLvls[1]/2)+.5f;
	}
	@Override
	public EdDestructable[] CheckForHit(EdDestructable[] targets) {
		if (beamOn==false){return null;}
		EdDestructable[] damaged = mBeam.CheckForHit(targets);
		EdDestructable outlist[] = new EdDestructable[50];
		int outIdx = 0;
		for(EdDestructable o:damaged){
			if (o!=null){
			o.TakeDamage((int) mDmg);
			if (o.IsDead()){
				outlist[outIdx]=o;
				outIdx=outIdx+1;
			}
			}
		}
		return outlist;
		
	}
	@Override
	public void FireAutoMode(boolean turnOn) {
		// TODO Auto-generated method stub
		
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
	public int[] GetUpgradeIcons() {
		return new int[]{R.drawable.ed_st_n_1234,R.drawable.ed_glowcap,R.drawable.ed_gun1};

	}
	@Override
	public int[] GetUpgradeLvls() {
		return upgradeLvls;
	}
	@Override
	public boolean MotionEvent(MotionEvent event) {
		
		if (event.getAction()== MotionEvent.ACTION_DOWN ){
			
			Log.v("earthdefensebeta","RF DOWN");
			flingState=0;
			eX1 = event.getX();
			eY1 = event.getY();				
			d1 = (float) Math.hypot(mX-eX1 , mY-eY1);
			if (d1<80){flingState=1;}
			Log.v("earthdefensebeta", String.valueOf(eX1)+","+String.valueOf(eY1));
			Log.v("earthdefensebeta", String.valueOf(d1));
			
		}else if (event.getAction()== MotionEvent.ACTION_MOVE ){
			if (d1 < 80){
				eX2 = event.getX();
				eY2 = event.getY();			
				d2 = (float) Math.hypot(eX1-eX2 , eY1-eY2);
				Log.v("earthdefensebeta","RF MOVE: "+String.valueOf(d2));
				if (d2>100){
					flingState=2;
					d1=1000;
				}
			}
		}else if (event.getAction()== MotionEvent.ACTION_UP){
			Log.v("earthdefensebeta","RF UP");
			if (flingState==2){
				eX2 = event.getX();
				eY2 = event.getY();
				d2 = (float) Math.hypot(eX1-eX2 , eY1-eY2);
				FireVectorX =  (eX1-eX2)/d2;
				FireVectorY = -(eY1-eY2)/d2;
				flingState=3;
				Log.v("earthdefensebeta", String.valueOf(FireVectorX)+","+String.valueOf(FireVectorY));
				Log.v("earthdefensebeta", String.valueOf(d2));
				Fire(null);
				flingState=0;
			}
			
		}
		return false;
	}
	@Override
	public void setWeaponEffect(EdWeaponEffect wEff) {
		// TODO Auto-generated method stub
		
	}
}