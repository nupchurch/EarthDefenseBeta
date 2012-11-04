package com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw;

import com.android.angle.AngleSpriteLayout;

public class EdSimpleParticalSystem extends EdMultiDraw{
	protected float[]   x2 = new float  [maxP];
	protected float[]   y2 = new float  [maxP];
	protected float[]    lt = new float   [maxP];
	
	public EdSimpleParticalSystem(AngleSpriteLayout layout) {
		super(null, null, layout);
		
		// TODO Auto-generated constructor stub
	}
	
	public boolean addone(float nx, float ny ,float xa,float ya, float nlt){
		synchronized(projlock){
		if (curIdx<maxP-1){
			
			d [curIdx]= false;
			x1[curIdx]= nx;	
			y1[curIdx]= ny;
			x2[curIdx]= xa;	
			y2[curIdx]= ya;
			lt[curIdx]= nlt;
			
			curIdx = curIdx + 1;
			return true;
		}else{
			for (int i=0;i<maxP;i=i+1){
				if (d[i]==true){

					d [i]= false;
					x1[i]= nx;	
					y1[i]= ny;
					x2[i]= xa;	
					y2[i]= ya;
					lt[i]= nlt;
					
					return true;
				}
			}
			return false;
		}
		
		}
	}
	
	@Override
	public boolean addone(float nx, float ny ){
		return addone(nx,ny,1,1,1000);
		
	}
	@Override
	public	void step(float secondsElapsed){
		super.step(secondsElapsed);
		for (int i=0;i<maxP;i=i+1){
			lt[i]=lt[i]- secondsElapsed;
			if (lt[i] > 0){
				x1[i]=x1[i]+x2[i];
				y1[i]=y1[i]+y2[i];
			}else{
				d[i]=true;
			}
		}
	}

	public void addone(float mX, float mY, float f, float mag, float g,int ncolor) {
		f=(float) ((f/180)*(Math.PI));
		
		addone(mX, mY, (float)Math.sin(f)*mag, (float)-Math.cos(f)*mag, g);
		
	}

	
}
