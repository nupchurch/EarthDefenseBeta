package com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw;

import com.android.angle.AngleSpriteLayout;


	public class EdExplosion extends EdMultiDraw{
		protected long[] birthTime= new long[maxP];
		protected long[] deathTime= new long[maxP];
		protected static final int expLifeTime = (int) (1000f*.075f);
		
		public EdExplosion(float[] nx1, float[] ny1, AngleSpriteLayout layout) {
			super(nx1, ny1, layout);
			for (int i=0;i<maxP;i=i+1){
				birthTime[i]= System.currentTimeMillis();
				deathTime[i]= birthTime[i]+expLifeTime;
			}
		}
		
		

		@Override
		public boolean addone(float nx, float ny ){
			if (curIdx<maxP-1){
				d [curIdx]= false;
				x1[curIdx]= nx;	
				y1[curIdx]= ny;
				curIdx = curIdx + 1;
				
				birthTime[curIdx]= System.currentTimeMillis();
				deathTime[curIdx]= birthTime[curIdx]+expLifeTime;
				
				return true;
			}else{
				for (int i=0;i<maxP;i=i+1){
					if (d[i]==true){
						d [i]= false;
						x1[i]= nx;	
						y1[i]= ny;
						birthTime[i]= System.currentTimeMillis();
						deathTime[i]= birthTime[i]+expLifeTime;
						
						return true;
					}
				}
				return false;
			}
		}
		
		@Override
		public void step(float secondsElapsed){
			super.step(secondsElapsed);
			synchronized(projlock){
			long now = System.currentTimeMillis();
			for (int i = 0;i<maxP;i=i+1){
				if (d [i]==false){
					if (now>deathTime[i]){
						d[i]=true;
					}
				}
			}
			}
		}
		
		@Override
		protected void partSetup(int idx) {
			long now = System.currentTimeMillis();
			long nmbt = now-birthTime[idx];
			mAlpha=(float) ((float) 1-Math.sin((nmbt/expLifeTime)*3.14f));
			
		}
	}

