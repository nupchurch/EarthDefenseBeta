package com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw;

import javax.microedition.khronos.opengles.GL10;


import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
public class EdMultiDraw extends AngleRotatingSprite{
	protected final int maxP = 50;
	protected final int checkP = (int) (maxP*.9f);
	protected float[]   x1 = new float  [ maxP];
	protected float[]   y1 = new float  [ maxP];
	protected boolean[] d  = new boolean[ maxP];
	protected int curIdx = 0;
	
	protected Object projlock = new Object();

	public EdMultiDraw(float[] nx1,float[] ny1,AngleSpriteLayout layout) {
		super(layout);		
		//mScale.set((9f/8f)*(320f/480f)*2, (4f/8f)*(320f/480f)*3);
		//mRed = .75f;
		//mGreen = .85f;
		if (nx1!= null){
			addSome(nx1,ny1);
		}
		
	}
	public void removeFromEngine(){
		AngleSurfaceView tsv= this.getSurfaceView();
			if (tsv!=null){
			removeObject(this);
		}else{
			
		}
	}
	public boolean addone(float nx, float ny ){
		if (curIdx<maxP-1){
			
			d [curIdx]= false;
			x1[curIdx]= nx;	
			y1[curIdx]= ny;
			
			curIdx = curIdx + 1;
			return true;
		}else{
			for (int i=0;i<maxP;i=i+1){
				if (d[i]==true){

					d [i]= false;
					x1[i]= nx;	
					y1[i]= ny;
					
					return true;
				}
			}
			return false;
		}
	}
	public boolean addSome(float[] nx, float[] ny){
		if (curIdx+nx.length<maxP-1){
			synchronized(projlock){
				for (int i=0;i<maxP;i=i+1){
					if (addone(nx[i],ny[i])==false){return false;}
				}
			}
		}
		return true;
	}
	
	
	@Override
	public void draw(GL10 gl){
		for (int idx = 0;idx<maxP;idx=idx+1){
			if(d[idx]==false){
				partSetup(idx);
				mPosition.setmX(x1[idx]);
				mPosition.setmY(y1[idx]);
				super.draw(gl);
			}
		}
	}
	
	protected void partSetup(int idx) {
		
	}
	
	public void remove(int i){
		d[i]=true;
		
	}

}