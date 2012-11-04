package com.ntu.way2fungames.earthdefensebeta;

import com.android.angle.AngleSurfaceView;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdEnemyType1;

public class EdEnemyTypeH extends EdBaseEnemy implements EdEnemyType1 {

//	protected final float[][] levelColors= new float[][]{ {.75f,.75f,.95f},
//														{.35f,.85f,.35f},
//														{.95f,.35f,.35f},
//														{.95f,.95f,.05f},
//														{1.00f,0.00f,0.00f},
//														{0.00f,1.00f,0.00f},
//														{0.05f,0.05f,0.15f}
//														};
	public int GetSpriteResId(int whatLayer) {
		if      (whatLayer==0){return Sprite0;}
		else if (whatLayer==1){return Sprite1;}
		else                  {return R.drawable.ed_gun1;}
	}
	
//	public EdEnemyTypeH(int nlevel){
//		//float h = (float) (Math.random()*2)+8;
//		//1
//		//10*1  =10
//		//10*2*2=40
//		//10*3*3=90
//		
//		super(10*nlevel*nlevel, 10, 10/(nlevel+1));
//		SetLevelColors();
// 
//		level = nlevel;
//		mValue=100;
//	}
	
	public EdEnemyTypeH(int nlevel,float nSpeedX,float nSpeedY, int nearthLine){
		//float h = (float) (Math.random()*2)+8;
		//1
		//10*1  =10
		//10*2*2=40
		//10*3*3=90
		
		super(5*nlevel*nlevel, 10,nSpeedX,nSpeedY,nearthLine);
		SetLevelColors();
 
		level = nlevel;
		mValue=100;
	}

	
	private void SetLevelColors() {
		levelColors = new float[][]{ {.75f,.75f,.95f},
				{.35f,.85f,.35f},
				{.95f,.35f,.35f},
				{.95f,.95f,.05f},
				{1.00f,0.00f,0.00f},
				{0.00f,1.00f,0.00f},
				{0.05f,0.05f,0.15f}
				};
	}

	@Override
	public float getColor(int rORgORb) {
		if      (rORgORb==0){return levelColors[level][0];}
		else if (rORgORb==1){return levelColors[level][1];}
		else if (rORgORb==2){return levelColors[level][2];}
		else if (rORgORb==3){return levelColors[level][3];}
		else                {return levelColors[level][4];}
	}
//	@Override
//	public boolean AddToAngle(AngleSurfaceView mGLSurfaceView, int i, int j,float nscale) {
//		 
//		if (super.AddToAngle(mGLSurfaceView, i, j, nscale)==true){
//			mSprites[0].mRed=  levelColors[level][0];
//			mSprites[0].mGreen=levelColors[level][1];
//			mSprites[0].mBlue= levelColors[level][2];
//			
//			mSprites[1].mRed=  levelColors[level][0];
//			mSprites[1].mGreen=levelColors[level][1];
//			mSprites[1].mBlue= levelColors[level][2];
//			
//			mHealth = mHealth*level;			
//			
//			return true;	
//		}
//		return false;
//		
//	}
}
