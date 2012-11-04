package com.ntu.way2fungames.earthdefensebeta;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.content.Context;
import android.util.AttributeSet;

import com.android.angle.AngleSurfaceView;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdEnemy;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.objects.EdStructure;

public class EdAngleSurfaceView extends AngleSurfaceView{
	private EdObject[] mEdObjects;


	public EdAngleSurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public EdAngleSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public EdAngleSurfaceView(Context context, EdObject[] nEdObjects) {
		super(context);
		mEdObjects= nEdObjects;
	}
	@Override
	public synchronized void draw(GL10 gl)
	{
		DoAiStep();
		super.draw(gl);
		
	}


	public void DoAiStep(){
		if (mEdObjects!=null){
			for (EdObject o:mEdObjects){
				if (o instanceof EdEnemy ){
					if (o.IsDead()==false){
						if(((EdEnemy)o).DoAIStep(getNearest(o))==true){// if enemy made it to earth
						//Score=Score-(int)(Math.pow(((EdEnemy)o).GetLvl()+1,3));
						}
					}
				}
			}
		}
	}
	public EdObject getNearest(EdObject in){
		if (in instanceof EdBaseEnemy){
			EdBaseEnemy in2 = (EdBaseEnemy) in;
			return getNearest(in2.getX() ,in2.getY());
			}
		return getNearest(in.getX() ,in.getY());
	}

	public EdObject getNearest(float X,float Y){
		float mind = Float.MAX_VALUE;
		EdObject out = null;
		for (EdObject o:mEdObjects){
			if (o instanceof EdStructure){
				float d = (float) Math.hypot(X-o.getX(), Y-o.getY());
				if (d<mind){mind=d;out = o;}
			}
		}
		return out;
	}
}
