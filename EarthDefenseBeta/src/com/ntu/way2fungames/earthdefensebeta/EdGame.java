package com.ntu.way2fungames.earthdefensebeta;

import android.content.Context;
import android.test.IsolatedContext;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.angle.AngleSurfaceView;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdAimingMechanics;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdCapacitor;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdDestructable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdEnemy;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdFireable;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdMotionEvent;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdUpgradeable;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.objects.EdStructure;
import com.ntu.way2fungames.earthdefensebeta.weapons.EdWeapon;

public class EdGame {
	EdAngleSurfaceView lAngleSurfaceView;
	int xSizePx=0;
	int ySizePx=0;
	int xSize=0;
	int ySize=0;
	float AR; 
	float tSizeX = 0;
	float tSizeY = 0;
	private final int maxobjs = 55+12;
	private int aoidx;
	EdObject AllObjects[] = new EdObject[maxobjs];
	int Score;
	float BuildPts=525;
	private EdObject[][] MyGrid;//= new edObject
	private double aIEffectivness =.05;
	private float bpBase=3;
	public boolean mPaused;

	public Boolean AddObjectToGameFree(EdObject nObject,int x, int y){
			Log.v("way2fungames","enter AddObjectToGameFree");
			int addIdx=-1;
			for (int i = 0;i<AllObjects.length;i=i+1){
				EdObject slotObj = AllObjects[i];
				if      (AllObjects[i]==null){//----------------free
					addIdx= i;break;
				}else if (nObject instanceof EdEnemyTypeH){//---alien
					if(slotObj instanceof EdEnemyTypeH){
						if (AllObjects[i].IsGone()==true){
							addIdx= i;break;
						}
					}
	//			}else if ((nObject instanceof EdObject )&&(slotObj instanceof EdObject)){//---
	//				if (AllObjects[i].IsGone()==true){
	//					addIdx= i;break;
	//				}
				}
			}
			//done adding angle
			if (addIdx == -1){
				Log.v("way2fungames","out of OBJ space");
				return false;
			}else{
				if (AllObjects[addIdx]==null){ // -----------------------if object slot is untaken
					nObject.AddToAngle(lAngleSurfaceView,x,y,1);
					AllObjects[addIdx]= nObject;
					aoidx=addIdx;
					Log.v("way2fungames","aoidx = " + String.valueOf(addIdx));
					//float uscale = (float)(tSizeX/((320f/480f)*128f));
					return true;
					
				}else if (nObject instanceof EdBaseEnemy){//-------------object slot is dead.
					if(AllObjects[addIdx]instanceof EdBaseEnemy){//recieving slot was edbaseenemy as well
						CloneEnemy(addIdx,nObject);
						((EdBaseEnemy)(AllObjects[addIdx])).SetPosition(x, y);
					}
					Log.v("way2fungames","AddObjectToGameFree cloned enemy");
					return true;
	
					//			}else if (nObject instanceof EdObject){//---------------
	//				AllObjects[addIdx]= nObject;
	//				aoidx = addIdx;
	//				Log.v("earthdefensebeta","huh aoidx = " + String.valueOf(addIdx));
	//				float uscale = (float)(tSizeX/((320f/480f)*128f));
	//				return nObject.AddToAngle(lAngleSurfaceView,x,y,1);
					
				}else{
					Log.v("way2fungames","addobject free: not sure what happend");
					return false;
				}
			}	
			
			
	//		if (aoidx >(maxobjs*.9f)){compact();}
		}
	public Boolean AddObjectToGameSnapToGrid(EdObject nObject, int pxX, int pxY) {
		int gridX = (int) (pxX/tSizeX);
		int gridY = (int) (pxY/tSizeY);
		return AddObjectToGame(nObject, gridX, gridY);
	}
	public Boolean AddObjectToGame(EdObject nObject,int gridX, int gridY){
		//int ay = (int) (ySizePx-(((ySize-y)*tSizeY)+(tSizeY/2f)));
		//                           800-200
		int pxY = (int)((gridY*tSizeY)+(tSizeY/2f));
		int pxX = (int)((gridX*tSizeX)+(tSizeX/2f));
		
		//int ay = (int) (ySizePx-(((ySize-y)*tSizeY)));
		//int ax = (int)((x*tSizeX));
	
		if (AddObjectToGameFree(nObject, pxX, pxY)==true){
			MyGrid[gridX][gridY]= nObject;
			return true;
			
		}
		return false;
	}
	
	EdGame(EdAngleSurfaceView nAngleSurfaceView,int nXSize,int nXSizePx,float nAR){
		//nAngleSurfaceView.getSurfaceHolder().
		lAngleSurfaceView=nAngleSurfaceView;
		xSizePx = nXSizePx;
		xSize = nXSize;
		
		ySize =   (int) (nXSize/nAR);
		ySizePx = (int) (nXSizePx/nAR);
		AR= nAR;
		tSizeX= xSizePx/nXSize;
		tSizeY= tSizeX;
		for (int i=0;i<12;i=i+1){
			AllObjects[i]= new EdObject();
		}
		MyGrid = new EdObject[xSize][ySize];
	}
	private void CloneEnemy(int addIdx,EdObject nObject){
		EdBaseEnemy slotObj = ((EdBaseEnemy)AllObjects[addIdx]);
		EdBaseEnemy newObj = ((EdEnemyTypeH)nObject);
		
		slotObj.clone(newObj);
		//((EdBaseEnemy)AllObjects[addIdx]).ReCreate((int)((float)Math.random()*xSizePx),0);
		
	}
	
	public void AimGuns(int x, int y){
		
		for (EdObject o:AllObjects){
			if (o instanceof EdAimingMechanics ){
			((EdAimingMechanics)o).AimAt(x, y,1);
			}}
	}
	
	public void AIAimGuns(){
		for (EdObject o:AllObjects){
			if (o instanceof EdAimingMechanics ){
				if (Math.random()>aIEffectivness ){	
					((EdAimingMechanics)o).AIAim(AllObjects);
				}
			}}
	}

	public void FireAutoMode(boolean on){
		for (EdObject o:AllObjects){
			if (o instanceof EdFireable ){
			((EdFireable)o).FireAutoMode(on);
			}
		}
	}
	public void ChargeCapacitors(float nEnergy){
		for (EdObject o:AllObjects){
			if (o instanceof EdCapacitor ){
			((EdCapacitor)o).CapEnergyIn(nEnergy);
			}}
	}
	public void DoAIStep(){
		for (EdObject o:AllObjects){
			if (o instanceof EdEnemy ){
				if(((EdEnemy)o).DoAIStep(getNearest(o))==true){// if enemy made it to earth
					Score=Score-(int)(Math.pow(((EdEnemy)o).GetLvl()+1,3));
				}
				
			}}
	}
	public void DoFireStep(){
		for (EdObject o:AllObjects){
			if (o instanceof EdFireable ){
			((EdFireable)o).Fire(null);
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
		for (EdObject o:AllObjects){
			if (o instanceof EdStructure){
				float d = (float) Math.hypot(X-o.getX(), Y-o.getY());
				if (d<mind){mind=d;out = o;}
			}
		}
		return out;
	}
	public void DoUIStep(View nView){
		TextView ScoreView = (TextView) nView.findViewById(R.id.TextView01);
		ScoreView.setText("Score: "+String.valueOf(Score));
		
		TextView BuildPtsView = (TextView) nView.findViewById(R.id.TextView02);
		BuildPtsView.setText("Build Pts: "+String.valueOf(Math.round(BuildPts)));
		
		//TextView ScoreView = (TextView) nView.findViewById(R.id.TextView03);
		//ScoreView.setText("Score: "+String.valueOf(Score));

		
	}
	
	public void DoInputStep(MotionEvent nEvent){
		for (EdObject o:AllObjects){
			if (o instanceof EdMotionEvent){
				((EdMotionEvent) o).MotionEvent(nEvent);
			}
		}
	}
	
	public void DoCollisionStep(){
		for (EdObject o:AllObjects){
			if (o instanceof EdFireable ){
				EdDestructable[] hitList= ((EdFireable)o).CheckForHit(AllObjects);
				
				if(hitList != null){
					for (EdDestructable hito:hitList){
						if(hito!=null){
							Score = (int) (Score + (bpBase*(((EdEnemy)hito).GetLvl())+1));
							BuildPts= BuildPts + (bpBase*(((EdEnemy)hito).GetLvl())+1);
							}
					}	
				}
			}}
	}
//	public void prune(){
//		for (int i=0;i<maxobjs+0;i=i+1){
//			if (AllObjects[i]!=null){
//				if (AllObjects[i].IsDead() ==true){
//					//AllObjects[i]=null;
//				}
//			}
//			
//		}
//	}
	private void compact(){
		int lIdx =0;
		int rIdx = aoidx;
		Log.v("earthdefensebeta","Before Compact: "+String.valueOf(aoidx));
		while (lIdx != rIdx){
			if (AllObjects[lIdx]==null){
				if (AllObjects[rIdx]!=null){
					AllObjects[lIdx]=AllObjects[rIdx];
					//AllObjects[rIdx]=null;
				}else{rIdx = rIdx-1;}//right right is dead	
			}else{lIdx = lIdx+1;}//left not dead
		}
		aoidx=lIdx+0;
		Log.v("earthdefensebeta","    After: "+String.valueOf(aoidx));
	}
	public EdObject WhatsAtGridPx(int x,int y){
		return WhatsAtGrid((int)(x/tSizeX),(int)(y/tSizeY));
	}
	public EdObject WhatsAtGrid(int x,int y){
		try{
			return MyGrid[x][y];}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
	}
	public void PauseMotion() {
		lAngleSurfaceView.PauseMotion();
		mPaused= true;
	}
	public void UnPauseMotion() {
		lAngleSurfaceView.UnPauseMotion();
		mPaused= false;
	}
	
	public void UpgradeAtGrid(int x,int y,int uidx){
		EdObject what = WhatsAtGrid(x, y);
		if (what!=null){
			if (BuildPts >= ((EdUpgradeable) (what)).GetUpgradeCost(uidx)){
				BuildPts=BuildPts-((EdUpgradeable) (what)).GetUpgradeCost(uidx);
				EdObject newobj =  ((EdUpgradeable) (what)).Upgrade(uidx);
				if (newobj!=null){
					if (newobj instanceof EdFireable){((EdFireable)(newobj)).FireAutoMode(true);};
					//if (newobj instanceof EdStructure){((EdStructure)(newobj)).restructure(getStructureConnections(x,y));};
					
					what.Destruct();
					AddObjectToGameSnapToGrid(newobj, (int) (x*tSizeX), (int)(y*tSizeY));
					
					localRestructure(x,y);
				}
				
			}
		}else{/// new object
			String cons =getStructureConnections(x, y);
			
			Log.v("way2fungames","-=====<"+ cons + ">=====-" );
			
			AddObjectToGameSnapToGrid(new EdStructure(cons), (int)(x*tSizeX),(int)(y*tSizeY));
			localRestructure(x,y);
			BuildPts =BuildPts-25;
		}	
	}
	private void localRestructure(int gridX,int gridY) {
		EdObject it;
		for (int dx=-1;dx<2;dx++){
			for (int dy=-1;dy<2;dy++){
				it = WhatsAtGrid(gridX+dx, gridY+dy);
				//if ((dx!=0)||(dy != 0)){
				if(it!= null){
					EdStructure st =  (EdStructure) it;
					st.restructure(getStructureConnections(gridX+dx,gridY+dy));
					st.updateSprite(lAngleSurfaceView,false);
				}
				//}
			}
			
		}
		
	}
	
	public void restructure(){
		for(EdObject obj: AllObjects){
			if  ((obj instanceof EdStructure)||(obj instanceof EdWeapon)) {
				EdStructure st =  (EdStructure) obj;
				
				int gx= (int) (st.getX()/tSizeX);
				int gy= (int) (st.getY()/tSizeY);
				
				st.restructure(getStructureConnections(gx,gy));
				st.updateSprite(lAngleSurfaceView,false);
			}
		}
	}
	public String getStructureConnections(int x,int y){
		String os="";
		if (WhatsAtGrid(x+1, y+0)!= null){os=os+"1";}
		if (WhatsAtGrid(x+0, y-1)!= null){os=os+"2";}
		if (WhatsAtGrid(x-1, y+0)!= null){os=os+"3";}
		if (WhatsAtGrid(x+0, y+1)!= null){os=os+"4";}		
		
		return os;
		
	}
	
	public int[] GetUpgradeValsGrid(int x,int y){
		EdObject what = WhatsAtGrid(x, y);
		if (what!=null){ return ((EdUpgradeable) (what)).GetUpgradeCosts();}
		return null;	
	}
	public int[] GetUpgradeLvlsGrid(int x,int y){
		EdObject what = WhatsAtGrid(x, y);
		if (what instanceof EdUpgradeable){ return ((EdUpgradeable) (what)).GetUpgradeLvls() ;}
		return null;	
	}
	public int[] GetUpgradeIconsGrid(int x, int y) {
		EdObject what = WhatsAtGrid(x, y);
		if (what instanceof EdUpgradeable){ return ((EdUpgradeable) (what)).GetUpgradeIcons() ;}
		return null;	
	}
	public String[] GetUpgrades(int x, int y) {
		EdObject what = WhatsAtGrid(x, y);
		if (what instanceof EdUpgradeable){ return ((EdUpgradeable) (what)).GetUpgradeButtons();}
		return null;	
	}

	
}
