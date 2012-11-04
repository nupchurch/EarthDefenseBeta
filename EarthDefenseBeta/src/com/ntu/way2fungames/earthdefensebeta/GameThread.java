//package com.ntu.way2fungames.earthdefensebeta;
//
//public class GameThread extends Thread{
//	private boolean mPaused;
//
//	@Override
//	public void run(){
//		if (mPaused == true){
//			ThisGame.DoUIStep(findViewById(R.id.MainLayout));
//		}
//		
//		if (mPaused==false){
//			long timepassed = java.lang.System.currentTimeMillis()- lasthbstarttime;
//			lasthbstarttime=java.lang.System.currentTimeMillis();
//			
//			elapsedturns = elapsedturns+1;
//			float dt = 		elapsedturns/((1000f/miliperHB)*60*10);
//			String dttxt=String.valueOf(dt)+"00";
//			if (tv3 != null){tv3.setText(String.valueOf(timepassed+":"+dttxt.substring(2,4)));}
//			
//			
//			
//			//if (B5.getstate()==true){
//				ThisGame.AIAimGuns();
//			//}else{
////				ThisGame.AimGuns((int)AimX,(int) AimY);
//			//}
//			ThisGame.ChargeCapacitors(1000);
//			//ThisGame.DoAIStep();
//			ThisGame.DoCollisionStep();
//			ThisGame.DoUIStep(findViewById(R.id.MainLayout));
//			
////			switch ((int)(dt*100)){
////				case 0: ThisGame.AddObjectToGameFree(new EdEnemyTypeH(),(int)(Math.random()*480),0);break;
////				default:break;	
////			}
//			float hbpersec = 1000/miliperHB;
//			float hbperwavetick = hbpersec*5;
//			
//			wavetick = wavetick+1;
//			if (wavetick>hbperwavetick){
//				wavetick=0;
//				WaveTick();
//			}
//			
//		}
//	}
//}
