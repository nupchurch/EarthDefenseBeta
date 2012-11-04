package com.ntu.way2fungames.earthdefensebeta;

import android.R.integer;
import android.util.Log;

import com.android.angle.AngleObject;
import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdAimingMechanics;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdEnemy;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdFireable;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdAngleSprite;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdBeam;
import com.ntu.way2fungames.earthdefensebeta.weaponeffects.EdWeaponEffect;

public class EdBaseEnemy extends EdObject implements EdEnemy, EdFireable,EdAimingMechanics,EdDestructable {
	int mDmg=0;
	int mHealth=0;
	int mValue=1;
	int mX=0;
	int mY=0;
	int mPPS;
	private final float fPI = (float)Math.PI;
	private final float f2PI = (float)(Math.PI*2f);
	boolean autoFireMode= true;
	private float wave1;
	private float pingpong1;
	private float pingpong2;
	private float pps;
	private float wRange;
	protected int level=0;
	protected float ySpeed;
	protected float[][] levelColors= new float[][]{ {.75f,.75f,.75f},
			{.35f,.85f,.35f},
			{.75f,.35f,.35f},
			{.95f,.95f,.25f}
			};
	private EdWeaponEffect mWeaponEffect;
	private AngleSpriteLayout mWeaponLayout;
	private int  earthLine;
	
	EdBaseEnemy(int nHealth,int nDmg, float nXSpeed,float nYSpeed,int nearthLine){
		super();
		earthLine = nearthLine;
		mHealth= Math.max(5, nHealth);
		mValue= nHealth*nDmg;
		mSprites= new EdSpriteShip[3];
		//mPPS = nPPS;
		mPPS= (int) nXSpeed ;//+ (int) ((Math.random()-.5f)*50f);		
		//pps = (float) Math.random()*.5f;
		//wRange=(float) Math.random()*90;
		ySpeed= nYSpeed;
		mDmg = nDmg;
	}

	@Override
	public synchronized boolean  AddToAngle(AngleSurfaceView mGLSurfaceView, int i, int j,float nscale) {
		mX=i;mY=j;
		
		AngleVector vScale = new AngleVector();
		
		
		mSpriteLayouts[0]= new AngleSpriteLayout(mGLSurfaceView,GetSpriteResId(0));
		mSpriteLayouts[1]= new AngleSpriteLayout(mGLSurfaceView,GetSpriteResId(1));
		
		mWeaponLayout= new AngleSpriteLayout(mGLSurfaceView,R.drawable.ed_flashsmall2);
		

		mSprites[0]= new EdSpriteShip(i, j,480,1,10,mSpriteLayouts[0]);
		mSprites[1]= new EdSpriteShip(i, j,480,1,10,mSpriteLayouts[1]);
		mSprites[2]= new EdSpriteShip(i, j,480,1,10,mSpriteLayouts[2]);
		
		setWeaponEffect( new EdBeam(mX, mY, 2, 2,mWeaponLayout));
		((EdBeam) (mWeaponEffect)).mGreen=0;
		((EdBeam) (mWeaponEffect)).mBlue=0;
		((EdBeam) (mWeaponEffect)).mRed=1;
		((EdBeam) (mWeaponEffect)).SetEffectType(1);
		
		mSprites[0].mPosition.setmX(i);mSprites[0].mPosition.setmY(j);
		mSprites[1].mPosition.setmX(i);mSprites[1].mPosition.setmY(j);
		mSprites[2].mPosition.setmX(i);mSprites[2].mPosition.setmY(j);
		
		float scale = ((480/480f));
		mSprites[0].mScale=new AngleVector(scale, scale);
		mSprites[1].mScale=new AngleVector(scale, scale);
		scale = ((320f/480f)*nscale*.25f);vScale.set(scale,scale);
		mSprites[2].mScale= vScale;
		
		mGLSurfaceView.addObject(mSprites[0]);
		mGLSurfaceView.addObject(mSprites[1]);
		mGLSurfaceView.addObject((AngleObject) mWeaponEffect);
		
		//mGLSurfaceView.addObject(mSprites[2]);
		SetSpriteColors();
		SetSpriteSpeeds(mPPS, ySpeed);
		return true;
	}

	@Override
	public boolean AIAim(EdObject[] AllObjs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean AimAt(float x, float y,float smod){return false;}//unused

	@Override
	public EdDestructable[] CheckForHit(EdDestructable[] targets) {
		// TODO Auto-generated method stub
		return null;
	}

	public void clone(EdBaseEnemy toClone){
		level= toClone.GetLvl();
		mHealth= toClone.mHealth;
		mDmg = toClone.mDmg;
		mPPS= toClone.mPPS;
		SetSpriteColors();
		SetSpriteSpeeds(toClone.mPPS,toClone.ySpeed);
		
		((EdSpriteShip)mSprites[0]).SetAlive();
		((EdSpriteShip)mSprites[1]).SetAlive();
		((EdSpriteShip)mSprites[0]).mScale  = new AngleVector(1,1);
		((EdSpriteShip)mSprites[1]).mScale  = new AngleVector(1,1);
		
//		((EdSpriteShip)mSprites[0]).mPosition=((EdSpriteShip)toClone.mSprites[0]).mPosition; 
//		((EdSpriteShip)mSprites[1]).mPosition=((EdSpriteShip)toClone.mSprites[1]).mPosition;
	}

	@Override
	public synchronized boolean Destruct() {
		this.Explode();		
		return false;
	}

	@Override
	public synchronized boolean DoAIStep(EdObject nearestTarget) {
		if (mHealth > 0){
//			mX =(int) mSprites[0].mPosition.mX;
//			mY =(int) mSprites[0].mPosition.mY;
			//int earthline =520;
			
			if (pingpong2>f2PI){
				pingpong2 = 0;
			}
			pingpong2 = pingpong2 +pps;
			wave1 = (float) Math.sin(pingpong2);
			float sf = .94f;
			
			for (EdAngleSprite ospr:mSprites){
				EdSpriteShip spr =(EdSpriteShip)ospr;
				
				if (spr.getY() >earthLine-400){
					ospr.mScale.set(ospr.mScale.getmX()*sf,ospr.mScale.getmX()*sf);
				}
				if (spr.getY()>earthLine){
					SetGone();
					return true;
				}
			}
			
			Fire(nearestTarget);
		}
		return false;

	}


	@Override
	public boolean Fire(EdObject nearestTarget) {
		if (mWeaponEffect!=null){
			if (nearestTarget !=null){
			float r = nearestTarget.getDistance(this);
			if (r<600){
				
				mWeaponEffect.AimAt(this,nearestTarget);
				mWeaponEffect.TurnOn();			
				}
			
			}
		}
		return false;
	}

	@Override
	public void FireAutoMode(boolean turnOn) {}

	@Override
	public int GetBottomOfBox() {
		int uy = (int) mSprites[0].mPosition.getmY();
		int halfy =(int) (mSprites[0].mScale.getmY()*40);
		return uy+halfy;
		
	}

	@Override
	public float getColor(int rORgORb) {
		if      (rORgORb==0){return levelColors[level][0];}
		else if (rORgORb==1){return levelColors[level][1];}
		else if (rORgORb==2){return levelColors[level][2];}
		else if (rORgORb==3){return levelColors[level][3];}
		else                {return levelColors[level][4];}
	}

	@Override
	public int GetDmg() {
		return mDmg;
	}

	@Override
	public int GetHealth() {
		return mHealth;
	}
	
	@Override
	public int GetLvl() {

		return level;
	}
	
	@Override
	public float GetTargetValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] GetUpgradeButtons() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int GetUpgradeCost(int idx) {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	private void SetSpriteSpeeds(){
//		((EdSpriteShip)mSprites[0]).ChangeSpeed(mPPS, 5-level);
//		((EdSpriteShip)mSprites[1]).ChangeSpeed(mPPS, 5-level);
//		((EdSpriteShip)mSprites[2]).ChangeSpeed(mPPS, 5-level);
//	}
	
	@Override
	public int[] GetUpgradeCosts() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int[] GetUpgradeIcons() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int[] GetUpgradeLvls() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int GetValue() {
		return (int) (mHealth*(mSprites[0].mScale.getmX()));
	}


	@Override
	public synchronized float getX() {
		mX = (int) ((EdSpriteShip) mSprites[0]).getX();
		return mX;
	}


	


	@Override
	public  synchronized float getY() {
			mY = (int) ((EdSpriteShip) mSprites[0]).getY();
			//if (mY>earthLine){TakeDamage(Integer.MAX_VALUE);}
			return mY;
	}

	@Override
	public boolean IsDead() {
		if (mHealth<=0){
			//Destruct();
			return true;
			}
		else {return false;}
	}


	@Override
	public boolean IsInMyBox(float interceptAtX, float interceptAtY) {
		int ux = (int) mSprites[0].mPosition.getmX();
		int uy = (int) mSprites[0].mPosition.getmY();
		int halfy =(int) (mSprites[0].mScale.getmY()*40);
		int halfx =(int) (mSprites[0].mScale.getmX()*40);
		
		int top    = uy-halfy;
		int bottom = uy+halfy;
		int right  = ux+halfx;
		int left   = ux-halfx;
		
		if     (interceptAtY<top)   {return false;}
		else if(interceptAtY>bottom){return false;}
		else if(interceptAtX>right) {return false;}
		else if(interceptAtX<left)  {return false;}
		else{return true;}
		
	}
	@Override
	public float[] Intersect(float x1, float y1, float x2, float y2) {
	
		int boxW = 80;//mSpriteLayouts[0].roCropWidth;
		
		float olinex1 = mSprites[0].mPosition.getmX()-(boxW/2);
		float olinex2 = mSprites[0].mPosition.getmX()+(boxW/2);
		
		float oliney1 = mSprites[0].mPosition.getmY()+(boxW/2);
		float oliney2 = mSprites[0].mPosition.getmY()+(boxW/2);
		if (oliney1<0){return null;} 
		float olinem = 0;
		float olineyi = oliney1;
		
		float m=((y1-y2)/(x1-x2));
		// y-y1= m(x-x1)
		// y= m(x-x1)+y1
		
		// y= mx+b
		// -b=mx-y
		// b = -(mx-y)
		
		float yi = -((m*x1)-y1);
		//float yi = (m*-x1)+y1;
		
		// (m2)(x)+b2 = (m1)(x)+b1
		// (m2)(x) = (m1)(x)+b1-b2
		// x = ((m1)(x)+b1-b2)/m2		
		//float interceptAtX = (m+yi-olineyi)/olinem;
		float interceptAtX = (yi-olineyi)/(0-m);
		float interceptAtY = (m*interceptAtX)+yi;
		
		// TODO Auto-generated method stub
	
		// x1=1, x2=2, i =0; 0>1 = fasle
		// x1=2, x2=1, i =0; 0>2 = fasle
		// x1=1, x2=2, i =3; (3>1)&(3<2)= false
		// x1=2, x2=1, i =3; (3>2)&(3<1)= false
		
		if (y2>y1){
			if((y2>=interceptAtY)&(interceptAtY>=y1)){
				
					if((olinex1<interceptAtX)&(interceptAtX<olinex2)){
						return new float[]{interceptAtX,interceptAtY};
						}
				
				}
			}
		
//		
//		if (IsInMyBox( (int)x1, (int)y1)){
//			int a = 1;
//			int b = a;
//		}
//		if (IsInMyBox(interceptAtX, interceptAtY)){
//			return true;
//		}
//		if ((interceptAtX>x1)&(interceptAtX<x2)){
//			if ((interceptAtY>y2)&(interceptAtY<y1)){
//				return true;
//			}
//			if ((interceptAtY>y1)&(interceptAtY<y2)){
//				return true;
//			}
//		}
//		// x1=1, x2=2, i =0; 0>2 = fasle
//		// x1=2, x2=1, i =0; 0>1 = fasle
//		// x1=1, x2=2, i =3; (3>2)&(3<1)= false
//		// x1=2, x2=1, i =3; (3>1)&(3<2)= false
//		
//		if ((interceptAtX>x2)&(interceptAtX<x1)){
//			if ((interceptAtY>y2)&(interceptAtY<y1)){
//				return true;
//			}
//			if ((interceptAtY>y1)&(interceptAtY<y2)){
//				return true;
//			}
//
//			
//		}
	
		return null;
		
	}
	
	@Override
	public boolean IsExploding() {
		return ((EdSpriteShip)mSprites[0]).IsExpoding();
	}

	@Override
	public boolean IsGone(){
		return super.IsGone();
	}

	public void SetPosition(int x, int y) {
		AngleVector tmp = new AngleVector(x,y);
		((EdSpriteShip)mSprites[0]).mPosition=tmp;
		((EdSpriteShip)mSprites[1]).mPosition=tmp;
		
	}

	private void SetSpriteColors(){
		int lngth=levelColors.length;
		if(lngth -1<level){
			Log.v("earthdefensebeta", "critical error #1");
			}
		mSprites[0].mRed=  (levelColors[level][0]/3f)+.66f;
		mSprites[0].mGreen=(levelColors[level][1]/3f)+.66f;
		mSprites[0].mBlue= (levelColors[level][2]/3f)+.66f;
		
		mSprites[1].mRed=  levelColors[level][0];
		mSprites[1].mGreen=levelColors[level][1];
		mSprites[1].mBlue= levelColors[level][2];
	
	}
	private void SetSpriteSpeeds(int xSpeed,float ySpeed2){
		((EdSpriteShip)mSprites[0]).ChangeSpeed(xSpeed, ySpeed2);
		((EdSpriteShip)mSprites[1]).ChangeSpeed(xSpeed, ySpeed2);
		((EdSpriteShip)mSprites[2]).ChangeSpeed(xSpeed, ySpeed2);
	}
	@Override
	public boolean TakeDamage(int nDmg) {
		if (mHealth>0){
			mHealth = mHealth -nDmg;
			if (mHealth<=0){Destruct();}
			return true;
		}
		return false;

	}
	@Override
	public EdObject Upgrade(int UpgradeIdx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWeaponEffect(EdWeaponEffect wEff) {
		mWeaponEffect = wEff;
		
	}

	private  synchronized void SetGone() {
		mHealth=0;
		if (mSprites[0]!= null){mSprites[0].exploding=0;((EdSpriteShip)mSprites[0]).isDead = true;}
		if (mSprites[1]!= null){mSprites[1].exploding=0;((EdSpriteShip)mSprites[1]).isDead = true;}
		if (mSprites[2]!= null){mSprites[2].exploding=0;((EdSpriteShip)mSprites[2]).isDead = true;}
		if (mWeaponEffect!=null){ ((EdBeam) (mWeaponEffect)).TurnOff();}

		
		
//		mSprites[0].exploding=0;
//		mSprites[0].isDead=true;
//		mSprites[1].exploding=0;
//		mSprites[1].isDead=true;
//	
//		if (mWeaponEffect!=null){ ((EdBeam) (mWeaponEffect)).TurnOff() ;}
		
	}

	public synchronized void Explode(){
			if (mSprites[0]!= null)((EdSpriteShip)mSprites[0]).Explode();
			if (mSprites[1]!= null)((EdSpriteShip)mSprites[1]).isDead=true;
			if (mSprites[2]!= null)((EdSpriteShip)mSprites[2]).isDead=true;
			if (mWeaponEffect!=null){ ((EdBeam) (mWeaponEffect)).TurnOff();}
	//		SetGone();
		}

//	@Override
//	public boolean Fire() {
//		// TODO Auto-generated method stub
//		return false;
//	}
}
