package com.ntu.way2fungames.earthdefensebeta.weaponeffects;

import javax.management.MXBean;
import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.angle.AngleSpriteLayout;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdMultiDraw;

public class EdBeam extends EdMultiDraw implements EdWeaponEffect {

	private int pr;
	private float ll;
	private float lperp;
	private float cx;
	private float cy;
	private int oX;
	private int oY;
	private boolean isOn;
	private int mEffectType;
	public Object rtlock;
	
	public EdBeam(int originatingX,int originatingY,int x2,int y2,AngleSpriteLayout layout) {
		super(null, null, layout);
		oX = originatingX;
		oY = originatingY;
		ChangeBeam(originatingX,originatingY,x2,y2);
		
	}
	
	public void ChangeBeam(int x1,int y1,int x2,int y2){
		//Log.v("way2fungames", String.valueOf(x1)+" "+String.valueOf(y1)+" "+String.valueOf(x2)+" "+String.valueOf(y2)+" ");
		
		ll = (float) Math.hypot(x1-x2, y1-y2);
		lperp= ll/maxP;
		cx = ((float)x1-x2)/maxP;
		cy = -((float)y1-y2)/maxP;
		for (int i =0;i<maxP;i=i+1){
			d[i]=true;
			addone(x1-(cx*i), y1+(cy*i));
		}
		
		cx= -cx*.001f;cy= -cy*.001f;
		
//		for (int i =0;i<maxP/2;i=i+1){
//			d[i]=true;
//			addone(x1, y1);
//		}
//		for (int i =maxP/2;i<maxP;i=i+1){
//			d[i]=true;
//			addone(x2, y2);
//		}

	}
	
	@Override
	protected void partSetup(int idx) {
		float meh = .02f;
		float mel = .01f;
		float sc = 0;

		if (mEffectType==0){
			x1[idx] = (float) (x1[idx]*(1+ (Math.random()*cx) -(cx/2)));
			y1[idx] = (float) (y1[idx]*(1+ (Math.random()*cy) -(cy/2)));
			sc = (float) (Math.random()*2f)+.25f;
			}
		if (mEffectType==1){
			x1[idx] = (float) (x1[idx]);
			y1[idx] = (float) (y1[idx]);

			sc = (float) (Math.random()*.5f)+.25f;
			}
		
		mScale.setmX(sc); mScale.setmY(sc);
	}
	@Override
	public void draw(GL10 gl){
		if (rtlock ==null){rtlock = new Object();}
		if (isOn){super.draw(gl);}
	}
	
	public EdDestructable[] CheckForHit(EdDestructable[] targets) {
		EdDestructable outlist[] = new EdDestructable[maxP];
		int oIdx = 0;
		
		for(EdDestructable o:targets){
			if (o!= null){
				for (int i=0;i<maxP;i=i+1){
					if (d[i]==false){
						if (o.IsDead()==false){
							if(o.IsInMyBox((int)x1[i], (int)y1[i])){
								//o.TakeDamage(1);
								//if(o.IsDead()){
								outlist[oIdx]=o;
								oIdx= oIdx+1;
									//}
								//d[i]=true;
								//explosions.addone(x1[i],o.GetBottomOfBox());
								//explosions.addone(x1[i]-(x2[i]/2),y1[i]-(y2[i]/2));								

						}	
					}
				}
				}
			}
		}
		
		return outlist;
	}

	@Override
	public void AimAt(int x, int y) {
		this.ChangeBeam(oX, oY, x, y);
	}

	@Override
	public void AimAt(EdObject atObj) {
		this.ChangeBeam(oX, oY,(int) atObj.getX(), (int)atObj.getY());
	}
	

	public void TurnOn(long msec) {
		
	}
	
	@Override
	public void TurnOn() {
		isOn = true;
		//this.mAlpha=1;
		
	}

	@Override
	public void TurnOff() {
		isOn = false;
		//this.mAlpha=0;
	}

	@Override
	public void AimAt(EdObject oSource, EdObject nearestTarget) {
		oX =(int) oSource.getX();oY =(int) oSource.getY();
		AimAt(nearestTarget);
	}

	public void SetEffectType(int i) {
		 mEffectType =i;
		
	}

}
