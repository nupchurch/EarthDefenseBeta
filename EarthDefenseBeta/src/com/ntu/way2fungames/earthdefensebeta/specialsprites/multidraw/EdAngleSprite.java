package com.ntu.way2fungames.earthdefensebeta.specialsprites.multidraw;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import android.util.Log;

import com.android.angle.AngleAbstractSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
	
	public class EdAngleSprite extends AngleAbstractSprite{
	/**
	 * Sprite with rotating capabilities. Uses hardware buffers if available
	 * 
	 * @author Ivan Pajuelo
	 * 
	 */

		public float mRotation;
		protected float[][][] mTexCoordValues;
		protected int[][] mTexCordBufIdxParts= new int[1][1];
		public float[] mVertexValues;
		public int mVertBufferIndex=0;
		protected boolean isFrameInvalid;
		protected float[][][] mPartsData;
		private int mSqParts;
		private int mNumParts;
		public int exploding;
		public boolean isDead;
		private float[] mTexCoordValuesFull;
		private int mTexCordBufIdxFullFrame;
		public float mZ=0;
		private FloatBuffer[][] wrapedTexCordValues;
		/**
		 * 
		 * @param layout AngleSpriteLayout
		 */
		public EdAngleSprite(AngleSpriteLayout layout)
		{
			super(layout);
			doInit(0, 0, 1);
		}

		/**
		 * 
		 * @param x Position
		 * @param y Position
		 * @param layout AngleSpriteLayout
		 */
		public EdAngleSprite(int x, int y,int nParts, AngleSpriteLayout layout)
		{
			super(layout);
			mNumParts = nParts;
			doInit(x, y, 1);
		}
		
		/**
		 * 
		 * @param x Position
		 * @param y Position
		 * @param alpha Normalized alpha channel value
		 * @param layout AngleSpriteLayout
		 */
		public EdAngleSprite(int x, int y, float alpha, AngleSpriteLayout layout)
		{
			super(layout);
			doInit(x, y, alpha);
		}
		
		public EdAngleSprite(int x, int y, AngleSpriteLayout layout)
		{
			super(layout);
			doInit(x, y,1);
		}

		private void doInit(int x, int y, float alpha)
		{
			if (mNumParts==0){mNumParts=16;}
			mSqParts= (int) Math.sqrt(mNumParts);
			mRotation = 0;
			int TriPerSquare = 2;
			int FloatsPerTri = 6;
			int FloatsPerSquare = FloatsPerTri*TriPerSquare;
			int squares=9;
			int bufferSize = FloatsPerSquare;
			
			mTexCoordValuesFull = new float[8];
			mTexCoordValues = new float [mSqParts][mSqParts][8];
			mPartsData      = new float[mSqParts][mSqParts][4];
			mTexCordBufIdxParts= new int [mSqParts][mSqParts];
			wrapedTexCordValues = new FloatBuffer[mSqParts][mSqParts];
//			mTextureCoordBufferIndex[0] = -1;
//			mTextureCoordBufferIndex[1] = -1;
//			mTextureCoordBufferIndex[2] = -1;
//			mTextureCoordBufferIndex[3] = -1;
			mVertexValues = new float[bufferSize];
			
			mVertBufferIndex = 0;
			setLayout(roLayout);
			mPosition.set(x,y);
			mAlpha=alpha;
			isFrameInvalid=true;
			
		}

		@Override
		public void setLayout(AngleSpriteLayout layout)
		{
			super.setLayout(layout);
			setFrame(0);
		}

		@Override
		public void invalidateTexture(GL10 gl)
		{
			setFrame(roFrame);
			super.invalidateTexture(gl);
		}

		@Override
		public void setFrame(int frame)
		{
			if (roLayout != null)
			{
				if (frame < roLayout.roFrameCount)
				{
					roFrame = frame;
					float W = roLayout.roTexture.mWidth;
					float H = roLayout.roTexture.mHeight;
					if ((W>0)&(H>0)){
						ResetParts();
						roLayout.fillVertexValues(roFrame, mVertexValues);
						mVertexValues[0] = mVertexValues[0]*.25f;
						mVertexValues[1] = mVertexValues[1]*.25f;
						mVertexValues[2] = mVertexValues[2]*.25f;
						mVertexValues[3] = mVertexValues[3]*.25f;
						mVertexValues[4] = mVertexValues[4]*.25f;
						mVertexValues[5] = mVertexValues[5]*.25f;
						mVertexValues[6] = mVertexValues[6]*.25f;
						mVertexValues[7] = mVertexValues[7]*.25f;}
						isFrameInvalid=false;
					
					mTexCordBufIdxParts[0][0]=0;
					mVertBufferIndex=0;
				}
			}
		}
		
		protected void ResetParts(){
			float loWidth =roLayout.roWidth;
			float loHeight =roLayout.roHeight;
			float psizex = (loWidth/mSqParts);
			float psizey = (loHeight/mSqParts);
			
			for (int x=0;x<mSqParts;x=x+1){
				for (int y=0;y<mSqParts;y=y+1){
					float a= (float) (Math.PI*2*Math.random());
					float m = (float) (Math.random()*20);
					mPartsData[x][y][0]=(psizex*x*1)+.125f-.5f;//-(loWidth *0.3636f);
					mPartsData[x][y][1]=(psizey*y*1)+.125f-.5f;//-(loWidth *0.3636f);
					
					mPartsData[x][y][0]=(loWidth* ((x-2f)/4f))+(psizex/2);
					mPartsData[x][y][1]=(loHeight*((y-2f)/4f))+(psizey/2);

					mPartsData[x][y][2]=(float)  Math.cos(a)* m;
					mPartsData[x][y][3]=(float) -Math.sin(a)* m;
					mTexCoordValues[x][y]=SetTexCordVals(y*(1f/mSqParts), x*(1f/mSqParts), (x*(1f/mSqParts))+(1f/mSqParts), (y*(1f/mSqParts))+(1f/mSqParts));
					
					wrapedTexCordValues[x][y] = FloatBuffer.wrap(mTexCoordValues[x][y]);
					
				}
			}
			mTexCoordValuesFull= SetTexCordVals(0,0,1,1);
		}
		private float[] SetTexCordVals(float top,float left,float right, float bottom){
			float[] outvals = new float[8];
			outvals[0] = left;
			outvals[2] = right;
			outvals[4] = left;
			outvals[6] = right;
			outvals[1] = bottom;
			outvals[3] = bottom;
			outvals[5] = top;
			outvals[7] = top;
			return outvals;
		}
		
		@Override
		public void step(float elapsed){
			if (exploding >0){
				for (int x=0;x<mSqParts;x=x+1){
					for (int y=0;y<mSqParts;y=y+1){
						mPartsData[x][y][0]=mPartsData[x][y][0]+mPartsData[x][y][2];
						mPartsData[x][y][1]=mPartsData[x][y][1]+mPartsData[x][y][3];
					}
				}
				exploding=exploding-1;
				mAlpha=exploding/30f;
			}
		}
		@Override
		public void invalidateHardwareBuffers(){
		mVertBufferIndex=0;
		mTexCordBufIdxFullFrame=0;
		for (int x=0;x<mSqParts;x=x+1){for (int y=0;y<mSqParts;y=y+1){
				mTexCordBufIdxParts[x][y]=0;
			}}		
		}
		
		
		@Override
		public void invalidateHardwareBuffers(GL10 gl){
			
			
			
			releaseHardwareBuffers( gl);
			//Log.v("way2fungames",getNativeHeapAllocatedSize());
			//Log.v("way2fungames","Start EdAS invalidateHardwareBuffers");
			
			int bufneeded= 18;//(mSqParts*mSqParts)+1;
			int[] hwBuffers = new int[bufneeded];
			((GL11) gl).glGenBuffers(bufneeded, hwBuffers, 0);
			//Log.v("way2fungames","1 EdAS invalidateHardwareBuffers");
			// Allocate and fill the texture buffer.
			int i=0;
			for (int x=0;x<mSqParts;x=x+1){
				for (int y=0;y<mSqParts;y=y+1){
					if (mTexCordBufIdxParts[x][y]== 0){
						
						mTexCordBufIdxParts[x][y] = hwBuffers[i];						
						Log.v("way2fungames","A EdAS invalidateHardwareBuffers");
						((GL11) gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, mTexCordBufIdxParts[x][y]);
						Log.v("way2fungames","B EdAS invalidateHardwareBuffers");
						((GL11) gl).glBufferData(GL11.GL_ARRAY_BUFFER,32, wrapedTexCordValues[x][y], GL11.GL_STATIC_DRAW);
						Log.v("way2fungames","C EdAS invalidateHardwareBuffers");
						
					//Log.v("way2fungames","InvTexCordBuffIdx");
					}
					i = i+1;		
				}
			}
			//Log.v("way2fungames","2 EdAS invalidateHardwareBuffers");
			if (mTexCordBufIdxFullFrame == 0){
				mTexCordBufIdxFullFrame = hwBuffers[16];
				((GL11) gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, mTexCordBufIdxFullFrame);
				((GL11) gl).glBufferData(GL11.GL_ARRAY_BUFFER,8*4, FloatBuffer.wrap(mTexCoordValuesFull), GL11.GL_STATIC_DRAW);
			}
			//Log.v("way2fungames","3 EdAS invalidateHardwareBuffers");
			if (mVertBufferIndex == 0 ){
				mVertBufferIndex = hwBuffers[17];
				((GL11) gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
				((GL11) gl).glBufferData(GL11.GL_ARRAY_BUFFER, 12*4, FloatBuffer.wrap(mVertexValues), GL11.GL_STATIC_DRAW);
				((GL11) gl).glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
			}
			super.invalidateHardwareBuffers(gl);
			//Log.v("way2fungames","End EdAS invalidateHardwareBuffers");
		}

		@Override
		public void releaseHardwareBuffers(GL10 gl)
		{
			int totTexBuffs = 18;
			int[] hwBuffers = new int[totTexBuffs];
			for (int x=0;x<mSqParts;x=x+1){
				for (int y=0;y<mSqParts;y=y+1){
					//mTexCordBufIdxParts[x][y] = hwBuffers[(y*(mSqParts))+x];
					hwBuffers[(y*(mSqParts))+x]  = mTexCordBufIdxParts[x][y];
				}
			}
			hwBuffers[totTexBuffs-1] = mVertBufferIndex;
			hwBuffers[totTexBuffs-2] = mTexCordBufIdxFullFrame;
			
			if (gl!=null){((GL11) gl).glDeleteBuffers(totTexBuffs, hwBuffers, 0);}
			
			mTexCordBufIdxFullFrame =0;			
			mTexCordBufIdxParts[0][0] = 0;
			mVertBufferIndex = 0;
		}

		@Override
		public void draw(GL10 gl){
			if ((isDead==true)&(exploding==0)){return;}
			if (roLayout != null){
				if (roLayout.roTexture != null){
					if (roLayout.roTexture.mHWTextureID > -1){
						
						if (isFrameInvalid){setFrame(roFrame);}
						if ((mVertBufferIndex == 0))       {invalidateHardwareBuffers(gl);}
						if ((mTexCordBufIdxFullFrame == 0)){invalidateHardwareBuffers(gl);}
						
						
						if (IsExpoding() ==true ){
							gl.glPushMatrix();
							//gl.glLoadIdentity();
	
							gl.glTranslatef(mPosition.getmX(), mPosition.getmY(), mZ);
							if (mRotation != 0)                      {gl.glRotatef(-mRotation, 0, 0, 1);}
							if ((mScale.getmX() != 1) || (mScale.getmY() != 1)){gl.glScalef(mScale.getmX(), mScale.getmY(), 1);}
	
							gl.glBindTexture(GL10.GL_TEXTURE_2D, roLayout.roTexture.mHWTextureID);
							gl.glColor4f(mRed, mGreen, mBlue, mAlpha);
							
							
							((GL11) gl).glBindBuffer     (GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
							((GL11) gl).glVertexPointer  (2, GL10.GL_FLOAT, 0, 0);
							((GL11) gl).glBindBuffer     (GL11.GL_ELEMENT_ARRAY_BUFFER, AngleSurfaceView.roIndexBufferIndex);							
							for (int x=0;x<mSqParts-0;x=x+1){
								for (int y=0;y<mSqParts-0;y=y+1){
									if (mTexCordBufIdxParts[x][y] < 0 ){
										//invalidateHardwareBuffers(gl);
										}
									
									float ux =mPartsData[x][y][0];		
									float uy =mPartsData[x][y][1];
									gl.glPushMatrix();
									gl.glTranslatef(ux,uy, mZ);
									((GL11) gl).glBindBuffer     (GL11.GL_ARRAY_BUFFER, mTexCordBufIdxParts[x][y]);
									((GL11) gl).glTexCoordPointer(2, GL10.GL_FLOAT, 0, 0);
	
									((GL11) gl).glDrawElements   (GL10.GL_TRIANGLES , 6, GL10.GL_UNSIGNED_SHORT, 0);
									gl.glPopMatrix();
								}
							}
							
							((GL11) gl).glBindBuffer     (GL11.GL_ARRAY_BUFFER, 0);
							((GL11) gl).glBindBuffer     (GL11.GL_ELEMENT_ARRAY_BUFFER, 0);
								
							gl.glPopMatrix();
						}else{
							gl.glPushMatrix();
							
							gl.glTranslatef(mPosition.getmX(), mPosition.getmY(), mZ);
							if (mRotation != 0) {gl.glRotatef(-mRotation, 0, 0, 1);}
							gl.glBindTexture(GL10.GL_TEXTURE_2D, roLayout.roTexture.mHWTextureID);
							gl.glColor4f(mRed, mGreen, mBlue, mAlpha);
							gl.glScalef(4f, 4f, 1f);
							if ((mScale.getmX() != 1) || (mScale.getmY() != 1)){gl.glScalef(mScale.getmX(), mScale.getmY(), 1);}
							
							if (mVertBufferIndex        == 0){invalidateHardwareBuffers(gl);}
							if (mTexCordBufIdxFullFrame == 0){invalidateHardwareBuffers(gl);}
							
							((GL11) gl).glBindBuffer     (GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
							((GL11) gl).glVertexPointer  (2, GL10.GL_FLOAT, 0, 0);
							
							((GL11) gl).glBindBuffer     (GL11.GL_ARRAY_BUFFER, mTexCordBufIdxFullFrame);
							((GL11) gl).glTexCoordPointer(2, GL10.GL_FLOAT, 0, 0);
							
							((GL11) gl).glBindBuffer     (GL11.GL_ELEMENT_ARRAY_BUFFER, AngleSurfaceView.roIndexBufferIndex);
							((GL11) gl).glDrawElements   (GL10.GL_TRIANGLES , 6, GL10.GL_UNSIGNED_SHORT, 0);
							
							((GL11) gl).glBindBuffer     (GL11.GL_ARRAY_BUFFER, 0);
							((GL11) gl).glBindBuffer     (GL11.GL_ELEMENT_ARRAY_BUFFER, 0);

							gl.glPopMatrix();
						}
					}
					else
						roLayout.roTexture.linkToGL(gl);
				}
			}
			super.draw(gl);
		}

		public synchronized boolean IsGone(){
			if ((isDead==true)&(exploding==0)){
				return true;
			}else{
				return false;
			}
		}
		public synchronized boolean IsExpoding(){
			if (exploding>0){return true;}
			return false;
		}
		
		public synchronized void Explode(){
			if (exploding==0){
				isDead= true;
				exploding=15;
			}
		}

		public synchronized void SetAlive() {
			isDead= false;
			ResetParts();
			mAlpha=1;			
		}

	}

	
