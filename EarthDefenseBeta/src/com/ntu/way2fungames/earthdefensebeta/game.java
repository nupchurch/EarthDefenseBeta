package com.ntu.way2fungames.earthdefensebeta;

import java.util.AbstractCollection;
import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.ntu.way2fungames.earthdefensebeta.interfaces.EdAimingMechanics;
import com.ntu.way2fungames.earthdefensebeta.objects.EdObject;
import com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw.EdProjectiles;
import com.ntu.way2fungames.earthdefensebeta.weapons.EdRailGun;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.res.Configuration;
public class game extends NtuActivity{
	Handler HeartBeat = new Handler(){


		private int wavetick;

		@Override
		public void handleMessage(Message m){
			if (ThisGame != null){
				//if (m.what==0){HeartBeat.sendEmptyMessageDelayed(0, (long) miliperHB);
				if (m.what==0){HeartBeat.sendEmptyMessageDelayed(0, (long) 0);
				
//				if (elapsedturns==0){
//					elapsedturns = elapsedturns+1;
//					
//					TextView ud = (TextView)   uMenu.findViewById(R.id.ValueText);
//					TextView tvCost= (TextView)uMenu.findViewById(R.id.tvCost);
//					ud.setText(String.valueOf(33));
//					tvCost.setText(String.valueOf(44));
//					uMenu.setVisibility(View.VISIBLE);		
//					uMenu.layout (100,100,100+240,100+160);
//				}
//				if (elapsedturns==30){
//					elapsedturns = elapsedturns+1;
//					uMenu.setVisibility(View.INVISIBLE);		
//					
//				}
				if (mPaused== true){

					long timepassed = java.lang.System.currentTimeMillis()- lasthbstarttime;
					lasthbstarttime=java.lang.System.currentTimeMillis();
					
					elapsedturns = elapsedturns+1;
					float dt = 		elapsedturns/((1000f/miliperHB)*60*10);
					String dttxt=String.valueOf(dt)+"00";
					if (tv3 != null){tv3.setText(String.valueOf(timepassed+":"+dttxt.substring(2,4)));}
					
					ThisGame.DoUIStep(findViewById(R.id.MainLayout));

				}
				if (mPaused==false){
					long timepassed = java.lang.System.currentTimeMillis()- lasthbstarttime;
					lasthbstarttime=java.lang.System.currentTimeMillis();
					
					elapsedturns = elapsedturns+1;
					float dt = 		elapsedturns/((1000f/miliperHB)*60*10);
					String dttxt=String.valueOf(dt)+"00";
					if (tv3 != null){tv3.setText(String.valueOf(timepassed+":"+dttxt.substring(2,4)));}
					
					
					
					//if (B5.getstate()==true){
						ThisGame.AIAimGuns();
					//}else{
//						ThisGame.AimGuns((int)AimX,(int) AimY);
					//}
					ThisGame.ChargeCapacitors(1000);
					//ThisGame.DoAIStep();
					ThisGame.DoCollisionStep();
					ThisGame.DoUIStep(findViewById(R.id.MainLayout));
					
//					switch ((int)(dt*100)){
//						case 0: ThisGame.AddObjectToGameFree(new EdEnemyTypeH(),(int)(Math.random()*480),0);break;
//						default:break;	
//					}
					float hbpersec = 1000/miliperHB;
					float hbperwavetick = hbpersec*5;
					
					wavetick = wavetick+1;
					if (wavetick>hbperwavetick){
						wavetick=0;
						WaveTick();
					}
					
				}
			}
		}}

		private void WaveTick() {
			elapsedturns = elapsedturns+1;
			float dt = 		elapsedturns/((1000f/miliperHB)*60*10);
			float tgRate = .05f;

				if (dt>.40){
					int rw = (int) Math.round(Math.random()+7f); 
					AddWave(rw);
				}else if (dt>.30){
					int rw = (int) Math.round(Math.random()+6f); 
					AddWave(rw);
					
				}else if (dt>.20){
					int rw = (int) Math.round(Math.random()+4f); 
					AddWave(rw);
					
				}else if (dt>.10){
					int rw = (int) Math.round(Math.random()+2f);
					AddWave(rw);

				}else if (dt>.00){
					int rw = (int) Math.round(Math.random());
					AddWave(rw);
				}


			}
			
		
				
	};

	private int earthX;
	private int earthY;
	
	
	private final int miliperHB = 1000/30;
	private float uiAreaTop;
	private EdGame ThisGame;
	
	long elapsedturns=0;
	private long lasthbstarttime;
	private TextView tv3;
	private boolean mPaused;
	private int selectedX;
	private int selectedY;
	private FrameLayout mainView;
	private RelativeLayout uMenu;
	private int screenHeight;
	private int screenWidth;
	private int upgradeSelected=1+2;
	private RelativeLayout selectedCircle;
	private AngleSprite FocusCircle;
	private int FCIdx;
	private float stationAreaTop;
	private int uy;
	private int ux;
	private int dx;
	private int dy;
	private String inputState="none";

	private int earthLine;
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		ThisGame.DoInputStep(event);
		if (event.getY()< uiAreaTop){
			this.GamePlayTouchEvent(event);
		}else{
			this.GameUITouchEvent(event);
		}
		return true;


	}
	private void GameUITouchEvent(MotionEvent event) {
//		B5.TouchEvent(event);
//		if (B5.PendingPress()==true){
//			B5.UsePendingPress();
//			if (mPaused== false){
//				if (B5.getstate()==true){
//					ThisGame.FireAutoMode(true);
//				}else{
//					ThisGame.FireAutoMode(false);
//				}
//			}
//			Log.v("earthdefense", "B5");
//		}
//		
	}
	private void GamePlayTouchEvent(MotionEvent event) {
		//Int gX = event.getx()/ThisGame.
		int ex = (int) event.getX();
		int ey = (int) event.getY();
		EdObject whatsthere =ThisGame.WhatsAtGridPx((int)ex,(int) ey);
		
		int nx = (int) (ex/80f);
		int ny = (int) (ey/80f);
		
		if (event.getAction()== MotionEvent.ACTION_DOWN){dx= nx;dy=ny;}
		if (event.getAction()== MotionEvent.ACTION_UP)  {ux= nx;uy=ny;}
		
		if ((ux==dx)&(uy==dy)){//press was up and down in same sqaure
			dx=-10;dy=-10;
			ux=-10;uy=-10;

			
			if (whatsthere==null)                   {//nothing there
				if (ey>stationAreaTop){
					if (inputState != "ep1"){
						selectedX =(int) (ex/80f);selectedY =(int) (ey/80f);
						SelectGrid(selectedX, selectedY);ShowUpgradeUI(selectedX,selectedY);
						PauseGame();
						inputState = "ep1";
					}
					else if (inputState == "ep1"){
						UnPause();
						inputState = "none";
					}
				}
			}else if(whatsthere instanceof EdObject){//something there
				Log.v("earthdefensebeta", inputState);
				
				
				if (inputState == "ep1"){
					inputState = "tp1";
					selectedX =(int) (ex/80f);selectedY =(int) (ey/80f);
					SelectGrid(selectedX, selectedY);
				}
				
				if (inputState == "none"){
					selectedX =(int) (ex/80f);selectedY =(int) (ey/80f);
					SelectGrid(selectedX, selectedY);
					inputState = "tp1";
					
				}
				else if (inputState == "tp1"){
					if ((selectedX == nx)&&(selectedY==ny)){
						PauseGame();
						ShowUpgradeUI(selectedX,selectedY);
						inputState = "tp2";
					}else{
						selectedX =(int) (ex/80f);selectedY =(int) (ey/80f);
						SelectGrid(selectedX, selectedY);
						inputState = "tp1";
					}

				}
				else if (inputState == "tp2"){
					if ((selectedX == nx)&&(selectedY==ny)){
						UnPause();
						inputState = "none";
					}else{
						selectedX =(int) (ex/80f);selectedY =(int) (ey/80f);
						SelectGrid(selectedX, selectedY);
						ShowUpgradeUI(selectedX,selectedY);
						inputState = "tp2";
					}
				}

				
			}else if (whatsthere !=null){
				if (mPaused== false){
					SelectGrid(nx, ny);
					PauseGame();
				}
			}
			
		}
		}
		
	private void PauseGame(){
		mPaused =true;
		if (ThisGame!=null){ThisGame.PauseMotion();}
	}
	private void SelectGrid(int x, int y){
//		ex=ex+40;
//		ey=ey+15;
		selectedX =x;
		selectedY =y;
		//mGLSurfaceView.addObject(FocusCircle);
		
		mGLSurfaceView.MoveObjectToTop(FCIdx);
		FCIdx= mGLSurfaceView.count()-1;
		FocusCircle.mAlpha=1;
		FocusCircle.mPosition.setmX((x*80)+40);		
		FocusCircle.mPosition.setmY ((y*80)+40);		
		
		
		
		
		
		
		//selectedCircle.setVisibility(View.VISIBLE);
		//selectedCircle.layout((x*80)-240+40, (y*80)-240+15,(x*80)+240+40, (y*80)+240+15);
		
	}
	
	private void ShowUpgradeUI(int x,int y) {
//		int ux = (int) (x*ThisGame.tSizeX)-120+40-20;
//		int uy = (int) (y*ThisGame.tSizeX)-160-20-50;
//
//		ux = Math.max(0, ux);
//		ux = Math.min(ux,screenWidth-240);

		LinearLayout selframes[]= new LinearLayout[3];
		
		selframes[0] = (LinearLayout) findViewById(R.id.select1highlight );
		selframes[1] = (LinearLayout) findViewById(R.id.select2highlight );
		selframes[2] = (LinearLayout) findViewById(R.id.select3highlight );
		
		selframes[0].setBackgroundColor(Color.argb(0, 0, 0, 0));
		selframes[1].setBackgroundColor(Color.argb(0, 0, 0, 0));
		selframes[2].setBackgroundColor(Color.argb(0, 0, 0, 0));
		selframes[upgradeSelected-2].setBackgroundColor(Color.argb(128, 0, 255, 0));
		
		TextView ul= (TextView)findViewById(R.id.upgradelabel);
		if (upgradeSelected-2==0){ ul.setText("Structure");}
		if (upgradeSelected-2==1){ ul.setText("Fire Speed");}
		if (upgradeSelected-2==2){ ul.setText("Mechanics");}

		
		
		UpdateUpgradeMenu();
		
		uMenu.setVisibility(View.VISIBLE);		
	}
	
	@Override
	public void onWindowAttributesChanged(WindowManager.LayoutParams nLayout){
		
		if (ThisGame == null){SetupGame();}
		
		
	}
	
	private void SetupGame() {
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		((EarthDefenseBeta) getApplication()).setDisplayMetrics(metrics);
		screenWidth =metrics.widthPixels;
		screenHeight=metrics.heightPixels;
		
		earthLine= screenHeight;
		
		float AR= (float)screenWidth/(float)screenHeight;
		earthY = screenHeight;
		
		ThisGame = new EdGame(mGLSurfaceView, (int) (screenWidth/80f),screenWidth, AR);
		CreateEdGlSurfaceView(ThisGame.AllObjects);
		ThisGame.lAngleSurfaceView= mGLSurfaceView;
		
		earthX = screenWidth/2;
		earthY = screenHeight;
		
		FocusCircle  = new AngleSprite(0, 0, new AngleSpriteLayout(mGLSurfaceView, com.ntu.way2fungames.earthdefensebeta.R.drawable.selection));
		MyRotatingEarthShadowSprite mShadowSprite      = new MyRotatingEarthShadowSprite(earthX,earthY,new AngleSpriteLayout(mGLSurfaceView, com.ntu.way2fungames.earthdefensebeta.R.drawable.earthnight_small ));
		MyRotatingEarthSprite       mEarthRotSprite    = new MyRotatingEarthSprite (earthX, earthY, new AngleSpriteLayout(mGLSurfaceView, com.ntu.way2fungames.earthdefensebeta.R.drawable.earth_small));
		MyRotatingEarthSprite       mEarthLightsSprite = new MyRotatingEarthSprite (earthX, earthY, new AngleSpriteLayout(mGLSurfaceView, com.ntu.way2fungames.earthdefensebeta.R.drawable.earthlights_small));
		FocusCircle.mScale.setmX(480f/256f);
		FocusCircle.mScale.setmY(480f/256f);
		//B5 =  new EdUIButtonToggle(com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_button_pf_auto , com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_button_pf_aim, com.ntu.way2fungames.earthdefensebeta.R.drawable.ed_button);

		mShadowSprite.mPosition.set(earthX, earthY);
		
		mGLSurfaceView.addObject(mEarthRotSprite);
		mGLSurfaceView.addObject(mShadowSprite);
		mGLSurfaceView.addObject(mEarthLightsSprite);
		
		mGLSurfaceView.addObject(FocusCircle);
		FocusCircle.mAlpha=1;
		FCIdx = 3;//mGLSurfaceView.count();
		
		uiAreaTop = screenHeight-ThisGame.tSizeY;
		stationAreaTop= screenHeight-(ThisGame.tSizeY*4);
		//B5.AddToAngle(mGLSurfaceView,(int)(screenWidth-32),(int) (uiAreaTop+32));
		
		HeartBeat.sendEmptyMessage(0);
		
		
	}
	@Override
	public void onStop(){
		super.onStop();
		//mGLSurfaceView.delete();
		//mGLSurfaceView= null;
		//mGLSurfaceView.VISIBLE
		
	}
	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
//		Debug.startMethodTracing("ed.log");

		mainView = (FrameLayout)View.inflate(this, R.layout.main, null);
		setContentView(mainView);
		FrameLayout al = (FrameLayout) findViewById(R.id.GameLayout);
		tv3 = (TextView) findViewById(R.id.TextView03 );
		al.addView(mGLSurfaceView);
		View.inflate(this, R.layout.upgrade  , mainView);
		uMenu =(RelativeLayout) mainView.getChildAt(mainView.getChildCount()-1);
			
		//LayoutInflater.from(this).
		selectedCircle = (RelativeLayout) findViewById(R.id.focuscircle);
		//mainView.addView(uMenu);
		SetupUpgradeMenu();
		
		int[][][] a = EdWaves.waves;
		uMenu.setVisibility(View.INVISIBLE);
		
	}
	private void SetupUpgradeMenu() {
//		uMenu.setVisibility(View.VISIBLE);
//		uMenu.layout (1,1,240,160);
//		uMenu.setVisibility(View.INVISIBLE);

		ImageButton ibStructure = (ImageButton) findViewById(R.id.OptionOneButton);
		ImageButton ibCap= (ImageButton) findViewById(R.id.OptionTwoButton);
		ImageButton ibGun= (ImageButton) findViewById(R.id.OptionThreeButton);
		Button buSub = (Button) findViewById(R.id.SubButton);
		Button buAdd = (Button) findViewById(R.id.AddButton);
		int selected;
		
		ibStructure.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ProccessUpgrade(2);
			}});
		ibCap.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ProccessUpgrade(3);
			}});
		ibGun.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ProccessUpgrade(4);
			}});
		buSub.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ProccessUpgrade(0);
			}});
		buAdd.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				ProccessUpgrade(1);
			}});
		
	}
	
	protected void ProccessUpgrade(int i) {
		//FrameLayout selFrame = (FrameLayout) findViewById(R.id.SelectFrame);
		
		if(i>=2){
			upgradeSelected= i;
			int x = (i-2)*80;
				//selFrame.layout(x, 80, x+80, 160);
				
				
		}
		
		if (i==1){
			ThisGame.UpgradeAtGrid(selectedX, selectedY, upgradeSelected-2);
		
		}
		
		LinearLayout selframes[]= new LinearLayout[3];
		
		selframes[0] = (LinearLayout) findViewById(R.id.select1highlight );
		selframes[1] = (LinearLayout) findViewById(R.id.select2highlight );
		selframes[2] = (LinearLayout) findViewById(R.id.select3highlight );
		
		selframes[0].setBackgroundColor(Color.argb(0, 0, 0, 0));
		selframes[1].setBackgroundColor(Color.argb(0, 0, 0, 0));
		selframes[2].setBackgroundColor(Color.argb(0, 0, 0, 0));
		selframes[upgradeSelected-2].setBackgroundColor(Color.argb(128, 0, 255, 0));
		
		
		UpdateUpgradeMenu();
	inputState = "tp2";

	}
	private void UpdateUpgradeMenu(){
		TextView ud = (TextView)   uMenu.findViewById(R.id.ValueText);
		int cost=0;
		int lvl=0;
		
		String upgrades[] = ThisGame.GetUpgrades(selectedX,selectedY);
		int icons[] = ThisGame.GetUpgradeIconsGrid(selectedX,selectedY);
		int costs[] = ThisGame.GetUpgradeValsGrid (selectedX, selectedY);
		int lvls[]  = ThisGame.GetUpgradeLvlsGrid (selectedX, selectedY);
		if (upgrades==null){
			icons = new int[3];
			icons[0]= R.drawable.ed_st_n_1234;icons[1]=icons[0];icons[2]=icons[0];
			
			upgrades = new String[3];
			upgrades[0]="Build Structure";upgrades[1]="Build Structure";upgrades[2]="Build Structure";
			
			lvls = new int[3];
			lvls[0]= -1;lvls[1]=0;lvls[2]=0;
			
			cost=25;
			lvl=0;
		}else{
			cost =costs[upgradeSelected-2];
			lvl = lvls [upgradeSelected-2];
		}
		ImageButton but1 = (ImageButton)uMenu.findViewById(R.id.OptionOneButton);
		ImageButton but2 = (ImageButton)uMenu.findViewById(R.id.OptionTwoButton);
		ImageButton but3 = (ImageButton)uMenu.findViewById(R.id.OptionThreeButton);
		but1.setBackgroundResource(icons[0]);
		but2.setBackgroundResource(icons[1]);
		but3.setBackgroundResource(icons[2]);
		
		TextView ul= (TextView)findViewById(R.id.upgradelabel);
		if ((upgradeSelected-2)==0){ ul.setText(upgrades[0]);}
		if ((upgradeSelected-2)==1){ ul.setText(upgrades[1]);}
		if ((upgradeSelected-2)==2){ ul.setText(upgrades[2]);}

		if (lvls[0]==-1){
			uMenu.findViewById(R.id.CurrentLvlLayout).setVisibility(View.GONE);
			uMenu.findViewById(R.id.SubButton).setVisibility(View.GONE);
			String bs = "Build for "+String.valueOf(cost)+" Build Pts";
			((Button)uMenu.findViewById(R.id.AddButton)).setText(bs);
		} else{
			uMenu.findViewById(R.id.CurrentLvlLayout).setVisibility(View.VISIBLE);
			uMenu.findViewById(R.id.SubButton).setVisibility(View.VISIBLE);
			Button b3 = (Button) uMenu.findViewById(R.id.AddButton);
			b3.setText(String.valueOf(cost));
			ud.setText(String.valueOf(lvl));
		}
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		if (mPaused==false){
			SelectGrid(selectedX,selectedY);
			PauseGame();
		}else{
			UnPause();
		}
		return true;
	}
	@Override
	public void onBackPressed(){
		
		if (mPaused== true){
			UnPause();		
		}else{
			ThisGame= null;
			mGLSurfaceView.removeAll();
			mGLSurfaceView.stop();
			java.lang.System.gc();
			super.onBackPressed();
			this.finish();
		}
	}
	private void UnPause(){
		FocusCircle.mAlpha=0;
		//RelativeLayout fc = (RelativeLayout) findViewById(R.id.focuscircle);
		//if (fc!=null){fc.setVisibility(View.INVISIBLE);}
		uMenu.setVisibility(View.INVISIBLE);
		mPaused =false;
		ThisGame.UnPauseMotion();

	}
	private class MyRotatingEarthSprite extends AngleRotatingSprite
	{
		private static final float sRotationSpeed = 20;
		private static final float sAlphaSpeed = 0.5f;
		private float mAplhaDir;

		public MyRotatingEarthSprite(int x, int y, AngleSpriteLayout layout)
		{
			super(x, y, layout);
			mAplhaDir=sAlphaSpeed;
			
		}

		@Override
		public void step(float secondsElapsed)
		{
			mRotation+=secondsElapsed*sRotationSpeed;
			if (mAlpha<0)
			{
				mAlpha=0;
				mAplhaDir=sAlphaSpeed;
			}
			super.step(secondsElapsed);
		}
		
	}
	
	private void AddWave(int waveIdx){
	
		int [][] type = EdWaves.GetWaveTypeData(waveIdx);
		int [][] direction= EdWaves.GetWaveDirectionData(waveIdx);
		int [][] level = EdWaves.GetWaveLeveleData(waveIdx);
		
		for (int x=0;x<6;x=x+1){for (int y=0;y<6;y=y+1){
			EdEnemyTypeH tmp = null;
			if(type[y][x]>0){
				int ulvl =level[y][x]+2;
//				if (direction[y][x]==0){tmp= new EdEnemyTypeH(ulvl-2,0,(1f/ulvl)*07.5f);}
//				if (direction[y][x]==1){tmp= new EdEnemyTypeH(ulvl-2,(1f/ulvl)*10f,(1f/ulvl)*05f);}
//				if (direction[y][x]==2){tmp= new EdEnemyTypeH(ulvl-2,(1f/ulvl)*-10f,(1f/ulvl)*05f);}
				
				if (direction[y][x]==0){tmp= new EdEnemyTypeH(ulvl-2,0               ,05f,earthLine);}
				if (direction[y][x]==1){tmp= new EdEnemyTypeH(ulvl-2  ,(1f/ulvl)*10f ,05f,earthLine);}
				if (direction[y][x]==2){tmp= new EdEnemyTypeH(ulvl-2  ,(1f/ulvl)*-10f,05f,earthLine);}
				ThisGame.AddObjectToGameFree(tmp,(int)(x*ThisGame.tSizeX+(ThisGame.tSizeX/2)),(int)((y*ThisGame.tSizeY)-(ThisGame.tSizeY*6)));}
			}
		}}
		
	
	
	private class MyRotatingEarthShadowSprite extends AngleRotatingSprite
	{
		private static final float sRotationSpeed = 05;
		private static final float sAlphaSpeed = 0.1f;
		private float mAplhaDir;

		public MyRotatingEarthShadowSprite(int x, int y, AngleSpriteLayout layout)
		{
			super(x, y, layout);
			mAplhaDir=sAlphaSpeed;
			
		}

		@Override
		public void step(float secondsElapsed)
		{
			mRotation+=secondsElapsed*sRotationSpeed;
			if (mAlpha<0)
			{
				mAlpha=0;
				mAplhaDir=sAlphaSpeed;
			}
			super.step(secondsElapsed);
		}
		
	}
	
	

	@Override
	public void onPause(){
		PauseGame();
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		UnPause();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		  // ignore orientation/keyboard change
		  super.onConfigurationChanged(newConfig);
		}
}
