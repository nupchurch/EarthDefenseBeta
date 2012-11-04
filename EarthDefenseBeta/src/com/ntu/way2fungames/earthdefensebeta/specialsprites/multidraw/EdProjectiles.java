package com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleVector;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;


public class EdProjectiles extends AngleRotatingSprite{
	//final int maxP = (int) (Math.random()*25f)+50;
	final int maxP = 500;
	final int checkP = (int) (maxP*.9f);
	private float[] x1 = new float[ maxP];
	private float[] y1 =  new float[ maxP];
	private float[] a =  new float[ maxP];
	private float[] m =  new float[ maxP];
	private float[] x2 =  new float[ maxP];
	private float[] y2 =  new float[ maxP];

	private float[] prevX =  new float[ maxP];
	private float[] prevY =  new float[ maxP];

	private boolean[] d =  new boolean[ maxP];

	private boolean[] explode =  new boolean[ maxP];
	
	private boolean oneDead;
	private int deadOnes=0;
	private int curIdx = 0;

	private AngleSpriteLayout ProjLayout;
	
	private Object projlock = new Object();
	protected EdExplosion explosions;
	
	public EdProjectiles(float[] nx1,float[] ny1,float[] na,float[] nm,AngleSpriteLayout layProj,AngleSpriteLayout layExp) {
		super(layProj);		
		mScale.set((9f/8f)*(320f/480f)*2, (4f/8f)*(320f/480f)*3);
		mRed = .75f;
		mGreen = .85f;
		
		for (int i = 0;i<nx1.length-1;i=i+1){
			addone (nx1[i],ny1[i],na[i],nm[i]);
		}
		for (int i = 0;i< maxP-1;i=i+1){
			d[i]= true;
		}

		ProjLayout= layProj;
		explosions = new EdExplosion(null, null, layExp);
	}
	public void addone(float nx, float ny, float na, float nm){
		
		x1[curIdx]= nx;	
		y1[curIdx]= ny;
		a[curIdx] = na;
		m[curIdx] = nm;
		  
		float r = (float) ((na /180f)* Math.PI);
		y2[curIdx] = (float)-( Math.sin(r)*nm);
		x2[curIdx] = (float)( Math.cos(r)*nm);
		d[curIdx] = false;
		
		curIdx = curIdx + 1;
		if (curIdx> maxP-1){curIdx =  maxP-1;}
	}
	public void addSome(float[] nx, float[] ny, float[] na, float[] nm){
		int onetoaddidx=0;		
		if (nx.length<= CountDead()){
			
			synchronized(projlock){
			for (int i=0;i<maxP;i=i+1){
				if (d[i]==true){
					x1[i]= nx[onetoaddidx];	
					y1[i]= ny[onetoaddidx];
					a[i] = na[onetoaddidx];
					m[i] = nm[onetoaddidx];
					  
					float r = (float) ((na[onetoaddidx] /180f)* Math.PI);
					y2[i] = (float)-(Math.sin(r)*nm[onetoaddidx]);
					x2[i] = (float) (Math.cos(r)*nm[onetoaddidx]);
					d[i] = false;
					onetoaddidx=onetoaddidx+1;
					//curIdx=curIdx+1;
					if (onetoaddidx==nx.length){break;}
				}
			}
			}
			
		}else{
			Log.v("earthdefensebeta","out of proj space: "+String.valueOf(CountDead()));
		}	
	}
	private int CountDead(){
		int dc=0;
		for (boolean dd:d){
			if (dd==true){dc=dc+1;}
		}
		return dc;
	}
//	public synchronized void addSome(float[] nx, float[] ny, float[] na, float[] nm){
//		if (curIdx+na.length > checkP){
//			compact();
//			Log.v("earthdefensebeta","add compact");
//			}
//		if (curIdx+na.length > maxP){return;}
//		
//		for (int i =0;i<ny.length;i=i+1){
//			x1[curIdx]= nx[i];	
//			y1[curIdx]= ny[i];
//			a[curIdx] = na[i];
//			m[curIdx] = nm[i];
//			  
//			float r = (float) ((na[i] /180f)* Math.PI);
//			y2[curIdx] = (float)-( Math.sin(r)*nm[i]);
//			x2[curIdx] = (float) (Math.cos(r)*nm[i]);
//			d[curIdx] = false;
//			curIdx = curIdx + 1;
//			if (curIdx> maxP-1){curIdx =  maxP-1;}
//		}
//		if (curIdx > checkP){compact();}
//		if (curIdx > maxP-1){curIdx=maxP-1;}
//	}

	
	@Override
	public void step(float secondsElapsed){
		super.step(secondsElapsed);
		synchronized(projlock){
		for (int i = 0;i<maxP;i=i+1){
			if (d [i]==false){
				prevX[i]=x1[i];prevY[i]=y1[i];
				
				x1[i] = x1[i]+(x2[i]*secondsElapsed);
				y1[i] = y1[i]+(y2[i]*secondsElapsed);
				
				if (x1[i]>1200) {oneDead= true;}
				if (x1[i]<0)   {oneDead= true;}
				if (y1[i]>1920) {oneDead= true;}
				if (y1[i]<0)   {oneDead= true;}
				if (oneDead){d[i]=true;deadOnes= deadOnes+1;oneDead=false;}
				if (curIdx>2){
					if (deadOnes >= curIdx-1){curIdx=0;deadOnes=0;}
					//if (deadOnes > checkP){compact();}
				}
			}
		}
		}
		
	}
	private void compact(){
		int lIdx =0;
		int rIdx = curIdx;
		Log.v("earthdefensebeta","Gun,Before Compact: "+String.valueOf(curIdx));
		curIdx=0;
		while (lIdx < rIdx){
			
			if (d[lIdx]== true){
				
				if (d[rIdx]== false){
					x1[lIdx]= x1[rIdx];	
					y1[lIdx]= y1[rIdx];
					a[lIdx] = a[rIdx];
					m[lIdx] = m[rIdx];
					a[lIdx] =a[rIdx];  
					y2[lIdx] = y2[rIdx];
					x2[lIdx] = x2[rIdx];
					d[lIdx] = false;
					d[rIdx] = true;
					curIdx=curIdx+1;
					
					deadOnes= deadOnes-1;
				}else{rIdx = rIdx-1;}//right right is dead AND left one is alive	
				
			}else{lIdx = lIdx+1;curIdx=curIdx+1;}//left not dead
			
		}
		if (curIdx>maxP){curIdx= maxP;}
		Log.v("earthdefensebeta","     After: "+String.valueOf(curIdx));
	}
	
	@Override
	public void draw(GL10 gl){
		//setFrame((int) (Math.random()*3));
		for (int ii = 0;ii<maxP;ii=ii+1){
			if (d[ii]==false){

				mPosition.setmX(x1[ii]);
				mPosition.setmY(y1[ii]);
				mRotation= a[ii];
				super.draw(gl);
				
			}
		}
		
	}
	public void remove(int i){
		d[i]=true;
		deadOnes= deadOnes+1;
	}
	public EdDestructable[] CheckForHit(EdDestructable[] targets) {
		EdDestructable outlist[] = new EdDestructable[10];
		int oIdx = 0;
		
		for(EdDestructable o:targets){
			if (o!= null){
				for (int i=0;i<maxP;i=i+1){
					if (d[i]==false){
						if (o.IsDead()==false){
							
//							if(o.IsInMyBox((int)x1[i], (int)y1[i])){
//								o.TakeDamage(1);
//								if(o.IsDead()){
//									outlist[oIdx]=o;
//									oIdx= oIdx+1;
//									}
//								d[i]=true;
//								explosions.addone(x1[i],o.GetBottomOfBox());
//								//explosions.addone(x1[i]-(x2[i]/2),y1[i]-(y2[i]/2));								
//
//							}
							
							float is[] = o.Intersect(x1[i],y1[i],prevX[i],prevY[i]); 
							if(is != null){
								o.TakeDamage(1);
								if(o.IsDead()){
									outlist[oIdx]=o;
									oIdx= oIdx+1;
									}
								d[i]=true;
								explosions.addone(is[0],is[1]);
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
	public void AddToAngle(AngleSurfaceView mGLSurfaceView) {
		mGLSurfaceView.addObject(this);
		//explosions.mScale = new AngleVector(.25f,.25f);
		explosions.mGreen=.75f;
		explosions.mBlue=.5f;		
		mGLSurfaceView.addObject(explosions);
		//mGLSurfaceView.MoveObjectToTop(mGLSurfaceView.WhatIdx(explosions));
		
	}
}
